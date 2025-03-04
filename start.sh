#!/bin/bash
# Provisional startup of infrastructure (requires already downloaded
# and cached images on a first run of `docker-compose up`)
docker-compose up schema-registry -d
echo "Waiting a bit for Kafka to start..."
sleep 35
echo "Initializing Kafka topics and schemas"
python init_kafka.py
docker-compose up ksqldb -d
echo "Waiting a bit for KSQLDB to start..."
sleep 35
echo "Initializing KSQLDB Streams"
source init_ksql.sh
docker-compose up --build
