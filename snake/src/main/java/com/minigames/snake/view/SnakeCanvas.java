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

public class SnakeCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private int cellSize;
	private transient SnakeMatchPresenter presenter;

	public SnakeCanvas(SnakeMatchPresenter presenter) {
		this.presenter = presenter;
		setPreferredSize(new Dimension(300, 300));
		setBackground(Color.WHITE);
	}

	public void refresh() {
		if (presenter.hasSetting()) {
			int newCellSize = (int) Math.round(300.0 / (Math.max(presenter.getMapHeight(), presenter.getMapWidth())));
			if (cellSize != newCellSize) {
				cellSize = newCellSize;
				setPreferredSize(
						new Dimension(cellSize * presenter.getMapWidth(), cellSize * presenter.getMapHeight()));
			}
		}
		if (isShowing()) {
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (presenter.hasSetting()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, cellSize * presenter.getMapWidth(), cellSize * presenter.getMapHeight());
			if (presenter.isPlaying()) {
				Point apple = presenter.getApple() != null
						? new Point(presenter.getApple().x, presenter.getMapHeight() - 1 - presenter.getApple().y)
						: null;
				Collection<Point> obstacles = presenter.getObstacles().stream()
						.map(p -> new Point(p.x, presenter.getMapHeight() - 1 - p.y)).collect(Collectors.toList());
				Collection<Point> snake = presenter.snakeCollection().stream()
						.map(p -> new Point(p.x, presenter.getMapHeight() - 1 - p.y)).collect(Collectors.toList());
				g.setColor(Color.RED);
				if (apple != null) {
					g.fillRect(apple.x * cellSize, apple.y * cellSize, cellSize, cellSize);
				}
				g.setColor(Color.DARK_GRAY);
				obstacles.stream().forEach(p -> g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize));
				g.setColor(Color.GREEN);
				snake.stream().forEach(p -> g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize));
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
