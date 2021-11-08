package com.tiagodeveloper.mockgenerator

import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


class MockGeneratorApplicationTests: BaseIntegrationTest() {


	@Test
	@SqlGroup(
		Sql(
			statements = ["""
				INSERT INTO customer (id, name, email, active, birth_date)
				VALUES('c2336da6-16b2-42ca-9320-4ca920c47033', 'Tânia Pereira da Silva',
			 	'taniapereiradasilva@gmail.com', true, '1998-12-14');
			"""],
			executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD ),
		Sql(
			statements = ["""
				DELETE FROM customer
				WHERE id='c2336da6-16b2-42ca-9320-4ca920c47033';
			"""],
			executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	)
	@Transactional(propagation = Propagation.NEVER)
	fun contextLoads() {

		Assert.assertNotNull(postgres.containerName)
	}





//	companion object {
//		@Container
//		val container = FixedPostgreSQLContainer()
//	}
//
//	class FixedPostgreSQLContainer : PostgreSQLContainer<FixedPostgreSQLContainer>("postgres:13.3") {
//		init {
//
//			super.addFixedExposedPort(5422, 5432)
//			val teste:URL? = javaClass.classLoader.getResource("data/init.sql")
//			this.withExposedPorts(5422)
//				.withDatabaseName("zupper_bff")
//				.withUsername("zupper_bff")
//				.withPassword("zupper_bff")
//				.withInitScript("data/init.sql")
//				.start()
//		}
//	}
}