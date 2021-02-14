package com.kochava.demo.service

import com.kochava.demo.rest.dto.AppsFlyerDto
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder


@Service
class AppsFlyerServiceImpl(
    val restTemplate: RestTemplate
) : AppsFlyerService {

    override fun post(pack: String, appsflyer_pid: String, appsFlyerDto: AppsFlyerDto): String? {
        val headers = HttpHeaders()
        headers.add("authentication", appsflyer_pid)
        val request: HttpEntity<AppsFlyerDto> = HttpEntity<AppsFlyerDto>(appsFlyerDto, headers)

        val uriComponents = UriComponentsBuilder.newInstance()
            .scheme("https").host("api2.appsflyer.com")
            .path("/inappevent/{pack}").buildAndExpand(pack)

        return restTemplate.postForObject(
            uriComponents.toUriString(),
            request,
            String::class.java
        )
    }
}
