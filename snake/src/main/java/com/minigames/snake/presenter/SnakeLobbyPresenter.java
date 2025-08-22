package com.minigames.snake.presenter;

import java.util.Collection;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.view.SnakeView;

public interface SnakeLobbyPresenter {

	void close();

	Collection<GameSetting> loadConfigurations();

	void saveConfiguration(int height, int width, int obstacleN, String name, SnakeView view);

	void renameConfiguration(GameSetting configuration, String newName, SnakeView view);

	void removeConfiguration(GameSetting configuration, SnakeView view);

	Collection<GameRecord> loadHistory();

	int loadHighScore();

	void removeRecord(GameRecord gameRecord, SnakeView view);

	void clearGameHistory(SnakeView view);

}
