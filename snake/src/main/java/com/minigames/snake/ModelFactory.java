package com.minigames.snake;

import java.util.UUID;

@Generated
public class ModelFactory {

	private ModelFactory() {
	}

	public static GameSetting gameSetting() {
		return new GameSetting(UUID.randomUUID().toString());
	}

	public static GameRecord gameRecord() {
		return new GameRecord(UUID.randomUUID().toString());
	}

}
