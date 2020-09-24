package com.example.beans.config

import kotlin.reflect.KClass

interface Config {
    fun <T : Any> findImplClass(type: KClass<T>): KClass<T>?
    fun packagesToScan(): List<String>
}
