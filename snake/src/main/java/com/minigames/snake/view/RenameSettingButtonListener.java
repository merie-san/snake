package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JTextField;

import com.minigames.snake.model.GameSetting;

public class RenameSettingButtonListener extends HighLightableButtonListener {
	
	private SnakeWindowView parentView;
	private JTextField linkedTextBox;
	private JList<GameSetting> linkedList;

	public RenameSettingButtonListener(SnakeWindowView parentView, JTextField linkedTextBox, JList<GameSetting> linkedList) {
		this.parentView = parentView;
		this.linkedTextBox = linkedTextBox;
		this.linkedList = linkedList;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		parentView.renameSetting(linkedList.getSelectedValue(), linkedTextBox.getText());
	}

}
