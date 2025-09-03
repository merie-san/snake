package com.minigames.snake.view;

import java.awt.event.MouseEvent;

public class DeleteAllRecordButtonListener extends HighLightableButtonListener {

	private SnakeWindowView parentView;

	public DeleteAllRecordButtonListener(SnakeWindowView parentView) {
		this.parentView = parentView;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		parentView.clearHistory();
	}

}
