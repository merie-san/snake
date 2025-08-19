package com.minigames.snake.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SnakeSQLRepositoryTest {

	@Mock
	private GameRecordDAO recordDao;

	@Mock
	private GameSettingDAO settingDao;

	@InjectMocks
	private SnakeSQLRepository repository;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindAllRecordsEmpty() {
		ArrayList<GameRecord> records = new ArrayList<GameRecord>();
		when(recordDao.findAll()).thenReturn(records);
		assertThat(repository.findAllRecords()).isEmpty();
		verify(recordDao).findAll();
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testFindAllRecordsSingle() {
		ArrayList<GameRecord> records = new ArrayList<GameRecord>();
		GameRecord record = new GameRecord();
		records.add(record);
		when(recordDao.findAll()).thenReturn(records);
		assertThat(repository.findAllRecords()).containsExactly(record);
		verify(recordDao).findAll();
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testFindAllRecordsMultiple() {
		ArrayList<GameRecord> records = new ArrayList<GameRecord>();
		GameRecord record1 = new GameRecord("1", 20, LocalDate.now(), new GameSetting());
		GameRecord record2 = new GameRecord("2", 30, LocalDate.now(), new GameSetting());
		records.add(record1);
		records.add(record2);
		when(recordDao.findAll()).thenReturn(records);
		assertThat(repository.findAllRecords()).containsExactlyInAnyOrder(record1, record2);
		verify(recordDao).findAll();
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testFindAllSettingsEmpty() {
		ArrayList<GameSetting> settings = new ArrayList<GameSetting>();
		when(settingDao.findAll()).thenReturn(settings);
		assertThat(repository.findAllSettings()).isEmpty();
		;
		verify(settingDao).findAll();
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testFindAllSettingsSingle() {
		ArrayList<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting = new GameSetting();
		settings.add(setting);
		when(settingDao.findAll()).thenReturn(settings);
		assertThat(repository.findAllSettings()).containsExactly(setting);
		verify(settingDao).findAll();
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testFindAllSettingsMultiple() {
		ArrayList<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 20, 1, 1);
		GameSetting setting2 = new GameSetting("2", 30, 1, 2);
		settings.add(setting1);
		settings.add(setting2);
		when(settingDao.findAll()).thenReturn(settings);
		assertThat(repository.findAllSettings()).containsExactlyInAnyOrder(setting1, setting2);
		verify(settingDao).findAll();
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testFindAllSettingsAllDeleted() throws Exception {
		ArrayList<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 20, 1, 1);
		GameSetting setting2 = new GameSetting("2", 30, 1, 2);
		setting1.setDeleted(true);
		setting2.setDeleted(true);
		settings.add(setting1);
		settings.add(setting2);
		when(settingDao.findAll()).thenReturn(settings);
		assertThat(repository.findAllSettings()).isEmpty();
		verify(settingDao).findAll();
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testFindAllSettingsDeletedAndNotDeleted() throws Exception {
		ArrayList<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 20, 1, 1);
		GameSetting setting2 = new GameSetting("2", 30, 1, 2);
		setting2.setDeleted(true);
		settings.add(setting1);
		settings.add(setting2);
		when(settingDao.findAll()).thenReturn(settings);
		assertThat(repository.findAllSettings()).containsExactlyInAnyOrder(setting1);
		verify(settingDao).findAll();
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testCreateRecord() {
		GameRecord record = new GameRecord();
		repository.createRecord(record);
		verify(recordDao).create(record);
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testCreateSetting() {
		GameSetting setting = new GameSetting();
		repository.createSetting(setting);
		verify(settingDao).create(setting);
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testDeleteRecord() {
		GameRecord record = new GameRecord();
		repository.deleteRecord(record);
		verify(recordDao).delete(record);
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testDeletesetting() {
		GameSetting setting = new GameSetting();
		repository.deleteSetting(setting);
		verify(settingDao).delete(setting);
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testClearHistoryEmpty() {
		when(recordDao.findAll()).thenReturn(new ArrayList<GameRecord>());
		repository.clearHistory();
		verify(recordDao).findAll();
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testClearHistorySingle() {
		ArrayList<GameRecord> records = new ArrayList<GameRecord>();
		records.add(new GameRecord());
		when(recordDao.findAll()).thenReturn(records);
		repository.clearHistory();
		verify(recordDao).findAll();
		for (GameRecord g : records) {
			verify(recordDao).delete(g);
		}
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testClearHistoryMultiple() {
		ArrayList<GameRecord> records = new ArrayList<GameRecord>();
		records.add(new GameRecord("1", 20, LocalDate.now(), new GameSetting()));
		records.add(new GameRecord("2", 20, LocalDate.now(), new GameSetting()));
		when(recordDao.findAll()).thenReturn(records);
		repository.clearHistory();
		verify(recordDao).findAll();
		for (GameRecord g : records) {
			verify(recordDao).delete(g);
		}
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testRenameSetting() {
		GameSetting setting = new GameSetting();
		repository.renameSetting(setting, "New name");
		verify(settingDao).rename(setting, "New name");
		verifyNoMoreInteractions(settingDao);

	}

}
