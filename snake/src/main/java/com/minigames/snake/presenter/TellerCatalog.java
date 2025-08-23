package com.minigames.snake.presenter;

import com.minigames.snake.model.Generated;

@Generated
public class TellerCatalog {
	public static final PositionTeller UP = new UpTeller();
	public static final PositionTeller DOWN = new DownTeller();
	public static final PositionTeller LEFT = new LeftTeller();
	public static final PositionTeller RIGHT = new RightTeller();

	private TellerCatalog() {
	}
}
