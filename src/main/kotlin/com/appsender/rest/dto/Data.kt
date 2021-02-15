package com.appsender.rest.dto

data class Data(
    val usertime: Long,
    val app_version: String = "1.0.0",
    val device_ver: String = "",
    val device_ids: DeviceId,
    val device_ua: String = "",
    val event_name: String = "af_purchase",
    val currency: String = "USD",
    val event_data: EventData
)