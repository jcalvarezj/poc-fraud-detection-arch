#!/bin/bash

KSQL_QUERIES=("CREATE STREAM fraudulent_transactions_stream
    (transaction_id STRING,
     sender_bank_account STRING,
     sender_details STRUCT<id STRING, name STRING, address STRING, email STRING, birthdate STRING, phone_number STRING, job STRING, company STRING, ssn STRING, blood_group STRING, website STRING, username STRING, bank_account STRING>,
     receiver_bank_account STRING,
     receiver_details STRUCT<id STRING, name STRING, address STRING, email STRING, birthdate STRING, phone_number STRING, job STRING, company STRING, ssn STRING, blood_group STRING, website STRING, username STRING, bank_account STRING>,
     amount DOUBLE,
     status STRING,
     evaluation STRING,
     transfer_date STRING,
     sender_bank STRING)
WITH (KAFKA_TOPIC='fraudulent-transactions', VALUE_FORMAT='AVRO', PARTITIONS=1);"
"CREATE STREAM fraudulent_transaction_results_stream
WITH (KAFKA_TOPIC='fraudulent-transactions-result', VALUE_FORMAT='AVRO', PARTITIONS=1) AS
    SELECT
        transaction_id,
        sender_bank_account,
        sender_details->id AS sender_id,
        receiver_bank_account,
        receiver_details->id AS receiver_id,
        amount,
        status,
        evaluation,
        transfer_date,
        sender_bank
    FROM fraudulent_transactions_stream
    EMIT CHANGES;"
"CREATE STREAM sender_users_stream
WITH (KAFKA_TOPIC='user', VALUE_FORMAT='AVRO', PARTITIONS=1) AS
    SELECT
        sender_details->id,
        sender_details->name,
        sender_details->address,
        sender_details->email,
        sender_details->birthdate,
        sender_details->phone_number,
        sender_details->job,
        sender_details->company,
        sender_details->ssn,
        sender_details->blood_group,
        sender_details->website,
        sender_details->username,
        sender_details->bank_account
    FROM fraudulent_transactions_stream
    EMIT CHANGES;"
"CREATE STREAM receiver_users_stream
WITH (KAFKA_TOPIC='user', VALUE_FORMAT='AVRO', PARTITIONS=1) AS
    SELECT
        receiver_details->id,
        receiver_details->name,
        receiver_details->address,
        receiver_details->email,
        receiver_details->birthdate,
        receiver_details->phone_number,
        receiver_details->job,
        receiver_details->company,
        receiver_details->ssn,
        receiver_details->blood_group,
        receiver_details->website,
        receiver_details->username,
        receiver_details->bank_account
    FROM fraudulent_transactions_stream
    EMIT CHANGES;"
)

for query in "${KSQL_QUERIES[@]}"
do
    docker-compose exec -T ksqldb ksql http://localhost:8088 <<EOF
    $query
EOF
done
