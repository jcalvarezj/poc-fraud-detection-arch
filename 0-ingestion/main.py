import os
import concurrent.futures

from confluent_kafka import Consumer, KafkaException, KafkaError


conf = {
    'bootstrap.servers': os.getenv("KAFKA_BOOTSTRAP"),
    'group.id': 'transactions-ingestor',
    'auto.offset.reset': 'earliest'
}

topic = 'bank-transactions'
consumer = Consumer(conf)
consumer.subscribe([topic])


def consume_message(message):
    print(f"Message: {message.value().decode('utf-8')}")

def consume_messages():
    while True:
        try:
            msg = consumer.poll(timeout=1.0)

            if msg is None:
                continue
            if msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    continue
                else:
                    raise KafkaException(msg.error())

            consume_message(msg)
        except KeyboardInterrupt:
            break
        except Exception as e:
            print(f"Error al consumir mensaje: {e}")

    consumer.close()


if __name__ == "__main__":
    with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
        future = executor.submit(consume_messages)
        future.result()
