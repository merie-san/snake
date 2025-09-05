package com.minigames.snake.presenter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;

import org.junit.Test;

public class RandomPositionSupplierTest {
	private RandomPositionSupplier supplier = new RandomPositionSupplier();

	@Test
	public void testGenerateSnakeHeadPositionNullMap() {
		assertThatThrownBy(() -> supplier.generateSnakeHeadPosition(null)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the map cannot be null");
	}

	@Test
	public void testGenerateSnakeHeadPositionEmptyMap() {
		assertThat(supplier.generateSnakeHeadPosition(SnakeMap.of(1, 1, new ArrayList<Point>()))).satisfies(point -> {
			assertThat(point).isEqualTo(new Point(0, 0));
		});
	}

	@Test
	public void testGenerateSnakeHeadPositionOneObstacle() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		assertThat(supplier.generateSnakeHeadPosition(SnakeMap.of(2, 1, obstacles))).isEqualTo(new Point(1, 0));

	}

	@Test
	public void testGenerateSnakeHeadPositionMultipleObstacles() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(2, 0));
		assertThat(supplier.generateSnakeHeadPosition(SnakeMap.of(3, 1, obstacles))).isEqualTo(new Point(1, 0));
	}

	@Test
	public void testGenerateSnakeHeadPositionMapFull() {
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(1, 0));
		obstacles.add(new Point(2, 0));
		SnakeMap map = SnakeMap.of(3, 1, obstacles);
		assertThatThrownBy(() -> supplier.generateSnakeHeadPosition(map)).isInstanceOf(IllegalStateException.class)
				.hasMessage("the map is full of obstacles");
	}

	@Test
	public void testGenerateApplePositionNullMap() {
		assertThatThrownBy(() -> supplier.generateApplePosition(null)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the map cannot be null");
	}

	@Test
	public void testGenerateApplePositionOnlySnakeHeadOnMap() {
		SnakeMap map = SnakeMap.of(2, 1, new ArrayList<Point>());
		map.setSnakeHead(new Point(1, 0));
		assertThat(supplier.generateApplePosition(map)).isEqualTo(new Point(0, 0));
	}

	@Test
	public void testGenerateApplePositionSnakeHeadSingleObstacle() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, 0));
		SnakeMap map = SnakeMap.of(3, 1, obstacles);
		map.setSnakeHead(new Point(1, 0));
		assertThat(supplier.generateApplePosition(map)).isEqualTo(new Point(2, 0));
	}

	@Test
	public void testGenerateApplePositionSnakeHeadMultipleObstacles() {
		ArrayList<Point> obstacles = new ArrayList<Point>();
		obstacles.add(new Point(0, 0));
		obstacles.add(new Point(2, 0));
		SnakeMap map = SnakeMap.of(4, 1, obstacles);
		map.setSnakeHead(new Point(1, 0));
		assertThat(supplier.generateApplePosition(map)).isEqualTo(new Point(3, 0));
	}

	@Test
	public void testGenerateApplePositionSnakeBodySingle() {
		SnakeMap map = SnakeMap.of(3, 1, new ArrayList<Point>());
		map.setSnakeHead(new Point(0, 0));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(1, 0));
		assertThat(supplier.generateApplePosition(map)).isEqualTo(new Point(2, 0));
	}

	@Test
	public void testGenerateApplePositionSnakeBodyMultiple() {
		SnakeMap map = SnakeMap.of(4, 1, new ArrayList<Point>());
		map.setSnakeHead(new Point(0, 0));
		Collection<Point> body = map.getSnakeBody();
		body.add(new Point(2, 0));
		body.add(new Point(1, 0));
		assertThat(supplier.generateApplePosition(map)).isEqualTo(new Point(3, 0));
	}

	@Test
	public void testGenerateApplePositionMapFull(){
		ArrayList<Point> obstacles = new ArrayList<>();
		obstacles.add(new Point(2, 0));
		obstacles.add(new Point(3, 0));
		SnakeMap map=SnakeMap.of(5, 1, obstacles);
		map.setSnakeHead(new Point(0,0));
		Deque<Point> snakeBody = map.getSnakeBody();
		snakeBody.add(new Point(1,1));
		snakeBody.add(new Point(1,0));
		assertThatThrownBy(()->supplier.generateApplePosition(map)).isInstanceOf(IllegalStateException.class).hasMessage("the map is full of objects");
	}
}
