package com.donghyukki.app.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode
import io.kotest.extensions.spring.SpringExtension

class KotestProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(SpringExtension)
    override val isolationMode: IsolationMode = IsolationMode.InstancePerLeaf
}
