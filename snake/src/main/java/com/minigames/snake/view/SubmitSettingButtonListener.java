package com.minigames.snake.view;

import java.awt.event.MouseEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SubmitSettingButtonListener extends HighLightableButtonListener {

	private SnakeWindowView parentView;
	private JTextField nameBox;
	private JFormattedTextField widthBox;
	private JFormattedTextField heightBox;
	private JFormattedTextField obstaclesBox;

	public SubmitSettingButtonListener(SnakeWindowView parentView, JTextField nameBox, JFormattedTextField widthBox,
			JFormattedTextField heightBox, JFormattedTextField obstaclesBox) {
		this.parentView = parentView;
		this.nameBox = nameBox;
		this.widthBox = widthBox;
		this.heightBox = heightBox;
		this.obstaclesBox = obstaclesBox;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int width = (Integer) widthBox.getValue();
		int height = (Integer) heightBox.getValue();
		int obstacles = (Integer) obstaclesBox.getValue();
		String name = nameBox.getText();
		if (width < 5 || width > 30 || height < 5 || height > 30) {
			JOptionPane.showMessageDialog(parentView, "the width and height of the map must be contained in [5, 30]");
			return;
		}
		if (obstacles < 0 || obstacles > (width * height / 2)) {
			JOptionPane.showMessageDialog(parentView,
					"the number of obstacles on the map must be contained in [0, width*height/2]");
			return;
		}
		if (name.length() > 20) {
			JOptionPane.showMessageDialog(parentView, "the lenght of the setting's name must be contained in [0, 20]");
			return;
		}
		parentView.createSetting(width, height, obstacles, name);
	}

}
