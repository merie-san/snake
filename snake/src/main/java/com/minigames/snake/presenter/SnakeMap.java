package com.minigames.snake.presenter;

import java.awt.Point;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

import com.minigames.snake.model.Generated;

public class SnakeMap {

	private final int mapHeight;
	private final int mapWidth;
	private final Collection<Point> obstacles;
	private Point snakeHead;
	private final Deque<Point> snakeBody = new LinkedList<>();
	private Point apple;

	@Generated
	private SnakeMap(int width, int height, Collection<Point> obstacles) {
		this.mapWidth = width;
		this.mapHeight = height;
		this.obstacles = obstacles;
	}

	public static SnakeMap of(int width, int height, Collection<Point> obstacles) {
		if (width <= 0) {
			throw new IllegalArgumentException("width cannot be zero or negative");
		}
		if (height <= 0) {
			throw new IllegalArgumentException("height cannot be zero or negative");
		}
		if (obstacles == null) {
			throw new IllegalArgumentException("obstacles collection cannot be null");
		}
		if (obstacles.stream().anyMatch(p -> p.x > width || p.y > height || p.x < 0 || p.y < 0)) {
			throw new IllegalArgumentException("an obstacle is out of bounds of the map");
		}
		return new SnakeMap(width, height, obstacles);
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public Collection<Point> obstaclesCopy() {
		return new HashSet<>(obstacles);
	}

	public Queue<Point> snakeBodyCopy() {
		return new LinkedList<>(snakeBody);
	}

	public void putSnakeHead(Point head) {
		if (head.x >= mapWidth || head.y >= mapHeight || head.x < 0 || head.y < 0) {
			throw new IllegalArgumentException("the snake is out of bounds of the map");
		}
		if (obstacles.stream().anyMatch(obstacle -> obstacle.equals(head))) {
			throw new IllegalArgumentException("the snake is on an obstacle");
		}
		snakeBody.clear();
		snakeHead = head;
	}

	public void putApple(Point apple) {
		if (apple.x >= mapWidth || apple.y >= mapHeight || apple.x < 0 || apple.y < 0) {
			throw new IllegalArgumentException("the apple is out of bounds of the map");
		}
		if (obstacles.stream().anyMatch(obstacle -> obstacle.equals(apple))) {
			throw new IllegalArgumentException("the apple is on an obstacle");
		}
		if (snakeBody.stream().anyMatch(body -> body.equals(apple)) || apple.equals(snakeHead)) {
			throw new IllegalArgumentException("the apple is on the snake");
		}
		this.apple = apple;
	}

	public Point getApple() {
		return apple;
	}

	public boolean checkFree(PositionTeller teller, boolean snakeGrew) {
		if (teller == null) {
			throw new IllegalArgumentException("the teller cannot be null");
		}
		Queue<Point> bodyPoints = snakeBodyCopy();
		Point nextPosition = teller.tellNextPosition(snakeHead);
		return !(nextPosition.x < 0 || nextPosition.x >= mapWidth || nextPosition.y < 0 || nextPosition.y >= mapHeight
				|| obstacles.stream().anyMatch(obstacle -> obstacle.equals(nextPosition))
				|| !snakeBody.isEmpty() && snakeBody.peekLast().equals(nextPosition)
				|| IntStream.range(0, snakeBody.size()).anyMatch(i -> {
					Point bodyPoint = bodyPoints.remove();
					if (snakeGrew || i > 0) {
						return bodyPoint.equals(nextPosition);
					} else
						return false;
				}));

	}

	public boolean moveSnake(PositionTeller teller, boolean snakeGrew) {
		if (!checkFree(teller, snakeGrew)) {
			throw new IllegalArgumentException("illegal direction");
		}
		Point nextPosition = teller.tellNextPosition(snakeHead);
		snakeBody.add(snakeHead);
		snakeHead = nextPosition;
		if (!snakeGrew) {
			snakeBody.remove();
		}
		return nextPosition.equals(apple);
	}

	@Generated
	public Point getSnakeHead() {
		return snakeHead;
	}

	// for testing
	@Generated
	Deque<Point> getSnakeBody() {
		return snakeBody;
	}

	// for testing
	@Generated
	Collection<Point> getObstacles() {
		return obstacles;
	}

	// for testing
	@Generated
	void setSnakeHead(Point snakeHead) {
		this.snakeHead = snakeHead;
	}

	// for testing
	@Generated
	void setApple(Point apple) {
		this.apple = apple;
	}
}
