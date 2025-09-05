package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import com.minigames.snake.presenter.SnakeLobbyPresenter;

public class DeleteAllRecordButtonListener extends HighLightableButtonListener {

	private SnakeView lobbyView;
	private SnakeLobbyPresenter presenter;

	public DeleteAllRecordButtonListener(SnakeView lobbyView, SnakeLobbyPresenter presenter) {
		this.lobbyView = lobbyView;
		this.presenter = presenter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		presenter.clearGameHistory(lobbyView);
	}

}
