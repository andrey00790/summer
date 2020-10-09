package com.example.beans.factory.impl


import com.example.beans.factory.annotation.Autowired
import com.example.context.support.ApplicationContext
import kotlin.reflect.KClass

open class DefaultBeanFactory(
    private val ctx: ApplicationContext,
) : BeanFactory {

    override fun <T : Any> createBean(type: KClass<T>): T {
        val javaType = type.java
        val beanName = type.simpleName

        var constr = javaType.constructors.filter {
            it.isAnnotationPresent(Autowired::class.java) && it.parameters.isNotEmpty()
        }

        if (constr.size > 1) {
            throw Exception("Component has many costructors with annotation @Autowired")
        }

        if (constr.isEmpty()){
            constr = javaType.constructors.filter {
                it.parameters.isEmpty()
            }
        }

        if (constr.isEmpty()) {
            throw Exception("Component hasn't costructor without parameters or with annotation @Autowired")
        }

        val constructorArgs = constr.first().parameters.map {
            ctx.getBean(it.type.kotlin)
        }

        return constr.first().newInstance(*constructorArgs.toTypedArray()) as T
    }
}
