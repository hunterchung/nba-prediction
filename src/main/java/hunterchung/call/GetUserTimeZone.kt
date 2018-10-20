package hunterchung.call

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.time.ZoneId

object GetUserTimeZone {

    fun call(input: HandlerInput): ZoneId {
        val deviceId = input.requestEnvelope.context.system.device.deviceId
        val accessToken = input.requestEnvelope.context.system.apiAccessToken
        val endpoint = input.requestEnvelope.context.system.apiEndpoint

        val (_, _, result) = "$endpoint/v2/devices/$deviceId/settings/System.timeZone"
            .httpGet()
            .header("Authorization" to "Bearer $accessToken")
            .responseString()

        return when (result) {
            is Result.Failure -> throw result.getException()
            is Result.Success -> result.get().removeSurrounding("\"").run { ZoneId.of(this) }
        }
    }
}