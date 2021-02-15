package com.appsender.config


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors


class LoggingInterceptor : ClientHttpRequestInterceptor {

    private val logger: Logger = LoggerFactory.getLogger(LoggingInterceptor::class.java)

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        logger.info("Request body: {}", String(body, StandardCharsets.UTF_8))
        val response: ClientHttpResponse = execution.execute(request, body)
        if (response.statusCode == HttpStatus.FORBIDDEN) {
            return response
        }
        val isr = InputStreamReader(
            response.body, StandardCharsets.UTF_8
        )
        val responseBody = BufferedReader(isr).lines()
            .collect(Collectors.joining("\n"))
        logger.info("Response body: {}", responseBody)
        return response
    }

}