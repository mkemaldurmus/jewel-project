package com.exastax.internship.jewel.ml.predict;

import com.mongodb.spark.MongoSpark;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.OneHotEncoderEstimator;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.GeneralizedLinearRegression;
import org.apache.spark.ml.regression.GeneralizedLinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

import java.io.IOException;
import static org.apache.spark.sql.functions.col;

public class TrainerMain {
    private static final String URL = "mongodb://localhost:27017/jewel_store.products";

    public static void main(String[] args) throws IOException {
        final SparkSession spark = SparkSession.builder()
                .appName("Jewel")
                .master("local[2]")
                .config("spark.executor.memory", "2g")
                .config("spark.driver.memory", "2g")
                .config("spark.memory.offHeap.enabled",true)
                .config("spark.memory.offHeap.size","2g")
                .config("spark.mongodb.input.uri", URL)
                .config("spark.driver.cores", "2").getOrCreate();

        final JavaSparkContext javaSparkContext = new JavaSparkContext(spark.sparkContext());

        final Dataset<Row> dataset = MongoSpark.load(javaSparkContext).toDF().drop("_id", "productId").withColumn("price", col("price").cast(DataTypes.DoubleType));

        final StringIndexer diamondTypeIndexer = new StringIndexer()
                .setInputCol("diamondType")
                .setOutputCol("diamondTypeIndex");

        final StringIndexer diamondColorIndexer = new StringIndexer()
                //.setInputCol(?)
                //.setOutputCol(?)
        ;

        final StringIndexer diamondClarityIndexer = new StringIndexer()
                //.setInputCol(?)
                //.setOutputCol(?)
        ;

        final StringIndexer diamondShapeIndexer = new StringIndexer()
                //.setInputCol(?)
                //.setOutputCol(?)
        ;

        final String[] featuresOneHot = new String[] { /* ?, ?, ? */ };
        final String[] oneHotOutput = new String[] { /* ?, ?, ? */ };

        final OneHotEncoderEstimator oneHotEncoderEstimator = new OneHotEncoderEstimator()
                .setInputCols(featuresOneHot)
                .setOutputCols(oneHotOutput);

        final String[] features = new String[] { "carat", "weight", "diamondWeight", /* ?, ?, ? */ "diamondTypeIndex" };

        final VectorAssembler vectorAssembler = new VectorAssembler()
                .setInputCols(features)
                .setOutputCol("features");

        final Pipeline pipeline = new Pipeline().setStages(new PipelineStage[]{ diamondClarityIndexer, diamondColorIndexer, diamondTypeIndexer, diamondShapeIndexer, oneHotEncoderEstimator, vectorAssembler });

        final PipelineModel pipelineModel = pipeline.fit(dataset);

        // TODO: Save pipeline model
        //
        //
        //


        final Dataset<Row> trainingDataset = pipelineModel.transform(dataset);

        final GeneralizedLinearRegression glr = new GeneralizedLinearRegression()
                .setFamily("gaussian")
                .setLink("identity")
                .setMaxIter(1000)
                //.setLabelCol(?)
                .setPredictionCol("prediction")
                //.setRegParam(?)
        ;

        final GeneralizedLinearRegressionModel model = glr.fit(trainingDataset);

        // TODO: Save regression model
        //
        //
        //

        System.out.println("Training completed");
    }
}
