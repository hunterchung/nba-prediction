package helloworld.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import nba.Game
import nba.PredictInput
import nba.ScheduleManager
import nba.Team
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

//        savePrediction(input.requestEnvelope.session.user, game, predictInput.team, intentRequest.timestamp)

        return input.responseBuilder
            .withSpeech(speechText)
            .withSimpleCard("HelloWorld", speechText)
            .build()
    }


    private fun getSpeechText(game: Game?, team: Team, date: LocalDate): String {
        if (game == null) return "Cannot find ${team.name} game on $date"

        return "There is a ${team.name} game on $date"
    }

//    private fun savePrediction(user: User, game: Game?, team: Team, timestamp: OffsetDateTime) {
//        if (game == null) return
//
//        val prediction = Prediction(user, game, team, timestamp)
//    }
}