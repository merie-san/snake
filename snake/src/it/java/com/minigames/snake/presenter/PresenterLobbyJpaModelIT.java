package com.minigames.snake.presenter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.minigames.snake.model.BaseEntity;
import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameRecordHibernateDao;
import com.minigames.snake.model.GameRecordHibernateDaoImpl;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.GameSettingHibernateDao;
import com.minigames.snake.model.GameSettingHibernateDaoImpl;
import com.minigames.snake.model.SnakeHibernateRepository;
import com.minigames.snake.model.SnakeRepository;
import com.minigames.snake.view.SnakeView;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import jakarta.persistence.SchemaValidationException;
import jakarta.persistence.TypedQuery;

public class PresenterLobbyJpaModelIT {
	private SnakeLobbyPresenter presenter;
	private static EntityManagerFactory emf;
	private GameSettingHibernateDao settingDao;
	private GameRecordHibernateDao recordDao;
	private SnakeRepository repository;
	private SnakeView view;

	@Before
	public void setup() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(PersistenceConfiguration.JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
		map.put(PersistenceConfiguration.JDBC_URL, "jdbc:mysql://localhost:3306/snakedb");
		map.put(PersistenceConfiguration.JDBC_USER, "root");
		map.put(PersistenceConfiguration.JDBC_PASSWORD, "root");
		emf = new PersistenceConfiguration("SnakePU").properties(map).managedClass(BaseEntity.class)
				.managedClass(GameRecord.class).managedClass(GameSetting.class).createEntityManagerFactory();
		try {
			emf.getSchemaManager().validate();
			emf.getSchemaManager().truncate();
		} catch (SchemaValidationException e) {
			emf.getSchemaManager().create(true);
		}
		settingDao = new GameSettingHibernateDaoImpl();
		settingDao.setEmf(emf);
		recordDao = new GameRecordHibernateDaoImpl();
		recordDao.setEmf(emf);
		repository = new SnakeHibernateRepository(settingDao, recordDao, emf);
		presenter = new SnakeLobbyPresenterImpl(repository);
		view = mock(SnakeView.class);
	}

	@After
	public void tearDown() {
		emf.getSchemaManager().drop(true);
		emf.close();
	}

	@Test
	public void testLoadConfigurations() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameSetting setting2 = new GameSetting("2", 10, 11, 2);
		GameSetting setting3 = new GameSetting("3", 6, 10, 3);
		emf.runInTransaction(em -> {
			em.persist(setting1);
			em.persist(setting2);
			em.persist(setting3);
		});
		assertThat(presenter.loadConfigurations()).containsExactlyInAnyOrder(setting1, setting2, setting3);
	}

	@Test
	public void testSaveConfiguration() {
		presenter.saveConfiguration(10, 11, 20, "setting", view);
		verify(view).updateLobby();
		verifyNoMoreInteractions(view);

		Collection<GameSetting> settings = emf.callInTransaction(em -> {
			return em.createQuery("SELECT g FROM GameSetting g", GameSetting.class).getResultList();
		});
		assertThat(settings).anySatisfy(s -> {
			assertThat(s.getName()).isEqualTo("setting");
			assertThat(s.getWidth()).isEqualTo(10);
			assertThat(s.getHeight()).isEqualTo(11);
			assertThat(s.getObstacleNumber()).isEqualTo(20);
		});
	}

	@Test
	public void testRenameConfiguration() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		setting1.setName("old name");
		emf.runInTransaction(em -> {
			em.persist(setting1);
		});
		presenter.renameConfiguration(setting1, "new name", view);
		GameSetting setting2 = emf.callInTransaction(em -> em.find(GameSetting.class, setting1.getId()));
		assertThat(setting2).isEqualTo(setting1).satisfies(s -> assertThat(s.getName()).isEqualTo("new name"));
	}

	@Test
	public void testRemoveConfiguration() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		setting1.setName("old name");
		emf.runInTransaction(em -> {
			em.persist(setting1);
		});
		presenter.removeConfiguration(setting1, view);
		verify(view).updateLobby();
		verifyNoMoreInteractions(view);
		GameSetting setting2 = emf.callInTransaction(em -> em.find(GameSetting.class, setting1.getId()));
		assertThat(setting2).isEqualTo(setting1).satisfies(s -> assertThat(s.isDeleted()).isTrue());
	}

	@Test
	public void testLoadHistory() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameSetting setting2 = new GameSetting("2", 20, 10, 2);
		GameRecord record1 = new GameRecord("3", 10, LocalDate.now(), setting1);
		GameRecord record2 = new GameRecord("4", 5, LocalDate.now(), setting2);
		GameRecord record3 = new GameRecord("5", 31, LocalDate.now(), setting2);
		emf.runInTransaction(em -> {
			em.persist(setting1);
			em.persist(setting2);
			em.persist(record1);
			em.persist(record2);
			em.persist(record3);
		});
		assertThat(presenter.loadHistory()).containsExactlyInAnyOrder(record1, record2, record3);
	}

	@Test
	public void testLoadHighScore() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameSetting setting2 = new GameSetting("2", 20, 10, 2);
		GameRecord record1 = new GameRecord("3", 10, LocalDate.now(), setting1);
		GameRecord record2 = new GameRecord("4", 5, LocalDate.now(), setting2);
		GameRecord record3 = new GameRecord("5", 31, LocalDate.now(), setting2);
		emf.runInTransaction(em -> {
			em.persist(setting1);
			em.persist(setting2);
			em.persist(record1);
			em.persist(record2);
			em.persist(record3);
		});
		assertThat(presenter.loadHighScore()).isEqualTo(31);
	}

	@Test
	public void testRemoveRecord() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameRecord record1 = new GameRecord("3", 10, LocalDate.now(), setting1);
		emf.runInTransaction(em -> {
			em.persist(setting1);
			em.persist(record1);
		});
		presenter.removeRecord(record1, view);
		verify(view).updateLobby();
		verifyNoMoreInteractions(view);
		GameRecord gameRecord = emf.callInTransaction(em -> em.find(GameRecord.class, record1.getId()));
		assertThat(gameRecord).isNull();
	}

	@Test
	public void testClearGameHistory() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameSetting setting2 = new GameSetting("2", 20, 10, 2);
		GameRecord record1 = new GameRecord("3", 10, LocalDate.now(), setting1);
		GameRecord record2 = new GameRecord("4", 5, LocalDate.now(), setting2);
		GameRecord record3 = new GameRecord("5", 31, LocalDate.now(), setting2);
		emf.runInTransaction(em -> {
			em.persist(setting1);
			em.persist(setting2);
			em.persist(record1);
			em.persist(record2);
			em.persist(record3);
		});
		presenter.clearGameHistory(view);
		verify(view).updateLobby();
		verifyNoMoreInteractions(view);
		Collection<GameRecord> records = emf.callInTransaction(em -> {
			TypedQuery<GameRecord> query = em.createQuery("select r from GameRecord r", GameRecord.class);
			return query.getResultList();
		});
		assertThat(records).isEmpty();
	}

}
