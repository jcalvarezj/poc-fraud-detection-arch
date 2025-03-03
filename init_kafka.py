from confluent_kafka.admin import AdminClient, NewTopic
from confluent_kafka import KafkaException

from confluent_kafka.avro import CachedSchemaRegistryClient
from avro.schema import parse


## Topic actions

admin_conf = {
    "bootstrap.servers": "localhost:9092"
}
admin_client = AdminClient(admin_conf)


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

for topic_name in ("fraudulent-transactions", "unidentified-transactions",
                   "users", "fraudulent-transaction-results"):
    create_topic(topic_name, num_partitions, replication_factor)


## Schema Registry actions

TRANSACTIONS_SCHEMA = "transaction-schema-value"
TRANSACTIONS_RESULT_SCHEMA = "transaction-result-schema-value"
USERS_SCHEMA = "user-schema-value"
sr_conf = {
    "url": "http://localhost:8081",
    "auto.register.schemas": False
}

def load_avro_schema(file_path):
    with open(file_path, 'r') as schema_file:
        schema_str = schema_file.read()
    return parse(schema_str)

schema_name_avros = {
    TRANSACTIONS_SCHEMA: load_avro_schema("avro/transaction.avsc"),
    TRANSACTIONS_RESULT_SCHEMA: load_avro_schema("avro/transaction_result.avsc"),
    USERS_SCHEMA: load_avro_schema("avro/user.avsc")
}

schema_registry_client = CachedSchemaRegistryClient(sr_conf)

def register_schema(schema, schema_name):
    try:
        schema_id = schema_registry_client.register(schema_name, schema)
        print(f"Registered {schema_name} with id: {schema_id}")
        return schema_id, None
    except Exception as e:
        print(f"Error registering schema: {e}")
        return None, e

def get_schema(schema_name):
    try:
        schema = schema_registry_client.get_latest_schema(schema_name)
        print(f"Obtained schema: {schema}")
        return schema, None
    except Exception as e:
        print(f"Error getting schema: {e}")
        return None, e

for schema_name in (TRANSACTIONS_SCHEMA, TRANSACTIONS_RESULT_SCHEMA, USERS_SCHEMA):
    id, err = register_schema(schema_name_avros[schema_name], schema_name)
    if id:
        print(f"Successfully registered {schema_name} schema")

    transaction_schema, err = get_schema(schema_name)
    if transaction_schema:
        print(transaction_schema)
