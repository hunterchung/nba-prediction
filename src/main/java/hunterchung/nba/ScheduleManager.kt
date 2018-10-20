package hunterchung.nba

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import hunterchung.nba.data.DynamoDBUtil
import java.time.LocalDate


object ScheduleManager {
    fun fetchGame(date: LocalDate, team: Team): Game? {
        val attributeValueMap = mapOf(
            ":startDate" to AttributeValue(date.toString()),
            ":teamId" to AttributeValue(team.id)
        )

        val expression = DynamoDBScanExpression()
            .withFilterExpression("begins_with(startTime, :startDate) and (hTeamId = :teamId or vTeamId = :teamId)")
            .withExpressionAttributeValues(attributeValueMap)

        val games = DynamoDBUtil.mapper.scan(Game::class.java, expression)

        if (games.size > 1) throw IllegalStateException("Has more than 1 game on this date.")

        return games.firstOrNull()
    }
}