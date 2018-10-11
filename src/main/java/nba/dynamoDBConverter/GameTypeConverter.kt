package nba.dynamoDBConverter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import kotlinx.serialization.json.JSON
import nba.Game

class GameTypeConverter : DynamoDBTypeConverter<String, Game> {
    override fun unconvert(s: String?): Game =
        JSON.parse(
            s ?: throw IllegalArgumentException("Cannot parse string to Game. $s")
        )

    override fun convert(game: Game) = JSON.stringify(game)
}