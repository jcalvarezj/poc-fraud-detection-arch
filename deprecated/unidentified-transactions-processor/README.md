# unidentified-transactions-processor

Kafka Streams worker that listens to the "unidentified-transactions" topic, identifies Transaction and User data, and emits them respectively into the "fraudulent-transactions-result" and "users" topics

## Requirements

To run this worker a Kafka cluster must be running in the same network with the expected topics, and it should be accessible on port 9092 for external connections and 29092 for internal Docker connections

## Execution

### JAR only

`mvn clean package -DskipTests`
`java -jar target/kafka-streams-app-1.0-SNAPSHOT.jar`

### Docker container

`docker build -t unidentified-transactions-processor .`
`docker run unidentified-transactions-processor`

### Docker-compose

At the parent project's root, run `docker-compose up unidentified-transactions-processor`
