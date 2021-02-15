package com.appsender.service

import com.appsender.rest.dto.AppsFlyerDto
import org.springframework.http.ResponseEntity

interface AppsFlyerService {
    fun post(pack: String, appsflyer_pid: String, appsFlyerDto: AppsFlyerDto): ResponseEntity<String>
}