package com.minigames.snake.presenter;

import java.awt.Point;
import java.util.Collection;
import java.util.Random;

public class RandomPositionSupplier implements PositionSupplier {

	private static final Random random = new Random();

	@Override
	public Point generateSnakeHeadPosition(SnakeMap map) {
		if (map == null) {
			throw new IllegalArgumentException("the map cannot be null");
		}
		if (map.obstaclesCopy().size() == map.getMapHeight() * map.getMapWidth()) {
			throw new IllegalStateException("the map is full of obstacles");
		}
		Point newHead;
		do {
			newHead = new Point(random.nextInt(map.getMapWidth()), random.nextInt(map.getMapHeight()));
		} while (map.obstaclesCopy().contains(newHead));
		return newHead;
	}

	@Override
	public Point generateApplePosition(SnakeMap map) {
		if (map == null) {
			throw new IllegalArgumentException("the map cannot be null");
		}
		Collection<Point> occupied = map.obstaclesCopy();
		occupied.add(map.getSnakeHead());
		occupied.addAll(map.getSnakeBody());
		if (occupied.size() == map.getMapHeight() * map.getMapWidth()) {
			return null;
		}
		Point newApple;
		do {
			newApple = new Point(random.nextInt(map.getMapWidth()), random.nextInt(map.getMapHeight()));
		} while (occupied.contains(newApple));
		return newApple;
	}

}
