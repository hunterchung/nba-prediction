package nba.data

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper

object DynamoDBUtil {
    private val client = AmazonDynamoDBClientBuilder.defaultClient()
    val mapper = DynamoDBMapper(client)
}