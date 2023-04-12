package com.donghyukki.multidatasource.presentation

import com.donghyukki.common.response.HyukiResponse
import com.donghyukki.multidatasource.application.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/api/v1/user")
    fun saveUser(): HyukiResponse<Unit> {
        userService.save()
        return HyukiResponse.success()
    }
}
