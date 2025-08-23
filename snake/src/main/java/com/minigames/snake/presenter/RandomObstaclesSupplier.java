package com.minigames.snake.presenter;

import java.awt.Point;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.minigames.snake.model.GameSetting;

public class RandomObstaclesSupplier implements ObstaclesSupplier {

	private static final Random random = new Random();

	@Override
	public Collection<Point> generateObstacles(GameSetting configuration) {
		if (configuration==null) {
			throw new IllegalArgumentException("the configuration cannot be null");
		}
		return Stream.generate(() -> new Point(random.nextInt(configuration.getWidth()), random.nextInt(configuration.getHeight())))
				.distinct().limit(configuration.getObstacleNumber()).collect(Collectors.toSet());
	}

}
