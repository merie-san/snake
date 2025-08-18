package com.minigames.snake;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.Test;

public class ModelFactoryTest {

	@Test
	public void testGameSettingNegativeHeight() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(-1, 1, 1);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("height cannot be zero o negative");
	}

	@Test
	public void testGameSettingZeroHeight() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(0, 1, 1);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("height cannot be zero o negative");
	}

	@Test
	public void testGameSettingNegativeWidth() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(1, -1, 1);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("width cannot be zero o negative");
	}

	@Test
	public void testGameSettingZeroWidth() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(1, 0, 1);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("width cannot be zero o negative");
	}

	@Test
	public void testGameSettingNegativeVelocity() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(1, 1, 0);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("velocity cannot be zero o negative");
	}

	@Test
	public void testGameSettingZeroVelocity() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(1, 1, 0);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("velocity cannot be zero o negative");
	}

	@Test
	public void testGameSettingNoException() {
		assertThatCode(() -> {
			ModelFactory.gameSetting(1, 1, 1);
		}).doesNotThrowAnyException();
	}

	@Test
	public void testGameRecordNegativeScore() {
		assertThatThrownBy(() -> {
			ModelFactory.gameRecord(-1, LocalDate.now(), new GameSetting());
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("score cannot be negative");
	}

	@Test
	public void testGameRecordNullDate() {
		assertThatThrownBy(() -> {
			ModelFactory.gameRecord(0, null, new GameSetting());
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("date cannot be null");
	}

	@Test
	public void testGameRecordNullSetting() {
		assertThatThrownBy(() -> {
			ModelFactory.gameRecord(0, LocalDate.now(), null);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("setting cannot be null");
	}

	@Test
	public void testGameRecordNoException() {
		assertThatCode(() -> {
			ModelFactory.gameRecord(0, LocalDate.now(), new GameSetting());
		}).doesNotThrowAnyException();
	}
}
