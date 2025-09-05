package com.minigames.snake.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public abstract class HighLightableButtonListener extends MouseAdapter {

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton button = (JButton) e.getSource();
		button.setBackground(button.getBackground().darker());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton button = (JButton) e.getSource();
		button.setBackground(null);
	}

}