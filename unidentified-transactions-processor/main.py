import os
import json
from uuid import uuid4

from confluent_kafka.schema_registry import SchemaRegistryClient
from quixstreams import Application
from quixstreams.models import (
    SchemaRegistryClientConfig,
    SchemaRegistrySerializationConfig,
)
from quixstreams.models.serializers.avro import AvroDeserializer, AvroSerializer
from quixstreams.sinks.core.csv import CSVSink


INPUT_TOPIC_NAME = "unidentified-transactions"
OUTPUT_TOPIC_NAME = "fraudulent-transactions-result"
TRANSACTIONS_SCHEMA_NAME = "fraudulent-transactions-value"
TRANSACTIONS_RESULT_SCHEMA_NAME = "fraudulent-transactions-result-value"

schema_registry_client_config = SchemaRegistryClientConfig(
    url='http://localhost:8081'
)
schema_registry_serialization_config = SchemaRegistrySerializationConfig(
    auto_register_schemas=False,
)
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
    "url": os.getenv("SCHEMA_REGISTRY_URL", "http://localhost:8081")
}

schema_registry_client = SchemaRegistryClient(sr_conf)

schema_response = schema_registry_client.get_latest_version(TRANSACTIONS_SCHEMA_NAME)
transactions_schema = json.loads(schema_response.schema.schema_str)
schema_response = schema_registry_client.get_latest_version(TRANSACTIONS_RESULT_SCHEMA_NAME)
transactions_result_schema = json.loads(schema_response.schema.schema_str)


def main():
    app = Application(
        broker_address="localhost:9092",
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
    output_topic = app.topic(OUTPUT_TOPIC_NAME,
                            value_serializer=AvroSerializer(
                                schema=transactions_result_schema,
                                schema_registry_client_config=schema_registry_client_config
                            ))

    #sdf = app.dataframe(input_topic).apply(lambda x: print(type(x), x)).to_topic(output_topic)
    sdf = app.dataframe(input_topic).apply(lambda x: print(type(x), x)).sink(
        CSVSink(
            path="file.csv"
        )
    )

    app.run()

if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        pass
