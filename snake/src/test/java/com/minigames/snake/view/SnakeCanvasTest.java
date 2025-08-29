package com.minigames.snake.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

import org.junit.Test;

public class SnakeCanvasTest {

	@Test
	public void testgenerateNewGridSingleElement() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		canvas.newGrid(1, 1);
		assertThat(canvas.getGrid()[0][0]).isEqualTo(Color.GRAY);
	}

	@Test
	public void testgenerateNewGridMultipleElementRowWise() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		canvas.newGrid(2, 1);
		assertThat(canvas.getGrid()[0][0]).isEqualTo(Color.GRAY);
		assertThat(canvas.getGrid()[1][0]).isEqualTo(Color.GRAY);
	}

	@Test
	public void testgenerateNewGridMultipleElementColumnWise() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		canvas.newGrid(1, 2);
		assertThat(canvas.getGrid()[0][0]).isEqualTo(Color.GRAY);
		assertThat(canvas.getGrid()[0][1]).isEqualTo(Color.GRAY);
	}

	@Test
	public void testSetColorIllegalArgsNegX() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		canvas.newGrid(1, 1);
		assertThatThrownBy(() -> canvas.setColor(-1, 0, Color.BLACK)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("illegal coordinates");
	}

	@Test
	public void testSetColorIllegalArgsNegY() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		canvas.newGrid(1, 1);
		assertThatThrownBy(() -> canvas.setColor(0, -1, Color.BLACK)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("illegal coordinates");
	}

	@Test
	public void testSetColorIllegalArgsXTooBig() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		canvas.newGrid(1, 1);
		assertThatThrownBy(() -> canvas.setColor(1, 0, Color.BLACK)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("illegal coordinates");
	}

	@Test
	public void testSetColorIllegalArgsYTooBig() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		canvas.newGrid(1, 1);
		assertThatThrownBy(() -> canvas.setColor(0, 1, Color.BLACK)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("illegal coordinates");
	}

	@Test
	public void testSetColorValidArgs() {
		assertThatCode(() -> {
			SnakeCanvas canvas = new SnakeCanvas(15);
			canvas.newGrid(1, 1);
			canvas.setColor(0, 0, Color.BLACK);
			assertThat(canvas.getGrid()[0][0]).isEqualTo(Color.BLACK);
		}).doesNotThrowAnyException();
	}

	@Test
	public void testConstructor() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(300, 300));
		assertThat(canvas.getBackground()).isEqualTo(Color.white);
	}


	@Test
	public void testPaintComponentSingleCell() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		canvas.newGrid(1, 1);
		BufferedImage image = new BufferedImage(15, 15, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = image.createGraphics();
		canvas.setColor(0, 0, Color.BLACK);
		canvas.paintComponent(graphics);
		graphics.dispose();
		IntStream.range(0, 15).forEach(x -> IntStream.range(0, 15)
				.forEach(y -> assertThat(image.getRGB(x, y)).isEqualTo(Color.BLACK.getRGB())));
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(15, 15));
	}

	@Test
	public void testPaintComponentMultipleCellsRowWise() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		canvas.newGrid(2, 1);
		BufferedImage image = new BufferedImage(15, 30, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = image.createGraphics();
		canvas.setColor(0, 0, Color.BLACK);
		canvas.paintComponent(graphics);
		graphics.dispose();
		IntStream.range(0, 15).forEach(x -> IntStream.range(0, 15)
				.forEach(y -> assertThat(image.getRGB(x, y)).isEqualTo(Color.BLACK.getRGB())));
		IntStream.range(0, 15).forEach(x -> IntStream.range(15, 30)
				.forEach(y -> assertThat(image.getRGB(x, y)).isEqualTo(Color.GRAY.getRGB())));
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(15, 30));
	}

	@Test
	public void testPaintComponentMultipleCellsColumnWise() {
		SnakeCanvas canvas = new SnakeCanvas(15);
		canvas.newGrid(1, 2);
		BufferedImage image = new BufferedImage(30, 15, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = image.createGraphics();
		canvas.setColor(0, 0, Color.BLACK);
		canvas.paintComponent(graphics);
		graphics.dispose();
		IntStream.range(0, 15).forEach(x -> IntStream.range(0, 15)
				.forEach(y -> assertThat(image.getRGB(x, y)).isEqualTo(Color.BLACK.getRGB())));
		IntStream.range(15, 30).forEach(x -> IntStream.range(0, 15)
				.forEach(y -> assertThat(image.getRGB(x, y)).isEqualTo(Color.GRAY.getRGB())));
		assertThat(canvas.getPreferredSize()).isEqualTo(new Dimension(30, 15));
	}

}
