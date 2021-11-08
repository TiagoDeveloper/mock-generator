package com.tiagodeveloper.mockgenerator

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.containers.PostgreSQLContainer

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class BaseIntegrationTest {

    class FixedPostgreSQLContainer : PostgreSQLContainer<FixedPostgreSQLContainer>("postgres:12") {
        companion object {
            private var container: FixedPostgreSQLContainer? = null

            fun getInstance(): FixedPostgreSQLContainer{
                if (container == null) {
                    container = FixedPostgreSQLContainer()
                }
                return container!!
            }
        }


        override fun start() {
            super.start()
            System.setProperty("DB_URL", container!!.jdbcUrl);
            System.setProperty("DB_USERNAME", container!!.username);
            System.setProperty("DB_PASSWORD", container!!.password);
        }
    }

    companion object {
        val postgres =  FixedPostgreSQLContainer.getInstance().apply {
            withDatabaseName("postgres")
            withUsername("postgres")
            withPassword("postgres")
            withExposedPorts(5432)
            withInitScript("data/init.sql")
            start()
        }

    }
}
