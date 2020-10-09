package com.example.context.support

import com.example.beans.config.Config
import com.example.beans.factory.impl.BeanFactory
import com.example.beans.prepare.PrepareProcessors
import org.reflections.Reflections
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlin.reflect.KClass

class GenericApplicationApplicationContext(private val config: Config) : ApplicationContextAware {
    private val beans: ConcurrentMap<String, Any> = ConcurrentHashMap()
    private val scanner: Reflections = Reflections(config.packagesToScan())
    private val beanFactory = resolveImpl(BeanFactory::class).constructors.first().call(this)

    init {
        processPrepareContext()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getBean(clazz: KClass<T>): T =
        findBean(clazz)
            ?: createBean(clazz)

    private fun <T : Any> createBean(clazz: KClass<T>): T =
        resolveImpl(clazz)
            .let { clazz ->
                beanFactory.createBean(clazz)
                    .also { beans[clazz.simpleName] = it }
            }

    private fun <T : Any> findBean(type: KClass<T>): T? =
        beans[resolveImpl(type).simpleName] as T?

    private fun <T : Any> resolveImpl(type: KClass<T>) =
        if (type.java.isInterface) {
            config.findImplClass(type)
                ?: resolveByType(type)
        } else type

    private fun <T : Any> resolveByType(type: KClass<T>): KClass<T> {
        val classes = scanner.getSubTypesOf(type.java)
        if (classes.size != 1) {
            throw IllegalStateException("0 or more than one impl found for type $type please update your config.")
        }
        return classes.iterator().next().kotlin as KClass<T>
    }

    private fun processPrepareContext() {
        for (clazz in scanner.getSubTypesOf(PrepareProcessors::class.java)) {
            clazz.getDeclaredConstructor()
                .newInstance()
                .configure(scanner, this)
        }
    }

    override fun <T : Any> addBean(beanName: String, bean: T) {
        beans[beanName] = bean
    }

}
