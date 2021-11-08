package com.tiagodeveloper.mockgenerator.entity

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Customer(
    @Id
    var id: String,
    val name: String,
    val email: String,
    val birthDate: LocalDate,
    val active: Boolean
)