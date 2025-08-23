package com.minigames.snake.presenter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.awt.Point;
import java.util.Collection;

import org.junit.Test;

import com.minigames.snake.model.GameSetting;

public class RandomObstaclesSupplierTest {

	private RandomObstaclesSupplier supplier = new RandomObstaclesSupplier();

	@Test
	public void testGenerateObstaclesNullConfigurationThrows() {
		assertThatThrownBy(() -> supplier.generateObstacles(null)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("the configuration cannot be null");
	}

	@Test
	public void testGenerateObstaclesConfigurationWithNoObstacles() {
		assertThat(supplier.generateObstacles(new GameSetting("1", 10, 10, 0))).isEmpty();
	}

	@Test
	public void testGenerateObstaclesConfigurationWithOneObstacle() {
		Collection<Point> obstacles = supplier.generateObstacles(new GameSetting("1", 1, 1, 1));
		assertThat(obstacles).hasSize(1).allSatisfy(obstacle -> {
			assertThat(obstacle).isEqualTo(new Point(0, 0));
		});
	}

	@Test
	public void testGenerateObstaclesConfigurationWithMultipleObstacles() {
		Collection<Point> obstacles = supplier.generateObstacles(new GameSetting("1", 2, 1, 2));
		assertThat(obstacles).containsExactlyInAnyOrder(new Point(0, 0), new Point(1, 0));
	}

}
