package com.example.beans.postprocessor.impl

import com.example.beans.factory.annotation.Autowired
import com.example.beans.factory.annotation.Component
import com.example.beans.factory.impl.BeanFactory
import com.example.beans.postprocessor.BeanPostProcessor
import kotlin.reflect.full.findAnnotation

class AutowiredAnnotationBeanPostProcessor : BeanPostProcessor {
    override fun configure(obj: Any, beanFactory: BeanFactory) {
        obj::class.findAnnotation<Component>()
            ?.let {
                obj::class.constructors
                    .filter { it::class.findAnnotation<Autowired>() != null && it.parameters.isNotEmpty() }
                    .map {
                        val constructorArgs = it.parameters.map {
                            beanFactory.getOrCreate(obj::class)
                        }
                        beanFactory.addBean(obj::class.java.canonicalName, it.call(constructorArgs))
                    }
            }
    }
}
