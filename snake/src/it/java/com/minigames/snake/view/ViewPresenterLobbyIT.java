package com.minigames.snake.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.SnakeRepository;
import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeLobbyPresenterImpl;
import com.minigames.snake.presenter.SnakeMatchPresenter;

@RunWith(GUITestRunner.class)
@SuppressWarnings("java:S2699")
public class ViewPresenterLobbyIT extends AssertJSwingJUnitTestCase {

	private SnakeRepository repository;
	private FrameFixture window;
	private SnakeLobbyPresenter presenter;
	private SnakeWindowView snakeView;

	@Override
	protected void onSetUp() throws Exception {
		repository = mock(SnakeRepository.class);
		presenter = new SnakeLobbyPresenterImpl(repository);
		snakeView = GuiActionRunner.execute(() -> {
			return new SnakeWindowView(presenter, mock(SnakeMatchPresenter.class));
		});
		window = new FrameFixture(robot(), snakeView);
		window.show();
	}

	@Test
	public void testHistoryPanelDeleteAll() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameSetting setting2 = new GameSetting("2", 20, 10, 2);
		GameRecord record1 = new GameRecord("3", 10, LocalDate.now(), setting1);
		GameRecord record2 = new GameRecord("4", 5, LocalDate.now(), setting2);
		Collection<GameRecord> records = new ArrayList<>();
		records.add(record1);
		records.add(record2);
		Collection<GameRecord> emptyRecords = new ArrayList<>();
		when(repository.findAllRecords()).thenReturn(records).thenReturn(emptyRecords);
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
			snakeView.updateLobby();
		});
		window.button("deleteAllButton").click();
		assertThat(window.list("historyList").contents()).isEmpty();
		verify(repository).clearHistory();
	}

	@Test
	public void testHistoryPanelDeleteSelected() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameSetting setting2 = new GameSetting("2", 20, 10, 2);
		GameRecord record1 = new GameRecord("3", 10, LocalDate.now(), setting1);
		GameRecord record2 = new GameRecord("4", 5, LocalDate.now(), setting2);
		Collection<GameRecord> records1 = new ArrayList<>();
		records1.add(record1);
		records1.add(record2);
		Collection<GameRecord> records2 = new ArrayList<>();
		records2.add(record1);
		when(repository.findAllRecords()).thenReturn(records1).thenReturn(records2);
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
			snakeView.updateLobby();
		});
		window.list("historyList").clickItem(1);
		window.button("deleteSelectedButton").click();
		assertThat(window.list("historyList").contents()).hasSize(1)
				.allSatisfy(recordString -> assertThat(recordString).isEqualTo(record1.toString()));
		verify(repository).deleteRecord(record2);
	}

	@Test
	public void testSettingsPanelDeleteSetting() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameSetting setting2 = new GameSetting("2", 20, 10, 2);
		Collection<GameSetting> settings1 = new ArrayList<>();
		settings1.add(setting1);
		settings1.add(setting2);
		Collection<GameSetting> settings2 = new ArrayList<>();
		settings2.add(setting1);
		when(repository.findAllSettings()).thenReturn(settings1).thenReturn(settings2);
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
			snakeView.updateLobby();
		});
		window.list("settingsList").selectItem(1);
		window.button("deleteSettingButton").click();
		verify(repository).deleteSetting(setting2);
		assertThat(window.list("settingsList").contents()).hasSize(1)
				.allSatisfy(settingString -> assertThat(settingString).isEqualTo(setting1.toString()));
	}

	@Test
	public void testSettingsPanelRenameSetting() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameSetting setting2 = new GameSetting("1", 10, 10, 2);
		setting2.setName("easy setting");
		Collection<GameSetting> settings1 = new ArrayList<>();
		settings1.add(setting1);
		Collection<GameSetting> settings2 = new ArrayList<>();
		settings2.add(setting2);
		when(repository.findAllSettings()).thenReturn(settings1).thenReturn(settings2);
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
			snakeView.updateLobby();
		});
		window.list("settingsList").selectItem(0);
		window.textBox("newNameTextBox").deleteText().enterText("easy setting");
		window.button("renameButton").click();
		verify(repository).renameSetting(setting1, "easy setting");
		assertThat(window.list("settingsList").contents()).hasSize(1)
				.allSatisfy(settingString -> assertThat(settingString).isEqualTo(setting2.toString()));
	}

	@Test
	public void testSettingsPanelCreateSetting() {
		GameSetting setting1 = new GameSetting("1", 10, 10, 2);
		GameSetting setting2 = new GameSetting("2", 20, 10, 20);
		setting2.setName("difficult setting");
		Collection<GameSetting> settings1 = new ArrayList<>();
		settings1.add(setting1);
		settings1.add(setting2);
		Collection<GameSetting> settings2 = new ArrayList<>();
		settings2.add(setting1);
		when(repository.findAllSettings()).thenReturn(settings2).thenReturn(settings1);
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
			snakeView.updateLobby();
		});
		window.textBox("nameTextBox").deleteText().enterText("difficult setting");
		window.textBox("widthTextBox").enterText("20");
		window.textBox("heightTextBox").enterText("10");
		window.textBox("obstaclesTextBox").enterText("20");
		window.button("submitButton").click();
		ArgumentCaptor<GameSetting> settingCaptor = ArgumentCaptor.forClass(GameSetting.class);
		verify(repository).createSetting(settingCaptor.capture());
		GameSetting capturedSetting = settingCaptor.getValue();
		assertThat(capturedSetting.getWidth()).isEqualTo(20);
		assertThat(capturedSetting.getHeight()).isEqualTo(10);
		assertThat(capturedSetting.getObstacleNumber()).isEqualTo(20);
		assertThat(capturedSetting.getName()).isEqualTo("difficult setting");
		assertThat(window.list("settingsList").contents()).hasSize(2)
				.anySatisfy(s -> assertThat(s).isEqualTo(setting2.toString()));
	}



}
