package com.minigames.snake.view;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.minigames.snake.model.GameSetting;

public class PanelListSelectionTextBoxListener implements ListSelectionListener {

	private JTextField linkedTextBox;
	private JList<GameSetting> monitoredList;

	public PanelListSelectionTextBoxListener(JTextField linkedTextBox, JList<GameSetting> monitoredList) {
		this.linkedTextBox = linkedTextBox;
		this.monitoredList = monitoredList;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			linkedTextBox.setEnabled(true);
			linkedTextBox.setText(monitoredList.getSelectedValue().getName());
		}
	}

}
