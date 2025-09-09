package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import com.minigames.snake.presenter.SnakeLobbyPresenter;

public class DeleteAllRecordButtonListener extends HighLightableButtonListener {

	private SnakeView snakeView;
	private SnakeLobbyPresenter presenter;

	public DeleteAllRecordButtonListener(SnakeView snakeView, SnakeLobbyPresenter presenter) {
		this.snakeView = snakeView;
		this.presenter = presenter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		presenter.clearGameHistory(snakeView);
	}

}
