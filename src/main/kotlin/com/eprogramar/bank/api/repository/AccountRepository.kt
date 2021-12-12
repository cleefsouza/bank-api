package com.eprogramar.bank.api.repository

import com.eprogramar.bank.api.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
}