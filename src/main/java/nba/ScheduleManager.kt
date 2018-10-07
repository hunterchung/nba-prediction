package nba

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import java.lang.Exception


object ScheduleManager {
    private const val TABLE_NAME = "NBA_schedule"

    private val client = AmazonDynamoDBClientBuilder.defaultClient()

    fun getItem(gameId: String): Game? {
        val key = mapOf("gameUrlCode" to AttributeValue(gameId))

        val request = GetItemRequest()
            .withTableName(TABLE_NAME)
            .withKey(key)

        val item = client.getItem(request)?.item ?: return null

        return try {
            Game.from(item)
        } catch (e: Exception) {
            return null
        }
    }
}