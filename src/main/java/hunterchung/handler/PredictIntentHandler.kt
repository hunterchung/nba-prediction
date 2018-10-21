package hunterchung.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import hunterchung.call.GetUserTimeZone
import hunterchung.nba.PredictInput
import hunterchung.nba.Prediction
import hunterchung.nba.PredictionManager
import hunterchung.nba.ScheduleManager
import org.apache.logging.log4j.LogManager
import java.util.*

/**
 * Handle prediction intent.
 */
class PredictIntentHandler : RequestHandler {
    companion object {
        private val logger = LogManager.getLogger(PredictIntentHandler::class.java)
    }

    override fun canHandle(input: HandlerInput) = input.matches(intentName("predict"))

    override fun handle(input: HandlerInput): Optional<Response> {
        val intentRequest =
            input.request as? IntentRequest ?: throw IllegalArgumentException("Cannot Parse the request.")
        val predictInput = PredictInput.from(intentRequest)
        val timeZoneId = GetUserTimeZone.call(input)

        val game = ScheduleManager.fetchGame(predictInput.date, timeZoneId, predictInput.team)

        if (game == null) {
            val responseText = "Cannot find ${predictInput.team.readName} game on ${predictInput.date}"
            return input.responseBuilder
                .withSpeech(responseText)
                .withSimpleCard("NBA Prediction", responseText)
                .build()
        } else {
            val prediction = Prediction(
                input.requestEnvelope.session.user.userId,
                game.id,
                game,
                predictInput.team,
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