package com.minigames.snake.presenter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.SnakeRepository;
import com.minigames.snake.view.SnakeView;

public class SnakeMatchPresenterImplTest {

	@Mock
	private ObstaclesSupplier obSupplier;
	@Mock
	private PositionSupplier poSupplier;
	@Mock
	SnakeRepository repository;
	@InjectMocks
	private SnakeMatchPresenterImpl presenter;
	private SnakeView view;
	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		view = mock(SnakeView.class);
	}

	@After
	public void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	public void testStartMatchNoObstacles() {
		GameSetting configuration = new GameSetting("1", 10, 10, 0);
		when(obSupplier.generateObstacles(configuration)).thenReturn(new ArrayList<Point>());
		when(poSupplier.generateSnakeHeadPosition(isA(SnakeMap.class))).thenReturn(new Point(0, 0));
		when(poSupplier.generateApplePosition(isA(SnakeMap.class))).thenReturn(new Point(5, 5));
		ArgumentCaptor<SnakeMap> mapCaptor1 = ArgumentCaptor.forClass(SnakeMap.class);
		ArgumentCaptor<SnakeMap> mapCaptor2 = ArgumentCaptor.forClass(SnakeMap.class);
		presenter.startMatch(configuration);
		verify(obSupplier).generateObstacles(configuration);
		verify(poSupplier).generateSnakeHeadPosition(mapCaptor1.capture());
		verify(poSupplier).generateApplePosition(mapCaptor2.capture());
		SnakeMap map = presenter.getMap();
		SnakeMap capturedMap1 = mapCaptor1.getValue();
		SnakeMap capturedMap2 = mapCaptor2.getValue();
		assertThat(map.getMapHeight()).isEqualTo(configuration.getHeight());
		assertThat(map.getMapWidth()).isEqualTo(configuration.getWidth());
		assertThat(map.getObstacles()).isEmpty();
		assertThat(map.getSnakeHead()).isEqualTo(new Point(0, 0));
		assertThat(map.getApple()).isEqualTo(new Point(5, 5));
		assertThat(presenter.getRawScore()).isZero();
		assertThat(presenter.getConfiguration()).isEqualTo(configuration);
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(capturedMap1.getMapHeight()).isEqualTo(10);
		assertThat(capturedMap1.getMapWidth()).isEqualTo(10);
		assertThat(capturedMap1.getObstacles()).isEmpty();
		assertThat(capturedMap2.getMapHeight()).isEqualTo(10);
		assertThat(capturedMap2.getMapWidth()).isEqualTo(10);
		assertThat(capturedMap2.getObstacles()).isEmpty();
		verifyNoInteractions(repository);
		verifyNoMoreInteractions(obSupplier);
	}

	@Test
	public void testStartMatchSingleObstacle() {
		GameSetting configuration = new GameSetting("1", 10, 10, 1);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(1, 1));
		when(obSupplier.generateObstacles(configuration)).thenReturn(obstacles);
		when(poSupplier.generateSnakeHeadPosition(isA(SnakeMap.class))).thenReturn(new Point(0, 0));
		when(poSupplier.generateApplePosition(isA(SnakeMap.class))).thenReturn(new Point(5, 5));
		ArgumentCaptor<SnakeMap> mapCaptor1 = ArgumentCaptor.forClass(SnakeMap.class);
		ArgumentCaptor<SnakeMap> mapCaptor2 = ArgumentCaptor.forClass(SnakeMap.class);
		presenter.startMatch(configuration);
		verify(obSupplier).generateObstacles(configuration);
		verify(poSupplier).generateSnakeHeadPosition(mapCaptor1.capture());
		verify(poSupplier).generateApplePosition(mapCaptor2.capture());
		SnakeMap map = presenter.getMap();
		SnakeMap capturedMap1 = mapCaptor1.getValue();
		SnakeMap capturedMap2 = mapCaptor2.getValue();
		assertThat(map.getMapHeight()).isEqualTo(configuration.getHeight());
		assertThat(map.getMapWidth()).isEqualTo(configuration.getWidth());
		assertThat(map.getObstacles()).hasSize(1);
		assertThat(map.getSnakeHead()).isEqualTo(new Point(0, 0));
		assertThat(map.getApple()).isEqualTo(new Point(5, 5));
		assertThat(presenter.getRawScore()).isZero();
		assertThat(presenter.getConfiguration()).isEqualTo(configuration);
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(capturedMap1.getMapHeight()).isEqualTo(10);
		assertThat(capturedMap1.getMapWidth()).isEqualTo(10);
		assertThat(capturedMap1.getObstacles()).containsExactly(new Point(1, 1));
		assertThat(capturedMap2.getMapHeight()).isEqualTo(10);
		assertThat(capturedMap2.getMapWidth()).isEqualTo(10);
		assertThat(capturedMap2.getObstacles()).containsExactly(new Point(1, 1));
		verifyNoInteractions(repository);
		verifyNoMoreInteractions(obSupplier);
	}

	@Test
	public void testStartMatchMultipleObstacles() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(1, 1));
		obstacles.add(new Point(1, 2));
		when(obSupplier.generateObstacles(configuration)).thenReturn(obstacles);
		when(poSupplier.generateSnakeHeadPosition(isA(SnakeMap.class))).thenReturn(new Point(0, 0));
		when(poSupplier.generateApplePosition(isA(SnakeMap.class))).thenReturn(new Point(5, 5));
		ArgumentCaptor<SnakeMap> mapCaptor1 = ArgumentCaptor.forClass(SnakeMap.class);
		ArgumentCaptor<SnakeMap> mapCaptor2 = ArgumentCaptor.forClass(SnakeMap.class);
		presenter.startMatch(configuration);
		verify(obSupplier).generateObstacles(configuration);
		verify(poSupplier).generateSnakeHeadPosition(mapCaptor1.capture());
		verify(poSupplier).generateApplePosition(mapCaptor2.capture());
		SnakeMap map = presenter.getMap();
		SnakeMap capturedMap1 = mapCaptor1.getValue();
		SnakeMap capturedMap2 = mapCaptor2.getValue();
		assertThat(map.getMapHeight()).isEqualTo(configuration.getHeight());
		assertThat(map.getMapWidth()).isEqualTo(configuration.getWidth());
		assertThat(map.getObstacles()).hasSize(2);
		assertThat(map.getSnakeHead()).isEqualTo(new Point(0, 0));
		assertThat(map.getApple()).isEqualTo(new Point(5, 5));
		assertThat(presenter.getRawScore()).isZero();
		assertThat(presenter.getConfiguration()).isEqualTo(configuration);
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(capturedMap1.getMapHeight()).isEqualTo(10);
		assertThat(capturedMap1.getMapWidth()).isEqualTo(10);
		assertThat(capturedMap1.getObstacles()).containsExactlyInAnyOrder(new Point(1, 1), new Point(1, 2));
		assertThat(capturedMap2.getMapHeight()).isEqualTo(10);
		assertThat(capturedMap2.getMapWidth()).isEqualTo(10);
		assertThat(capturedMap2.getObstacles()).containsExactlyInAnyOrder(new Point(1, 1), new Point(1, 2));
		verifyNoInteractions(repository);
		verifyNoMoreInteractions(obSupplier);
	}

	@Test
	public void testEndMatch() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		GameSetting setting = new GameSetting("1", 10, 10, 0);
		presenter.setMap(SnakeMap.of(10, 10, obstacles));
		presenter.setRawScore(10);
		presenter.setConfiguration(setting);
		presenter.endMatch(view);
		ArgumentCaptor<GameRecord> recordCaptor = ArgumentCaptor.forClass(GameRecord.class);
		verify(view).update();
		verifyNoMoreInteractions(view);
		verify(repository).createRecord(recordCaptor.capture());
		verifyNoMoreInteractions(repository);
		GameRecord newRecord = recordCaptor.getValue();
		assertThat(newRecord.getDate()).isToday();
		assertThat(newRecord.getSetting()).isEqualTo(setting);
		assertThat(newRecord.getScore()).isEqualTo(10);
	}

	@Test
	public void testCurrentScore() {
		GameSetting setting = new GameSetting("1", 10, 10, 10);
		presenter.setRawScore(10);
		presenter.setConfiguration(setting);
		assertThat(presenter.currentScore()).isEqualTo(26);
	}

	@Test
	public void testSnakeQueueEmptyBody() {
		SnakeMap map = SnakeMap.of(10, 10, new ArrayList<Point>());
		map.setSnakeHead(new Point(1, 1));
		presenter.setMap(map);
		assertThat(presenter.snakeCollection()).containsExactlyInAnyOrder(new Point(1, 1));
	}

	@Test
	public void testSnakeQueueSingleBody() {
		SnakeMap map = SnakeMap.of(10, 10, new ArrayList<Point>());
		map.setSnakeHead(new Point(1, 1));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(1, 3));
		presenter.setMap(map);
		assertThat(presenter.snakeCollection()).containsExactlyInAnyOrder(new Point(1, 1), new Point(1, 3));
	}

	@Test
	public void testSnakeQueueMultipleBody() {
		SnakeMap map = SnakeMap.of(10, 10, new ArrayList<Point>());
		map.setSnakeHead(new Point(1, 1));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(1, 3));
		body.add(new Point(1, 2));
		presenter.setMap(map);
		assertThat(presenter.snakeCollection()).containsExactlyInAnyOrder(new Point(1, 1), new Point(1, 3),
				new Point(1, 2));
	}

	@Test
	public void testGoUpEmpty() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goUp(view);
		assertThat(presenter.isGameOver()).isFalse();
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(presenter.getRawScore()).isZero();
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoUpObstacle() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(5, 6));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goUp(view);
		assertThat(presenter.isGameOver()).isTrue();
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(presenter.getRawScore()).isZero();
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoUpApple() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		map.setApple(new Point(5, 6));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goUp(view);
		assertThat(presenter.isGameOver()).isFalse();
		assertThat(presenter.isSnakeBigger()).isTrue();
		assertThat(presenter.getRawScore()).isEqualTo(1);
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoDownEmpty() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goDown(view);
		assertThat(presenter.isGameOver()).isFalse();
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(presenter.getRawScore()).isZero();
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoDownObstacle() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(5, 4));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goDown(view);
		assertThat(presenter.isGameOver()).isTrue();
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(presenter.getRawScore()).isZero();
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoDownApple() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		map.setApple(new Point(5, 4));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goDown(view);
		assertThat(presenter.isGameOver()).isFalse();
		assertThat(presenter.isSnakeBigger()).isTrue();
		assertThat(presenter.getRawScore()).isEqualTo(1);
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoLeftEmpty() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goLeft(view);
		assertThat(presenter.isGameOver()).isFalse();
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(presenter.getRawScore()).isZero();
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoLeftObstacle() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(4, 5));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goLeft(view);
		assertThat(presenter.isGameOver()).isTrue();
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(presenter.getRawScore()).isZero();
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoLeftApple() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		map.setApple(new Point(4, 5));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goLeft(view);
		assertThat(presenter.isGameOver()).isFalse();
		assertThat(presenter.isSnakeBigger()).isTrue();
		assertThat(presenter.getRawScore()).isEqualTo(1);
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoRightEmpty() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goRight(view);
		assertThat(presenter.isGameOver()).isFalse();
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(presenter.getRawScore()).isZero();
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoRightObstacle() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(6, 5));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goRight(view);
		assertThat(presenter.isGameOver()).isTrue();
		assertThat(presenter.isSnakeBigger()).isFalse();
		assertThat(presenter.getRawScore()).isZero();
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoRightApple() {
		GameSetting configuration = new GameSetting("1", 10, 10, 2);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		map.setApple(new Point(6, 5));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.goRight(view);
		assertThat(presenter.isGameOver()).isFalse();
		assertThat(presenter.isSnakeBigger()).isTrue();
		assertThat(presenter.getRawScore()).isEqualTo(1);
		verify(view).update();
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testGoUpBeatGameNoObstacles() {
		GameSetting configuration = new GameSetting("1", 2, 2, 0);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(2, 2, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(1, 1));
		body.add(new Point(1, 0));
		map.setApple(new Point(0, 1));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.setRawScore(3);
		presenter.goUp(view);
		assertThat(presenter.isGameOver()).isTrue();
	}
	
	@Test
	public void testGoUpBeatGameWithObstacles() {
		GameSetting configuration = new GameSetting("1", 2, 2, 1);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(1, 1));
		SnakeMap map = SnakeMap.of(2, 2, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(1, 0));
		map.setApple(new Point(0, 1));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.setRawScore(2);
		presenter.goUp(view);
		assertThat(presenter.isGameOver()).isTrue();
	}
	
	@Test
	public void testGoDownBeatGameNoObstacles() {
		GameSetting configuration = new GameSetting("1", 2, 2, 0);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(2, 2, obstacles);
		map.setSnakeHead(new Point(0, 1));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(1, 0));
		body.add(new Point(1, 1));
		map.setApple(new Point(0, 0));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.setRawScore(3);
		presenter.goDown(view);
		assertThat(presenter.isGameOver()).isTrue();
	}
	
	@Test
	public void testGoDownBeatGameWithObstacles() {
		GameSetting configuration = new GameSetting("1", 2, 2, 1);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(1, 0));
		SnakeMap map = SnakeMap.of(2, 2, obstacles);
		map.setSnakeHead(new Point(0, 1));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(1, 1));
		map.setApple(new Point(0, 0));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.setRawScore(2);
		presenter.goDown(view);
		assertThat(presenter.isGameOver()).isTrue();
	}
	
	@Test
	public void testGoLeftBeatGameNoObstacles() {
		GameSetting configuration = new GameSetting("1", 2, 2, 0);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(2, 2, obstacles);
		map.setSnakeHead(new Point(1, 0));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(0, 1));
		body.add(new Point(1, 1));
		map.setApple(new Point(0, 0));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.setRawScore(3);
		presenter.goLeft(view);
		assertThat(presenter.isGameOver()).isTrue();
	}
	
	@Test
	public void testGoLeftBeatGameWithObstacles() {
		GameSetting configuration = new GameSetting("1", 2, 2, 1);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, 1));
		SnakeMap map = SnakeMap.of(2, 2, obstacles);
		map.setSnakeHead(new Point(1, 0));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(1, 1));
		map.setApple(new Point(0, 0));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.setRawScore(2);
		presenter.goLeft(view);
		assertThat(presenter.isGameOver()).isTrue();
	}
	
	@Test
	public void testGoRightBeatGameNoObstacles() {
		GameSetting configuration = new GameSetting("1", 2, 2, 0);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		SnakeMap map = SnakeMap.of(2, 2, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(1, 1));
		body.add(new Point(0, 1));
		map.setApple(new Point(1, 0));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.setRawScore(3);
		presenter.goRight(view);
		assertThat(presenter.isGameOver()).isTrue();
	}
	
	@Test
	public void testGoRightBeatGameWithObstacles() {
		GameSetting configuration = new GameSetting("1", 2, 2, 1);
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(1, 1));
		SnakeMap map = SnakeMap.of(2, 2, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(0, 1));
		map.setApple(new Point(1, 0));
		presenter.setConfiguration(configuration);
		presenter.setMap(map);
		presenter.setRawScore(2);
		presenter.goRight(view);
		assertThat(presenter.isGameOver()).isTrue();
	}
	
}
