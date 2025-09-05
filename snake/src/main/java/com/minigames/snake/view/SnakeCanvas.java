package com.minigames.snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import com.minigames.snake.model.Generated;
import com.minigames.snake.presenter.SnakeMatchPresenter;

public class SnakeCanvas extends JPanel implements SnakeView {

	private static final long serialVersionUID = 1L;
	private int cellSize;
	private transient SnakeMatchPresenter presenter;

	public SnakeCanvas( SnakeMatchPresenter presenter) {
		this.presenter = presenter;
		setPreferredSize(new Dimension(300, 300));
		setBackground(Color.WHITE);
	}

	public void refresh() {
		cellSize = (int) Math.round(300.0 / (Math.max(presenter.getMapHeight(), presenter.getMapWidth())));
		setPreferredSize(new Dimension(cellSize * presenter.getMapWidth(), cellSize * presenter.getMapHeight()));
	}

	@Override
	public void update() {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (presenter.hasSetting()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, cellSize * presenter.getMapWidth(), cellSize * presenter.getMapHeight());
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
	int getCellSize() {
		return cellSize;
	}

	// for testing
	@Generated
	void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

}
