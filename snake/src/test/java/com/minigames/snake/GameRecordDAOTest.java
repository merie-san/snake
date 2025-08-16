package com.minigames.snake;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;

public class GameRecordDAOTest {

	@SuppressWarnings("resource")
	@ClassRule
	public static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.33").withDatabaseName("snakedb")
			.withUsername("root").withPassword("root");
	private static EntityManagerFactory emf;
	private GameRecordDAO dao;

	@BeforeClass
	public static void setupEMF() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(PersistenceConfiguration.JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
		map.put(PersistenceConfiguration.JDBC_URL, mysql.getJdbcUrl());
		map.put(PersistenceConfiguration.JDBC_USER, "root");
		map.put(PersistenceConfiguration.JDBC_PASSWORD, "root");
		emf = new PersistenceConfiguration("SnakePU").properties(map).managedClass(BaseEntity.class)
				.managedClass(GameRecord.class).managedClass(GameSetting.class).createEntityManagerFactory();
		emf.getSchemaManager().create(true);
	}

	@Before
	public void setup() {
		dao = new GameRecordDAO(emf);
	}

	@After
	public void tearDown() {
		emf.getSchemaManager().truncate();
	}

	@AfterClass
	public static void tearDownEMF() {
		emf.close();
	}

	@Test
	public void testFindAllEmpty() {
		assertThat(dao.findAll()).isEmpty();
	}

	@Test
	public void testFindAllOne() {
		GameSetting setting = new GameSetting("2", 10, 10, 2);
		GameRecord record = new GameRecord("1", 10, LocalDate.now(), setting);
		emf.runInTransaction(em -> {
			em.persist(setting);
			em.persist(record);
		});
		assertThat(dao.findAll()).containsExactly(new GameRecord[] { record });
	}

}
