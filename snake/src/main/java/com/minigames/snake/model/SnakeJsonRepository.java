package com.minigames.snake.model;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class SnakeJsonRepository implements SnakeRepository {

	public static final Logger LOGGER = LogManager.getLogger(SnakeJsonRepository.class);
	private Collection<GameRecord> records;
	private Collection<GameSetting> settings;
	private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	private File recordsFile = new File(DEFAULT_RECORDS_JSON_DB_PATH);
	private File settingsFile = new File(DEFAULT_SETTINGS_JSON_DB_PATH);
	public static final String DEFAULT_RECORDS_JSON_DB_PATH = "jsonDB/snake_records_db.json";
	public static final String DEFAULT_SETTINGS_JSON_DB_PATH = "jsonDB/snake_settings_db.json";

	public SnakeJsonRepository(File recordsFile, File settingsFile, Supplier<Collection<GameRecord>> recordsSupplier,
			Supplier<Collection<GameSetting>> settingsSupplier) throws IOException {
		if (recordsSupplier == null || settingsSupplier == null) {
			throw new IllegalArgumentException("Collection suppliers need to be both provided");
		}
		if (recordsFile != null) {
			this.recordsFile = recordsFile;
		}
		if (settingsFile != null) {
			this.settingsFile = settingsFile;
		}

		if (this.recordsFile.exists()) {
			records = mapper.readValue(this.recordsFile, new TypeReference<Collection<GameRecord>>() {
			});
		} else {
			records = recordsSupplier.get();
			mapper.writeValue(this.recordsFile, records);
		}
		if (this.settingsFile.exists()) {
			settings = mapper.readValue(this.settingsFile, new TypeReference<Collection<GameSetting>>() {
			});
		} else {
			settings = settingsSupplier.get();
			mapper.writeValue(this.settingsFile, settings);
		}
	}

	@Override
	public Collection<GameRecord> findAllRecords() {
		return records;
	}

	@Override
	public Collection<GameSetting> findAllSettings() {
		return settings.stream().filter(s -> !s.isDeleted()).collect(Collectors.toList());
	}

	@Override
	public void createRecord(GameRecord gameRecord) {
		records.add(gameRecord);
	}

	@Override
	public void createSetting(GameSetting setting) {
		settings.add(setting);
	}

	@Override
	public void deleteRecord(GameRecord gameRecord) {
		records.remove(gameRecord);
	}

	@Override
	public void clearHistory() {
		records.clear();
	}

	@Override
	public void deleteSetting(GameSetting setting) {
		settings.forEach(s -> {
			if (s.equals(setting)) {
				s.setDeleted(true);
			}
		});
	}

	@Override
	public void renameSetting(GameSetting setting, String newName) {
		settings.forEach(s -> {
			if (s.equals(setting)) {
				s.setName(newName);
			}
		});
	}

	@Override
	public void close() {
		try {
			mapper.writeValue(recordsFile, records);
			mapper.writeValue(settingsFile, settings);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	// for testing
	@Generated
	Collection<GameRecord> getRecords() {
		return records;
	}

	// for testing
	@Generated
	void setRecords(Collection<GameRecord> records) {
		this.records = records;
	}

	// for testing
	@Generated
	Collection<GameSetting> getSettings() {
		return settings;
	}

	// for testing
	@Generated
	void setSettings(Collection<GameSetting> settings) {
		this.settings = settings;
	}

	// for testing
	@Generated
	File getRecordsFile() {
		return recordsFile;
	}

	// for testing
	@Generated
	void setRecordsFile(File recordsFile) {
		this.recordsFile = recordsFile;
	}

	// for testing
	@Generated
	File getSettingsFile() {
		return settingsFile;
	}

	// for testing
	@Generated
	void setSettingsFile(File settingsFile) {
		this.settingsFile = settingsFile;
	}

}
