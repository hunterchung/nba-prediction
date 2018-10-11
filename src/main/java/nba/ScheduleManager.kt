package nba

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import java.time.LocalDate
import java.time.format.DateTimeFormatter


object ScheduleManager {
    private const val TABLE_NAME = "NBA_schedule"

    private val client = AmazonDynamoDBClientBuilder.defaultClient()
    private val mapper = DynamoDBMapper(client)

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

    fun fetchGame(date: LocalDate, team: Team): Game? {
        val attributeValueMap = mapOf(":startDate" to AttributeValue(date.format(DateTimeFormatter.BASIC_ISO_DATE)))

        val scanRequest = ScanRequest()
            .withTableName(TABLE_NAME)
            .withFilterExpression("startDate = :startDate")
            .withExpressionAttributeValues(attributeValueMap)

        val result = client.scan(scanRequest)

        val games = result.items
            .asSequence()
            .map { Game.from(it) }
            .filter { it.homeTeam == team || it.visitorTeam == team }
            .toList()

        if (games.size > 1) throw IllegalStateException("Has more than 1 game on this date.")

        return games.firstOrNull()
    }

    fun getOne(): Game? = mapper.load(Game::class.java, "20181016/PHIBOS")
}