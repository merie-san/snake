package com.minigames.snake;

import java.awt.Dimension;
import java.awt.EventQueue;

import com.minigames.snake.model.Generated;
import com.minigames.snake.view.SnakeWindowView;

/**
 * Hello world!
 */
@Generated
public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				SnakeWindowView frame = new SnakeWindowView(null, null);
				frame.pack();
				frame.setMinimumSize(new Dimension(600, 500));
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
