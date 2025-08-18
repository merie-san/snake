package com.minigames.snake;

import java.util.Collection;

public interface SnakeRepository {

	Collection<GameRecord> findAllRecords();

	Collection<GameSetting> findAllSettings();

	void createRecord(GameRecord record);

	void createSetting(GameSetting setting);

	void deleteRecord(GameRecord record);

	void clearHistory();

	void deleteSetting(GameSetting setting);

	void renameSetting(GameSetting setting, String newName);
}
