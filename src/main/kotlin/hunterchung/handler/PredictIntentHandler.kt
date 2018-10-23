package hunterchung.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import hunterchung.extensions.getTopResolutionValue
import hunterchung.extensions.requestTimeZone
import hunterchung.nba.Prediction
import hunterchung.nba.PredictionManager
import hunterchung.nba.ScheduleManager
import hunterchung.nba.Team
import org.apache.logging.log4j.LogManager
import java.time.LocalDate
import java.util.*

/**
 * Handle prediction intent.
 */
class PredictIntentHandler : RequestHandler {
    companion object {
        private val logger = LogManager.getLogger(PredictIntentHandler::class.java)

        private const val SLOT_DATE = "date"
        private const val SLOT_TEAM = "team"
    }

    override fun canHandle(input: HandlerInput) = input.matches(intentName("predict"))

    override fun handle(input: HandlerInput): Optional<Response> {
        val intentRequest =
            input.request as? IntentRequest ?: throw IllegalArgumentException("Cannot Parse the request.")
        val teamId = intentRequest.intent.slots[SLOT_TEAM]?.getTopResolutionValue()?.id
            ?: throw IllegalArgumentException("Cannot find team slot in request.")
        val dateString = intentRequest.intent.slots[SLOT_DATE]?.value
            ?: throw IllegalArgumentException("Cannot find date slot in request.")
        val team = Team.valueOf(teamId)
        val date = LocalDate.parse(dateString)

        val timeZoneId = input.requestTimeZone()

        val game = ScheduleManager.fetchGame(date, timeZoneId, team)

        if (game == null) {
            val responseText = "Cannot find ${team.readName} game on $date"
            return input.responseBuilder
                .withSpeech(responseText)
                .withSimpleCard("NBA Prediction", responseText)
                .build()
        } else {
            val prediction = Prediction(
                input.requestEnvelope.session.user.userId,
                game.id,
                game,
                team,
                intentRequest.timestamp
            )
            PredictionManager.savePrediction(prediction)

            val responseText = "You predict ${prediction.toSpeech(timeZoneId)}"
            return input.responseBuilder
                .withSpeech(responseText)
                .withSimpleCard("NBA Prediction", responseText)
                .build()
        }
    }
}
