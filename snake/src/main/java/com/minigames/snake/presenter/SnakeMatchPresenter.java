package com.minigames.snake.presenter;

import java.awt.Point;
import java.util.Collection;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.view.SnakeView;

public interface SnakeMatchPresenter {

	void startMatch(GameSetting configuration);

	void endMatch(SnakeView globalView);

	int currentScore();

	Collection<Point> snakeCollection();

	Collection<Point> getObstacles();

	int getMapHeight();

	int getMapWidth();

	Point getApple();

	void goUp(SnakeView globalView, SnakeView matchView);

	void goDown(SnakeView globalView, SnakeView matchView);

	void goRight(SnakeView globalView, SnakeView matchView);

	void goLeft(SnakeView globalView, SnakeView matchView);

	boolean isPlaying();

}
