package com.example.beans.aop

import kotlin.reflect.KClass

interface ProxyConfigurator {
    fun <T : Any> wrapWithPoxy(obj: T,  type: KClass<T>): T?
}
