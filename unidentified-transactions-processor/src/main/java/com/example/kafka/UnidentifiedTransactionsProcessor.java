package com.example.kafka;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

import com.example.kafka.avro.FraudulentTransaction;
import com.example.kafka.avro.FraudulentTransactionResult;
//import com.example.kafka.avro.User;


public class UnidentifiedTransactionsProcessor {
    private final static String INPUT_TOPIC = "unidentified-transactions";
    private final static String USERS_OUTPUT_TOPIC = "users";
    private final static String TXNS_OUTPUT_TOPIC = "fraudulent-transactions-result";
    private final static String BOOTSTRAP_SERVER = System.getenv("KAFKA_BOOTSTRAP_SERVER") != null ? System.getenv("KAFKA_BOOTSTRAP_SERVER") : "localhost:9092";
    private final static String SCHEMA_REGISTRY_URL = System.getenv("SCHEMA_REGISTRY_URL") != null ? System.getenv("SCHEMA_REGISTRY_URL") : "http://localhost:8081";

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "fraud-detection-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);

        StreamsBuilder builder = new StreamsBuilder();
        System.out.println("Created StreamsBuilder instance");

        Map<String, String> serdeConfig = new HashMap<>();
        serdeConfig.put("schema.registry.url", SCHEMA_REGISTRY_URL);
        SpecificAvroSerde<FraudulentTransaction> fraudulentTransactionSerde = new SpecificAvroSerde<>();
        fraudulentTransactionSerde.configure(serdeConfig, false);
        System.out.println("Created fraudulentTransactionSerde");
        
        SpecificAvroSerde<FraudulentTransactionResult> fraudulentTransactionResultSerde = new SpecificAvroSerde<>();
        fraudulentTransactionResultSerde.configure(serdeConfig, false);
        System.out.println("Created fraudulentTransactionResultSerde");
        
        KStream<String, FraudulentTransaction> stream = builder.stream(
            INPUT_TOPIC,
            Consumed.with(Serdes.String(),
            fraudulentTransactionSerde));
        System.out.println("Created input stream");

        stream.mapValues(transaction -> {
            FraudulentTransactionResult result = new FraudulentTransactionResult();
            result.setTransactionId(transaction.getTransactionId());
            result.setSenderBankAccount(transaction.getSenderBankAccount());
            result.setSenderId(transaction.getSenderDetails().getId());
            result.setReceiverBankAccount(transaction.getReceiverBankAccount());
            result.setReceiverId(transaction.getReceiverDetails().getId());
            result.setAmount(transaction.getAmount());
            result.setStatus(transaction.getStatus());
            result.setEvaluation(transaction.getEvaluation());
            result.setTransferDate(transaction.getTransferDate());
            result.setSenderBank(transaction.getSenderBank());
            return result;
        })
        .to(TXNS_OUTPUT_TOPIC, Produced.with(Serdes.String(), 
            fraudulentTransactionResultSerde));

        System.out.println("Prepared sending of output");
        
        System.out.println("Starting worker...");
        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
