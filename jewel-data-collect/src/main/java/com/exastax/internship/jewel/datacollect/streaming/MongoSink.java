package com.exastax.internship.jewel.datacollect.streaming;

import com.exastax.internship.jewel.core.JewelItem;
import com.mongodb.*;
import com.mongodb.spark.MongoSpark;
import com.mongodb.util.JSON;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Time;
import org.bson.BsonDocument;
import org.bson.Document;
//
import java.util.List;
  //List<Document> bulkDoc = documentJavaRDD.map(doc -> Document.parse(doc.toJson())).collect();
  //MongoSpark.save((JavaRDD<Document>) bulkDoc);
public class MongoSink implements VoidFunction2<JavaRDD<Document>, Time> {

    SparkSession spark = SparkSession.builder()
            .master("local")
            .appName("MongoSparkConnectorIntro")
            .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/test.myCollection")
            .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/test.myCollection")
            .getOrCreate();

    JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

    @Override
    public void call(JavaRDD<Document> documentJavaRDD, Time time) throws Exception {
        //TODO: Save RDD to Mongo Collection

        List<BasicDBObject> bulkDoc = documentJavaRDD.map(doc -> BasicDBObject.parse(doc.toJson())).collect();
        MongoSpark.save((JavaRDD<Document>) bulkDoc);

    }
}
