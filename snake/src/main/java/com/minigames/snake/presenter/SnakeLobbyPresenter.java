package com.minigames.snake.presenter;

import java.util.Collection;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;

public interface SnakeLobbyPresenter {

	void close();

	Collection<GameSetting> loadConfigurations();

	void saveConfiguration(int height, int width, int obstacleN, String name);

	void renameConfiguration(GameSetting configuration, String newName);

	void removeConfiguration(GameSetting configuration);

	Collection<GameRecord> loadHistory();

	int loadHighScore();

	void removeRecord(GameRecord gameRecord);

	void clearGameHistory();

}
