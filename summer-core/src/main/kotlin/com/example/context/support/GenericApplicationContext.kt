package com.example.context.support

import com.example.beans.config.Config
import com.example.beans.factory.impl.BeanFactory
import com.example.beans.factory.impl.DefaultBeanFactory
import org.reflections.Reflections
import kotlin.reflect.KClass

class GenericApplicationContext(config: Config) : ApplicationContext {
    private val beanFactory: BeanFactory = createBeanFactory(config)


    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getBean(clazz: KClass<T>): T =
        beanFactory.getOrCreate(clazz)


    private fun createBeanFactory(
        config: Config,
    ): BeanFactory =
        DefaultBeanFactory(
            config,
            Reflections(config.packagesToScan())
        )

}
