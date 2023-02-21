package com.donghyukki.app.config

import org.springframework.context.annotation.Conditional

@Conditional(OnlyLocalTestCondition::class)
annotation class OnlyLocalTest
