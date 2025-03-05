import os
from uuid import uuid4

from models import Transaction

from confluent_kafka.serialization import SerializationContext
from confluent_kafka.schema_registry.avro import AvroSerializer
from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka import Consumer, SerializingProducer, KafkaException, KafkaError

consumer_conf = {
    'bootstrap.servers': os.getenv("KAFKA_BOOTSTRAP_SERVER"),
    'group.id': 'transactions-ingestor',
    'auto.offset.reset': 'earliest'
}
producer_conf = {
    'bootstrap.servers': os.getenv("KAFKA_BOOTSTRAP_SERVER"),
    "transactional.id": str(uuid4())
}
sr_conf = {
    "url": os.getenv("SCHEMA_REGISTRY_URL")
}
serializer_conf = {
    "auto.register.schemas": False
}

TRANSACTIONS_SCHEMA = "fraudulent-transactions-value"
TOPIC = 'bank-transactions'
consumer = Consumer(consumer_conf)
consumer.subscribe([TOPIC])
producer = SerializingProducer(producer_conf)
producer.init_transactions()
schema_registry_client = SchemaRegistryClient(sr_conf)
serializer = None

def _consume_message(raw_message):
    global serializer

    message = raw_message.value().decode('utf-8')
    transaction_data = Transaction.model_validate_json(message)

    print("Received transaction: ", transaction_data)
    print(sr_conf)

    schema_response = schema_registry_client.get_latest_version(TRANSACTIONS_SCHEMA)
    context_topic = TRANSACTIONS_SCHEMA.split("-value")[0]

    print("Obtained schema information")

    if not serializer:
        serializer = AvroSerializer(
            schema_registry_client=schema_registry_client,
            schema_str=schema_response.schema.schema_str,
            conf=serializer_conf
        )

    print("Created serializer")

    context = SerializationContext(topic=context_topic, field="value")
    serialized_message = serializer(transaction_data.model_dump(), context)

    try:
        eval = transaction_data.evaluation
        if eval == "fraudulent" or eval == "unidentified":
            producer.begin_transaction()
            producer.produce(
                topic=f'{eval}-transactions',
                value=serialized_message
            )
            producer.commit_transaction()

            print(f"Sent transaction to topic {eval}-transactions")
    except Exception as e:
        print(f"An error has occurred when sending data to Kafka: {e}")
        producer.abort_transaction()
        raise e

def consume_messages():
    while True:
        try:
            kafka_message = consumer.poll(timeout=1.0)

            if kafka_message is None:
                continue
            if kafka_message.error():
                if kafka_message.error().code() == KafkaError._PARTITION_EOF:
                    continue
                else:
                    raise KafkaException(kafka_message.error())

            _consume_message(kafka_message)
        except KeyboardInterrupt:
            break
        except Exception as e:
            print(f"Error consuming the message: {e}")

    consumer.close()
