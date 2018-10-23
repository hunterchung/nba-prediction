package hunterchung.nba.data

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper

/**
 * DyanomDB related singleton.
 */
object DynamoDBUtil {
    private val client = AmazonDynamoDBClientBuilder.defaultClient()
    val mapper = DynamoDBMapper(client)
}
