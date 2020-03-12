// import required spark classes
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

// define main method (Spark entry point)
object HelloWorld {
  def main(args: Array[String]) {

    // initialise spark context
    val df = SparkSession.builder.getOrCreate.read.json("/Users/bartholome/Documents/tweets.json")
    //df.show(3)
    val text = df.select(df.col("text"))
    val hashtags = df.select(df.col("entities.hashtags.text"))
    val coordinate = df.select(df.col("coordinate"))
    hashtags.show()
    //df.printSchema()
    //text.show(3)
    //text.foreach(println(_))
    //hashtags.show()

    case class Tweets(corps: String, coordinate: String, hashtags: List[String])

    

    println("************")
    val conf = new SparkConf().setAppName(HelloWorld.getClass.getName)
    val spark: SparkSession = SparkSession.builder.config(conf).getOrCreate()

    // do stuff
    println("************")
    println("************")
    println("Hello, world!")
    val rdd = spark.sparkContext.parallelize(Array(1 to 10))
    rdd.count()
    println("************")
    println("************")

    // terminate spark context
    spark.stop()

  }
}