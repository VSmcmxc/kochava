package com.kochava.demo.rest.dto

data class KochavaDto (
   val data: Data,
   val action: String = "event",
   val kochava_app_id: String,
   val kochava_device_id: String
)