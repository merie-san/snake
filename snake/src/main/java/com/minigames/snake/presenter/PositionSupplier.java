package com.minigames.snake.presenter;

import java.awt.Point;

public interface PositionSupplier {

	Point generateSnakeHeadPosition(SnakeMap map);

	Point generateApplePosition(SnakeMap map);

}
