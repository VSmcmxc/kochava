package com.kochava.demo.service

import com.kochava.demo.rest.dto.Data
import com.kochava.demo.rest.dto.DeviceId
import com.kochava.demo.rest.dto.EventData
import com.kochava.demo.rest.dto.KochavaDto
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.Instant

@Service
class KochavaServiceImpl(
    val restTemplate: RestTemplate
) : KochavaService {

    private val kochavaUrl = "http://control.kochava.com/track/json"

    override fun post(kochava_app_id: String, google_id: String, kochava_device_id: String): String? {
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
        return restTemplate.postForObject<String>(kochavaUrl, request, String::class.java)
    }
}