package study.kafka.kafkaproducer.controller

import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import study.kafka.kafkaproducer.controller.model.UserEventDto
import java.text.SimpleDateFormat
import java.util.Date

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class ProduceController(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ProduceController::class.java)
    }

    @GetMapping("/api/select")
    fun selectColor(
        @RequestHeader("user-agent") userAgentName: String,
        @RequestParam(value = "color") colorName: String,
        @RequestParam(value = "user") userName: String,
    ) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
        val now = Date()
        val gson = Gson()
        val userEventDto = UserEventDto(
            timestamp = sdf.format(now),
            userAgent = userAgentName,
            colorName = colorName,
            userName = userName
        )
        val jsonColorLog = gson.toJson(userEventDto)

        kafkaTemplate.send("select-color", jsonColorLog).addCallback(
            object : ListenableFutureCallback<SendResult<String, String>> {
                override fun onSuccess(result: SendResult<String, String>?) {
                    logger.info(result.toString())
                }

                override fun onFailure(ex: Throwable) {
                    logger.error(ex.message, ex)
                }
            })
    }

}