package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.minigames.snake.model.GameSetting;

public class UseSettingButtonListener extends HighLightableButtonListener {

	private SnakeWindowView parentView;
	private JList<GameSetting> monitoredList;

	public UseSettingButtonListener(SnakeWindowView parentView, JList<GameSetting> monitoredList) {
		this.parentView = parentView;
		this.monitoredList = monitoredList;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		parentView.updateSetting(monitoredList.getSelectedValue());
	}

}
