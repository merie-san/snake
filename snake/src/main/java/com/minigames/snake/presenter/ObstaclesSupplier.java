package com.minigames.snake.presenter;

import java.awt.Point;
import java.util.Collection;

import com.minigames.snake.model.GameSetting;

public interface ObstaclesSupplier {
	Collection<Point> generateObstacles(GameSetting configuration);
}
