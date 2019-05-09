package com.exastax.internship.jewel.datacollect.streaming;

import com.exastax.internship.jewel.core.JewelItem;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.bson.Document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataCollectorMain implements Serializable {
    public static void main(String[] args) throws InterruptedException {
        final SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("local[2]");
        sparkConf.setAppName("JewelStoreCrawler");
        sparkConf.set("spark.mongodb.input.uri", "mongodb://localhost/jewel_store.products");
        sparkConf.set("spark.mongodb.output.uri", "mongodb://localhost/jewel_store.products");

        final JavaStreamingContext streamingContext = new JavaStreamingContext(
                sparkConf, Durations.seconds(1));

        /*
            Common Kafka parameters.
         */
        final Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "cg_jewel_project");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", true);
        kafkaParams.put("retention.ms", 5000);
        Collection<String> topics = Arrays.asList("jewel_store");

        /*
            Get messages from Kafka broker.
         */
        final JavaInputDStream<ConsumerRecord<String, String>> kafkaStream = KafkaUtils.createDirectStream(
                streamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

        /*
            Transform to user defined Java Class.
         */
        final JavaDStream<JewelItem> products = kafkaStream.map(new URLResultMapFunction());

        /*
            Transform to Mongo Document type.
         */
        final JavaDStream<Document> productDocuments = products.map(new JewelItemToDocumentFunction());

        /*
            Write to Mongo Database.
         */

         productDocuments.repartition(10).foreachRDD(new MongoSink());

        /*
            Also print for debug.
         */
        productDocuments.print();

        streamingContext.start();
        streamingContext.awaitTermination();
    }
}
