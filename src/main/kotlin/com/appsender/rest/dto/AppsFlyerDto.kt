package com.appsender.rest.dto

data class AppsFlyerDto(
    val appsflyer_id: String,
    val advertising_id: String,
    val eventName: String = "af_purchase",
    val eventCurrency: String = "USD",
    val eventValue: EventValue
)