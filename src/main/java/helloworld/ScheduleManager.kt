package helloworld

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.GetItemRequest


object ScheduleManager {
    private const val TABLE_NAME = "NBA_schedule"

    private val client = AmazonDynamoDBClientBuilder.defaultClient()

    fun getItem(gameId: String): Map<String, Any>? {
        val key = mapOf("gameUrlCode" to AttributeValue(gameId))

        val request = GetItemRequest()
            .withTableName(TABLE_NAME)
            .withKey(key)

        return client.getItem(request).item
    }
}