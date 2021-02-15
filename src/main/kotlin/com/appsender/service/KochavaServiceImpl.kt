package com.appsender.service

import com.appsender.rest.dto.Data
import com.appsender.rest.dto.DeviceId
import com.appsender.rest.dto.EventData
import com.appsender.rest.dto.KochavaDto
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.Instant

@Service
class KochavaServiceImpl(
    val restTemplate: RestTemplate
) : KochavaService {

    private val kochavaUrl = "http://control.kochava.com/track/json"

    override fun post(kochava_app_id: String, google_id: String, kochava_device_id: String): ResponseEntity<String> {
        val kochavaDto = KochavaDto(
            data = Data(
                usertime = Instant.now().toEpochMilli(),
                device_ids = listOf(DeviceId(google_id)),
                event_data = EventData()
            ),
            kochava_app_id = kochava_app_id,
            kochava_device_id = kochava_device_id
        )
        val request: HttpEntity<KochavaDto> = HttpEntity<KochavaDto>(kochavaDto)
        return restTemplate.postForEntity(kochavaUrl, request, String::class.java)
    }
}