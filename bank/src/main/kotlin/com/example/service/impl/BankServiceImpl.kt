package com.example.service.impl

import com.example.beans.factory.annotation.Autowired
import com.example.beans.factory.annotation.Component
import com.example.beans.profiler.Profile
import com.example.model.Account
import com.example.model.Money
import com.example.service.BankService

@Component
class BankServiceImpl constructor(
) : BankService {
    @Profile
    override fun transferMoney(fromAccount: Account, toAccount: Account, amount: Money) {
        validateTransfer(fromAccount, amount)

        fromAccount.amount -= amount
        toAccount.amount += amount
    }

    private fun validateTransfer(fromAccount: Account, amount: Money) {
        assert(fromAccount.amount >= amount)
    }
}
