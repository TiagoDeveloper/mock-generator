package com.tiagodeveloper.mockgenerator.service

import com.tiagodeveloper.mockgenerator.entity.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile

interface CustomerService {
    fun findAll(pageable: Pageable): Page<Customer>
    fun generateCustomer(files: Array<MultipartFile>)
}