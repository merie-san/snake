package com.minigames.snake.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeMatchPresenter;

@RunWith(GUITestRunner.class)
@SuppressWarnings("java:S2699")
public class SettingsWindowTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private SnakeLobbyPresenter lobbyPresenter;
	private SnakeMatchPresenter matchPresenter;
	private SnakeWindowView snakeView;

	@Override
	protected void onSetUp() {
		lobbyPresenter = mock(SnakeLobbyPresenter.class);
		matchPresenter = mock(SnakeMatchPresenter.class);
		snakeView = GuiActionRunner.execute(() -> {
			return new SnakeWindowView(lobbyPresenter, matchPresenter);
		});
		window = new FrameFixture(robot(), snakeView);
		window.show();
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

}
