package com.minigames.snake.view;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import java.awt.Color;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GUITestRunner.class)
@SuppressWarnings("java:S2699")
public class SnakeWindowViewPanelTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;

	private SnakeWindowView snakeView;

	@Override
	protected void onSetUp() {
		GuiActionRunner.execute(() -> {
			snakeView = new SnakeWindowView();
			return snakeView;
		});
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
		window.textBox("widthTextBox").requireText("10").requireEnabled().requireEditable();
		window.textBox("heightTextBox").requireText("10").requireEnabled().requireEditable();
		window.textBox("obstaclesTextBox").requireText("2").requireEnabled().requireEditable();
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

}
