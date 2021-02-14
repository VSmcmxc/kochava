package com.kochava.demo.service

import com.kochava.demo.rest.dto.AppsFlyerDto

interface AppsFlyerService {
    fun post(pack: String, appsflyer_pid: String, appsFlyerDto: AppsFlyerDto): String?
}