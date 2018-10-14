package hunterchung.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import nba.Prediction
import nba.PredictionManager
import java.util.*


class MyPredictionHandler : RequestHandler {
    override fun canHandle(input: HandlerInput) = input.matches(intentName("myPrediction"))

    override fun handle(input: HandlerInput?): Optional<Response> {
        val user =
            input?.requestEnvelope?.session?.user ?: throw IllegalStateException("Cannot find user in the request.")

        val predictions = PredictionManager.fetchPrediction(user)
        val speechText = getResponseText(predictions)

        return input.responseBuilder
            .withSpeech(speechText)
            .withSimpleCard("NBA Prediction", speechText)
            .build()
    }

    private fun getResponseText(predictions: List<Prediction>): String {
        return when (predictions.size) {
            0 -> "You do't have any prediction yet."
            1 -> "Here is your prediction. ${predictions.first().toSpeech()}"
            10 -> "Here are your recent 10 predictions. ${predictions.joinToString(".\n") { it.toSpeech() }}"
            else -> "You have ${predictions.size} predictions. ${predictions.joinToString(".\n") { it.toSpeech() }}"
        }
    }
}