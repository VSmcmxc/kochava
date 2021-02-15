package com.appsender.config

import com.appsender.rest.MainController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import java.io.IOException
import org.springframework.http.HttpStatus
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors


class CustomErrorHandler : ResponseErrorHandler {

    private val logger: Logger = LoggerFactory.getLogger(CustomErrorHandler::class.java)

    @Throws(IOException::class)
    override fun handleError(clienthttpresponse: ClientHttpResponse) {
        if (clienthttpresponse.statusCode == HttpStatus.FORBIDDEN) {
            logger.error("Get ${clienthttpresponse.statusCode} in response")
        }
    }

    @Throws(IOException::class)
    override fun hasError(clienthttpresponse: ClientHttpResponse): Boolean {
        if (clienthttpresponse.statusCode != HttpStatus.OK) {
            logger.error("Status code: " + clienthttpresponse.statusCode)
            val isr = InputStreamReader(
                clienthttpresponse.body, StandardCharsets.UTF_8
            )
            val responseBody = BufferedReader(isr).lines()
                .collect(Collectors.joining("\n"))
            logger.error("Response body: {}", responseBody)
        }
        return false
    }
}
