package com.minigames.snake.view;

import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PanelListSelectionButtonListener implements ListSelectionListener {

	private JButton[] linkedButtons;

	public <T> PanelListSelectionButtonListener(JButton... linkedButtons) {
		this.linkedButtons = linkedButtons;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			Arrays.stream(linkedButtons).forEach(btn -> btn.setEnabled(true));
		}
	}

}
