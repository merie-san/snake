package com.minigames.snake.view;

import java.awt.CardLayout;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class PanelSwitchButtonListener extends HighLightableButtonListener{

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



}
