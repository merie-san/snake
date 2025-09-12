package com.minigames.snake.presenter;

import java.awt.Point;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Queue;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.Generated;
import com.minigames.snake.model.ModelFactory;
import com.minigames.snake.model.SnakeRepository;
import com.minigames.snake.view.SnakeView;

public class SnakeMatchPresenterImpl implements SnakeMatchPresenter {

	private SnakeMap map;
	private ObstaclesSupplier obstaclesSupplier;
	private PositionSupplier positionSupplier;
	private int rawScore;
	private boolean playing = false;
	private boolean snakeBigger;
	private GameSetting configuration;
	private SnakeRepository repository;

	public SnakeMatchPresenterImpl(SnakeRepository repository, ObstaclesSupplier obstaclesSupplier,
			PositionSupplier positionSupplier) {
		this.repository = repository;
		this.obstaclesSupplier = obstaclesSupplier;
		this.positionSupplier = positionSupplier;
	}

	@Override
	public void startMatch(SnakeView snakeView) {
		if (configuration == null) {
			throw new IllegalArgumentException("Cannot start new game with null configuration");
		}
		map = SnakeMap.of(configuration.getWidth(), configuration.getHeight(),
				obstaclesSupplier.generateObstacles(configuration));
		map.setSnakeHead(positionSupplier.generateSnakeHeadPosition(map));
		map.setApple(positionSupplier.generateApplePosition(map));
		snakeBigger = false;
		playing = true;
		rawScore = 0;
		snakeView.updateMatch();
	}

	@Override
	public void changeSetting(GameSetting newConfiguration, SnakeView snakeView) {
		if (newConfiguration == null) {
			throw new IllegalArgumentException("Cannot change to null configuration");
		}
		configuration = newConfiguration;
		snakeView.updateMatch();
	}

	@Override
	public void endMatch(SnakeView snakeView) {
		repository.createRecord(ModelFactory.gameRecord(currentScore(), LocalDate.now(), configuration));
		playing = false;
		snakeView.updateLobby();
		snakeView.updateMatch();
	}

	@Override
	public void goUp(SnakeView snakeView) {
		goDirection(snakeView, TellerCatalog.UP);
	}

	@Override
	public void goDown(SnakeView snakeView) {
		goDirection(snakeView, TellerCatalog.DOWN);
	}

	@Override
	public void goRight(SnakeView snakeView) {
		goDirection(snakeView, TellerCatalog.RIGHT);
	}

	@Override
	public void goLeft(SnakeView snakeView) {
		goDirection(snakeView, TellerCatalog.LEFT);
	}

	private void goDirection(SnakeView snakeView, PositionTeller teller) {
		if (map.checkFree(teller, snakeBigger)) {
			if (map.moveSnake(teller, snakeBigger)) {
				map.setApple(null);
				snakeBigger = true;
				rawScore++;
				if (map.getMapHeight() * map.getMapWidth() - configuration.getObstacleNumber() - 1 == rawScore) {
					endMatch(snakeView);
					return;
				} else {
					map.setApple(positionSupplier.generateApplePosition(map));
				}
			} else {
				snakeBigger = false;
			}
		} else {
			endMatch(snakeView);
			return;
		}
		snakeView.updateMatch();
	}

	@Override
	public int currentScore() {
		return configuration != null
				? (int) Math.round(rawScore * Math.pow(1.1, configuration.getObstacleNumber() / 100.0))
				: 0;
	}

	@Override
	public Collection<Point> snakeCollection() {
		Queue<Point> snakePoints = map.snakeBodyCopy();
		snakePoints.add(map.getSnakeHead());
		return snakePoints;
	}

	@Generated
	public void setSupplierStrategy(PositionSupplier supplier) {
		positionSupplier = supplier;
	}

	@Generated
	public void setSupplierStrategy(ObstaclesSupplier supplier) {
		obstaclesSupplier = supplier;
	}

	@Override
	@Generated
	public Collection<Point> getObstacles() {
		return map.obstaclesCopy();
	}

	@Override
	@Generated
	public int getMapHeight() {
		return configuration.getHeight();
	}

	@Override
	@Generated
	public int getMapWidth() {
		return configuration.getWidth();
	}

	@Override
	@Generated
	public Point getApple() {
		return map.getApple();
	}

	@Override
	@Generated
	public boolean isPlaying() {
		return playing;
	}

	@Override
	public boolean hasSetting() {
		return configuration != null;
	}

	@Generated
	void setPlaying(boolean isPlaying) {
		this.playing = isPlaying;
	}

	// for testing
	@Generated
	int getRawScore() {
		return rawScore;
	}

	// for testing
	@Generated
	void setRawScore(int score) {
		rawScore = score;
	}

	// for testing
	@Generated
	SnakeMap getMap() {
		return map;
	}

	// for testing
	@Generated
	void setMap(SnakeMap map) {
		this.map = map;
	}

	// for testing
	@Generated
	GameSetting getConfiguration() {
		return configuration;
	}

	// for testing
	@Generated
	void setConfiguration(GameSetting configuration) {
		this.configuration = configuration;
	}

	// for testing
	@Generated
	boolean isSnakeBigger() {
		return snakeBigger;
	}

}
