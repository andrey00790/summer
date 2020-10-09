package com.example.beans.profiler

import com.example.beans.factory.impl.DefaultBeanFactory
import com.example.context.support.ApplicationContext
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.findAnnotation

class ProfilerBeanFactory(ctx: ApplicationContext) : DefaultBeanFactory(ctx) {
    override fun <T : Any> createBean(type: KClass<T>): T {
        val bean = super.createBean(type)
        var beanWithProfile:T?=null
        if (type.declaredFunctions.any {it.findAnnotation<Profile>()!=null }) {
           beanWithProfile = Proxy.newProxyInstance(bean.javaClass.classLoader, bean.javaClass.interfaces, object : InvocationHandler {
                override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>): Any? {
                    var result: Any? = null
                    println("Bean do ${method?.name}")
                    val currentTime = System.currentTimeMillis()
                    result = method?.invoke(bean, *args)
                    println("Spent time = ${System.currentTimeMillis() - currentTime}")
                    return result
                }
            }) as T
        }

        return beanWithProfile ?: bean
    }
}