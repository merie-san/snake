package com.minigames.snake.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

import javax.swing.DefaultListModel;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.image.ScreenshotTaker;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeMatchPresenter;

@RunWith(GUITestRunner.class)
@SuppressWarnings("java:S2699")
public class SnakeWindowViewTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private SnakeLobbyPresenter lobbyPresenter;
	private SnakeMatchPresenter matchPresenter;
	private SnakeWindowView snakeView;
	private SnakeCanvas canvas;

	@Override
	protected void onSetUp() {
		lobbyPresenter = mock(SnakeLobbyPresenter.class);
		matchPresenter = mock(SnakeMatchPresenter.class);
		snakeView = GuiActionRunner.execute(() -> {
			return new SnakeWindowView(lobbyPresenter, matchPresenter);
		});
		canvas = snakeView.getMatchPanel().getCanvas();
		window = new FrameFixture(robot(), snakeView);
		window.show();
	}

	@Test
	public void testWelcomeCardInitialState() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Welcome panel");
		});
		window.label("welcomeLabel").requireText("Turn-based Snake");
		window.button("historyButton").requireText("History").requireEnabled();
		window.button("settingsButton").requireText("Settings").requireEnabled();
		window.button("matchButton").requireText("Play").requireEnabled();
	}

	@Test
	public void testHistoryCardInitialState() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
		});
		window.button("settingsButton").requireText("Settings").requireEnabled();
		window.button("matchButton").requireText("Play").requireEnabled();
		window.label("highScoreLabel").requireText("All time high score: 0");
		window.list("historyList").requireItemCount(0).requireNoSelection();
		window.button("deleteAllButton").requireText("Clear history").requireEnabled();
		window.button("deleteSelectedButton").requireText("Delete selected").requireDisabled();
	}

	@Test
	public void testSettingsCardInitialState() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		window.button("matchButton").requireText("Play").requireEnabled();
		window.button("historyButton").requireText("History").requireEnabled();
		window.list("settingsList").requireItemCount(0).requireNoSelection();
		window.button("useSettingButton").requireText("Use setting").requireDisabled();
		window.button("deleteSettingButton").requireText("Delete setting").requireDisabled();
		window.textBox("newNameTextBox").requireText("New name").requireDisabled().requireEditable();
		window.button("renameButton").requireText("Rename").requireDisabled();
		window.label("createSettingLabel").requireText("Setting form");
		window.label("nameLabel").requireText("Name");
		window.label("widthLabel").requireText("Width");
		window.label("heightLabel").requireText("Height");
		window.label("obstaclesLabel").requireText("N. obstacles");
		window.textBox("nameTextBox").requireText("New setting").requireEnabled().requireEditable();
		window.textBox("widthTextBox").requireText("5").requireEnabled().requireEditable();
		window.textBox("heightTextBox").requireText("5").requireEnabled().requireEditable();
		window.textBox("obstaclesTextBox").requireText("5").requireEnabled().requireEditable();
		window.button("submitButton").requireText("Submit").requireEnabled();
	}

	@Test
	public void testMatchCardInitialState() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		window.button("settingsButton").requireText("Settings").requireEnabled();
		window.button("historyButton").requireText("History").requireEnabled();
		window.panel("matchCanvas").requireEnabled();
		window.label("scoreLabel").requireText("Current score: 0").requireEnabled();
		window.label("messageLabel").requireText("No message").requireEnabled();
		window.button("startButton").requireText("Start").requireDisabled();
		window.button("quitButton").requireText("Quit").requireDisabled();
	}

	@Test
	public void testWelcomePanelToHistoryPanel() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Welcome panel");
		});
		Color initialColor = window.button("historyButton").target().getBackground();
		window.robot().moveMouse(window.button("historyButton").target());
		assertThat(initialColor.darker()).isEqualTo(window.button("historyButton").target().getBackground());
		window.robot().moveMouse(0, 0);
		assertThat(initialColor).isEqualTo(window.button("historyButton").target().getBackground());
		window.button("historyButton").click();
		window.panel("History panel").requireVisible();
	}

	@Test
	public void testWelcomePanelToSettingsPanel() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Welcome panel");
		});
		Color initialColor = window.button("settingsButton").target().getBackground();
		window.robot().moveMouse(window.button("settingsButton").target());
		assertThat(initialColor.darker()).isEqualTo(window.button("settingsButton").target().getBackground());
		window.robot().moveMouse(0, 0);
		assertThat(initialColor).isEqualTo(window.button("settingsButton").target().getBackground());
		window.button("settingsButton").click();
		window.panel("Settings panel").requireVisible();
	}

	@Test
	public void testWelcomePanelToMatchPanel() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Welcome panel");
		});
		Color initialColor = window.button("matchButton").target().getBackground();
		window.robot().moveMouse(window.button("matchButton").target());
		assertThat(initialColor.darker()).isEqualTo(window.button("matchButton").target().getBackground());
		window.robot().moveMouse(0, 0);
		assertThat(initialColor).isEqualTo(window.button("matchButton").target().getBackground());
		window.button("matchButton").click();
		window.panel("Match panel").requireVisible();
	}

	@Test
	public void testHistoryPanelToSettingsPanel() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
		});
		Color initialColor = window.button("settingsButton").target().getBackground();
		window.robot().moveMouse(window.button("settingsButton").target());
		assertThat(initialColor.darker()).isEqualTo(window.button("settingsButton").target().getBackground());
		window.robot().moveMouse(0, 0);
		assertThat(initialColor).isEqualTo(window.button("settingsButton").target().getBackground());
		window.button("settingsButton").click();
		window.panel("Settings panel").requireVisible();
	}

	@Test
	public void testHistoryPanelToMatchPanel() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
		});
		Color initialColor = window.button("matchButton").target().getBackground();
		window.robot().moveMouse(window.button("matchButton").target());
		assertThat(initialColor.darker()).isEqualTo(window.button("matchButton").target().getBackground());
		window.robot().moveMouse(0, 0);
		assertThat(initialColor).isEqualTo(window.button("matchButton").target().getBackground());
		window.button("matchButton").click();
		window.panel("Match panel").requireVisible();
	}

	@Test
	public void testSettingsPanelToHistoryPanel() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		Color initialColor = window.button("historyButton").target().getBackground();
		window.robot().moveMouse(window.button("historyButton").target());
		assertThat(initialColor.darker()).isEqualTo(window.button("historyButton").target().getBackground());
		window.robot().moveMouse(0, 0);
		assertThat(initialColor).isEqualTo(window.button("historyButton").target().getBackground());
		window.button("historyButton").click();
		window.panel("History panel").requireVisible();
	}

	@Test
	public void testSettingsPanelToMatchPanel() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		Color initialColor = window.button("matchButton").target().getBackground();
		window.robot().moveMouse(window.button("matchButton").target());
		assertThat(initialColor.darker()).isEqualTo(window.button("matchButton").target().getBackground());
		window.robot().moveMouse(0, 0);
		assertThat(initialColor).isEqualTo(window.button("matchButton").target().getBackground());
		window.button("matchButton").click();
		window.panel("Match panel").requireVisible();
	}

	@Test
	public void testMatchPanelToHistoryPanel() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		Color initialColor = window.button("historyButton").target().getBackground();
		window.robot().moveMouse(window.button("historyButton").target());
		assertThat(initialColor.darker()).isEqualTo(window.button("historyButton").target().getBackground());
		window.robot().moveMouse(0, 0);
		assertThat(initialColor).isEqualTo(window.button("historyButton").target().getBackground());
		window.button("historyButton").click();
		window.panel("History panel").requireVisible();
	}

	@Test
	public void testMatchPanelToSettingsPanel() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		Color initialColor = window.button("settingsButton").target().getBackground();
		window.robot().moveMouse(window.button("settingsButton").target());
		assertThat(initialColor.darker()).isEqualTo(window.button("settingsButton").target().getBackground());
		window.robot().moveMouse(0, 0);
		assertThat(initialColor).isEqualTo(window.button("settingsButton").target().getBackground());
		window.button("settingsButton").click();
		window.panel("Settings panel").requireVisible();
	}

	@Test
	public void testUpdateHistoryPanelEmpty() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
		});
		when(lobbyPresenter.loadHistory()).thenReturn(new ArrayList<>());
		GuiActionRunner.execute(() -> {
			snakeView.updateLobby();
		});
		assertThat(window.list("historyList").contents()).isEmpty();
		assertThat(window.label("highScoreLabel").text()).isEqualTo("All time high score: 0");
		verify(lobbyPresenter).loadHistory();
	}

	@Test
	public void testUpdateHistoryPanelMultiple() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
		});
		GameSetting setting = new GameSetting("2", 0, 0, 0);
		Collection<GameRecord> history = new ArrayList<>();
		GameRecord record1 = new GameRecord("1", 10, LocalDate.now(), setting);
		GameRecord record2 = new GameRecord("3", 30, LocalDate.now(), setting);
		history.add(record1);
		history.add(record2);
		when(lobbyPresenter.loadHistory()).thenReturn(history);
		GuiActionRunner.execute(() -> {
			snakeView.updateLobby();
		});
		assertThat(window.list("historyList").contents()).containsExactlyInAnyOrder(record1.toString(),
				record2.toString());
		assertThat(window.label("highScoreLabel").text()).isEqualTo("All time high score: 30");
		verify(lobbyPresenter).loadHistory();
	}

	@Test
	public void testUpdateSettingsPanelEmpty() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		when(lobbyPresenter.loadConfigurations()).thenReturn(new ArrayList<>());
		GuiActionRunner.execute(() -> {
			snakeView.updateLobby();
		});
		assertThat(window.list("settingsList").contents()).isEmpty();
		verify(lobbyPresenter).loadConfigurations();
	}

	@Test
	public void testUpdateSettingsPanelMultiple() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		Collection<GameSetting> settings = new ArrayList<>();
		GameSetting setting1 = new GameSetting("1", 10, 10, 10);
		GameSetting setting2 = new GameSetting("2", 15, 10, 10);
		settings.add(setting1);
		settings.add(setting2);
		when(lobbyPresenter.loadConfigurations()).thenReturn(settings);
		GuiActionRunner.execute(() -> {
			snakeView.updateLobby();
		});
		assertThat(window.list("settingsList").contents()).containsExactlyInAnyOrder(setting1.toString(),
				setting2.toString());
		verify(lobbyPresenter).loadConfigurations();
	}

	@Test
	public void testDeleteSelectedButtonEnabledAtItemSelection() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
			snakeView.getHistoryPanel().getListModel()
					.addElement(new GameRecord("1", 10, LocalDate.now(), new GameSetting("2", 10, 10, 10)));
		});
		window.button("deleteSelectedButton").requireDisabled();
		window.list("historyList").clickItem(0);
		window.button("deleteSelectedButton").requireEnabled();
	}

	@Test
	public void testDeleteSelectedButtonEnabledAtSelectionChange() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
			GameSetting setting = new GameSetting("2", 10, 10, 10);
			GameRecord record1 = new GameRecord("1", 10, LocalDate.now(), setting);
			GameRecord record2 = new GameRecord("3", 10, LocalDate.now(), setting);
			Collection<GameRecord> records = new ArrayList<>();
			records.add(record1);
			records.add(record2);
			snakeView.getHistoryPanel().getListModel().addAll(records);
		});
		window.list("historyList").clickItem(0);
		window.button("deleteSelectedButton").requireEnabled();
		window.list("historyList").clickItem(1);
		window.button("deleteSelectedButton").requireEnabled();
	}

	@Test
	public void testHistoryPanelDeleteAll() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
		});
		Collection<GameRecord> history = new ArrayList<>();
		GameSetting setting = new GameSetting("2", 10, 10, 10);
		GameRecord record1 = new GameRecord("1", 10, LocalDate.now(), setting);
		GameRecord record2 = new GameRecord("3", 10, LocalDate.now(), setting);
		history.add(record1);
		history.add(record2);
		GuiActionRunner.execute(() -> {
			snakeView.getHistoryPanel().getListModel().addAll(history);
		});
		window.button("deleteAllButton").click();
		verify(lobbyPresenter).clearGameHistory(snakeView);
		verifyNoMoreInteractions(lobbyPresenter);
	}

	@Test
	public void testHistoryPanelDeleteSelected() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
		});
		Collection<GameRecord> history = new ArrayList<>();
		GameSetting setting = new GameSetting("2", 10, 10, 10);
		GameRecord record1 = new GameRecord("1", 10, LocalDate.now(), setting);
		GameRecord record2 = new GameRecord("3", 10, LocalDate.now(), setting);
		history.add(record1);
		history.add(record2);
		DefaultListModel<GameRecord> listModel = snakeView.getHistoryPanel().getListModel();
		GuiActionRunner.execute(() -> {
			listModel.addAll(history);
		});
		window.list("historyList").clickItem(0);
		window.button("deleteSelectedButton").click();
		verify(lobbyPresenter).removeRecord(listModel.getElementAt(0), snakeView);
		verifyNoMoreInteractions(lobbyPresenter);
	}

	@Test
	public void testSettingsPanelButtonsEnabledAtItemSelection() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		Collection<GameSetting> settings = new ArrayList<>();
		settings.add(new GameSetting("1", 10, 10, 10));
		settings.add(new GameSetting("2", 20, 10, 1));
		GuiActionRunner.execute(() -> {
			snakeView.getSettingsPanel().getListModel().addAll(settings);
		});
		window.button("renameButton").requireDisabled();
		window.button("deleteSettingButton").requireDisabled();
		window.button("useSettingButton").requireDisabled();
		window.list("settingsList").clickItem(0);
		window.button("renameButton").requireEnabled();
		window.button("deleteSettingButton").requireEnabled();
		window.button("useSettingButton").requireEnabled();
	}

	@Test
	public void testSettingsPanelButtonsEnabledAtSelectionChange() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		Collection<GameSetting> settings = new ArrayList<>();
		settings.add(new GameSetting("1", 10, 10, 10));
		settings.add(new GameSetting("2", 20, 10, 1));
		GuiActionRunner.execute(() -> {
			snakeView.getSettingsPanel().getListModel().addAll(settings);
		});
		window.list("settingsList").clickItem(0);
		window.button("renameButton").requireEnabled();
		window.button("deleteSettingButton").requireEnabled();
		window.button("useSettingButton").requireEnabled();
		window.list("settingsList").clickItem(1);
		window.button("renameButton").requireEnabled();
		window.button("deleteSettingButton").requireEnabled();
		window.button("useSettingButton").requireEnabled();
	}

	@Test
	public void testSettingsPanelNewNameTextBoxFilledAtSelection() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		Collection<GameSetting> settings = new ArrayList<>();
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		settings.add(setting);
		GuiActionRunner.execute(() -> {
			snakeView.getSettingsPanel().getListModel().addAll(settings);
		});
		window.list("settingsList").clickItem(0);
		window.textBox("newNameTextBox").requireEnabled().requireText(setting.getName());
	}

	@Test
	public void testSettingsPanelNewNameTextBoxEmptyAtNullSelection() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		Collection<GameSetting> settings = new ArrayList<>();
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		settings.add(setting);
		GuiActionRunner.execute(() -> {
			snakeView.getSettingsPanel().getListModel().addAll(settings);
			snakeView.getSettingsPanel().getList().setSelectedIndex(0);
		});
		window.list("settingsList").clearSelection();
		window.textBox("newNameTextBox").requireDisabled().requireText("");

	}

	@Test
	public void testSettingsPanelUseSettingButton() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		Collection<GameSetting> settings = new ArrayList<>();
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		settings.add(setting);
		GuiActionRunner.execute(() -> {
			snakeView.getSettingsPanel().getListModel().addAll(settings);
		});
		window.list("settingsList").selectItem(0);
		window.button("useSettingButton").click();
		verify(matchPresenter).changeSetting(setting, snakeView);
	}

	@Test
	public void testSettingsPanelDeleteSettingButton() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		Collection<GameSetting> settings = new ArrayList<>();
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		settings.add(setting);
		GuiActionRunner.execute(() -> {
			snakeView.getSettingsPanel().getListModel().addAll(settings);
		});
		window.list("settingsList").selectItem(0);
		window.button("deleteSettingButton").click();
		verify(lobbyPresenter).removeConfiguration(setting, snakeView);
		verifyNoMoreInteractions(lobbyPresenter);
	}

	@Test
	public void testSettingsPanelRenameSettingButton() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		Collection<GameSetting> settings = new ArrayList<>();
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		settings.add(setting);
		GuiActionRunner.execute(() -> {
			snakeView.getSettingsPanel().getListModel().addAll(settings);
		});
		window.list("settingsList").selectItem(0);
		window.textBox("newNameTextBox").deleteText().enterText("Random name");
		window.button("renameButton").click();
		verify(lobbyPresenter).renameConfiguration(setting, "Random name", snakeView);
		verifyNoMoreInteractions(lobbyPresenter);
	}

	@Test
	public void testSettingsPanelWidthTextBoxInvalidInput() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		window.textBox("widthTextBox").enterText("Not a number").pressAndReleaseKeys(KeyEvent.VK_TAB);
		window.textBox("widthTextBox").requireText("5");
	}

	@Test
	public void testSettingsPanelHeightTextBoxInvalidInput() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		window.textBox("heightTextBox").enterText("Not a number").pressAndReleaseKeys(KeyEvent.VK_TAB);
		window.textBox("heightTextBox").requireText("5");
	}

	@Test
	public void testSettingsPanelObstaclesTextBoxInvalidInput() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		window.textBox("obstaclesTextBox").enterText("Not a number").pressAndReleaseKeys(KeyEvent.VK_TAB);
		window.textBox("obstaclesTextBox").requireText("5");
	}

	@Test
	public void testSettingsPanelValidInputWidth() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		window.textBox("widthTextBox").enterText("10").pressAndReleaseKeys(KeyEvent.VK_TAB);
		window.textBox("widthTextBox").requireText("10");
	}

	@Test
	public void testSettingsPanelValidInputHeight() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		window.textBox("heightTextBox").enterText("10").pressAndReleaseKeys(KeyEvent.VK_TAB);
		window.textBox("heightTextBox").requireText("10");
	}

	@Test
	public void testSettingsPanelValidInputObstacles() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});

		window.textBox("obstaclesTextBox").enterText("10").pressAndReleaseKeys(KeyEvent.VK_TAB);
		window.textBox("obstaclesTextBox").requireText("10");
	}

	@Test
	public void testSettingsPanelSubmit() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		window.button("submitButton").click();
		verify(lobbyPresenter).saveConfiguration(5, 5, 5, "New setting", snakeView);
	}

	@Test
	public void testMatchPanelButtonsEnabledAtEnableButtons() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		window.button("startButton").requireDisabled();
		GuiActionRunner.execute(() -> {
			snakeView.getMatchPanel().enableButtons();
		});
		window.button("historyButton").requireEnabled();
		window.button("settingsButton").requireEnabled();
		window.button("startButton").requireEnabled();
		window.button("quitButton").requireDisabled();
	}
	

	@Test
	public void testMatchPanelStartButtonDisablesPanelButtons() {
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			window.button("historyButton").target().setEnabled(true);
			window.button("settingsButton").target().setEnabled(true);
			window.button("startButton").target().setEnabled(true);
			window.button("quitButton").target().setEnabled(false);
		});
		window.button("startButton").click();
		window.button("historyButton").requireDisabled();
		window.button("settingsButton").requireDisabled();
		window.button("startButton").requireDisabled();
		window.button("quitButton").requireEnabled();
	}

	@Test
	public void testMatchPanelQuitButtonEnablesPanelButtons() {
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			window.button("historyButton").target().setEnabled(false);
			window.button("settingsButton").target().setEnabled(false);
			window.button("startButton").target().setEnabled(false);
			window.button("quitButton").target().setEnabled(true);
		});
		window.button("quitButton").click();
		window.button("historyButton").requireEnabled();
		window.button("settingsButton").requireEnabled();
		window.button("startButton").requireEnabled();
		window.button("quitButton").requireDisabled();
	}

	@Test
	public void testMatchPanelStartButton() {
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			window.button("startButton").target().setEnabled(true);
		});
		window.button("startButton").click();
		window.button("historyButton").requireDisabled();
		window.button("settingsButton").requireDisabled();
		window.button("startButton").requireDisabled();
		window.button("quitButton").requireEnabled();
		assertThat(canvas.getKeyListeners()).hasSize(1)
				.allSatisfy(listener -> assertThat(listener).isInstanceOf(SnakeCanvasKeyListener.class));
	}

	@Test
	public void testMatchPanelQuitButton() {
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			window.button("quitButton").target().setEnabled(true);
			canvas.addKeyListener(new SnakeCanvasKeyListener(matchPresenter, snakeView));
		});
		window.button("quitButton").click();
		window.button("historyButton").requireEnabled();
		window.button("settingsButton").requireEnabled();
		window.button("startButton").requireEnabled();
		window.button("quitButton").requireDisabled();
		assertThat(canvas.getKeyListeners()).isEmpty();
	}

	@Test
	public void testUpdateMatchPanelAtGameEnd() {
		when(matchPresenter.isPlaying()).thenReturn(false);
		when(matchPresenter.hasSetting()).thenReturn(true);
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			window.button("quitButton").target().setEnabled(true);
			window.button("startButton").target().setEnabled(false);
			window.button("historyButton").target().setEnabled(false);
			window.button("settingsButton").target().setEnabled(false);
			snakeView.updateMatch();
		});
		window.button("historyButton").requireEnabled();
		window.button("settingsButton").requireEnabled();
		window.button("startButton").requireEnabled();
		window.button("quitButton").requireDisabled();
		assertThat(canvas.getKeyListeners()).isEmpty();
	}

	@Test
	public void testMatchCardCanvasInitializationNoSetting() {
		when(matchPresenter.hasSetting()).thenReturn(false);
		when(matchPresenter.isPlaying()).thenReturn(false);
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			snakeView.updateMatch();
		});
		BufferedImage image = new ScreenshotTaker().takeScreenshotOf(canvas);
		IntStream.range(0, 300).forEach(x -> IntStream.range(0, 300)
				.forEach(y -> assertThat(image.getRGB(x, y)).isEqualTo(Color.WHITE.getRGB())));
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(300, 300));
	}

	@Test
	public void testMatchCardCanvasInitializationNotPlaying() {
		when(matchPresenter.getMapHeight()).thenReturn(2);
		when(matchPresenter.getMapWidth()).thenReturn(2);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		when(matchPresenter.hasSetting()).thenReturn(true);
		when(matchPresenter.isPlaying()).thenReturn(false);
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			canvas.setCellSize(150);
			snakeView.updateMatch();
		});
		BufferedImage image = new ScreenshotTaker().takeScreenshotOf(canvas);
		IntStream.range(0, 300).forEach(x -> IntStream.range(0, 300)
				.forEach(y -> assertThat(image.getRGB(x, y)).isEqualTo(Color.LIGHT_GRAY.getRGB())));
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(300, 300));
	}

	@Test
	public void testMatchCardInitializationEmptyMap() {
		when(matchPresenter.hasSetting()).thenReturn(true);
		when(matchPresenter.isPlaying()).thenReturn(true);
		when(matchPresenter.getApple()).thenReturn(null);
		when(matchPresenter.getObstacles()).thenReturn(new ArrayList<>());
		when(matchPresenter.snakeCollection()).thenReturn(new ArrayList<Point>());
		when(matchPresenter.getMapWidth()).thenReturn(10);
		when(matchPresenter.getMapHeight()).thenReturn(10);
		canvas.setCellSize(30);
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			snakeView.updateMatch();
		});
		BufferedImage image = new ScreenshotTaker().takeScreenshotOf(canvas);
		IntStream.range(0, 300).forEach(x -> IntStream.range(0, 300)
				.forEach(y -> assertThat(image.getRGB(x, y)).isEqualTo(Color.LIGHT_GRAY.getRGB())));
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(300, 300));
	}

	@Test
	public void testMatchCardRefreshIllegalState() {
		when(matchPresenter.hasSetting()).thenReturn(false);
		when(matchPresenter.isPlaying()).thenReturn(true);
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			assertThatThrownBy(snakeView::updateMatch).isInstanceOf(IllegalStateException.class)
					.hasMessage("Player cannot be in game while having no setting");

		});
	}

	@Test
	public void testMatchCardCanvasInitializationPlaying() {
		Collection<Point> obstacles = new ArrayList<Point>();
		Collection<Point> snake = new ArrayList<Point>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(2, 1));
		snake.add(new Point(4, 2));
		snake.add(new Point(4, 1));
		snake.add(new Point(4, 0));
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		when(matchPresenter.hasSetting()).thenReturn(true);
		when(matchPresenter.isPlaying()).thenReturn(true);
		when(matchPresenter.getObstacles()).thenReturn(obstacles);
		when(matchPresenter.snakeCollection()).thenReturn(snake);
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			canvas.setCellSize(60);
			snakeView.updateMatch();
		});
		BufferedImage image = new ScreenshotTaker().takeScreenshotOf(canvas);
		IntStream.range(0, 300).forEach(x -> IntStream.range(0, 300).forEach(y -> {
			if (y < 120 && y >= 60 && x < 180 && x >= 120) {
				assertThat(image.getRGB(x, y)).isEqualTo(Color.RED.getRGB());
			} else if (y < 300 && y >= 120 && x < 300 && x >= 240) {
				assertThat(image.getRGB(x, y)).isEqualTo(Color.GREEN.getRGB());
			} else if ((y < 300 && y >= 240 && x < 60 && x >= 0) || (y < 240 && y >= 180 && x < 180 && x >= 120)) {
				assertThat(image.getRGB(x, y)).isEqualTo(Color.DARK_GRAY.getRGB());
			} else {
				assertThat(image.getRGB(x, y)).isEqualTo(Color.LIGHT_GRAY.getRGB());
			}
		}));
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(300, 300));
	}

	@Test
	public void testMatchPanelKeyboardControls() {
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		when(matchPresenter.hasSetting()).thenReturn(true);
		when(matchPresenter.isPlaying()).thenReturn(true);
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			canvas.addKeyListener(new SnakeCanvasKeyListener(matchPresenter, snakeView));
		});
		window.panel("matchCanvas").click();
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_LEFT);
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_RIGHT);
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_UP);
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_DOWN);
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_ESCAPE);
		verify(matchPresenter).goRight(snakeView);
		verify(matchPresenter).goUp(snakeView);
		verify(matchPresenter).goDown(snakeView);
		verify(matchPresenter).goLeft(snakeView);
		verify(matchPresenter).endMatch(snakeView);
	}

	@Test
	public void testMatchCardUpdatePlaying() {
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		when(matchPresenter.currentScore()).thenReturn(30);
		when(matchPresenter.isPlaying()).thenReturn(true);
		when(matchPresenter.hasSetting()).thenReturn(true);
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			snakeView.updateMatch();
		});
		window.label("scoreLabel").requireText("Current score: 30");
		window.label("messageLabel").requireText("In game");
	}

	@Test
	public void testMatchCardUpdateNotPlaying() {
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		when(matchPresenter.currentScore()).thenReturn(30);
		when(matchPresenter.isPlaying()).thenReturn(false);
		GuiActionRunner.execute(() -> {
			snakeView.updateMatch();
		});
		window.label("scoreLabel").requireText("Current score: 30");
		window.label("messageLabel").requireText("No game");
	}

}
