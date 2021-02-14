package com.kochava.demo.rest

import com.kochava.demo.rest.dto.AppsFlyerDto
import com.kochava.demo.rest.dto.EventValue
import com.kochava.demo.service.AppsFlyerService
import com.kochava.demo.service.KochavaService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/app/data"], produces = [MediaType.APPLICATION_JSON_VALUE])
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

        if (kochava_device_id != null && kochava_device_id != KOCHAVA_DEVICE_ID) {
            if (app_guid == null) {
                logger.error("App_guid can't be null")
                return ResponseEntity(google_id, HttpStatus.BAD_REQUEST)
            }
            kochavaService.post(
                app_guid, google_id, kochava_device_id
            )
        }

        if (appsflyer_uid != null && appsflyer_uid != APPSFLYER_UID) {
            if (pack == null) {
                logger.error("Pack can't be null")
                return ResponseEntity(google_id, HttpStatus.BAD_REQUEST)
            }
            if (appsflyer_pid == null) {
                logger.error("Appsflyer_uid can't be null")
                return ResponseEntity(google_id, HttpStatus.BAD_REQUEST)
            }

            appsFlyerService.post(
                pack, appsflyer_pid, AppsFlyerDto(
                    appsflyer_id = appsflyer_uid,
                    advertising_id = google_id,
                    eventValue = EventValue()
                )
            )
        }
        return ResponseEntity(google_id, HttpStatus.OK)
    }
}