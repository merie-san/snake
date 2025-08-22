package com.minigames.snake.presenter;

import com.minigames.snake.model.GameSetting;

public interface SnakeMatchPresenter {

	void startMatch(GameSetting configuration);

	void quitMatch();

	void endMatch();

	void goUp();

	void goDown();

	void goRight();

	void goLeft();

	
}
