package hunterchung.nba

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import hunterchung.nba.data.DynamoDBUtil
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Manage NBA game.
 */
object ScheduleManager {
    /**
     * Fetch a game by a [team] play on a [date] consider time zone.
     */
    fun fetchGame(date: LocalDate, zoneId: ZoneId, team: Team): Game? {
        val instant = date.atStartOfDay(zoneId).toInstant()
        val startTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"))
        val endTime = startTime.plusDays(1).minusMinutes(1)

        val attributeValueMap = mapOf(
            ":startTime" to AttributeValue(startTime.toString()),
            ":endTime" to AttributeValue(endTime.toString()),
            ":teamId" to AttributeValue(team.id)
        )

        val expression = DynamoDBScanExpression()
            .withFilterExpression(
                "startTime BETWEEN :startTime AND :endTime and (hTeamId = :teamId or vTeamId = :teamId)"
            )
            .withExpressionAttributeValues(attributeValueMap)

        val games = DynamoDBUtil.mapper.scan(Game::class.java, expression)

        if (games.size > 1) throw IllegalStateException("Has more than 1 game on this date.")

        return games.firstOrNull()
    }
}
