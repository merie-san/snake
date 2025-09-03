package com.minigames.snake.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeCanvasKeyListener extends KeyAdapter {

	private SnakeCanvas canvas;

	public SnakeCanvasKeyListener( SnakeCanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			canvas.moveUp();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			canvas.moveDown();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			canvas.moveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			canvas.moveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			canvas.quitGame();
		}
	}

}
