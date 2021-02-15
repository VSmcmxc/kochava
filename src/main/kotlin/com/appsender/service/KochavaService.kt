package com.appsender.service

import org.springframework.http.ResponseEntity

interface KochavaService {
    fun post(kochava_app_id: String, google_id: String, kochava_device_id: String): ResponseEntity<String>
}