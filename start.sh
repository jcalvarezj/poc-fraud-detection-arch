#!/bin/bash
# Provisional startup of infrastructure. Include flag --build
# to build container images (recommended for first use)
# It will create a 'venv' virtual environment if it doesn't exist
# If it fails use 'docker-compose down' and try again

if [ ! -d "venv" ]; then
    echo "Virtual environment not found. Creating and installing dependencies..."
    python3 -m venv venv
    source venv/bin/activate
    pip install -r requirements.txt
else
    echo "Activating found virtual environment..."
    source venv/bin/activate
fi

if [[ $1 == "--build" ]]; then
    echo "Building docker-compose services..."
    docker-compose build
fi

docker-compose up schema-registry -d
echo "Waiting a bit for Kafka to start..."
sleep 40
echo "Initializing Kafka topics and schemas"
python init_kafka.py
docker-compose up ksqldb -d
echo "Waiting a bit for KSQLDB to start..."
sleep 40
echo "Initializing KSQLDB Streams"
source init_ksql.sh
echo "Starting the rest of the infrastructure (detached mode)..."
docker-compose up -d
deactivate
