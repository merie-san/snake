package com.minigames.snake.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.awt.event.KeyEvent;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeMatchPresenter;

@RunWith(GUITestRunner.class)
public class MatchWindowTest extends AssertJSwingJUnitTestCase {

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
	public void testMatchPanelQuitButtonDelegation() {
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
		verify(matchPresenter).endMatch(snakeView);
	}

	@Test
	public void testMatchPanelStartButtonAddsListener() {
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			window.button("startButton").target().setEnabled(true);
		});
		window.button("startButton").click();
		assertThat(canvas.getKeyListeners()).hasSize(1)
				.allSatisfy(listener -> assertThat(listener).isInstanceOf(SnakeCanvasKeyListener.class));
	}

	@Test
	public void testMatchPanelQuitButtonWhitKeyListeners() {
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			window.button("quitButton").target().setEnabled(true);
			canvas.addKeyListener(new SnakeCanvasKeyListener(matchPresenter, snakeView));
		});
		window.button("quitButton").click();
		assertThat(canvas.getKeyListeners()).isEmpty();
	}

	@Test
	public void testMatchPanelQuitButtonWhenNoKeyListeners() {
		when(matchPresenter.getMapHeight()).thenReturn(5);
		when(matchPresenter.getMapWidth()).thenReturn(5);
		when(matchPresenter.getApple()).thenReturn(new Point(2, 3));
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			window.button("quitButton").target().setEnabled(true);
		});
		window.button("quitButton").click();
		assertThat(canvas.getKeyListeners()).isEmpty();
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
}
