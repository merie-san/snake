package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.minigames.snake.model.GameRecord;

public class DeleteRecordButtonListener extends HighLightableButtonListener {

	private JList<GameRecord> linkedList;
	private SnakeWindowView parentView;

	public DeleteRecordButtonListener(JList<GameRecord> linkedList, SnakeWindowView parentView) {
		this.linkedList = linkedList;
		this.parentView = parentView;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		parentView.deleteRecord(linkedList.getSelectedValue());
	}

}
