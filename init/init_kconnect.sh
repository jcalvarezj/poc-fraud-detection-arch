#!/bin/bash

TXNS_TOPIC=fraudulent-transactions-result
USERS_TOPIC=users
TXNS_TABLE_NAME=transactions
USERS_TABLE_NAME=users

S3_BUCKET=test0001-fraudulent-transactions-raw
TOP_FOLDER=fraudulent-transactions
TXNS_S3_FOLDER=fraudulent-transactions/transactions
USERS_S3_FOLDER=fraudulent-transactions/users

FLUSH_EVERY=30

KCONNECT_HOST="http://localhost:8083/connectors"
SCHEMA_REGISTRY_URL="http://schema-registry:8081"

TXNS_MONGO_PAYLOAD=$(cat <<EOF
{
    "name": "transactions-mongo-sink-connector",
    "config": {
        "connector.class": "com.mongodb.kafka.connect.MongoSinkConnector",
        "tasks.max": "1",
        "topics": "${TXNS_TOPIC}",
        "connection.uri": "mongodb+srv://${MY_MONGODB_USER}:${MY_MONGODB_PASS}@${MY_MONGODB_CLUSTER}.mongodb.net/",
        "database": "fraudulent_transactions_db",
        "collection": "${TXNS_TABLE_NAME}",
        "insert.mode": "insert",
        "batch.size": "1000",
        "max.num.retries": "3",
        "retry.delay.ms": "5000",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "${SCHEMA_REGISTRY_URL}",
        "value.converter.schema.registry.url": "${SCHEMA_REGISTRY_URL}",
        "value.converter.schemas.enable": "true"
    }
}
EOF
)

USERS_MONGO_PAYLOAD=$(cat <<EOF
{
    "name": "users-mongo-sink-connector",
    "config": {
        "connector.class": "com.mongodb.kafka.connect.MongoSinkConnector",
        "tasks.max": "1",
        "topics": "${USERS_TOPIC}",
        "connection.uri": "mongodb+srv://${MY_MONGODB_USER}:${MY_MONGODB_PASS}@${MY_MONGODB_CLUSTER}.mongodb.net/",
        "database": "fraudulent_transactions_db",
        "collection": "${USERS_TABLE_NAME}",
        "insert.mode": "insert",
        "batch.size": "1000",
        "max.num.retries": "3",
        "retry.delay.ms": "5000",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "${SCHEMA_REGISTRY_URL}",
        "value.converter.schema.registry.url": "${SCHEMA_REGISTRY_URL}",
        "value.converter.schemas.enable": "true"
    }
}
EOF
)

TXNS_S3_PAYLOAD=$(cat <<EOF
{
    "name": "transactions-s3-sink-connector",
    "config": {
        "connector.class": "io.confluent.connect.s3.S3SinkConnector",
        "tasks.max": "1",
        "topics": "${TXNS_TOPIC}",
        "topics.dir": "${TOP_FOLDER}",
        "s3.bucket.name": "${S3_BUCKET}",
        "s3.region": "${MY_AWS_REGION}",
        "storage.class": "io.confluent.connect.s3.storage.S3Storage",
        "format.class": "io.confluent.connect.s3.format.avro.AvroFormat",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "${SCHEMA_REGISTRY_URL}",
        "value.converter.schema.registry.url": "${SCHEMA_REGISTRY_URL}",
        "aws.access.key.id": "${MY_AWS_ACCESS_KEY_ID}",
        "aws.secret.access.key": "${MY_AWS_SECRET_ACCESS_KEY}",
        "flush.size": "${FLUSH_EVERY}"
    }
}
EOF
)

USERS_S3_PAYLOAD=$(cat <<EOF
{
    "name": "users-s3-sink-connector",
    "config": {
        "connector.class": "io.confluent.connect.s3.S3SinkConnector",
        "tasks.max": "1",
        "topics": "${USERS_TOPIC}",
        "topics.dir": "${TOP_FOLDER}",
        "s3.bucket.name": "${S3_BUCKET}",
        "s3.region": "${MY_AWS_REGION}",
        "storage.class": "io.confluent.connect.s3.storage.S3Storage",
        "format.class": "io.confluent.connect.s3.format.avro.AvroFormat",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "${SCHEMA_REGISTRY_URL}",
        "value.converter.schema.registry.url": "${SCHEMA_REGISTRY_URL}",
        "aws.access.key.id": "${MY_AWS_ACCESS_KEY_ID}",
        "aws.secret.access.key": "${MY_AWS_SECRET_ACCESS_KEY}",
        "flush.size": "${FLUSH_EVERY}"
    }
}
EOF
)

KCONNECT_PAYLOADS=("$TXNS_MONGO_PAYLOAD"
"$USERS_MONGO_PAYLOAD"
"$TXNS_S3_PAYLOAD"
"$USERS_S3_PAYLOAD")

echo "Creating connectors..."

for payload in "${KCONNECT_PAYLOADS[@]}"
do
    curl -X POST "${KCONNECT_HOST}" -H "Content-Type: application/json" -d $payload > /dev/null
    echo
done
