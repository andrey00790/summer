package com.example.configuration

import com.example.beans.config.Config
import com.example.beans.factory.impl.BeanFactory
import com.example.beans.profiler.CglibProfilerBeanFactory
import com.example.beans.profiler.ProfilerBeanFactory
import com.example.dal.dao.AccountDao
import com.example.dal.dao.impl.AccountDaoImpl
import com.example.service.AccountService
import com.example.service.BankService
import com.example.service.DboService
import com.example.service.impl.AccountServiceImpl
import com.example.service.impl.BankServiceImpl
import com.example.service.impl.DboServiceImpl
import kotlin.reflect.KClass

class BankConfig: Config {
    private val interface2Class = mapOf<KClass<*>,KClass<*>>(
        BeanFactory::class to CglibProfilerBeanFactory::class,
        AccountService::class to AccountServiceImpl::class,
        BankService::class to BankServiceImpl::class,
        DboService::class to DboServiceImpl::class,
        AccountDao::class to AccountDaoImpl::class
    )

    override fun <T : Any> findImplClass(type: KClass<T>): KClass<T>? = interface2Class[type]?.let { it as KClass<T> }

    override fun packagesToScan(): List<String> = listOf("com.example")
}
