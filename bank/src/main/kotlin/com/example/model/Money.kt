package com.example.model

import java.math.BigDecimal

data class Money(
    var value: BigDecimal,
    val currency: Int
){
    operator fun minusAssign(amount: Money) {
        assert (this.currency == amount.currency)
        this.value -= amount.value
    }

    operator fun plusAssign(amount: Money) {
        assert (this.currency == amount.currency)
        this.value += amount.value
    }

    operator fun compareTo(amount: Money): Int {
        assert (this.currency == amount.currency)
        return this.value.compareTo(amount.value)
    }
}
