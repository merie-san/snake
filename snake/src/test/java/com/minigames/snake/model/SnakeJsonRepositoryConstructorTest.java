package com.minigames.snake.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class SnakeJsonRepositoryConstructorTest {

	@After
	public void tearDown() {
		File dir = new File("jsonDB");
		if (dir.exists()) {
			Stream.of(dir.listFiles()).forEach(File::delete);
			dir.delete();
		}
	}

	@Test
	public void testRecordsSupplierNull() {
		Supplier<Collection<GameSetting>> supplier = new Supplier<Collection<GameSetting>>() {
			@Override
			public Collection<GameSetting> get() {
				return new ArrayList<GameSetting>();
			}
		};
		assertThatThrownBy(() -> new SnakeJsonRepository(null, null, null, supplier))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Collection suppliers need to be both provided");
	}

	@Test
	public void testSettingsSupplierNull() {
		Supplier<Collection<GameRecord>> supplier = new Supplier<Collection<GameRecord>>() {
			@Override
			public Collection<GameRecord> get() {
				return new ArrayList<GameRecord>();
			}
		};
		assertThatThrownBy(() -> new SnakeJsonRepository(null, null, supplier, null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Collection suppliers need to be both provided");
	}

	@Test
	public void testSuppliersBothNull() {
		assertThatThrownBy(() -> new SnakeJsonRepository(null, null, null, null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Collection suppliers need to be both provided");
	}

	@Test
	public void testDefaultFiles() {
		assertThatCode(() -> {
			SnakeJsonRepository repository = new SnakeJsonRepository(null, null,
					new Supplier<Collection<GameRecord>>() {
						@Override
						public Collection<GameRecord> get() {
							return new ArrayList<GameRecord>();
						}
					}, new Supplier<Collection<GameSetting>>() {
						@Override
						public Collection<GameSetting> get() {
							return new ArrayList<GameSetting>();
						}
					});
			assertThat(repository.getRecordsFile().getPath()).isEqualTo("jsonDB/snake_records_db.json");
			assertThat(repository.getSettingsFile().getPath()).isEqualTo("jsonDB/snake_settings_db.json");
			assertThat(new File("jsonDB").list()).containsExactlyInAnyOrder("snake_records_db.json",
					"snake_settings_db.json");
			assertThat(repository.getRecords()).isEmpty();
			assertThat(repository.getSettings()).isEmpty();
		}).doesNotThrowAnyException();

	}

	@Test
	public void testNewFiles() {
		assertThatCode(() -> {
			SnakeJsonRepository repository = new SnakeJsonRepository(new File("jsonDB/records.json"),
					new File("jsonDB/settings.json"), new Supplier<Collection<GameRecord>>() {
						@Override
						public Collection<GameRecord> get() {
							return new ArrayList<GameRecord>();
						}
					}, new Supplier<Collection<GameSetting>>() {
						@Override
						public Collection<GameSetting> get() {
							return new ArrayList<GameSetting>();
						}
					});
			assertThat(repository.getRecordsFile().getPath()).isEqualTo("jsonDB/records.json");
			assertThat(repository.getSettingsFile().getPath()).isEqualTo("jsonDB/settings.json");
			assertThat(new File("jsonDB").list()).containsExactlyInAnyOrder("records.json", "settings.json");
			assertThat(repository.getRecords()).isEmpty();
			assertThat(repository.getSettings()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testLoadExistingFiles() {
		assertThatCode(() -> {
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
			Collection<GameSetting> settings = new ArrayList<GameSetting>();
			GameSetting setting1 = new GameSetting("3", 1, 20, 1);
			GameSetting setting2 = new GameSetting("4", 1, 30, 2);
			settings.add(setting1);
			settings.add(setting2);
			File file = new File("jsonDB/snake_settings_db.json");
			file.getParentFile().mkdirs();
			mapper.writeValue(file, settings);
			Collection<GameRecord> records = new ArrayList<>();
			GameRecord record1 = new GameRecord("1", 20, LocalDate.now(), setting1);
			GameRecord record2 = new GameRecord("2", 30, LocalDate.now(), setting2);
			records.add(record1);
			records.add(record2);
			mapper.writeValue(new File("jsonDB/snake_records_db.json"), records);
			SnakeJsonRepository repository = new SnakeJsonRepository(null, null,
					new Supplier<Collection<GameRecord>>() {
						@Override
						public Collection<GameRecord> get() {
							return new ArrayList<GameRecord>();
						}
					}, new Supplier<Collection<GameSetting>>() {
						@Override
						public Collection<GameSetting> get() {
							return new ArrayList<GameSetting>();
						}
					});
			assertThat(repository.getRecordsFile().getPath()).isEqualTo("jsonDB/snake_records_db.json");
			assertThat(repository.getSettingsFile().getPath()).isEqualTo("jsonDB/snake_settings_db.json");
			assertThat(new File("jsonDB").list()).containsExactlyInAnyOrder("snake_records_db.json",
					"snake_settings_db.json");
			assertThat(repository.getRecords()).containsExactlyElementsOf(records);
			assertThat(repository.getSettings()).containsExactlyElementsOf(settings);
		}).doesNotThrowAnyException();
	}

}
