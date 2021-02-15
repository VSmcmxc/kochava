package com.appsender.rest

import com.appsender.rest.dto.AppsFlyerDto
import com.appsender.rest.dto.EventValue
import com.appsender.service.AppsFlyerService
import com.appsender.service.KochavaService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/app/data"]/*, produces = [MediaType.APPLICATION_JSON_VALUE]*/)
class MainController(
    val appsFlyerService: AppsFlyerService,
    val kochavaService: KochavaService
) {

    val APPSFLYER_UID = "{appsflyer_uid}"
    val KOCHAVA_DEVICE_ID = "{kochava_device_id}"

    val logger: Logger = LoggerFactory.getLogger(MainController::class.java)

    @PostMapping()
    fun sendData(
        @RequestParam(value = "appsflyer_uid", required = false) appsflyer_uid: String?,
        @RequestParam(value = "google_id", required = true) google_id: String,
        @RequestParam(value = "pack", required = false) pack: String?,
        @RequestParam(value = "appsflyer_pid", required = false) appsflyer_pid: String?,
        @RequestParam(value = "app_guid", required = false) app_guid: String?,
        @RequestParam(value = "kochava_device_id", required = false) kochava_device_id: String?
    ): ResponseEntity<String>? {

        val errorList = mutableListOf<String>()
        if (kochava_device_id != null && kochava_device_id != KOCHAVA_DEVICE_ID) {
            if (app_guid == null) {
                val message = "App_guid can't be null"
                logger.error(message)
                errorList.add(message)
            } else {
                kochavaService.post(
                    app_guid, google_id, kochava_device_id
                )
            }
        }

        if (appsflyer_uid != null && appsflyer_uid != APPSFLYER_UID) {
            when {
                pack == null -> {
                    val message = "Pack can't be null"
                    logger.error(message)
                    errorList.add(message)
                }
                appsflyer_pid == null -> {
                    val message = "Appsflyer_uid can't be null"
                    logger.error(message)
                    errorList.add(message)
                }
                else -> {
                    appsFlyerService.post(
                        pack, appsflyer_pid, AppsFlyerDto(
                            appsflyer_id = appsflyer_uid,
                            advertising_id = google_id,
                            eventValue = EventValue()
                        )
                    )
                }
            }
        }

        if (errorList.isNotEmpty()) {
            return ResponseEntity(errorList.reduce { acc, next ->
                "$acc, $next"
            }, HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("Success", HttpStatus.OK)
    }
}