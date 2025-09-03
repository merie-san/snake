package com.minigames.snake.view;

import static org.assertj.core.api.Assertions.assertThat;
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
	private SnakeLobbyPresenter presenterLobby;
	private SnakeMatchPresenter presenterMatch;
	private SnakeWindowView snakeView;
	private SnakeCanvas canvas;

	@Override
	protected void onSetUp() {
		presenterLobby = mock(SnakeLobbyPresenter.class);
		presenterMatch = mock(SnakeMatchPresenter.class);
		snakeView = GuiActionRunner.execute(() -> {
			return new SnakeWindowView(presenterLobby, presenterMatch);
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
		when(presenterLobby.loadHistory()).thenReturn(new ArrayList<>());
		GuiActionRunner.execute(() -> {
			snakeView.update();
		});
		assertThat(window.list("historyList").contents()).isEmpty();
		assertThat(window.label("highScoreLabel").text()).isEqualTo("All time high score: 0");
		verify(presenterLobby).loadHistory();
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
		when(presenterLobby.loadHistory()).thenReturn(history);
		GuiActionRunner.execute(() -> {
			snakeView.update();
		});
		assertThat(window.list("historyList").contents()).containsExactlyInAnyOrder(record1.toString(),
				record2.toString());
		assertThat(window.label("highScoreLabel").text()).isEqualTo("All time high score: 30");
		verify(presenterLobby).loadHistory();
	}

	@Test
	public void testUpdateSettingsPanelEmpty() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		when(presenterLobby.loadConfigurations()).thenReturn(new ArrayList<>());
		GuiActionRunner.execute(() -> {
			snakeView.update();
		});
		assertThat(window.list("settingsList").contents()).isEmpty();
		verify(presenterLobby).loadConfigurations();
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
		when(presenterLobby.loadConfigurations()).thenReturn(settings);
		GuiActionRunner.execute(() -> {
			snakeView.update();
		});
		assertThat(window.list("settingsList").contents()).containsExactlyInAnyOrder(setting1.toString(),
				setting2.toString());
		verify(presenterLobby).loadConfigurations();
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
		verify(presenterLobby).clearGameHistory(snakeView);
		verifyNoMoreInteractions(presenterLobby);
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
		verify(presenterLobby).removeRecord(listModel.getElementAt(0), snakeView);
		verifyNoMoreInteractions(presenterLobby);
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
		assertThat(snakeView.getMatchPanel().getCurrentSetting()).isEqualTo(setting);
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
		verify(presenterLobby).removeConfiguration(setting, snakeView);
		verifyNoMoreInteractions(presenterLobby);
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
		verify(presenterLobby).renameConfiguration(setting, "Random name", snakeView);
		verifyNoMoreInteractions(presenterLobby);
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
		verify(presenterLobby).saveConfiguration(5, 5, 5, "New setting", snakeView);
	}

	@Test
	public void testMatchPanelAtRefreshStartEnabled() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		window.button("startButton").requireDisabled();
		GuiActionRunner.execute(() -> {
			snakeView.getMatchPanel().refresh(new GameSetting("1", 10, 10, 10));
		});
		window.button("startButton").requireEnabled();
	}

	@Test
	public void testUpdateMatchPanelStartButtonDisablesPanelButtons() {
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
	public void testUpdateMatchPanelQuitButtonEnablesPanelButtons() {
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
	public void testUpdateMatchPanelStartButtonDelegation() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		canvas.setSetting(setting);
		GuiActionRunner.execute(() -> {
			window.button("startButton").target().setEnabled(true);
		});
		window.button("startButton").click();
		verify(presenterMatch).startMatch(setting);
	}

	@Test
	public void testUpdateMatchPanelQuitButtonDelegation() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		canvas.setSetting(new GameSetting("1", 10, 10, 10));
		GuiActionRunner.execute(() -> {
			window.button("quitButton").target().setEnabled(true);
		});
		window.button("quitButton").click();
		verify(presenterMatch).endMatch(snakeView);
	}

	@Test
	public void testMatchCardCanvasInitializationNotPlaying() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		canvas.setSetting(new GameSetting("1", 2, 2, 0));
		canvas.setCellSize(150);
		canvas.repaint();
		BufferedImage image = new ScreenshotTaker().takeScreenshotOf(canvas);
		IntStream.range(0, 300).forEach(x -> IntStream.range(0, 300)
				.forEach(y -> assertThat(image.getRGB(x, y)).isEqualTo(Color.LIGHT_GRAY.getRGB())));
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(300, 300));
	}

	@Test
	public void testMatchCardCanvasInitializationPlaying() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		when(presenterMatch.getMapHeight()).thenReturn(5);
		when(presenterMatch.getMapWidth()).thenReturn(5);
		when(presenterMatch.getApple()).thenReturn(new Point(2, 3));
		when(presenterMatch.isPlaying()).thenReturn(true);
		Collection<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(2, 1));
		when(presenterMatch.getObstacles()).thenReturn(obstacles);
		Collection<Point> snake = new ArrayList<Point>();
		snake.add(new Point(4, 2));
		snake.add(new Point(4, 1));
		snake.add(new Point(4, 0));
		when(presenterMatch.snakeCollection()).thenReturn(snake);
		canvas.setSetting(new GameSetting("1", 5, 5, 2));
		canvas.setCellSize(60);
		canvas.repaint();
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
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
		});
		when(presenterMatch.isPlaying()).thenReturn(true);
		window.panel("matchCanvas").click();
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_LEFT);
		verify(presenterMatch).goLeft(snakeView, canvas);
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_RIGHT);
		verify(presenterMatch).goRight(snakeView, canvas);
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_UP);
		verify(presenterMatch).goUp(snakeView, canvas);
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_DOWN);
		verify(presenterMatch).goDown(snakeView, canvas);
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_ESCAPE);
		verify(presenterMatch).endMatch(snakeView);
	}

	@Test
	public void testMatchCardSetScore() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			snakeView.getMatchPanel().updateMatchMessage(30, "New message");
		});
		window.label("scoreLabel").requireText("Current score: 30");
		window.label("messageLabel").requireText("New message");
	}

}
