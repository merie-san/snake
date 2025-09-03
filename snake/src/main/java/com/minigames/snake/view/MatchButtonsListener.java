 package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class MatchButtonsListener extends HighLightableButtonListener {
	private JButton historyButton;
	private JButton settingsButton;
	private JButton playButton;
	private JButton quitButton;
	private boolean isStartListener;
	private SnakeCanvas canvas;

	public MatchButtonsListener(JButton historyButton, JButton settingsButton, JButton playButton, JButton quitButton,
			boolean isStartListener, SnakeCanvas canvas) {
		this.historyButton = historyButton;
		this.settingsButton = settingsButton;
		this.playButton = playButton;
		this.quitButton = quitButton;
		this.isStartListener = isStartListener;
		this.canvas = canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		historyButton.setEnabled(!(isStartListener ^ false));
		settingsButton.setEnabled(!(isStartListener ^ false));
		playButton.setEnabled(!(isStartListener ^ false));
		quitButton.setEnabled(!(isStartListener ^ true));
		if (isStartListener) {
			canvas.startGame();
		} else {
			canvas.quitGame();
		}
	}

}
