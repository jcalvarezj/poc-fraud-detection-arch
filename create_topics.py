from confluent_kafka.admin import AdminClient, NewTopic
from confluent_kafka import KafkaException


conf = {
    'bootstrap.servers': 'localhost:9092'
}

admin_client = AdminClient(conf)


def delete_topic(topic_name: str):
    try:
        fs = admin_client.delete_topics([topic_name], operation_timeout=30)
        fs[topic_name].result()
        print(f"Topic {topic_name} successfully removed")
    except Exception as e:
        print(f"Error when removing topic: {e}")

def create_topic(topic_name: str, num_partitions: int, replication_factor: int):
    """
    Crea un tópico Kafka en el servidor especificado.

    :param topic_name: Nombre del tópico a crear.
    :param num_partitions: Número de particiones para el tópico.
    :param replication_factor: Factor de replicación del tópico.
    """
    try:
        new_topic = NewTopic(topic_name, num_partitions=num_partitions, 
                             replication_factor=replication_factor)

        fs = admin_client.create_topics([new_topic])

        for topic, future in fs.items():
            try:
                future.result()
                print(f"Topic '{topic}' created successfully")
            except KafkaException as e:
                print(f"Error when creating '{topic}': {e}")
    except Exception as e:
        print(f"An error occurred when creating topics: {e}")

num_partitions = 1
replication_factor = 1

for topic_name in ["fraudulent-transactions", "unidentified-transactions",
                   "users", "fraudulent-transaction-results"]:
    #delete_topic(topic_name)
    create_topic(topic_name, num_partitions, replication_factor)
