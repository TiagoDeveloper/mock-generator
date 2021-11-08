package com.tiagodeveloper.mockgenerator.service.impl

import com.tiagodeveloper.mockgenerator.entity.Customer
import com.tiagodeveloper.mockgenerator.repository.CustomerRepository
import com.tiagodeveloper.mockgenerator.service.CustomerService
import kotlinx.coroutines.runBlocking
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.text.Normalizer
import java.time.LocalDate
import java.util.Random
import java.util.UUID
import java.util.function.Supplier
import java.util.stream.Collectors

@Service
@Slf4j
class CustomerServiceImpl (
    val customerRepository: CustomerRepository
):CustomerService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun findAll(pageable: Pageable): Page<Customer> {
        return customerRepository.findAll(pageable)
    }

    override fun generateCustomer(files: Array<MultipartFile>) {
        logger.info("start")
        val contentFile = files.mapIndexed {  index, file ->
            index to getContent(file).lines
        }.toMap()

        val fullNames = mutableListOf<String>()
        val aux = mutableListOf<String>()

        contentFile.forEach { (key, value) ->

            if(key == 0){
                fullNames.addAll(value)
            } else {
                fullNames.forEach { startWith ->
                    value.forEach { endWith ->
                        aux.add("$startWith $endWith")
                    }
                }
                fullNames.clear()
                fullNames.addAll(aux)
                aux.clear()
            }

        }

        process(fullNames)

        logger.info("end")
    }

    private fun process(content: List<String>) = runBlocking {
            content.parallelStream().forEachOrdered {
                customerRepository.save(
                    Customer(
                        id = UUID.randomUUID().toString(),
                        name = it,
                        active = true,
                        email = "${it.removeAccent().toLowerCase().trimAll()}@gmail.com",
                        birthDate = generateRandomLocalDate()
                    )
                )
            }

    }

    private fun generateRandomLocalDate(): LocalDate {
        var now = LocalDate.now()
        val random = Random()

        now = now.minusYears(
            random
                .longs(15, 30)
                .findFirst()
                .asLong
        ).minusMonths(
            random
                .longs(1, 12)
                .findFirst()
                .asLong
        ).minusDays(
            random
                .longs(1, 30)
                .findFirst()
                .asLong
        )
        return now
    }

    private fun getContent(file: MultipartFile, page: Long = 0, size: Long = 100): Content {
        val stream =  Supplier {
            BufferedReader(InputStreamReader(file.inputStream, StandardCharsets.UTF_8)).lines()
            .filter {
                it.isNotBlank()
            }
        }

        return Content(
            count =  stream.get().count(),
            lines = stream.get().skip(page * size)
                    .limit(size)
                    .collect(Collectors.toList())
        )
    }
}

data class Content(
    val count: Long,
    val lines: List<String>
)

fun String.removeAccent(): String {
    val text = Normalizer.normalize(this, Normalizer.Form.NFD);
    return text.replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "");
}
fun String.trimAll(): String {
    return this.replace(" ", "");
}

//fun main() {
//
//    val list = listOf("Tiago", "Tania", "Luke", "Michelle", "Lucas", "Alessandra", "Ana Paula", "Monique", "Letícia", "Manu",
//    "João", "Tony", "Bruno", "Ricardo", "Melissa", "Jamile", "Débora", "Gabriela", "Danúbia","Simon",
//    "Daniela", "Pedro", "Fabrício", "Joana", "Sibele", "Janaína", "Rodrigo", "Mário","Marcelo", "Nilmar",
//    "Marlon", "Adão", "Romulo", "Felipe", "Maia", "Alexandre", "Cristiano", "Rafaela", "Valéria",
//    "Juliana", "Natália", "Núbia", "Isadora", "Camila", "Thayanne", "Fabíola", "Josiane", "Rebeca",
//    "Arcel", "Aender", "Aluir", "Leonardo", "Igor", "Maria", "Laís", "Denise", "Tassia", "Ananda",
//    "Zoraides", "Birliut", "Antonio", "Lucimar", "Ivanio")
//
//    val pageSize = 6
//    val predicateQtdNumberOfPage = list.size % pageSize == 0
//    val numberOfPages = list.size / pageSize
//    val lastPageSize = if(predicateQtdNumberOfPage) pageSize else (list.size - (numberOfPages * pageSize))
//
//    var page = 0
//    var initialIndex = 0
//    while(page < numberOfPages){
//
//            initialIndex = page*pageSize
//
//            println(" $initialIndex to ${initialIndex+pageSize}")
//            println(list.subList(initialIndex, (initialIndex+pageSize)))
//
//        page++
//    }
//
//    initialIndex = page*pageSize
//
//    if (!predicateQtdNumberOfPage) {
//        println(" Last $initialIndex to ${initialIndex + lastPageSize}")
//        println(list.subList(initialIndex, (initialIndex + lastPageSize)))
//    }
//
//    println("""
//        Data size: ${list.size}
//        Number of pages: $numberOfPages
//        Last page size: $lastPageSize
//    """)
//
//}

