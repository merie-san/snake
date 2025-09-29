package com.minigames.snake.presenter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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

public class PresenterMatchJpaModelIT {

	private SnakeMatchPresenterImpl presenter;
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
		presenter = new SnakeMatchPresenterImpl(repository, null, null);
		view = mock(SnakeView.class);
	}

	@After
	public void tearDown() {
		emf.getSchemaManager().drop(true);
		emf.close();
	}

	@Test
	public void testEndMatch() {
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		presenter.setPlaying(true);
		presenter.setRawScore(20);
		presenter.setConfiguration(setting);
		emf.runInTransaction(em -> em.persist(setting));
		presenter.endMatch(view);
		verify(view).updateLobby();
		verify(view).updateMatch();
		verifyNoMoreInteractions(view);
		Collection<GameRecord> gameRecords = emf.callInTransaction(em -> {
			TypedQuery<GameRecord> query = em.createQuery("SELECT r FROM GameRecord r", GameRecord.class);
			return query.getResultList();
		});
		assertThat(gameRecords).anySatisfy(r -> {
			assertThat(r.getDate()).isToday();
			assertThat(r.getScore()).isEqualTo(20);
			assertThat(r.getSetting()).isEqualTo(setting);
		});
	}
}
