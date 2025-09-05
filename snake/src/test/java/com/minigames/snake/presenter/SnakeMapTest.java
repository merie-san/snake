package com.minigames.snake.presenter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;

import org.junit.Test;

public class SnakeMapTest {

	@Test
	public void testOfValidArguments() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<Point>();
			obstacles.add(new Point(0, 0));
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			assertThat(map.getMapHeight()).isEqualTo(10);
			assertThat(map.getMapWidth()).isEqualTo(10);
			assertThat(map.getObstacles()).containsExactlyElementsOf(obstacles);
		}).doesNotThrowAnyException();
	}

	@Test
	public void testOfNegativeWidth() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		assertThatThrownBy(() -> {
			SnakeMap.of(-10, 10, obstacles);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("width cannot be zero or negative");
	}

	@Test
	public void testOfNegativeHeight() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		assertThatThrownBy(() -> SnakeMap.of(10, -10, obstacles)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("height cannot be zero or negative");
	}

	@Test
	public void testOfZeroWidth() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		assertThatThrownBy(() -> SnakeMap.of(0, 10, obstacles)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("width cannot be zero or negative");
	}

	@Test
	public void testOfZeroHeight() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		assertThatThrownBy(() -> SnakeMap.of(10, 0, obstacles)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("height cannot be zero or negative");
	}

	@Test
	public void testContructorNullObstacles() {
		assertThatThrownBy(() -> SnakeMap.of(10, 10, null)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("obstacles collection cannot be null");
	}

	@Test
	public void testOfOutOfBoundObstaclesX() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(100, 0));
		assertThatThrownBy(() -> SnakeMap.of(10, 10, obstacles)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("an obstacle is out of bounds of the map");
	}

	@Test
	public void testOfOutOfBoundObstaclesY() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, 100));
		assertThatThrownBy(() -> SnakeMap.of(10, 10, obstacles)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("an obstacle is out of bounds of the map");
	}

	@Test
	public void testOfOutOfBoundObstaclesXNegative() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(-100, 0));
		assertThatThrownBy(() -> SnakeMap.of(10, 10, obstacles)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("an obstacle is out of bounds of the map");
	}

	@Test
	public void testOfOutOfBoundObstaclesYNegative() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, -100));
		assertThatThrownBy(() -> SnakeMap.of(10, 10, obstacles)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("an obstacle is out of bounds of the map");
	}

	@Test
	public void testOfOutOfBoundsObstaclesSomeAreNot() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(100, 100));
		obstacles.add(new Point(-100, -100));
		assertThatThrownBy(() -> SnakeMap.of(10, 10, obstacles)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("an obstacle is out of bounds of the map");
	}

	@Test
	public void testPutSnakeEmptyMap() {
		assertThatCode(() -> {
			SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
			map.putSnakeHead(new Point(1, 1));
			assertThat(map.getSnakeHead()).isEqualTo(new Point(1, 1));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutSnakeNotEmptyMap() {
		assertThatCode(() -> {
			Collection<Point> obstacles = new ArrayList<Point>();
			obstacles.add(new Point(5, 5));
			SnakeMap map = SnakeMap.of(20, 20, obstacles);
			map.putSnakeHead(new Point(1, 1));
			assertThat(map.getSnakeHead()).isEqualTo(new Point(1, 1));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutSnakeBodyCleared() {
		assertThatCode(() -> {
			SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
			Deque<Point> body = map.getSnakeBody();
			body.add(new Point(1, 1));
			map.putSnakeHead(new Point(5, 5));
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 5));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutSnakeAtBoundaryXMax() {
		assertThatCode(() -> {
			SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
			map.putSnakeHead(new Point(19, 10));
			assertThat(map.getSnakeHead()).isEqualTo(new Point(19, 10));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutSnakeAtBoundaryXZero() {
		assertThatCode(() -> {
			SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
			map.putSnakeHead(new Point(0, 10));
			assertThat(map.getSnakeHead()).isEqualTo(new Point(0, 10));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutSnakeAtBoundaryYMax() {
		assertThatCode(() -> {
			SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
			map.putSnakeHead(new Point(10, 19));
			assertThat(map.getSnakeHead()).isEqualTo(new Point(10, 19));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutSnakeAtBoundaryYZero() {
		assertThatCode(() -> {
			SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
			map.putSnakeHead(new Point(0, 10));
			assertThat(map.getSnakeHead()).isEqualTo(new Point(0, 10));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutSnakeOutOfBoundsX() {
		SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
		Point snake = new Point(20, 1);
		assertThatThrownBy(() -> map.putSnakeHead(snake)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the snake is out of bounds of the map");
	}

	@Test
	public void testPutSnakeOutOfBoundsY() {
		SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
		Point snake = new Point(1, 20);
		assertThatThrownBy(() -> map.putSnakeHead(snake)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the snake is out of bounds of the map");
	}

	@Test
	public void testPutSnakeOutOfBoundsXNegative() {
		SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
		Point snake = new Point(-1, 1);
		assertThatThrownBy(() -> map.putSnakeHead(snake)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the snake is out of bounds of the map");
	}

	@Test
	public void testPutSnakeOutOfBoundsYNegative() {
		SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
		Point snake = new Point(1, -1);
		assertThatThrownBy(() -> map.putSnakeHead(snake)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the snake is out of bounds of the map");
	}

	@Test
	public void testPutSnakeOnObstacleSingle() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, 0));
		SnakeMap map = SnakeMap.of(20, 20, obstacles);
		Point snake = new Point(0, 0);
		assertThatThrownBy(() -> map.putSnakeHead(snake)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the snake is on an obstacle");
	}

	@Test
	public void testPutSnakeOnObstacleMultiple() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(1, 1));
		SnakeMap map = SnakeMap.of(20, 20, obstacles);
		Point snake = new Point(0, 0);
		assertThatThrownBy(() -> map.putSnakeHead(snake)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the snake is on an obstacle");
	}

	@Test
	public void testPutAppleValidArgumentsMapNotClear() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<Point>();
			obstacles.add(new Point(3, 3));
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(0, 0));
			Deque<Point> snakeBody = map.getSnakeBody();
			snakeBody.add(new Point(1, 0));
			map.putApple(new Point(1, 1));
			assertThat(map.getApple()).isEqualTo(new Point(1, 1));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutAppleValidArgumentsMapClear() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<Point>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(0, 0));
			map.putApple(new Point(1, 1));
			assertThat(map.getApple()).isEqualTo(new Point(1, 1));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutAppleAtBoundaryXMax() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<Point>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(0, 0));
			map.putApple(new Point(9, 1));
			assertThat(map.getApple()).isEqualTo(new Point(9, 1));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutAppleAtBoundaryXZero() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<Point>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(0, 0));
			map.putApple(new Point(0, 1));
			assertThat(map.getApple()).isEqualTo(new Point(0, 1));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutAppleAtBoundaryYMax() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<Point>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(0, 0));
			map.putApple(new Point(5, 9));
			assertThat(map.getApple()).isEqualTo(new Point(5, 9));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutAppleAtBoundaryYZero() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<Point>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			map.putApple(new Point(5, 0));
			assertThat(map.getApple()).isEqualTo(new Point(5, 0));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testPutAppleOutOfBoundsX() {
		SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
		Point apple = new Point(20, 1);
		assertThatThrownBy(() -> map.putApple(apple)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the apple is out of bounds of the map");
	}

	@Test
	public void testPutAppleOutOfBoundsXNegative() {
		SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
		Point apple = new Point(-1, 1);
		assertThatThrownBy(() -> map.putApple(apple)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the apple is out of bounds of the map");
	}

	@Test
	public void testPutAppleOutOfBoundsY() {
		SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
		Point apple = new Point(1, 20);
		assertThatThrownBy(() -> map.putApple(apple)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the apple is out of bounds of the map");
	}

	@Test
	public void testPutAppleOutOfBoundsYNegative() {
		SnakeMap map = SnakeMap.of(20, 20, new ArrayList<Point>());
		Point apple = new Point(1, -1);
		assertThatThrownBy(() -> map.putApple(apple)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the apple is out of bounds of the map");
	}

	@Test
	public void testPutAppleOnObstacleSingle() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, 0));
		SnakeMap map = SnakeMap.of(20, 20, obstacles);
		Point apple = new Point(0, 0);
		assertThatThrownBy(() -> map.putApple(apple)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the apple is on an obstacle");
	}

	@Test
	public void testPutAppleOnObstacleMultiple() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(1, 1));
		SnakeMap map = SnakeMap.of(20, 20, obstacles);
		Point apple = new Point(0, 0);
		assertThatThrownBy(() -> map.putApple(apple)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the apple is on an obstacle");
	}

	@Test
	public void testPutAppleOnSnakeHead() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(3, 3));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Point apple = new Point(0, 0);
		assertThatThrownBy(() -> map.putApple(apple)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the apple is on the snake");
	}

	@Test
	public void testPutAppleOnSnakeBody() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(3, 3));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(1, 0));
		Point apple = new Point(1, 0);
		assertThatThrownBy(() -> map.putApple(apple)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the apple is on the snake");
	}

	@Test
	public void testObstaclesCopyEmpty() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		assertThat(map.obstaclesCopy()).isEmpty();
	}

	@Test
	public void testObstaclesCopySingle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(1, 1));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		assertThat(map.obstaclesCopy()).containsExactly(new Point(1, 1));
	}

	@Test
	public void testObstaclesCopyMultiple() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(1, 2));
		obstacles.add(new Point(1, 1));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		assertThat(map.obstaclesCopy()).containsExactlyInAnyOrder(new Point(1, 1), new Point(1, 2));
	}

	@Test
	public void testSnakeBodyCopyEmpty() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		assertThat(map.snakeBodyCopy()).isEmpty();
	}

	@Test
	public void testSnakeBodyCopySingle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.getSnakeBody().add(new Point(1, 1));
		assertThat(map.snakeBodyCopy()).containsExactly(new Point(1, 1));
	}

	@Test
	public void testSnakeBodyCopyMultiple() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(1, 1));
		snakeBody.add(new Point(1, 2));
		assertThat(map.snakeBodyCopy()).containsExactlyInAnyOrder(new Point(1, 1), new Point(1, 2));
	}

	@Test
	public void testCheckFreeDirectionNull() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 10));
		assertThatThrownBy(() -> map.checkFree(null, false)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the teller cannot be null");
	}

	@Test
	public void testCheckFreeUpHitMapBoundary() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 9));
		assertThat(map.checkFree(new UpTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeDownHitMapBoundary() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		assertThat(map.checkFree(new DownTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeLeftHitMapBoundary() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		assertThat(map.checkFree(new LeftTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeRightHitMapBoundary() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(9, 9));
		assertThat(map.checkFree(new RightTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeAtMapBoundaryXMax() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 8));
		assertThat(map.checkFree(new UpTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeAtMapBoundaryXZero() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 1));
		assertThat(map.checkFree(new DownTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeAtMapBoundaryYMax() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(8, 5));
		assertThat(map.checkFree(new RightTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeAtMapBoundaryYZero() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(1, 5));
		assertThat(map.checkFree(new LeftTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeUpHitObstacle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 1));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		assertThat(map.checkFree(new UpTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeDownHitObstacle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 1));
		assertThat(map.checkFree(new DownTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeLeftHitObstacle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(1, 0));
		assertThat(map.checkFree(new LeftTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeRightHitObstacle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(1, 0));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		assertThat(map.checkFree(new RightTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeUpHitNeckSingle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(0, 1));
		assertThat(map.checkFree(new UpTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeDownHitNeckSingle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 1));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(0, 0));
		assertThat(map.checkFree(new DownTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeLeftHitNeckSingle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(1, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(0, 0));
		assertThat(map.checkFree(new LeftTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeRightHitNeckSingle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(1, 0));
		assertThat(map.checkFree(new RightTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeUpHitNeckMultiple() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(0, 2));
		snakeBody.add(new Point(0, 1));
		assertThat(map.checkFree(new UpTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeDownHitNeckMultiple() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 2));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(0, 0));
		snakeBody.add(new Point(0, 1));
		assertThat(map.checkFree(new DownTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeLeftHitNeckMultiple() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(2, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(0, 0));
		snakeBody.add(new Point(1, 0));
		assertThat(map.checkFree(new LeftTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeRightHitNeckMultiple() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(2, 0));
		snakeBody.add(new Point(1, 0));
		assertThat(map.checkFree(new RightTeller(), false)).isFalse();
	}

	@Test
	public void testCheckFreeUpHitBodyNoGrowth() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(1, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(1, 1));
		snakeBody.add(new Point(0, 1));
		snakeBody.add(new Point(0, 0));
		assertThat(map.checkFree(new UpTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeDownHitBodyNoGrowth() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(1, 1));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(1, 0));
		snakeBody.add(new Point(0, 0));
		snakeBody.add(new Point(0, 1));
		assertThat(map.checkFree(new DownTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeLeftHitBodyNoGrowth() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(1, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(0, 0));
		snakeBody.add(new Point(0, 1));
		snakeBody.add(new Point(1, 1));
		assertThat(map.checkFree(new LeftTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeRightHitBodyNoGrowth() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(1, 0));
		snakeBody.add(new Point(1, 1));
		snakeBody.add(new Point(0, 1));
		assertThat(map.checkFree(new RightTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeUpHitBodyGrowth() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(1, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(1, 1));
		snakeBody.add(new Point(0, 1));
		snakeBody.add(new Point(0, 0));
		assertThat(map.checkFree(new UpTeller(), true)).isFalse();
	}

	@Test
	public void testCheckFreeDownHitBodyGrowth() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(1, 1));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(1, 0));
		snakeBody.add(new Point(0, 0));
		snakeBody.add(new Point(0, 1));
		assertThat(map.checkFree(new DownTeller(), true)).isFalse();
	}

	@Test
	public void testCheckFreeLeftHitBodyGrowth() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(1, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(0, 0));
		snakeBody.add(new Point(0, 1));
		snakeBody.add(new Point(1, 1));
		assertThat(map.checkFree(new LeftTeller(), true)).isFalse();
	}

	@Test
	public void testCheckFreeRightHitBodyGrowth() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(1, 0));
		snakeBody.add(new Point(1, 1));
		snakeBody.add(new Point(0, 1));
		assertThat(map.checkFree(new RightTeller(), true)).isFalse();
	}

	@Test
	public void testCheckFreeUpMapEmpty() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		assertThat(map.checkFree(new UpTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeDownMapEmpty() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		assertThat(map.checkFree(new DownTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeLeftMapEmpty() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		assertThat(map.checkFree(new LeftTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeRightMapEmpty() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		assertThat(map.checkFree(new RightTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeUpMapSingle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(5, 4));
		assertThat(map.checkFree(new UpTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeDownMapSingle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(5, 6));
		assertThat(map.checkFree(new DownTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeLeftMapSingle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(6, 5));
		assertThat(map.checkFree(new LeftTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeRightMapSingle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(4, 5));
		assertThat(map.checkFree(new RightTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeUpMapMultiple() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(10, 10));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(5, 4));
		snakeBody.add(new Point(5, 3));
		assertThat(map.checkFree(new UpTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeDownMapMultiple() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(10, 10));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(5, 6));
		snakeBody.add(new Point(5, 7));
		assertThat(map.checkFree(new DownTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeLeftMapMultiple() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(10, 10));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(6, 5));
		snakeBody.add(new Point(7, 5));
		assertThat(map.checkFree(new LeftTeller(), false)).isTrue();
	}

	@Test
	public void testCheckFreeRightMapMultiple() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(10, 10));
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(5, 5));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(4, 5));
		snakeBody.add(new Point(3, 5));
		assertThat(map.checkFree(new RightTeller(), false)).isTrue();
	}

	@Test
	public void testMoveSnakeUpMapEmpty() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			assertThat(map.moveSnake(new UpTeller(), false)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 6));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeDownMapEmpty() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			assertThat(map.moveSnake(new DownTeller(), false)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 4));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeLeftMapEmpty() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			assertThat(map.moveSnake(new LeftTeller(), false)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(4, 5));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeRightMapEmpty() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			assertThat(map.moveSnake(new RightTeller(), false)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(6, 5));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeUpMapEmptySnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			assertThat(map.moveSnake(new UpTeller(), true)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 6));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeDownMapEmptySnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			assertThat(map.moveSnake(new DownTeller(), true)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 4));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeLeftMapEmptySnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			assertThat(map.moveSnake(new LeftTeller(), true)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(4, 5));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeRightMapEmptySnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			assertThat(map.moveSnake(new RightTeller(), true)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(6, 5));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeUpHasBody() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			Deque<Point> snakeBody = map.getSnakeBody();
			snakeBody.add(new Point(5, 4));
			assertThat(map.moveSnake(new UpTeller(), false)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 6));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeDownHasBody() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			Deque<Point> snakeBody = map.getSnakeBody();
			snakeBody.add(new Point(5, 6));
			assertThat(map.moveSnake(new DownTeller(), false)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 4));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeLeftHasBody() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			Deque<Point> snakeBody = map.getSnakeBody();
			snakeBody.add(new Point(6, 5));
			assertThat(map.moveSnake(new LeftTeller(), false)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(4, 5));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeRightHasBody() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			Deque<Point> snakeBody = map.getSnakeBody();
			snakeBody.add(new Point(4, 5));
			assertThat(map.moveSnake(new RightTeller(), false)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(6, 5));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeUpHasBodySnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			Deque<Point> snakeBody = map.getSnakeBody();
			snakeBody.add(new Point(5, 4));
			assertThat(map.moveSnake(new UpTeller(), true)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 6));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 4), new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeDownHasBodySnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			Deque<Point> snakeBody = map.getSnakeBody();
			snakeBody.add(new Point(5, 6));
			assertThat(map.moveSnake(new DownTeller(), true)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 4));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 6), new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeLeftHasBodySnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			Deque<Point> snakeBody = map.getSnakeBody();
			snakeBody.add(new Point(6, 5));
			assertThat(map.moveSnake(new LeftTeller(), true)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(4, 5));
			assertThat(map.getSnakeBody()).containsExactly(new Point(6, 5), new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeRightHasBodySnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			Deque<Point> snakeBody = map.getSnakeBody();
			snakeBody.add(new Point(4, 5));
			assertThat(map.moveSnake(new RightTeller(), true)).isFalse();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(6, 5));
			assertThat(map.getSnakeBody()).containsExactly(new Point(4, 5), new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeUpBlocked() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 10));
		PositionTeller teller = new UpTeller();
		assertThatThrownBy(() -> map.moveSnake(teller, false)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("illegal direction");
	}

	@Test
	public void testMoveSnakeDownBlocked() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		PositionTeller teller = new DownTeller();
		assertThatThrownBy(() -> map.moveSnake(teller, false)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("illegal direction");
	}

	@Test
	public void testMoveSnakeLeftBlocked() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(0, 0));
		PositionTeller teller = new LeftTeller();
		assertThatThrownBy(() -> {
			map.moveSnake(teller, false);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("illegal direction");
	}

	@Test
	public void testMoveSnakeRightBlocked() {
		ArrayList<Point> obstacles = new ArrayList<>();
		SnakeMap map = SnakeMap.of(10, 10, obstacles);
		map.setSnakeHead(new Point(10, 10));
		PositionTeller teller = new RightTeller();
		assertThatThrownBy(() -> map.moveSnake(teller, false)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("illegal direction");
	}

	@Test
	public void testMoveSnakeUpHitApple() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			map.setApple(new Point(5, 6));
			assertThat(map.moveSnake(new UpTeller(), false)).isTrue();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 6));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeDownHitApple() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			map.setApple(new Point(5, 4));
			assertThat(map.moveSnake(new DownTeller(), false)).isTrue();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 4));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeLeftHitApple() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			map.setApple(new Point(4, 5));
			assertThat(map.moveSnake(new LeftTeller(), false)).isTrue();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(4, 5));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeRightHitApple() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			map.setApple(new Point(6, 5));
			assertThat(map.moveSnake(new RightTeller(), false)).isTrue();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(6, 5));
			assertThat(map.getSnakeBody()).isEmpty();
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeUpHitAppleSnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			map.setApple(new Point(5, 6));
			assertThat(map.moveSnake(new UpTeller(), true)).isTrue();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 6));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeDownHitAppleSnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			map.setApple(new Point(5, 4));
			assertThat(map.moveSnake(new DownTeller(), true)).isTrue();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(5, 4));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeLeftHitAppleSnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			map.setApple(new Point(4, 5));
			assertThat(map.moveSnake(new LeftTeller(), true)).isTrue();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(4, 5));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

	@Test
	public void testMoveSnakeRightHitAppleSnakeGrew() {
		assertThatCode(() -> {
			ArrayList<Point> obstacles = new ArrayList<>();
			SnakeMap map = SnakeMap.of(10, 10, obstacles);
			map.setSnakeHead(new Point(5, 5));
			map.setApple(new Point(6, 5));
			assertThat(map.moveSnake(new RightTeller(), true)).isTrue();
			assertThat(map.getSnakeHead()).isEqualTo(new Point(6, 5));
			assertThat(map.getSnakeBody()).containsExactly(new Point(5, 5));
		}).doesNotThrowAnyException();
	}

}
