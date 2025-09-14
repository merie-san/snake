package com.minigames.snake.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.awt.Color;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeMatchPresenter;

@RunWith(GUITestRunner.class)
@SuppressWarnings("java:S2699")
public class WelcomeWindowTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private SnakeWindowView snakeView;

	@Override
	protected void onSetUp() {
		snakeView = GuiActionRunner.execute(() -> {
			return new SnakeWindowView(mock(SnakeLobbyPresenter.class), mock(SnakeMatchPresenter.class));
		});
		window = new FrameFixture(robot(), snakeView);
		window.show();
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

}
