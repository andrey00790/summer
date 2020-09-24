package com.example.configuration

import com.example.beans.config.Config
import kotlin.reflect.KClass

class BankConfig: Config {
    private val interface2Class = mapOf<KClass<Any>,KClass<Any>>(

    )

    override fun <T : Any> findImplClass(type: KClass<T>): KClass<T>? = interface2Class[type].let { it as KClass<T> }

    override fun packagesToScan(): List<String> = listOf("com.example")
}
