package com.minigames.snake.presenter;

import java.awt.Point;
import java.util.Collection;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.view.SnakeView;

public interface SnakeMatchPresenter {

	void startMatch(GameSetting configuration);

	void endMatch(SnakeView view);

	int currentScore();

	Collection<Point> snakeCollection();

	Collection<Point> getObstacles();

	int getMapHeight();

	int getMapWidth();

	Point getApple();

	void goUp(SnakeView view);

	void goDown(SnakeView view);

	void goRight(SnakeView view);

	void goLeft(SnakeView view);
	
	boolean isGameOver();

}
