package com.minigames.snake;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import com.minigames.snake.model.BaseEntity;
import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameRecordHibernateDao;
import com.minigames.snake.model.GameRecordHibernateDaoImpl;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.GameSettingHibernateDao;
import com.minigames.snake.model.GameSettingHibernateDaoImpl;
import com.minigames.snake.model.Generated;
import com.minigames.snake.model.SnakeHibernateRepository;
import com.minigames.snake.presenter.RandomObstaclesSupplier;
import com.minigames.snake.presenter.RandomPositionSupplier;
import com.minigames.snake.presenter.SnakeLobbyPresenterImpl;
import com.minigames.snake.presenter.SnakeMatchPresenterImpl;
import com.minigames.snake.view.SnakeWindowView;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import jakarta.persistence.SchemaValidationException;

/**
 * Hello world!
 */
@Generated
public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Map<String, String> map = new HashMap<>();
			map.put(PersistenceConfiguration.JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
			map.put(PersistenceConfiguration.JDBC_URL, "jdbc:mysql://localhost:3306/snakedb");
			map.put(PersistenceConfiguration.JDBC_USER, "root");
			map.put(PersistenceConfiguration.JDBC_PASSWORD, "root");
			EntityManagerFactory emf = new PersistenceConfiguration("SnakePU").properties(map)
					.managedClass(BaseEntity.class).managedClass(GameRecord.class).managedClass(GameSetting.class)
					.createEntityManagerFactory();
			try {
				emf.getSchemaManager().validate();
			} catch (SchemaValidationException e) {
				emf.getSchemaManager().drop(true);
				emf.getSchemaManager().create(true);
			}
			GameRecordHibernateDao recordDAO = new GameRecordHibernateDaoImpl();
			GameSettingHibernateDao settingDAO = new GameSettingHibernateDaoImpl();
			SnakeHibernateRepository repository = new SnakeHibernateRepository(settingDAO, recordDAO, emf);
			SnakeLobbyPresenterImpl lobbyPresenter = new SnakeLobbyPresenterImpl(repository);
			SnakeMatchPresenterImpl matchPresenter = new SnakeMatchPresenterImpl(repository,
					new RandomObstaclesSupplier(), new RandomPositionSupplier());
			try {
				SnakeWindowView frame = new SnakeWindowView(lobbyPresenter, matchPresenter);
				frame.pack();
				frame.setMinimumSize(new Dimension(600, 500));
				frame.setVisible(true);
				frame.updateLobby();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
