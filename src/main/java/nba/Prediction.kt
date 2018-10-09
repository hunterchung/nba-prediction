package nba

import com.amazon.ask.model.User
import java.time.OffsetDateTime

data class Prediction(
    val user: User,
    val game: Game,
    val betTeam: Team,
    val datetime: OffsetDateTime,
    val result: PredictionResult = PredictionResult.UNDETERMINED
)

enum class PredictionResult {
    WIN, LOSE, UNDETERMINED
}