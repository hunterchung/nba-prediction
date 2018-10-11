package helloworld.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import nba.*
import java.time.LocalDate
import java.util.*

class PredictIntentHandler : RequestHandler {
    override fun canHandle(input: HandlerInput) = input.matches(intentName("predict"))

    override fun handle(input: HandlerInput): Optional<Response> {
        val intentRequest =
            input.request as? IntentRequest ?: throw IllegalArgumentException("Cannot Parse the request.")
        val predictInput = PredictInput.from(intentRequest)
        val game = ScheduleManager.fetchGame(predictInput.date, predictInput.team)
        val speechText = getSpeechText(game, predictInput.team, predictInput.date)

        val test = Game("123", "avc", Team.ATLANTA_HAWKS, Team.BOSTON_CELTICS, LocalDate.now())
        val prediction =
            Prediction(input.requestEnvelope.session.user.userId, test, predictInput.team, intentRequest.timestamp)
        PredictionManager.savePrediction(prediction)

        return input.responseBuilder
            .withSpeech(speechText)
            .withSimpleCard("HelloWorld", speechText)
            .build()
    }


    private fun getSpeechText(game: Game?, team: Team, date: LocalDate): String {
        if (game == null) return "Cannot find ${team.name} game on $date"

        return "There is a ${team.name} game on $date"
    }
}