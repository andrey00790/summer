package com.example.beans.factory.impl

import kotlin.reflect.KClass

interface BeanFactory {
    fun <T : Any> getOrCreate(type: KClass<T>): T

    fun <T : Any> addBean(beanName: String, bean: T)
}
