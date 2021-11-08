package com.tiagodeveloper.mockgenerator.repository

import com.tiagodeveloper.mockgenerator.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, String> {
}