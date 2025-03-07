#!/bin/bash
# Startup script that runs the infrastructure. Include flag --build
# to build container images (recommended for first use)
# It will create a 'venv' virtual environment if it doesn't exist
# If it fails use 'docker-compose down' and try again (remember to
# deactivate the virtual environment in case of error exit)

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

start_service_and_wait() {
  service_name=$1
  wait_time=$2

  echo "Starting $service_name..."
  docker-compose up $service_name -d

  echo "Waiting for $service_name to start..."
  sleep $wait_time
}

start_service_and_wait "schema-registry" 50

echo "Initializing Kafka topics and schemas"
python init/init_kafka.py

start_service_and_wait "ksqldb" 40

echo "Initializing KSQLDB Streams"
source init/init_ksql.sh

start_service_and_wait "kafka-connect" 70

echo "Initializing Kafka Connect connectors"
source init/init_kconnect.sh

echo "Starting the rest of the infrastructure (detached mode)..."
docker-compose up -d

deactivate
