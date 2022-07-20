package com.example.demo.datasource.mock

import com.example.demo.datasource.BankDataSource
import com.example.demo.model.Bank
import org.springframework.stereotype.Repository

@Repository("mock")
class MockBankDataSource : BankDataSource {

    val banks = mutableListOf(
        Bank("1234", 3.14, 17),
        Bank("1010", 17.0, 0),
        Bank("5678", 0.0, 100),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNo: String): Bank =
        banks.firstOrNull() { it.accountNo == accountNo }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNo")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNo == bank.accountNo }) {
            throw IllegalArgumentException("Bank with account number ${bank.accountNo} already exists.")
        }
        banks.add(bank)

        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNo == bank.accountNo }
            ?: throw NoSuchElementException("Could not find a bank with account number ${bank.accountNo}")

        banks.remove(currentBank)
        banks.add(bank)

        return bank
    }

    override fun deleteBank(accountNo: String):String {
        val currentBank = banks.firstOrNull { it.accountNo == accountNo }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNo")

        banks.remove(currentBank)
        return "Bank account deleted"
    }
}