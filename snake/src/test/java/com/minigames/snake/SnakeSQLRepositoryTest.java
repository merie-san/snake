package com.minigames.snake;

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
	public void testFindAllRecords() {
		repository.findAllRecords();
		verify(recordDao).findAll();
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testFindAllSettings() {
		repository.findAllSettings();
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
