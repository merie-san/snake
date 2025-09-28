package com.minigames.snake;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import com.minigames.snake.model.SnakeJsonRepository;
import com.minigames.snake.model.SnakeRepository;
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
	private static final String DEFAULT_USERNAME = "root";
	private static final String DEFAULT_PASSWORD = "root";
	private static final String DF_HOST_ADDRESS = "localhost";
	private static final String DF_HOST_PORT = "3306";
	private static final String DF_DATABASE_NAME = "snakedb";
	private static final String DEFAULT_RECORDS_JSON_DB_PATH = "jsonDB/snake_records_db.json";
	private static final String DEFAULT_SETTINGS_JSON_DB_PATH = "jsonDB/snake_settings_db.json";

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("H", "host", true, "mysql host address");
		options.addOption("p", "port", true, "mysql host port");
		options.addOption("d", "db-name", true, "database name");
		options.addOption("U", "username", true, "login username");
		options.addOption("P", "password", true, "login password");
		options.addOption("t", "test", false, "test the app");
		options.addOption("j", "json", false, "use a json file for storage");
		options.addOption("jr", "json-records", true, "json file for game record storage");
		options.addOption("js", "json-settings", true, "json file for game setting storage");
		options.addOption("h", "help", false, "show help");

		HelpFormatter formatter = HelpFormatter.builder().get();
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("h")) {
				formatter.printHelp("SnakeApp", "App options: ", options.getOptions(), null, true);
				return;
			}
			SnakeRepository repository;
			if (cmd.hasOption("j")) {
				repository = initializeJsonRepository(cmd);
			} else {
				repository = initializeHibernateRepository(cmd);
			}
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

	private static SnakeRepository initializeHibernateRepository(CommandLine cmd) {
		SnakeRepository repository;
		Map<String, String> map = new HashMap<>();
		map.put(PersistenceConfiguration.JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
		map.put(PersistenceConfiguration.JDBC_URL,
				String.format("jdbc:mysql://%s:%s/%s", cmd.hasOption("H") ? cmd.getOptionValue("H") : DF_HOST_ADDRESS,
						cmd.hasOption("p") ? cmd.getOptionValue("p") : DF_HOST_PORT,
						cmd.hasOption("n") ? cmd.getOptionValue("d") : DF_DATABASE_NAME));
		map.put(PersistenceConfiguration.JDBC_USER, cmd.hasOption("U") ? cmd.getOptionValue("U") : DEFAULT_USERNAME);
		map.put(PersistenceConfiguration.JDBC_PASSWORD,
				cmd.hasOption("P") ? cmd.getOptionValue("P") : DEFAULT_PASSWORD);

		EntityManagerFactory emf = new PersistenceConfiguration("SnakePU").properties(map)
				.managedClass(BaseEntity.class).managedClass(GameRecord.class).managedClass(GameSetting.class)
				.createEntityManagerFactory();
		checkDatabaseSchema(emf);

		GameRecordHibernateDao recordDAO = new GameRecordHibernateDaoImpl();
		GameSettingHibernateDao settingDAO = new GameSettingHibernateDaoImpl();
		repository = new SnakeHibernateRepository(settingDAO, recordDAO, emf);
		return repository;
	}

	private static SnakeRepository initializeJsonRepository(CommandLine cmd) throws IOException {
		SnakeRepository repository;
		repository = new SnakeJsonRepository(
				cmd.hasOption("jr") ? new File(cmd.getOptionValue("jr")) : new File(DEFAULT_RECORDS_JSON_DB_PATH),
				cmd.hasOption("js") ? new File(cmd.getOptionValue("js")) : new File(DEFAULT_SETTINGS_JSON_DB_PATH),
				() -> new ArrayList<>(), () -> new ArrayList<>());
		return repository;
	}

	private static void checkDatabaseSchema(EntityManagerFactory emf) {
		try {
			emf.getSchemaManager().validate();
		} catch (SchemaValidationException e) {
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
