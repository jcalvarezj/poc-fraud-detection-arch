import os
import json

from quixstreams.models import (
    SchemaRegistryClientConfig,
    SchemaRegistrySerializationConfig,
)
from quixstreams import Application
from quixstreams.models.serializers.avro import AvroDeserializer, AvroSerializer
from confluent_kafka.schema_registry import SchemaRegistryClient

from transformations import (extract_transaction_data, extract_user_data,
                             identify_fraudulence)


INPUT_TOPIC_NAME = "unidentified-transactions"
TXN_TOPIC_NAME = "fraudulent-transactions"
TXN_RESULT_TOPIC_NAME = "fraudulent-transactions-result"
USERS_TOPIC_NAME = "users"

KAFKA_BOOTSTRAP_SERVER = os.getenv("KAFKA_BOOTSTRAP_SERVER", "http://localhost:9092")
SCHEMA_REGISTRY_URL = os.getenv("SCHEMA_REGISTRY_URL", "http://localhost:8081")

schema_registry_client_config = SchemaRegistryClientConfig(
    url=SCHEMA_REGISTRY_URL
)
schema_registry_serialization_config = SchemaRegistrySerializationConfig(
    auto_register_schemas=False,
)
sr_conf = {
    "url": SCHEMA_REGISTRY_URL
}

schema_registry_client = SchemaRegistryClient(sr_conf)

schema_response = schema_registry_client.get_latest_version(f"{TXN_TOPIC_NAME}-value")
transactions_schema = json.loads(schema_response.schema.schema_str)

schema_response = schema_registry_client.get_latest_version(f"{TXN_RESULT_TOPIC_NAME}-value")
transactions_result_schema = json.loads(schema_response.schema.schema_str)

schema_response = schema_registry_client.get_latest_version(f"{USERS_TOPIC_NAME}-value")
users_schema = json.loads(schema_response.schema.schema_str)


def main():
    app = Application(
        broker_address=KAFKA_BOOTSTRAP_SERVER,
        loglevel="INFO",
        consumer_group="unidentified-transactions-processor",
        auto_offset_reset="latest",
        auto_create_topics=False
    )

    input_topic = app.topic(INPUT_TOPIC_NAME,
                            value_deserializer=AvroDeserializer(
                                schema=transactions_schema,
                                schema_registry_client_config=schema_registry_client_config
                            ))
    txn_result_topic = app.topic(TXN_RESULT_TOPIC_NAME,
                            value_serializer=AvroSerializer(
                                schema=transactions_result_schema,
                                schema_registry_client_config=schema_registry_client_config
                            ))
    users_topic = app.topic(USERS_TOPIC_NAME,
                            value_serializer=AvroSerializer(
                                schema=users_schema,
                                schema_registry_client_config=schema_registry_client_config
                            ))

    sdf_input = app.dataframe(input_topic)

    sdf_fraud_txn = sdf_input.apply(identify_fraudulence).filter(
        lambda message: message["is_fraudulent"]
    )

    sdf_txn_result = sdf_fraud_txn.apply(extract_transaction_data)
    sdf_txn_result.to_topic(txn_result_topic)

    sdf_sender_user = sdf_fraud_txn.apply(lambda message: extract_user_data(message, True))
    sdf_sender_user.to_topic(users_topic)

    sdf_receiver_user = sdf_fraud_txn.apply(extract_user_data)
    sdf_receiver_user.to_topic(users_topic)

    app.run()

if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        pass
