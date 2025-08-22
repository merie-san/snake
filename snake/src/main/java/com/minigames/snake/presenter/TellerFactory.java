package com.minigames.snake.presenter;

import com.minigames.snake.model.Generated;

@Generated
public class TellerFactory {

	public PositionTeller up() {
		return new UpTeller();
	}

	public PositionTeller down() {
		return new DownTeller();
	}

	public PositionTeller left() {
		return new LeftTeller();
	}

	public PositionTeller right() {
		return new RightTeller();
	}

}
