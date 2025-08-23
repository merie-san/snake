package com.minigames.snake.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityManagerFactory;

public class SnakeHibernateRepositoryTest {

	@Mock
	private GameRecordHibernateDao recordDao;

	@Mock
	private GameSettingHibernateDao settingDao;

	@Mock
	private EntityManagerFactory emf;

	@InjectMocks
	private SnakeHibernateRepository repository;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	public void testFindAllRecordsEmpty() {
		clearInvocations(recordDao);
		Collection<GameRecord> records = new ArrayList<GameRecord>();
		when(recordDao.findAll()).thenReturn(records);
		assertThat(repository.findAllRecords()).isEmpty();
		verify(recordDao).findAll();
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testFindAllRecordsSingle() {
		clearInvocations(recordDao);
		Collection<GameRecord> records = new ArrayList<GameRecord>();
		GameRecord gameRecord = new GameRecord();
		records.add(gameRecord);
		when(recordDao.findAll()).thenReturn(records);
		assertThat(repository.findAllRecords()).containsExactly(gameRecord);
		verify(recordDao).findAll();
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testFindAllRecordsMultiple() {
		clearInvocations(recordDao);
		Collection<GameRecord> records = new ArrayList<GameRecord>();
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
		clearInvocations(settingDao);
		Collection<GameSetting> settings = new ArrayList<GameSetting>();
		when(settingDao.findAll()).thenReturn(settings);
		assertThat(repository.findAllSettings()).isEmpty();
		verify(settingDao).findAll();
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testFindAllSettingsSingle() {
		clearInvocations(settingDao);
		Collection<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting = new GameSetting();
		settings.add(setting);
		when(settingDao.findAll()).thenReturn(settings);
		assertThat(repository.findAllSettings()).containsExactly(setting);
		verify(settingDao).findAll();
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testFindAllSettingsMultiple() {
		clearInvocations(settingDao);
		Collection<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 1, 20, 1);
		GameSetting setting2 = new GameSetting("2", 1, 30, 2);
		settings.add(setting1);
		settings.add(setting2);
		when(settingDao.findAll()).thenReturn(settings);
		assertThat(repository.findAllSettings()).containsExactlyInAnyOrder(setting1, setting2);
		verify(settingDao).findAll();
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testFindAllSettingsAllDeleted() {
		clearInvocations(settingDao);
		Collection<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 1, 20, 1);
		GameSetting setting2 = new GameSetting("2", 1, 30, 2);
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
	public void testFindAllSettingsBothPresent() {
		clearInvocations(settingDao);
		Collection<GameSetting> settings = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 1, 20, 1);
		GameSetting setting2 = new GameSetting("2", 1, 30, 2);
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
		clearInvocations(recordDao);
		GameRecord gameRecord = new GameRecord();
		repository.createRecord(gameRecord);
		verify(recordDao).create(gameRecord);
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testCreateSetting() {
		clearInvocations(settingDao);
		GameSetting setting = new GameSetting();
		repository.createSetting(setting);
		verify(settingDao).create(setting);
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testDeleteRecord() {
		clearInvocations(recordDao);
		GameRecord gameRecord = new GameRecord();
		repository.deleteRecord(gameRecord);
		verify(recordDao).delete(gameRecord);
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testDeletesetting() {
		clearInvocations(settingDao);
		GameSetting setting = new GameSetting();
		repository.deleteSetting(setting);
		verify(settingDao).delete(setting);
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testClearHistoryEmpty() {
		clearInvocations(recordDao);
		Collection<GameRecord> records = new ArrayList<GameRecord>();
		when(recordDao.findAll()).thenReturn(records);
		repository.clearHistory();
		verify(recordDao).findAll();
		verifyNoMoreInteractions(recordDao);
	}

	@Test
	public void testClearHistorySingle() {
		clearInvocations(recordDao);
		Collection<GameRecord> records = new ArrayList<GameRecord>();
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
		clearInvocations(recordDao);
		Collection<GameRecord> records = new ArrayList<GameRecord>();
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
		clearInvocations(settingDao);
		GameSetting setting = new GameSetting();
		repository.renameSetting(setting, "New name");
		verify(settingDao).rename(setting, "New name");
		verifyNoMoreInteractions(settingDao);
	}

	@Test
	public void testCloseRepository() {
		repository.close();
		verify(emf).close();
		verifyNoMoreInteractions(emf);
	}

}
