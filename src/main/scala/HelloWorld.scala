// import required spark classes
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import java.time.Instant

// define main method (Spark entry point)
object HelloWorld {
  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName(HelloWorld.getClass.getName)
    val spark: SparkSession = SparkSession.builder.config(conf).getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    /**
      * Premiere partie pour tests en local sur l'edge
      */
    /*val df = spark.read.json("hdfs://master.sagean.fr:8020/user/hapoop/tweets")

    df.show()

    val text = df.select(col("text"))
    val hashtags = df.select(explode(col("entities.hashtags.text")).alias("hashtag"))

    hashtags.show()

    hashtags
      .groupBy("hashtag")
      .count()
      .orderBy(desc("count"))
      .limit(10)
      .show()*/

    /**
      * Deuxieme partie fonctionnelle sur l'hdfs
      */
    val tweetsDirDf = spark.read.json("hdfs://master.sagean.fr:8020/user/hapoop/tweets_dir")
    var tweetsLastHour = tweetsDirDf.filter("time > " + String.valueOf(Instant.now().getEpochSecond() - 3600))

    val hashtags = tweetsLastHour.select(explode(col("entities.hashtags.text")).alias("hashtag"))
  
    val topTenHashtags = hashtags
      .groupBy("hashtag")
      .count()
      .orderBy(desc("count"))
      .limit(10)

    topTenHashtags.write.json(
      "hdfs://master.sagean.fr:8020/user/hapoop/toptweets/time=" + String.valueOf(Instant.now().getEpochSecond())
    )

    // terminate spark context
    spark.stop()
  }
}