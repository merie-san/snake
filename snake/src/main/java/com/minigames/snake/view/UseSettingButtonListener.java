package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.presenter.SnakeMatchPresenter;

public class UseSettingButtonListener extends HighLightableButtonListener {

	private SnakeView snakeView;
	private JList<GameSetting> monitoredList;
	private SnakeMatchPresenter presenter;
	private SnakeMatchPanel matchPanel;


	public UseSettingButtonListener(SnakeView snakeView, SnakeMatchPresenter presenter,
			JList<GameSetting> monitoredList, SnakeMatchPanel matchPanel) {
		this.snakeView = snakeView;
		this.presenter = presenter;
		this.monitoredList = monitoredList;
		this.matchPanel = matchPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		presenter.changeSetting(monitoredList.getSelectedValue(), snakeView);
		matchPanel.enableButtons();
	}

}
