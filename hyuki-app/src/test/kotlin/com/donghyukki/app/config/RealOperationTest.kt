package com.donghyukki.app.config

/**
실제 환경 및 실제 기기 및 서비스에 영향을 줄 수 있는 테스트 이므로 Gradle test 에서 제외시켜야 하며
운영 중 문제가 있는것 같은 경우에만 빠르게 테스트 해볼 수 있도록 제공한다.
테스트 동작 시키기 .config(enabled = true)
테스트 제외 시키기 .config(enabled = false)
 **/
@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class RealOperationTest
