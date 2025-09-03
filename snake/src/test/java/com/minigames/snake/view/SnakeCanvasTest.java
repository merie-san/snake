package com.minigames.snake.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.Dimension;

import org.junit.Before;
import org.junit.Test;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.presenter.SnakeMatchPresenter;

public class SnakeCanvasTest {
	SnakeMatchPanel parentPanel;
	SnakeCanvas canvas;
	SnakeView parentView;
	SnakeMatchPresenter presenter;

	@Before
	public void setup() {
		presenter = mock(SnakeMatchPresenter.class);
		parentPanel = mock(SnakeMatchPanel.class);
		parentView = mock(SnakeView.class);
		canvas = spy(new SnakeCanvas(parentView, presenter, parentPanel));
	}

	@Test
	public void testgenerateNewGridSingleElement() {
		canvas.newSetting(new GameSetting("1", 1, 1, 0));
		assertThat(canvas.getCellSize()).isEqualTo(300);
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(300, 300));
	}

	@Test
	public void testgenerateNewGridMultipleElementRowWise() {
		canvas.newSetting(new GameSetting("1", 2, 1, 0));
		assertThat(canvas.getCellSize()).isEqualTo(150);
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(300, 150));
	}

	@Test
	public void testgenerateNewGridMultipleElementColumnWise() {
		canvas.newSetting(new GameSetting("1", 1, 2, 0));
		assertThat(canvas.getCellSize()).isEqualTo(150);
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(150, 300));
	}

	@Test
	public void testConstructor() {
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(300, 300));
		assertThat(canvas.getBackground()).isEqualTo(Color.WHITE);
	}

	@Test
	public void testSnakeCanvasStartMatchDelegation() {
		canvas.setSetting(new GameSetting("1", 10, 10, 10));
		canvas.startGame();
		verify(presenter).startMatch(new GameSetting("1", 10, 10, 10));
	}

	@Test
	public void testSnakeCanvasQuitMatchDelegation() {
		canvas.quitGame();
		verify(presenter).endMatch(parentView);
	}

	@Test
	public void testUpdateDelegationPlaying() {
		when(presenter.isPlaying()).thenReturn(true);
		when(presenter.currentScore()).thenReturn(100);
		canvas.update();
		verify(canvas).repaint();
		verify(presenter).isPlaying();
		verify(presenter).currentScore();
		verify(parentPanel).updateMatchMessage(100, "In game");
		verifyNoMoreInteractions(parentPanel);
		verifyNoMoreInteractions(presenter);
	}

	@Test
	public void testUpdateDelegationNotPlaying() {
		when(presenter.isPlaying()).thenReturn(false);
		when(presenter.currentScore()).thenReturn(100);
		canvas.update();
		verify(canvas).repaint();
		verify(presenter).isPlaying();
		verify(presenter).currentScore();
		verify(parentPanel).updateMatchMessage(100, "No game");
		verifyNoMoreInteractions(parentPanel);
		verifyNoMoreInteractions(presenter);
	}
	
}
