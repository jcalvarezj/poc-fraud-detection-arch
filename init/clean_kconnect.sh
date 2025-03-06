echo "Removing the connectors from Kafka Connect..."

curl -X DELETE http://localhost:8083/connectors/transactions-mongo-sink-connector
curl -X DELETE http://localhost:8083/connectors/users-mongo-sink-connector
curl -X DELETE http://localhost:8083/connectors/transactions-s3-sink-connector
curl -X DELETE http://localhost:8083/connectors/users-s3-sink-connector

echo "Done"
