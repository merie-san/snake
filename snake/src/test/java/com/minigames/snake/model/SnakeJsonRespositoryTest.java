package com.minigames.snake.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class SnakeJsonRespositoryTest {

	private SnakeJsonRepository repository;

	@Before
	public void setUp() throws IOException {
		repository = new SnakeJsonRepository(null, null, ArrayList<GameRecord>::new, ArrayList<GameSetting>::new);
	}

	@After
	public void tearDown() {
		File dir = new File("jsonDB");
		Stream.of(dir.listFiles()).forEach(File::delete);
		dir.delete();
	}

	@Test
	public void testFindAllRecordsEmpty() {
		Collection<GameRecord> records = new ArrayList<>();
		repository.setRecords(records);
		assertThat(repository.findAllRecords()).isEmpty();
	}

	@Test
	public void testFindAllRecordsSingle() {
		Collection<GameRecord> records = new ArrayList<>();
		GameRecord record1 = new GameRecord("1", 20, LocalDate.now(), new GameSetting());
		records.add(record1);
		repository.setRecords(records);
		assertThat(repository.findAllRecords()).hasSize(1).containsExactlyInAnyOrder(record1);
	}

	@Test
	public void testFindAllRecordsMultiple() {
		Collection<GameRecord> records = new ArrayList<>();
		GameRecord record1 = new GameRecord("1", 20, LocalDate.now(), new GameSetting());
		GameRecord record2 = new GameRecord("2", 30, LocalDate.now(), new GameSetting());
		records.add(record1);
		records.add(record2);
		repository.setRecords(records);
		assertThat(repository.findAllRecords()).hasSize(2).containsExactlyInAnyOrder(record1, record2);
	}

	@Test
	public void testFindAllSettingsEmpty() {
		Collection<GameSetting> settings = new ArrayList<>();
		repository.setSettings(settings);
		assertThat(repository.findAllSettings()).isEmpty();
	}

	@Test
	public void testFindAllSettingsSingle() {
		Collection<GameSetting> settings = new ArrayList<>();
		GameSetting setting1 = new GameSetting("1", 1, 20, 1);
		settings.add(setting1);
		repository.setSettings(settings);
		assertThat(repository.findAllSettings()).hasSize(1).containsExactlyInAnyOrder(setting1);
	}

	@Test
	public void testFindAllSettingsMultiple() {
		Collection<GameSetting> settings = new ArrayList<>();
		GameSetting setting1 = new GameSetting("1", 1, 20, 1);
		GameSetting setting2 = new GameSetting("2", 1, 30, 2);
		settings.add(setting1);
		settings.add(setting2);
		repository.setSettings(settings);
		assertThat(repository.findAllSettings()).hasSize(2).containsExactlyInAnyOrder(setting1, setting2);
	}

	@Test
	public void testFindAllSettingsAllDeleted() {
		Collection<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 1, 20, 1);
		GameSetting setting2 = new GameSetting("2", 1, 30, 2);
		setting1.setDeleted(true);
		setting2.setDeleted(true);
		settings.add(setting1);
		settings.add(setting2);
		repository.setSettings(settings);
		assertThat(repository.findAllSettings()).isEmpty();
	}

	@Test
	public void testFindAllSettingsDeletedAndNot() {
		Collection<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 1, 20, 1);
		GameSetting setting2 = new GameSetting("2", 1, 30, 2);
		setting2.setDeleted(true);
		settings.add(setting1);
		settings.add(setting2);
		repository.setSettings(settings);
		assertThat(repository.findAllSettings()).containsExactlyInAnyOrder(setting1);
	}

	@Test
	public void testCreateRecord() {
		repository.setRecords(new ArrayList<GameRecord>());
		repository.createRecord(new GameRecord("1", 20, LocalDate.now(), new GameSetting()));
		assertThat(repository.getRecords()).contains(new GameRecord("1", 20, LocalDate.now(), new GameSetting()));
	}

	@Test
	public void testCreateSetting() {
		repository.setSettings(new ArrayList<GameSetting>());
		repository.createSetting(new GameSetting("1", 1, 20, 1));
		assertThat(repository.getSettings()).contains(new GameSetting("1", 1, 20, 1));
	}

	@Test
	public void testDeleteRecord() {
		Collection<GameRecord> records = new ArrayList<>();
		GameRecord record1 = new GameRecord("1", 20, LocalDate.now(), new GameSetting());
		GameRecord record2 = new GameRecord("2", 30, LocalDate.now(), new GameSetting());
		records.add(record1);
		records.add(record2);
		repository.setRecords(records);
		repository.deleteRecord(record1);
		assertThat(repository.getRecords()).doesNotContain(record1);
	}

	@Test
	public void testDeleteSetting() {
		Collection<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 1, 20, 1);
		GameSetting setting2 = new GameSetting("2", 1, 30, 2);
		settings.add(setting1);
		settings.add(setting2);
		repository.setSettings(settings);
		repository.deleteSetting(setting1);
		assertThat(repository.getSettings()).contains(setting1);
		assertThat(setting1.isDeleted()).isTrue();
	}

	@Test
	public void testClearHistoryEmpty() {
		Collection<GameRecord> records = new ArrayList<GameRecord>();
		repository.setRecords(records);
		repository.clearHistory();
		assertThat(repository.getRecords()).isEmpty();
	}

	@Test
	public void testClearHistorySingle() {
		Collection<GameRecord> records = new ArrayList<GameRecord>();
		records.add(new GameRecord());
		repository.setRecords(records);
		repository.clearHistory();
		assertThat(repository.getRecords()).isEmpty();
	}

	@Test
	public void testClearHistoryMultiple() {
		Collection<GameRecord> records = new ArrayList<GameRecord>();
		records.add(new GameRecord("1", 20, LocalDate.now(), new GameSetting()));
		records.add(new GameRecord("2", 20, LocalDate.now(), new GameSetting()));
		repository.setRecords(records);
		repository.clearHistory();
		assertThat(repository.getRecords()).isEmpty();
	}

	@Test
	public void testRenameSetting() {
		Collection<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 10, 20, 1);
		GameSetting setting2 = new GameSetting("2", 30, 20, 1);
		settings.add(setting1);
		settings.add(setting2);
		repository.setSettings(settings);
		repository.renameSetting(setting1, "New name");
		assertThat(repository.getSettings()).contains(setting1);
		assertThat(setting1.getName()).isEqualTo("New name");
	}

	@Test
	public void testClose() {
		assertThatCode(() -> {
			Collection<GameSetting> settings = new ArrayList<GameSetting>();
			GameSetting setting1 = new GameSetting("1", 10, 20, 1);
			GameSetting setting2 = new GameSetting("2", 30, 20, 1);
			settings.add(setting1);
			settings.add(setting2);
			repository.setSettings(settings);
			Collection<GameRecord> records = new ArrayList<GameRecord>();
			records.add(new GameRecord("3", 20, LocalDate.now(), setting1));
			records.add(new GameRecord("4", 20, LocalDate.now(), setting2));
			repository.setRecords(records);
			repository.close();
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
			assertThat(mapper.readValue(new File("jsonDB/snake_settings_db.json"),
					new TypeReference<Collection<GameSetting>>() {
					})).containsExactlyElementsOf(settings);
			assertThat(mapper.readValue(new File("jsonDB/snake_records_db.json"),
					new TypeReference<Collection<GameRecord>>() {
					})).containsExactlyElementsOf(records);
		}).doesNotThrowAnyException();
	}

	@Test
	public void testCloseException() {
		assertThatCode(() -> {
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
			Collection<GameSetting> settings = new ArrayList<GameSetting>();
			GameSetting setting1 = new GameSetting("1", 10, 20, 1);
			GameSetting setting2 = new GameSetting("2", 30, 20, 1);
			settings.add(setting1);
			settings.add(setting2);
			repository.setSettings(settings);
			File file = new File("jsonDB/snake_settings_db.json");
			file.setReadOnly();
			repository.close();
			assertThat(mapper.readValue(new File("jsonDB/snake_settings_db.json"),
					new TypeReference<Collection<GameSetting>>() {
					})).isEmpty();
		}).doesNotThrowAnyException();
	}

}
