package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.presenter.SnakeMatchPresenter;

public class UseSettingButtonListener extends HighLightableButtonListener {

	private SnakeView lobbyView;
	private JList<GameSetting> monitoredList;
	private SnakeMatchPresenter presenter;

	public UseSettingButtonListener(SnakeView lobbyView, SnakeMatchPresenter presenter,
			JList<GameSetting> monitoredList) {
		this.lobbyView = lobbyView;
		this.presenter = presenter;
		this.monitoredList = monitoredList;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		presenter.changeSetting(monitoredList.getSelectedValue(), lobbyView);
	}

}
