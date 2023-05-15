package com.donghyukki.app.utils

import com.donghyukki.app.infra.jpa.BaseEntity
import io.kotest.matchers.equality.FieldsEqualityCheckConfig
import io.kotest.matchers.equality.beEqualComparingFields
import io.kotest.matchers.should
import kotlin.reflect.full.memberProperties

inline fun <reified T : BaseEntity> T.shouldBeEqualEntityExcludeBaseEntity(
    other: T,
) {
    val propertiesToExclude = listOf(
        BaseEntity::createdAt,
        BaseEntity::updatedAt,
        T::class.memberProperties.first { it.name == BaseEntity::createdAt.name },
        T::class.memberProperties.first { it.name == BaseEntity::updatedAt.name }
    )

    this should beEqualComparingFields(
        other,
        FieldsEqualityCheckConfig(
            ignorePrivateFields = false,
            ignoreComputedFields = true,
            propertiesToExclude = propertiesToExclude
        )
    )
}
