package com.example.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public class UnidentifiedTransactionsProcessor {
    private final static String INPUT_TOPIC = "unidentified-transactions";
    private final static String USERS_OUTPUT_TOPIC = "users";
    private final static String TXS_OUTPUT_TOPIC = "fraudulent-transactions-result";
    private final static String BOOTSTRAP_SERVER = System.getenv("KAFKA_BOOTSTRAP_SERVER") != null ? System.getenv("KAFKA_BOOTSTRAP_SERVER") : "localhost:9092";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "simple-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> inputStream = builder.stream(INPUT_TOPIC);
        inputStream.foreach((key, value) -> {
            System.out.println("Received message: key = " + key + ", value = " + value);
        });

        KStream<String, String> transformedStream = inputStream
            .mapValues(value -> value.toUpperCase())
            .filter((key, value) -> !value.trim().isEmpty());

        transformedStream.to(USERS_OUTPUT_TOPIC);

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
