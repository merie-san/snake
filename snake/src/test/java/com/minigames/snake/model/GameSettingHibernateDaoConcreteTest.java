package com.minigames.snake.model;

import static org.assertj.core.api.Assertions.assertThat;

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

public class GameSettingHibernateDaoConcreteTest {

	@SuppressWarnings("resource")
	@ClassRule
	public static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.33").withDatabaseName("snakedb")
			.withUsername("root").withPassword("root");
	private static EntityManagerFactory emf;
	private GameSettingHibernateDaoImpl dao;

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
		dao = new GameSettingHibernateDaoImpl();
		dao.setEmf(emf);
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
	public void testFindAllSettingEmpty() {
		assertThat(dao.findAll()).isEmpty();
	}

	@Test
	public void testFindAllSettingSingle() {
		GameSetting setting = new GameSetting("1", 10, 10, 2);
		emf.runInTransaction(em -> {
			em.persist(setting);
		});
		assertThat(dao.findAll()).containsExactly(new GameSetting[] { setting });
	}

	@Test
	public void testFindAllSettingMultiple() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameSetting setting2 = new GameSetting("2", 10, 20, 2);
		emf.runInTransaction(em -> {
			em.persist(setting1);
			em.persist(setting2);
		});
		assertThat(dao.findAll()).containsExactlyInAnyOrder(new GameSetting[] { setting1, setting2 });
	}

	@Test
	public void testCreateSetting() {
		GameSetting setting = new GameSetting("1", 10, 10, 2);
		dao.create(setting);
		assertThat(emf.<GameSetting>callInTransaction(em -> {
			return em.find(GameSetting.class, setting.getId());
		})).isNotNull();
	}

	@Test
	public void testDeleteSetting() {
		GameSetting setting = new GameSetting("1", 10, 10, 2);
		emf.runInTransaction(em -> {
			em.persist(setting);
		});
		dao.delete(setting);
		assertThat(emf.<GameSetting>callInTransaction(em -> {
			return em.find(GameSetting.class, setting.getId());
		}).isDeleted()).isTrue();
	}

	@Test
	public void testRenameSetting() {
		GameSetting setting = new GameSetting("1", 10, 10, 2);
		emf.runInTransaction(em -> {
			em.persist(setting);
		});
		dao.rename(setting, "New name");
		assertThat(emf.<GameSetting>callInTransaction(em -> {
			return em.find(GameSetting.class, setting.getId());
		}).getName()).isEqualTo("New name");
	}

}
