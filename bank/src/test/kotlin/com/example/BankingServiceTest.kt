package com.example

import com.example.configuration.BankConfig
import com.example.context.support.GenericApplicationContext
import com.example.dal.provider.DataProvider
import com.example.model.Money
import com.example.service.AccountService
import com.example.service.DboService
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BankingServiceTest {

    @Test
    fun `Should be success transfer money between clients`() {
        val amount = Money(1000.toBigDecimal(), 643)

        val ctx = GenericApplicationContext(BankConfig())

        val dboService = ctx.getBean(DboService::class)
        val accountService = ctx.getBean(AccountService::class)

        dboService.transferMoney(DataProvider.fromClient, DataProvider.toClient, amount)

        val currentAmount = accountService.getDefaultAccount(DataProvider.fromClient).amount.value

        assertEquals(99000.toBigDecimal(), currentAmount)
    }
}
