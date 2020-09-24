package com.example.beans.aop.impl

import com.example.beans.aop.ProxyConfigurator
import kotlin.reflect.KClass

class BenchmarkProxyConfigurator : ProxyConfigurator {
    override fun <T : Any> wrapWithPoxy(obj: T, type: KClass<T>): T? {
        TODO("Not yet implemented")
    }

}
