package hunterchung.extensions

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.time.ZoneId

/**
 * Call Alexa settings API to get user's time zone.
 *
 * This is a network call.
 */
fun HandlerInput.requestTimeZone(): ZoneId {
    val deviceId = requestEnvelope.context.system.device.deviceId
    val accessToken = requestEnvelope.context.system.apiAccessToken
    val endpoint = requestEnvelope.context.system.apiEndpoint

    val (_, _, result) = "$endpoint/v2/devices/$deviceId/settings/System.timeZone"
        .httpGet()
        .header("Authorization" to "Bearer $accessToken")
        .responseString()

    return when (result) {
        is Result.Failure -> throw result.getException()
        is Result.Success -> result.get().removeSurrounding("\"").run { ZoneId.of(this) }
    }
}
