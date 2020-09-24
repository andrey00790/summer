package com.example.beans.postprocessor

import com.example.beans.factory.impl.BeanFactory

interface BeanPostProcessor {
    fun configure(obj: Any, beanFactory: BeanFactory)
}
