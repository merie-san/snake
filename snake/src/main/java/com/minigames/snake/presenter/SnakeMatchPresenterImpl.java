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
	public void startMatch(GameSetting configuration) {
		this.configuration = configuration;
		map = SnakeMap.of(configuration.getWidth(), configuration.getHeight(),
				obstaclesSupplier.generateObstacles(configuration));
		map.setSnakeHead(positionSupplier.generateSnakeHeadPosition(map));
		map.setApple(positionSupplier.generateApplePosition(map));
		snakeBigger = false;
		playing = true;
		rawScore = 0;
	}

	@Override
	public void endMatch(SnakeView globalView) {
		repository.createRecord(ModelFactory.gameRecord(currentScore(), LocalDate.now(), configuration));
		playing = false;
		globalView.update();
	}

	@Override
	public void goUp(SnakeView globalView, SnakeView matchView) {
		goDirection(globalView, matchView, TellerCatalog.UP);
	}

	@Override
	public void goDown(SnakeView globalView, SnakeView matchView) {
		goDirection(globalView, matchView, TellerCatalog.DOWN);
	}

	@Override
	public void goRight(SnakeView globalView, SnakeView matchView) {
		goDirection(globalView, matchView, TellerCatalog.RIGHT);
	}

	@Override
	public void goLeft(SnakeView globalView, SnakeView matchView) {
		goDirection(globalView, matchView, TellerCatalog.LEFT);
	}

	private void goDirection(SnakeView globalView, SnakeView matchView, PositionTeller teller) {
		if (map.checkFree(teller, snakeBigger)) {
			if (map.moveSnake(teller, snakeBigger)) {
				snakeBigger = true;
				rawScore++;
				if (map.getMapHeight() * map.getMapWidth() - configuration.getObstacleNumber() - 1 == rawScore) {
					matchEnded(globalView);
				}
			} else {
				snakeBigger = false;
			}
		} else {
			matchEnded(globalView);
		}
		matchView.update();
	}

	private void matchEnded(SnakeView globalView) {
		playing = false;
		repository.createRecord(ModelFactory.gameRecord(currentScore(), LocalDate.now(), configuration));
		globalView.update();
	}

	@Override
	public int currentScore() {
		return (int) Math.round(rawScore * Math.pow(1.1, configuration.getObstacleNumber()));
	}

	@Override
	public Collection<Point> snakeCollection() {
		Queue<Point> snakePoints = map.snakeBodyCopy();
		snakePoints.add(map.getSnakeHead());
		return snakePoints;
	}

	@Override
	@Generated
	public Collection<Point> getObstacles() {
		return map.obstaclesCopy();
	}

	@Override
	@Generated
	public int getMapHeight() {
		return map.getMapHeight();
	}

	@Override
	@Generated
	public int getMapWidth() {
		return map.getMapWidth();
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
