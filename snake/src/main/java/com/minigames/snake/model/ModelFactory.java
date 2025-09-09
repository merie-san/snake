package com.minigames.snake.model;

import java.time.LocalDate;
import java.util.UUID;

public class ModelFactory {

	@Generated
	private ModelFactory() {
	}

	public static GameSetting gameSetting(int width, int height, int obstacleNumber) {
		if (height <= 0) {
			throw new IllegalArgumentException("height cannot be zero o negative");
		}
		if (width <= 0) {
			throw new IllegalArgumentException("width cannot be zero o negative");
		}
		if (obstacleNumber < 0) {
			throw new IllegalArgumentException("number of obstacles cannot be negative");
		}
		if (obstacleNumber>=width*height) {
			throw new IllegalArgumentException("number of obstacles must be less than the surface of the map");
		}
		return new GameSetting(UUID.randomUUID().toString(), width, height, obstacleNumber);
	}

	public static GameRecord gameRecord(int score, LocalDate date, GameSetting setting) {
		if (score < 0) {
			throw new IllegalArgumentException("score cannot be negative");
		}
		if (date == null) {
			throw new IllegalArgumentException("date cannot be null");
		}
		if (setting == null) {
			throw new IllegalArgumentException("setting cannot be null");
		}
		return new GameRecord(UUID.randomUUID().toString(), score, date, setting);
	}

}
