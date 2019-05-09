package com.exastax.internship.jewel.ml.predict;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.regression.GeneralizedLinearRegressionModel;
import org.apache.spark.mllib.evaluation.RegressionMetrics;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

import static org.apache.spark.sql.functions.col;

public class TestMain {
    public static void main(String[] args) {
        final SparkSession spark = SparkSession.builder()
                .appName("Jewel")
                .master("local[2]")
                .config("spark.executor.memory", "2g")
                .config("spark.driver.memory", "2g")
                .config("spark.memory.offHeap.enabled",true)
                .config("spark.memory.offHeap.size","2g")
                .config("spark.driver.cores", "2").getOrCreate();


        final JavaSparkContext javaSparkContext = new JavaSparkContext(spark.sparkContext());

        final SQLContext sqlContext = new SQLContext(javaSparkContext);

        // TODO: Read CSV file at jewel-ml/src/main/resources/test.csv
        final Dataset<Row> csv = sqlContext.read()
                .load("?")
                .withColumn("price", col("price").cast(DataTypes.DoubleType));


        // TODO Read pipeline model
        final PipelineModel pipelineModel = null;
        // pipelineModel = ?

        final Dataset<Row> transform = pipelineModel.transform(csv);

        // TODO Read regression model
        final GeneralizedLinearRegressionModel regressionModel = null;
        // regressionModel = ?

        Dataset<Row> predictions = regressionModel.transform(transform);
        // TODO: 'predictions' throws exception when it used for metrics. Select required columns (price, prediction)
        // predictions = ?

        final RegressionMetrics metrics = new RegressionMetrics(predictions);

        System.out.println("R2 : " + metrics.r2());
        System.out.println("Root mean squared error : " + metrics.rootMeanSquaredError());
        System.out.println("Mean absolute error : " + metrics.meanAbsoluteError());
        System.out.println("Mean squared error : " + metrics.meanSquaredError());
    }
}
