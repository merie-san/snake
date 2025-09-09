package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.presenter.SnakeLobbyPresenter;

public class DeleteRecordButtonListener extends HighLightableButtonListener {

	private JList<GameRecord> linkedList;
	private SnakeView snakeView;
	private SnakeLobbyPresenter presenter;

	public DeleteRecordButtonListener(JList<GameRecord> linkedList, SnakeView snakeView,
			SnakeLobbyPresenter presenter) {
		this.linkedList = linkedList;
		this.snakeView = snakeView;
		this.presenter = presenter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		presenter.removeRecord(linkedList.getSelectedValue(), snakeView);
	}

}
