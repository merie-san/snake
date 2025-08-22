package com.minigames.snake.presenter;

import java.awt.Point;

public class DownTeller implements PositionTeller {

	@Override
	public Point tellNextPosition(Point currentPosition) {
		return new Point(currentPosition.x, currentPosition.y - 1);
	}

}
