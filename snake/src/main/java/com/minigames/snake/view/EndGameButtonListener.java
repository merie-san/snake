package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import com.minigames.snake.presenter.SnakeMatchPresenter;

public class EndGameButtonListener extends HighLightableButtonListener {

	private SnakeMatchPresenter presenter;
	private SnakeView snakeView;
	private SnakeCanvas canvas;

	public EndGameButtonListener(SnakeMatchPresenter presenter, SnakeView snakeView, SnakeCanvas canvas) {
		this.presenter = presenter;
		this.snakeView = snakeView;
		this.canvas = canvas;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		presenter.endMatch(snakeView);
		canvas.removeKeyListener(canvas.getKeyListeners().length != 0 ? canvas.getKeyListeners()[0] : null);
	}
}
