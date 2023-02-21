package com.donghyukki.app.config

import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata

/**
 * Environment 에 `ci` 라는 키 값이 있는 경우 해당 어노테이션이 있는 Bean 은 활성화 시키지 않는다.
 * Active.Profiles 가 `test` 인 경우에만 해당 어노테이션이 있는 Bean 을 활성화 시킨다.
 * eg. ./gradlew test -Pci=true
 */
class OnlyLocalTestCondition : Condition {

    companion object {
        private const val TEST_ACTIVE_PROFILES = "test"
        private const val CI_TEST_ENV = "ci"
        private const val CI_TEST_ENV_VALUE = "true"
    }

    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        if (context.environment.getProperty(CI_TEST_ENV) == CI_TEST_ENV_VALUE) {
            return false
        }

        if (context.environment.activeProfiles.contains(TEST_ACTIVE_PROFILES)) {
            return true
        }

        return false
    }
}
