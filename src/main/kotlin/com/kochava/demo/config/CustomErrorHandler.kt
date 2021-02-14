package com.kochava.demo.config

import com.kochava.demo.rest.MainController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import java.io.IOException
import org.springframework.http.HttpStatus


class CustomErrorHandler : ResponseErrorHandler {

    private val logger: Logger = LoggerFactory.getLogger(MainController::class.java)

    @Throws(IOException::class)
    override fun handleError(clienthttpresponse: ClientHttpResponse) {
        if (clienthttpresponse.statusCode == HttpStatus.FORBIDDEN) {
            logger.error("Get ${clienthttpresponse.statusCode} in response")
        }
    }

    @Throws(IOException::class)
    override fun hasError(clienthttpresponse: ClientHttpResponse): Boolean {
        if (clienthttpresponse.statusCode != HttpStatus.OK) {
            logger.debug("Status code: " + clienthttpresponse.statusCode)
            logger.debug("Response" + clienthttpresponse.statusText)
            logger.debug(clienthttpresponse.body.toString())
            if (clienthttpresponse.statusCode == HttpStatus.FORBIDDEN) {
                logger.debug("Call returned a error 403 forbidden response ")
                return true
            }
        }
        return false
    }
}
