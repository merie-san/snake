package com.minigames.snake.presenter;

import java.awt.Point;
import java.util.Collection;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.view.SnakeView;

public interface SnakeMatchPresenter {

	void startMatch(SnakeView snakeView);

	void changeSetting(GameSetting setting, SnakeView snakeView);

	boolean hasSetting();

	void endMatch(SnakeView snakeView);

	int currentScore();

	Collection<Point> snakeCollection();

	Collection<Point> getObstacles();

	int getMapHeight();

	int getMapWidth();

	Point getApple();

	void goUp(SnakeView snakeView);

	void goDown(SnakeView snakeView);

	void goRight(SnakeView snakeView);

	void goLeft(SnakeView snakeView);

	boolean isPlaying();

}
