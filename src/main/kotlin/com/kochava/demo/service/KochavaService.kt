package com.kochava.demo.service

interface KochavaService {
    fun post(kochava_app_id: String, google_id: String, kochava_device_id: String): String?
}