package nba

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class Game(val id: String, val urlCode: String, val homeTeam: Team, val visitorTeam: Team, val startDate: LocalDate) {
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

            return Game(id, urlCode, homeTeam, visitorTeam, startDate)
        }
    }
}