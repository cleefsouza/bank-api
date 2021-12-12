package com.eprogramar.bank.api.services

import com.eprogramar.bank.api.model.Account
import com.eprogramar.bank.api.repository.AccountRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountServiceImpl(private val repository: AccountRepository) : AccountService {

    override fun create(account: Account): Account {
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
}