package com.tiagodeveloper.mockgenerator.controller

import com.tiagodeveloper.mockgenerator.entity.Customer
import com.tiagodeveloper.mockgenerator.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class CustomerController(
    val customerService: CustomerService
) {

    @GetMapping
    fun getCustomers(pageable: Pageable): Page<Customer> {
        return customerService.findAll(pageable)
    }

    @PostMapping
    fun generatedCustomers(@RequestParam("files")  files: Array<MultipartFile>): ResponseEntity<Void> {
        customerService.generateCustomer(files)

        return ResponseEntity(HttpStatus.OK)
    }
}