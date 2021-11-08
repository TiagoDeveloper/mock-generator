package com.tiagodeveloper.mockgenerator.converter

import java.sql.Date
import java.time.LocalDate
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class LocalDateConverter: AttributeConverter<LocalDate, Date> {

    override fun convertToDatabaseColumn(localDate: LocalDate?): Date {
        return Date.valueOf(localDate)
    }

    override fun convertToEntityAttribute(date: Date?): LocalDate? {
        return date?.toLocalDate()
    }
}