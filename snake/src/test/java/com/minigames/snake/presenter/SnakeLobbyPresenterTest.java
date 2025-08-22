package com.minigames.snake.presenter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.SnakeRepository;
import com.minigames.snake.view.SnakeView;

public class SnakeLobbyPresenterTest {

	private SnakeView view;

	@Mock
	private SnakeRepository repository;

	@InjectMocks
	private SnakeLobbyPresenterImpl presenter;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		view=mock(SnakeView.class);
	}

	@After
	public void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	public void testClosePresenter() {
		presenter.close();
		verify(repository).close();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testLoadConfigurationsEmpty() {
		Collection<GameSetting> configurations = new ArrayList<GameSetting>();
		when(repository.findAllSettings()).thenReturn(configurations);
		assertThat(presenter.loadConfigurations()).isEmpty();
		verify(repository).findAllSettings();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testLoadConfigurationsSingle() {
		Collection<GameSetting> configurations = new ArrayList<GameSetting>();
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		configurations.add(setting);
		when(repository.findAllSettings()).thenReturn(configurations);
		assertThat(presenter.loadConfigurations()).containsExactly(setting);
		verify(repository).findAllSettings();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testLoadConfigurationsMultiple() {
		Collection<GameSetting> configurations = new ArrayList<GameSetting>();
		GameSetting setting1 = new GameSetting("1", 10, 10, 10);
		GameSetting setting2 = new GameSetting("2", 10, 20, 10);
		configurations.add(setting1);
		configurations.add(setting2);
		when(repository.findAllSettings()).thenReturn(configurations);
		assertThat(presenter.loadConfigurations()).containsExactlyInAnyOrder(setting1, setting2);
		verify(repository).findAllSettings();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testSaveConfiguration() {
		ArgumentCaptor<GameSetting> settingCaptor = ArgumentCaptor.forClass(GameSetting.class);
		presenter.saveConfiguration(10, 10, 10, "new setting", view);
		verify(repository).createSetting(settingCaptor.capture());
		verify(view).update();
		verifyNoMoreInteractions(repository);
		GameSetting newConfiguration = settingCaptor.getValue();
		assertThat(newConfiguration.getHeight()).isEqualTo(10);
		assertThat(newConfiguration.getWidth()).isEqualTo(10);
		assertThat(newConfiguration.getObstacleNumber()).isEqualTo(10);
		assertThat(newConfiguration.getName()).isEqualTo("new setting");
	}

	@Test
	public void testSaveConfigurationNullName() {
		assertThatThrownBy(() -> presenter.saveConfiguration(10, 10, 10, null, null))
				.isInstanceOf(IllegalArgumentException.class).hasMessage("name cannot be null");
	}

	@Test
	public void testRenameConfiguration() {
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		String newName = "new name";
		presenter.renameConfiguration(setting, newName, view);
		verify(repository).renameSetting(setting, newName);
		verify(view).update();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testRenameConfigurationNullName() {
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		assertThatThrownBy(() -> presenter.renameConfiguration(setting, null, null))
				.isInstanceOf(IllegalArgumentException.class).hasMessage("name cannot be null");
	}

	@Test
	public void testRenameConfigurationNullConfiguration() {
		assertThatThrownBy(() -> presenter.renameConfiguration(null, "new name", null))
				.isInstanceOf(IllegalArgumentException.class).hasMessage("configuration cannot be null");
	}

	@Test
	public void testRemoveConfiguration() {
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		presenter.removeConfiguration(setting, view);
		verify(repository).deleteSetting(setting);
		verify(view).update();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testRemoveConfigurationNullConfiguration() {
		assertThatThrownBy(() -> presenter.removeConfiguration(null, null)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("configuration cannot be null");
	}

	@Test
	public void testLoadHistoryEmpty() {
		Collection<GameRecord> history = new ArrayList<GameRecord>();
		when(repository.findAllRecords()).thenReturn(history);
		assertThat(presenter.loadHistory()).isEmpty();
		verify(repository).findAllRecords();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testLoadHistorySingle() {
		Collection<GameRecord> history = new ArrayList<GameRecord>();
		GameRecord record1 = new GameRecord("1", 0, null, null);
		history.add(record1);
		when(repository.findAllRecords()).thenReturn(history);
		assertThat(presenter.loadHistory()).containsExactly(record1);
		verify(repository).findAllRecords();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testLoadHistoryMultiple() {
		Collection<GameRecord> history = new ArrayList<GameRecord>();
		GameRecord record1 = new GameRecord("1", 0, null, null);
		GameRecord record2 = new GameRecord("2", 0, null, null);
		history.add(record1);
		history.add(record2);
		when(repository.findAllRecords()).thenReturn(history);
		assertThat(presenter.loadHistory()).containsExactlyInAnyOrder(record1, record2);
		verify(repository).findAllRecords();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testLoadHighScoreEmptyHistory() {
		when(repository.findAllRecords()).thenReturn(new ArrayList<GameRecord>());
		assertThat(presenter.loadHighScore()).isZero();
		verify(repository).findAllRecords();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testLoadHighScoreSingleRecord() {
		Collection<GameRecord> history = new ArrayList<GameRecord>();
		history.add(new GameRecord("1", 1, null, null));
		when(repository.findAllRecords()).thenReturn(history);
		assertThat(presenter.loadHighScore()).isEqualTo(1);
		verify(repository).findAllRecords();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testLoadHighScoreMultipleRecords() {
		Collection<GameRecord> history = new ArrayList<GameRecord>();
		history.add(new GameRecord("1", 1, null, null));
		history.add(new GameRecord("2", 10, null, null));
		when(repository.findAllRecords()).thenReturn(history);
		assertThat(presenter.loadHighScore()).isEqualTo(10);
		verify(repository).findAllRecords();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testRemoveRecordNullRecord() {
		assertThatThrownBy(() -> presenter.removeRecord(null, null)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("record cannot be null");
	}

	@Test
	public void testRemoveRecord() {
		GameRecord gameRecord = new GameRecord("1", 0, null, null);
		presenter.removeRecord(gameRecord, view);
		verify(repository).deleteRecord(gameRecord);
		verify(view).update();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void testClearGameHistory() {
		presenter.clearGameHistory(view);
		verify(repository).clearHistory();
		verify(view).update();
		verifyNoMoreInteractions(repository);
	}
}
