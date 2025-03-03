import os

from models import Transaction

from avro.schema import Parse
from confluent_kafka import Consumer, Producer, KafkaException, KafkaError
from confluent_kafka.schema_registry.avro import AvroSerializer

conf = {
    'bootstrap.servers': os.getenv("KAFKA_BOOTSTRAP"),
    'group.id': 'transactions-ingestor',
    'auto.offset.reset': 'earliest'
}
sr_conf = {
    "url": "http://localhost:8081",
    "auto.register.schemas": False
}

topic = 'bank-transactions'
consumer = Consumer(conf)
consumer.subscribe([topic])


def _delivery_report(error, message):
    if error is not None:
        print(f'Error sending message: {error}')
    else:
        print(f'Message sent to topic {message.topic()}')

def _load_avro_schema(file_path):
    with open(file_path, 'r') as schema_file:
        schema_str = schema_file.read()
        return Parse(schema_str)

def _consume_message(raw_message):
    message = raw_message.value().decode('utf-8')
    transaction_data = Transaction.model_validate_json(message)

    producer = Producer(conf, default_value_schema=_load_avro_schema("avro/transaction.avsc"))

    serializer = AvroSerializer(
        schema_registry_client=schema_registry_client,
        schema_str=response.schema.schema_str,
        conf=serializer_conf,
    )

    if transaction_data.evaluation == "fraudulent":
        producer.produce('fraudulent-transactions', value=transaction_data, callback=_delivery_report)
        producer.flush()

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
            print(f"Error al consumir mensaje: {e}")

    consumer.close()
