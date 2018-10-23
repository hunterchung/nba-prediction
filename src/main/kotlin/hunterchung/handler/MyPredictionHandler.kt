package hunterchung.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import com.amazon.ask.request.Predicates.requestType
import hunterchung.extensions.addTemplate
import hunterchung.extensions.requestTimeZone
import hunterchung.nba.Prediction
import hunterchung.nba.PredictionManager
import hunterchung.nba.PredictionManager.PREDICTION_FETCH_LIMIT
import hunterchung.template.PredictionTemplate
import org.apache.logging.log4j.LogManager
import java.time.ZoneId
import java.util.*

/**
 * Handle my prediction intent.
 */
class MyPredictionHandler : RequestHandler {
    companion object {
        private val logger = LogManager.getLogger(MyPredictionHandler::class)
    }

    override fun canHandle(input: HandlerInput) =
        input.matches(intentName("myPrediction")) || input.matches(requestType(LaunchRequest::class.java))

    override fun handle(input: HandlerInput?): Optional<Response> {
        val user = input?.requestEnvelope?.session?.user
            ?: throw IllegalStateException("Cannot find user in the request.")
        val timeZoneId = input.requestTimeZone()

        val predictions = PredictionManager.fetchPredictions(user)
        val speechText = getResponseText(predictions, timeZoneId)

        return input.responseBuilder
            .addTemplate(PredictionTemplate.from("NBA Prediction", predictions, timeZoneId))
            .withSpeech(speechText)
            .build()
    }

    private fun getResponseText(predictions: List<Prediction>, timeZoneId: ZoneId) = when (predictions.size) {
        0 -> "You don't have any prediction yet."
        1 -> "Here is your prediction. ${predictions.first().toSpeech(timeZoneId)}"
        PREDICTION_FETCH_LIMIT -> "Here are your recent $PREDICTION_FETCH_LIMIT predictions. " +
                predictions.joinToString("\n") { it.toSpeech(timeZoneId) }
        else -> "You have ${predictions.size} predictions. ${predictions.joinToString("\n") {
            it.toSpeech(
                timeZoneId
            )
        }}"
    }
}
