package com.minigames.snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.Generated;
import com.minigames.snake.presenter.SnakeMatchPresenter;

public class SnakeCanvas extends JPanel implements SnakeView {

	private static final long serialVersionUID = 1L;
	private int cellSize;
	private transient GameSetting setting;
	private transient SnakeMatchPresenter presenter;
	private SnakeMatchPanel parentPanel;
	private transient SnakeView rootPanel;

	public SnakeCanvas(SnakeView rootPanel, SnakeMatchPresenter presenter, SnakeMatchPanel parentPanel) {
		this.presenter = presenter;
		this.parentPanel = parentPanel;
		this.rootPanel = rootPanel;
		setPreferredSize(new Dimension(300, 300));
		setBackground(Color.WHITE);
		this.addKeyListener(new SnakeCanvasKeyListener(this));
	}

	public void newSetting(GameSetting setting) {
		this.setting = setting;
		cellSize = (int) Math.round(300.0 / (Math.max(setting.getHeight(), setting.getWidth())));
		setPreferredSize(new Dimension(cellSize * setting.getWidth(), cellSize * setting.getHeight()));
	}

	public void startGame() {
		presenter.startMatch(setting);
	}

	public void quitGame() {
		presenter.endMatch(rootPanel);
	}

	public void moveUp() {
		presenter.goUp(rootPanel, this);
	}

	public void moveDown() {
		presenter.goDown(rootPanel, this);
	}

	public void moveLeft() {
		presenter.goLeft(rootPanel, this);
	}

	public void moveRight() {
		presenter.goRight(rootPanel, this);
	}

	@Override
	public void update() {
		repaint();
		parentPanel.updateMatchMessage(presenter.currentScore(), presenter.isPlaying() ? "In game" : "No game");
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (setting != null) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, cellSize * setting.getWidth(), cellSize * setting.getHeight());
			if (presenter.isPlaying()) {
				Point apple = new Point(presenter.getApple().x, presenter.getMapHeight() - 1 - presenter.getApple().y);
				Collection<Point> obstacles = presenter.getObstacles().stream()
						.map(p -> new Point(p.x, presenter.getMapHeight() - 1 - p.y)).collect(Collectors.toList());
				Collection<Point> snake = presenter.snakeCollection().stream()
						.map(p -> new Point(p.x, presenter.getMapHeight() - 1 - p.y)).collect(Collectors.toList());
				g.setColor(Color.RED);
				g.fillRect(apple.x * cellSize, apple.y * cellSize, cellSize, cellSize);
				obstacles.stream().forEach(p -> {
					g.setColor(Color.DARK_GRAY);
					g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
				});
				snake.stream().forEach(p -> {
					g.setColor(Color.GREEN);
					g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
				});
			}
		}
	}

	// for testing
	@Generated
	void setSetting(GameSetting setting) {
		this.setting = setting;
	}

	// for testing
	@Generated
	int getCellSize() {
		return cellSize;
	}

	// for testing
	@Generated
	void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

}
