from avro.schema import parse, Schema
from confluent_kafka import KafkaException
from confluent_kafka.admin import AdminClient, NewTopic
from confluent_kafka.avro import CachedSchemaRegistryClient


## Topic actions

admin_conf = {
    "bootstrap.servers": "localhost:9092"
}
admin_client = AdminClient(admin_conf)


def create_topic(topic_name: str, num_partitions: int, replication_factor: int):
    """
    Creates a topic given a name, a number of partitions, and a replication factor

    Args:
        - topic_name (str): Name of the topic
        - num_partitions (int): Number of partitions
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
                   "users", "fraudulent-transactions-result"):
    create_topic(topic_name, num_partitions, replication_factor)


## Schema Registry actions

TRANSACTIONS_SCHEMA = "fraudulent-transactions-value"
sr_conf = {
    "url": "http://localhost:8081",
    "auto.register.schemas": False
}


def load_avro_schema(file_path: str) -> Schema:
    """
    Returns a parsed AVRO Schema from a file given its path

    Args:
        - file_path (str) - The path of the AVSC file
    """
    with open(file_path, 'r') as schema_file:
        schema_str = schema_file.read()
    return parse(schema_str)


schema_name_avros = {
    TRANSACTIONS_SCHEMA: load_avro_schema("avro/fraudulent_transaction.avsc")
}

schema_registry_client = CachedSchemaRegistryClient(sr_conf)


def register_schema(schema: Schema, schema_name: str) -> tuple[int, Exception]:
    """
    Registers a Schema inside Schema Registry with a given name

    Args:
        - schema (avro.schema.Schema): A parsed Schema to register
        - schema_name (str): The subject name associated to the Schema

    Returns:
        Tuple containing id of the created schema and an Exception if it
        occurs
    """
    try:
        schema_id = schema_registry_client.register(schema_name, schema)
        print(f"Registered {schema_name} with id: {schema_id}")
        return schema_id, None
    except Exception as e:
        print(f"Error registering schema: {e}")
        return None, e


def get_schema(schema_name: str) -> Schema:
    """
    Retrieves a Schema from Schema Registry given its subject name
    """
    try:
        schema = schema_registry_client.get_latest_schema(schema_name)
        print(f"Obtained schema: {schema}")
        return schema, None
    except Exception as e:
        print(f"Error getting schema: {e}")
        return None, e


for schema_name in schema_name_avros:
    id, err = register_schema(schema_name_avros[schema_name], schema_name)
    if id:
        print(f"Successfully registered {schema_name} schema")

    transaction_schema, err = get_schema(schema_name)
    if transaction_schema:
        print(transaction_schema)
