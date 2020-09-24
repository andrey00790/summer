package com.example.service.impl

import com.example.beans.factory.annotation.Autowired
import com.example.beans.factory.annotation.Component
import com.example.model.Client
import com.example.model.Money
import com.example.service.BankService
import com.example.service.AccountService
import com.example.service.DboService

@Component
class DboServiceImpl @Autowired constructor(
   private val bankService: BankService,
   private val accountService: AccountService
): DboService {
    override fun transferMoney(fromClient: Client, toClient: Client, amount: Money) {
        val fromAccount = accountService.getDefaultAccount(fromClient)
        val toAccount = accountService.getDefaultAccount(toClient)

        bankService.transferMoney(fromAccount, toAccount, amount)
    }

}
