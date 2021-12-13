package com.eprogramar.bank.api.services

import com.eprogramar.bank.api.model.Account
import com.eprogramar.bank.api.repository.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.util.*

@Service
class AccountServiceImpl(private val repository: AccountRepository) : AccountService {

    override fun create(account: Account): Account {
        validateFields(account.name, account.document);

        return repository.save(account);
    }

    override fun getAll(): List<Account> {
        return repository.findAll();
    }

    override fun getById(id: Long): Optional<Account> {
        return repository.findById(id);
    }

    override fun update(id: Long, account: Account): Optional<Account> {
        val acc = getById(id);

        if (!acc.isPresent) return Optional.empty<Account>();

        return acc.map {
            val accountToUpdate = it.copy(
                name = account.name,
                document = account.document,
                phone = account.phone
            )

            repository.save(accountToUpdate)
        }
    }

    override fun delete(id: Long) {
        repository.findById(id).map {
            repository.delete(it)
        }.orElseThrow { throw RuntimeException("Id not found: $id") };
    }

    private fun validateFields(name: String, document: String) {
        Assert.hasLength(name, "Name cannot be empty!")
        Assert.isTrue(name.length >= 5, "Name should be 5 character!")
        Assert.hasLength(document, "Document cannot be empty!")
        Assert.isTrue(document.length == 11, "Document should be 11 character!")
    }
}