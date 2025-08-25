package com.minigames.snake.view;

import static org.junit.Assert.*;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GUITestRunner.class)
@SuppressWarnings("java:S2699")
public class SnakeWindowViewTest extends AssertJSwingJUnitTestCase {

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
		window.panel("Welcome panel").label("welcomeLabel").requireText("Turn-based Snake");
		window.panel("Welcome panel").button("historyButton").requireText("History").requireEnabled();
		window.panel("Welcome panel").button("settingsButton").requireText("Settings").requireEnabled();
		window.panel("Welcome panel").button("matchButton").requireText("Play").requireEnabled();
	}

	@Test
	public void testHistoryCardInitialState() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
		});
		window.panel("History panel").button("settingsButton").requireText("Settings").requireEnabled();
		window.panel("History panel").button("matchButton").requireText("Play").requireEnabled();
		window.panel("History panel").label("highScoreLabel").requireText("All time high score: 0");
		window.panel("History panel").list("historyList").requireItemCount(0).requireNoSelection();
		window.panel("History panel").button("deleteAllButton").requireText("Clear history").requireEnabled();
		window.panel("History panel").button("deleteSelectedButton").requireText("Delete selected").requireDisabled();
	}

	@Test
	public void testSettingsCardInitialState() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		window.panel("Settings panel").button("matchButton").requireText("Play").requireEnabled();
		window.panel("Settings panel").button("historyButton").requireText("History").requireEnabled();
		window.panel("Settings panel").list("settingsList").requireItemCount(0).requireNoSelection();
		window.panel("Settings panel").button("useSettingButton").requireText("Use setting").requireDisabled();
		window.panel("Settings panel").button("deleteSettingButton").requireText("Delete setting").requireDisabled();
		window.panel("Settings panel").textBox("newNameTextBox").requireText("New name").requireDisabled()
				.requireEditable();
		window.panel("Settings panel").button("renameButton").requireText("Rename").requireDisabled();
		window.panel("Settings panel").label("createSettingLabel").requireText("Setting form");
		window.panel("Settings panel").label("nameLabel").requireText("Name");
		window.panel("Settings panel").label("widthLabel").requireText("Width");
		window.panel("Settings panel").label("heightLabel").requireText("Height");
		window.panel("Settings panel").label("obstaclesLabel").requireText("N. obstacles");
		window.panel("Settings panel").textBox("nameTextBox").requireText("New setting").requireEnabled()
				.requireEditable();
		window.panel("Settings panel").textBox("widthTextBox").requireText("10").requireEnabled().requireEditable();
		window.panel("Settings panel").textBox("heightTextBox").requireText("10").requireEnabled().requireEditable();
		window.panel("Settings panel").textBox("obstaclesTextBox").requireText("2").requireEnabled().requireEditable();
		window.panel("Settings panel").button("submitButton").requireText("Submit").requireEnabled();
	}

}
