package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.minigames.snake.presenter.SnakeMatchPresenter;

public class EndGameButtonListener extends HighLightableButtonListener {

	private JButton historyButton;
	private JButton settingsButton;
	private JButton playButton;
	private JButton quitButton;
	private SnakeMatchPresenter presenter;
	private SnakeView lobbyView;
	private SnakeCanvas canvas;

	public EndGameButtonListener(JButton historyButton, JButton settingsButton, JButton playButton, JButton quitButton,
			SnakeMatchPresenter presenter, SnakeView lobbyView, SnakeCanvas canvas) {
		this.historyButton = historyButton;
		this.settingsButton = settingsButton;
		this.playButton = playButton;
		this.quitButton = quitButton;
		this.presenter = presenter;
		this.lobbyView = lobbyView;
		this.canvas = canvas;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		historyButton.setEnabled(true);
		settingsButton.setEnabled(true);
		playButton.setEnabled(true);
		quitButton.setEnabled(false);
		presenter.endMatch(lobbyView);
		canvas.removeKeyListener(canvas.getKeyListeners().length!=0?canvas.getKeyListeners()[0]:null);
	}
}
