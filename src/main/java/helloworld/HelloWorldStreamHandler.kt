package helloworld

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import helloworld.handler.PredictIntentHandler


class HelloWorldStreamHandler : SkillStreamHandler(HelloWorldStreamHandler.skill) {
    companion object {
        private val skill = Skills.standard()
            .addRequestHandlers(
//                CancelandStopIntentHandler(),
                PredictIntentHandler()
//                HelpIntentHandler(),
//                LaunchRequestHandler(),
//                SessionEndedRequestHandler(),
//                FallbackIntentHandler()
            )
            .build()
    }
}