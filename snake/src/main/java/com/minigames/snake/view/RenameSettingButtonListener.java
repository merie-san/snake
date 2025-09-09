package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JTextField;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.presenter.SnakeLobbyPresenter;

public class RenameSettingButtonListener extends HighLightableButtonListener {

	private SnakeView snakeView;
	private JTextField linkedTextBox;
	private JList<GameSetting> linkedList;
	private SnakeLobbyPresenter presenter;

	public RenameSettingButtonListener(SnakeView snakeView, SnakeLobbyPresenter presenter, JTextField linkedTextBox,
			JList<GameSetting> linkedList) {
		this.snakeView = snakeView;
		this.presenter = presenter;
		this.linkedTextBox = linkedTextBox;
		this.linkedList = linkedList;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		presenter.renameConfiguration(linkedList.getSelectedValue(), linkedTextBox.getText(), snakeView);
	}

}
