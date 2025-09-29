package com.minigames.snake.model;

import java.util.Collection;

public interface SnakeRepository {

	Collection<GameRecord> findAllRecords();

	Collection<GameSetting> findAllSettings();

	void createRecord(GameRecord gameRecord);

	void createSetting(GameSetting setting);

	void deleteRecord(GameRecord gameRecord);

	void clearHistory();

	void deleteSetting(GameSetting setting);

	void renameSetting(GameSetting setting, String newName);

	void close();
}
