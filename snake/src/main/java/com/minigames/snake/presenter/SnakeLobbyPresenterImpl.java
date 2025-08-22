package com.minigames.snake.presenter;

import java.util.Collection;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.ModelFactory;
import com.minigames.snake.model.SnakeRepository;
import com.minigames.snake.view.SnakeLobbyView;

public class SnakeLobbyPresenterImpl implements SnakeLobbyPresenter {

	private SnakeRepository repository;
	private SnakeLobbyView view;

	public SnakeLobbyPresenterImpl(SnakeRepository repository, SnakeLobbyView view) {
		this.repository = repository;
		this.view = view;
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
	public void saveConfiguration(int height, int width, int obstacleN, String name) {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		GameSetting newSetting = ModelFactory.gameSetting(height, width, obstacleN);
		newSetting.setName(name);
		repository.createSetting(newSetting);
		view.updateView();
	}

	@Override
	public void renameConfiguration(GameSetting configuration, String newName) {
		if (newName == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		if (configuration == null) {
			throw new IllegalArgumentException("configuration cannot be null");
		}
		repository.renameSetting(configuration, newName);
		view.updateView();
	}

	@Override
	public void removeConfiguration(GameSetting configuration) {
		if (configuration == null) {
			throw new IllegalArgumentException("configuration cannot be null");
		}
		repository.deleteSetting(configuration);
		view.updateView();
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
	public void removeRecord(GameRecord gameRecord) {
		if (gameRecord == null) {
			throw new IllegalArgumentException("record cannot be null");
		}
		repository.deleteRecord(gameRecord);
		view.updateView();
	}

	@Override
	public void clearGameHistory() {
		repository.clearHistory();
		view.updateView();
	}

}
