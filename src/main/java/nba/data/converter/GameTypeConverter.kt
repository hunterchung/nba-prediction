package nba.data.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import kotlinx.serialization.json.JSON
import nba.Game
import org.apache.logging.log4j.LogManager

class GameTypeConverter : DynamoDBTypeConverter<String, Game> {
    companion object {
        private val logger = LogManager.getLogger(GameTypeConverter::class)
    }

    override fun unconvert(s: String?): Game {
        logger.error("game string: $s")
        return JSON.parse(s ?: throw IllegalArgumentException("Cannot parse string to Game. $s"))
    }

    override fun convert(game: Game) = JSON.stringify(game)
}