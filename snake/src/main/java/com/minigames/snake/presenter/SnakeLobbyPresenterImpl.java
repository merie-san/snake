package com.minigames.snake.presenter;

import java.util.Collection;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.ModelFactory;
import com.minigames.snake.model.SnakeRepository;
import com.minigames.snake.view.SnakeView;

public class SnakeLobbyPresenterImpl implements SnakeLobbyPresenter {

	private SnakeRepository repository;

	public SnakeLobbyPresenterImpl(SnakeRepository repository) {
		this.repository = repository;
	}

	@Override
	public void close() {
		repository.close();
	}

	@Override
	public Collection<GameSetting> loadConfigurations() {
		return repository.findAllSettings();
	}

	@Override
	public void saveConfiguration(int height, int width, int obstacleN, String name, SnakeView view) {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		GameSetting newSetting = ModelFactory.gameSetting(width, height, obstacleN);
		newSetting.setName(name);
		repository.createSetting(newSetting);
		view.update();
	}

	@Override
	public void renameConfiguration(GameSetting configuration, String newName, SnakeView view) {
		if (newName == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		if (configuration == null) {
			throw new IllegalArgumentException("configuration cannot be null");
		}
		repository.renameSetting(configuration, newName);
		view.update();
	}

	@Override
	public void removeConfiguration(GameSetting configuration, SnakeView view) {
		if (configuration == null) {
			throw new IllegalArgumentException("configuration cannot be null");
		}
		repository.deleteSetting(configuration);
		view.update();
	}

	@Override
	public Collection<GameRecord> loadHistory() {
		return repository.findAllRecords();
	}

	@Override
	public int loadHighScore() {
		return repository.findAllRecords().stream().map(GameRecord::getScore).max((score1, score2) -> score1 - score2)
				.orElse(0);
	}

	@Override
	public void removeRecord(GameRecord gameRecord, SnakeView view) {
		if (gameRecord == null) {
			throw new IllegalArgumentException("record cannot be null");
		}
		repository.deleteRecord(gameRecord);
		view.update();
	}

	@Override
	public void clearGameHistory(SnakeView view) {
		repository.clearHistory();
		view.update();
	}

}
