package nba

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
@DynamoDBTable(tableName = "NBA_schedule")
class Game() {
    @DynamoDBAttribute
    lateinit var id: String

    @DynamoDBHashKey(attributeName = "gameUrlCode")
    lateinit var urlCode: String

    @DynamoDBAttribute
    lateinit var homeTeam: Team

    @DynamoDBAttribute
    lateinit var visitorTeam: Team

    @DynamoDBAttribute
    lateinit var startDate: LocalDate

    constructor(id: String, urlCode: String, homeTeam: Team, visitorTeam: Team, startDate: LocalDate) : this() {
        this.id = id
        this.urlCode = urlCode
        this.homeTeam = homeTeam
        this.visitorTeam = visitorTeam
        this.startDate = startDate
    }

    companion object {
        fun from(map:  Map<String, AttributeValue>): Game {
            val id = map["gameId"]?.s ?: throw IllegalArgumentException("Cannot find game id")
            val urlCode = map["gameUrlCode"]?.s ?: throw IllegalArgumentException("Cannot find game url code")
            val homeTeam = Team.values()
                .first { it.id == map["hTeamId"]?.s ?: throw IllegalArgumentException("Cannot find hTeam") }
            val visitorTeam = Team.values()
                .first { it.id == map["vTeamId"]?.s ?: throw IllegalArgumentException("Cannot find vTeam") }
            val startDate = LocalDate.parse(
                map["startDate"]?.s ?: throw IllegalArgumentException("Cannot find start date"),
                DateTimeFormatter.BASIC_ISO_DATE
            )

            return Game()
        }
    }
}