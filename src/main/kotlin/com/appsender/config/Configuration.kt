package com.appsender.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.util.CollectionUtils
import org.springframework.web.client.RestTemplate
import org.springframework.web.filter.CommonsRequestLoggingFilter
import java.util.*


@Configuration
class Configuration {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {

        val restTemplate = RestTemplate(BufferingClientHttpRequestFactory(SimpleClientHttpRequestFactory()))

        var interceptors = restTemplate.interceptors
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = ArrayList()
        }
        interceptors.add(LoggingInterceptor())
        restTemplate.interceptors = interceptors
        restTemplate.errorHandler = CustomErrorHandler()
        return restTemplate
    }

    @Bean
    fun logFilter(): CommonsRequestLoggingFilter {
        val filter = CommonsRequestLoggingFilter()
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)
        filter.setMaxPayloadLength(100000)
        filter.setIncludeHeaders(false)
        return filter
    }

}