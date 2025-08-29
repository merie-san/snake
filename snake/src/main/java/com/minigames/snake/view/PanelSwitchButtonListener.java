package com.minigames.snake.view;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelSwitchButtonListener extends MouseAdapter {

	private String targetPanelName;
	private JPanel cards;
	private CardLayout layout;

	public PanelSwitchButtonListener(JPanel cards, String targetName) {
		this.cards = cards;
		layout = (CardLayout) cards.getLayout();
		targetPanelName = targetName;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		layout.show(cards, targetPanelName);
	}

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
