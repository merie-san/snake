package com.minigames.snake.presenter;

import java.awt.Point;

public class LeftTeller implements PositionTeller {

	@Override
	public Point tellNextPosition(Point currentPosition) {
		return new Point(currentPosition.x - 1, currentPosition.y);
	}

}
