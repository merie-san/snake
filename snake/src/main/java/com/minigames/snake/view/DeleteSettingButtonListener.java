package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.minigames.snake.model.GameSetting;

public class DeleteSettingButtonListener extends HighLightableButtonListener {
	private JList<GameSetting> linkedList;
	private SnakeWindowView parentView;

	public DeleteSettingButtonListener(JList<GameSetting> linkedList, SnakeWindowView parentView) {
		this.linkedList = linkedList;
		this.parentView = parentView;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		parentView.deleteSetting(linkedList.getSelectedValue());
	}

}
