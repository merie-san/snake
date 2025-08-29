package com.minigames.snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.JPanel;

import com.minigames.snake.model.Generated;

public class SnakeCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private Color[][] grid;
	private int cellsize;
	private int numRows;
	private int numCols;

	public SnakeCanvas(int cellsize) {
		this.cellsize = cellsize;
		grid = new Color[][] {};
		setPreferredSize(new Dimension(cellsize * 20, cellsize * 20));
		setBackground(Color.WHITE);
	}

	public void newGrid(int numRows, int numCols) {
		grid = new Color[numRows][numCols];
		this.numRows = numRows;
		this.numCols = numCols;
		Arrays.stream(grid).forEach(row -> Arrays.fill(row, Color.GRAY));
		setPreferredSize(new Dimension(cellsize * numCols, cellsize * numRows));
	}

	public void setColor(int x, int y, Color color) {
		if (x < 0 || x >= numCols || y < 0 || y >= numRows) {
			throw new IllegalArgumentException("illegal coordinates");
		}
		grid[y][x] = color;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		IntStream.range(0, numRows).forEach(rowInd -> IntStream.range(0, numCols).forEach(colInd -> {
			g.setColor(grid[rowInd][colInd]);
			g.fillRect(colInd * cellsize, rowInd * cellsize, cellsize, cellsize);
		}));
	}

	// for testing
	@Generated
	Color[][] getGrid() {
		return grid;
	}

}
