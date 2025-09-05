package com.minigames.snake.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.minigames.snake.presenter.SnakeMatchPresenter;

public class SnakeCanvasKeyListener extends KeyAdapter {

	private SnakeMatchPresenter presenter;
	private SnakeView matchView;
	private SnakeView panelView;
	private SnakeView lobbyView;

	public SnakeCanvasKeyListener(SnakeMatchPresenter presenter, SnakeView matchView, SnakeView panelView,
			SnakeView lobbyView) {
		this.presenter = presenter;
		this.matchView = matchView;
		this.panelView = panelView;
		this.lobbyView = lobbyView;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			presenter.goUp(lobbyView, panelView, matchView);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			presenter.goDown(lobbyView, panelView, matchView);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			presenter.goLeft(lobbyView, panelView, matchView);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			presenter.goRight(lobbyView, panelView, matchView);
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			presenter.endMatch(lobbyView);
		}
	}

}
