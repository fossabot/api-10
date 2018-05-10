package com.dongfg.project.api.web.controller.admin

import com.dongfg.project.api.common.WeChatTemplate.ScheduleReminder.ScheduleReminderData
import com.dongfg.project.api.common.WeChatTemplate.TaskNotify.TaskNotifyData
import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.model.WeChatUser
import com.dongfg.project.api.service.WeChatService
import com.dongfg.project.api.service.WeChatTemplateService
import com.dongfg.project.api.web.payload.GenericPayload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/admin/wechat")
@RestController
class AdminWeChatController {

    @Autowired
    private lateinit var apiProperty: ApiProperty

    @Autowired
    private lateinit var weChatService: WeChatService

    @Autowired
    private lateinit var weChatTemplateService: WeChatTemplateService

    @GetMapping("users")
    fun users(@RequestParam page: Int, @RequestParam size: Int): Page<WeChatUser> {
        return weChatService.listUser(PageRequest.of(page, size))
    }

    @PostMapping("scheduleReminder", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun scheduleReminder(touser: String?, topic: String, time: String, location: String, desc: String, level: String): GenericPayload {
        val data = ScheduleReminderData(
                topic = topic,
                time = time,
                location = location,
                desc = desc,
                level = level
        )
        val openId = if (touser != null && touser.isNotEmpty()) touser else apiProperty.push.openId
        return weChatTemplateService.scheduleReminder(openId, data)
    }

    @PostMapping("taskNotify", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun taskNotify(touser: String?, date: String, time: String, name: String, type: String, status: String, desc: String): GenericPayload {
        val data = TaskNotifyData(
                time = "$date $time",
                name = name,
                type = type,
                status = status,
                desc = desc
        )
        val openId = if (touser != null && touser.isNotEmpty()) touser else apiProperty.push.openId
        return weChatTemplateService.taskNotify(openId, data)
    }
}