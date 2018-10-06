package helloworld

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.*

/**
 * Handler for requests to Lambda function.
 */
class App : RequestHandler<Any, Any> {

    override fun handleRequest(input: Any, context: Context): Any {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["X-Custom-Header"] = "application/json"
        return try {
            val pageContents = this.getPageContents("https://checkip.amazonaws.com")

            val map = ScheduleManager.getItem("20181016/PHIBOS")

            val output = String.format(
                "{ \"message\": \"hello world2\", \"data\": \"%s\", \"location\": \"%s\" }",
                map,
                pageContents
            )
            GatewayResponse(output, headers, 200)
        } catch (e: IOException) {
            GatewayResponse("{}", headers, 500)
        }

    }

    @Throws(IOException::class)
    private fun getPageContents(address: String): String {
        var br: BufferedReader? = null
        val lines = StringJoiner(System.lineSeparator())
        try {
            val url = URL(address)
            br = BufferedReader(InputStreamReader(url.openStream()))

            br.lines().forEach { lines.add(it) }

        } finally {
            br?.close()
        }
        return lines.toString()
    }
}
