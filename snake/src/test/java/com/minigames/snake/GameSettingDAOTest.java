package com.minigames.snake;

import static org.junit.Assert.fail;

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

public class GameSettingDAOTest {

	@SuppressWarnings("resource")
	@ClassRule
	public static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.33").withDatabaseName("snakedb")
			.withUsername("root").withPassword("root");
	private static EntityManagerFactory emf;
	private GameSettingDAO dao;

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
		dao = new GameSettingDAO(emf);
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
	public void test() {
		fail("Not yet implemented");
	}

}
