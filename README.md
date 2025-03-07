# poc-fraud-detection-arch

This repo. consists on a Proof of Concept of a processing architecture that uses Kafka-connected services to perform real-time processing of incoming streams of data related to bank transactions

It relies on two services provided by [sgg10's Docker Hub images](https://hub.docker.com/u/sgg10):
- **multi-bank-system**: Constantly produces transaction messages into Kafka topic bank-transactions
- **fraud-validator-api**: Web service that exposes an API to check whether an input transaction is fraudulent or not (documentation at http://localhost:8000/docs)

There is an ingestion service called **transactions-ingestor**, which is in charge of sending transaction data to either the "fraudulent-transactions" or "unidentified-transactions" topic, according to the evaluation field in the transactions that determines whether or not transactions have been identified as fraudulent

A KSQLDB service is connected to the infrastructure to automatically determine transaction and user data from transactions that have been identified as fraudulent from start (from the "fraudulent-transactions" topic), and sends them respectively to the "fraudulent-transactions-result" and "users" topics

A Python Quix Streams service, **unidentified-transactions-processor**, is also connected in order to process unidentified transactions ("unidentified-transactions" topic) to call the **fraud-validator-api** service and send fraudulent transaction data respectively to the "fraudulent-transactions-result" and "users" topics, while discarding non-fraudulent transactions

Finally, Kafka Connect connectors have been configured to dump the resulting output data from the "fraudulent-transactions-result" and "users" topics:
- An S3 Sink Connector to send the data to an S3 bucket into folders per topic
- A MongoDB Connector to send the data to a MongoDB database into tables per topic


## Configuration

A Unix/Linux system with bash, Python, Docker, and Docker Compose support is required

The following environment variables are required for the execution of this processing architecture:

- MY_MONGODB_USER: User credentials for MongoDB
- MY_MONGODB_PASS: Password credentials for MongoDB
- MY_MONGODB_CLUSTER: Name of the MongoDB Atlas cluster (e.g. "clusterX.xxxxx")
- MY_AWS_ACCESS_KEY_ID: Access key id credentials for AWS with S3 permissions
- MY_AWS_SECRET_ACCESS_KEY: Secret access key credentials for AWS with S3 permissions
- MY_AWS_REGION: Region credentials for AWS

## Execution

For the first use please run with the `--build` flag, so that the images are created beforehand

`source start.sh --build`

Next uses can be started with

`source start.sh`

## Monitoring

- To check the logs of a service, run
```shell
docker-compose logs <service-name> -f
```
- To check on a specific Kafka topic, run
```shell
docker exec -it kafka /bin/bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic <topic-name> --from-beginning
```
- To query KSQLDB Streams, use
```shell
docker exec -it ksqldb-server ksql http://ksqldb-server:8088
SELECT * FROM <STREAM_NAME> [EMIT CHANGES];
```
- To check a Kafka Connect connector's status:
```shell
curl -X GET http://localhost:8083/connectors/<connector-name>/status
```

## Querying

After the data has been successfully uploaded into MongoDB or S3, you can perform queries on either system (for example using MongoDB Compass, Amazon Athena, custom scripts, CLI, etc.)

Note that in order to properly query on Amazon Athena, you need to populate the Data Catalog with a Crawler first

An example of a query in MongoDB to join transaction and user data:

```js
db.transactions.aggregate([
  {
    $lookup: {
      from: "users",
      localField: "RECEIVER_ID",
      foreignField: "ID",
      as: "receiver_info"
    }
  },
  {
    $lookup: {
      from: "users",
      localField: "SENDER_ID",
      foreignField: "ID",
      as: "sender_info"
    }
  },
  {
    $unwind: "$receiver_info"
  },
  {
    $unwind: "$sender_info"
  },
  {
    $project: {
      TRANSACTION_ID: 1,
      SENDER_BANK_ACCOUNT: 1,
      RECEIVER_BANK_ACCOUNT: 1,
      AMOUNT: 1,
      STATUS: 1,
      EVALUATION: 1,
      TRANSFER_DATE: 1,
      SENDER_BANK: 1,
      SOURCE_PROCESS: 1,
      SENDER_NAME: "$sender_info.NAME",
      SENDER_ADDRESS: "$sender_info.ADDRESS",
      SENDER_EMAIL: "$sender_info.EMAIL",
      RECEIVER_NAME: "$receiver_info.NAME",
      RECEIVER_ADDRESS: "$receiver_info.ADDRESS",
      RECEIVER_EMAIL: "$receiver_info.EMAIL"
    }
  }
])
```

Or, for instance, the same query in Amazon Athena:

```SQL
SELECT
    rft.transaction_id,
    rft.sender_bank_account,
    rft.receiver_bank_account,
    rft.amount,
    rft.status,
    rft.evaluation,
    rft.transfer_date,
    rft.sender_bank,
    rft.source_process,
    sender.name AS sender_name,
    sender.address AS sender_address,
    sender.email AS sender_email,
    receiver.name AS receiver_name,
    receiver.address AS receiver_address,
    receiver.email AS receiver_email
FROM
    raw_fraudulent_transactions_result rft
LEFT JOIN raw_users sender
    ON rft.sender_id = sender.id
LEFT JOIN raw_users receiver
    ON rft.receiver_id = receiver.id;
```
