package com.minigames.snake;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.help.HelpFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class SnakeApp {

	private static final Logger LOGGER = LogManager.getLogger(SnakeApp.class);
	private static SnakeWindowView frame;

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("H", "host", true, "mysql host address");
		options.addOption("p", "port", true, "mysql host port");
		options.addOption("d", "db-name", true, "database name");
		options.addOption("U", "username", true, "login username");
		options.addOption("P", "password", true, "login password");
		options.addOption("t", "test", false, "test the app");
		options.addOption("h", "help", false, "show help");

		String hostAddress = "localhost";
		String hostPort = "3306";
		String databaseName = "snakedb";
		String username = "root";
		String password = "root";

		HelpFormatter formatter = HelpFormatter.builder().get();
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("H")) {
				hostAddress = cmd.getOptionValue("H");
			} else if (cmd.hasOption("p")) {
				hostPort = cmd.getOptionValue("p");
			} else if (cmd.hasOption("d")) {
				databaseName = cmd.getOptionValue("d");
			} else if (cmd.hasOption("U")) {
				username = cmd.getOptionValue("U");
			} else if (cmd.hasOption("P")) {
				password = cmd.getOptionValue("P");
			} else if (cmd.hasOption("h")) {
				formatter.printHelp("SnakeApp", "App options: ", options.getOptions(), null, true);
				return;
			}

			Map<String, String> map = new HashMap<>();
			map.put(PersistenceConfiguration.JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
			map.put(PersistenceConfiguration.JDBC_URL,
					String.format("jdbc:mysql://%s:%s/%s", hostAddress, hostPort, databaseName));
			map.put(PersistenceConfiguration.JDBC_USER, username);
			map.put(PersistenceConfiguration.JDBC_PASSWORD, password);

			EntityManagerFactory emf = new PersistenceConfiguration("SnakePU").properties(map)
					.managedClass(BaseEntity.class).managedClass(GameRecord.class).managedClass(GameSetting.class)
					.createEntityManagerFactory();
			checkDatabaseSchema(emf);

			GameRecordHibernateDao recordDAO = new GameRecordHibernateDaoImpl();
			GameSettingHibernateDao settingDAO = new GameSettingHibernateDaoImpl();
			SnakeHibernateRepository repository = new SnakeHibernateRepository(settingDAO, recordDAO, emf);
			SnakeLobbyPresenterImpl lobbyPresenter = new SnakeLobbyPresenterImpl(repository);
			SnakeMatchPresenterImpl matchPresenter = new SnakeMatchPresenterImpl(repository,
					new RandomObstaclesSupplier(), new RandomPositionSupplier());

			EventQueue.invokeLater(() -> {
				frame = new SnakeWindowView(lobbyPresenter, matchPresenter);
				frame.setCloseAction(() -> {
					frame.dispose();
					System.exit(0);
				});
				frame.pack();
				frame.setMinimumSize(new Dimension(600, 500));
				frame.setVisible(true);
				frame.updateLobby();
				if (cmd.hasOption("t")) {
					frame.close();
				}
			});
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	private static void checkDatabaseSchema(EntityManagerFactory emf) {
		try {
			emf.getSchemaManager().validate();
		} catch (SchemaValidationException e) {
			emf.getSchemaManager().drop(true);
			emf.getSchemaManager().create(true);
		}
	}

	// for testing
	@Generated
	static void updateUI() {
		frame.updateLobby();
		frame.updateMatch();
	}

}
