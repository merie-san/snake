package com.minigames.snake.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.minigames.snake.presenter.SnakeMatchPresenter;

public class SnakeCanvasKeyListener extends KeyAdapter {

	private SnakeMatchPresenter presenter;
	private SnakeView snakeView;

	public SnakeCanvasKeyListener(SnakeMatchPresenter presenter, SnakeView snakeView) {
		this.presenter = presenter;
		this.snakeView = snakeView;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			presenter.goUp( snakeView);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			presenter.goDown(snakeView);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			presenter.goLeft( snakeView);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			presenter.goRight(snakeView);
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			presenter.endMatch(snakeView);
		}
	}

}
