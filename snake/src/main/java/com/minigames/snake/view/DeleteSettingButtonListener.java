package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.presenter.SnakeLobbyPresenter;

public class DeleteSettingButtonListener extends HighLightableButtonListener {
	private JList<GameSetting> linkedList;
	private SnakeView snakeView;
	private SnakeLobbyPresenter presenter;

	public DeleteSettingButtonListener(JList<GameSetting> linkedList, SnakeView snakeView,
			SnakeLobbyPresenter presenter) {
		this.linkedList = linkedList;
		this.snakeView = snakeView;
		this.presenter = presenter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		presenter.removeConfiguration(linkedList.getSelectedValue(), snakeView);
	}

}
