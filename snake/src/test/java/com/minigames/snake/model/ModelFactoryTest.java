package com.minigames.snake.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.Test;

public class ModelFactoryTest {

	@Test
	public void testGameSettingNegativeHeight() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(1, -1, 1);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("height cannot be zero o negative");
	}

	@Test
	public void testGameSettingZeroHeight() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(1, 0, 1);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("height cannot be zero o negative");
	}

	@Test
	public void testGameSettingNegativeWidth() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(-1, 1, 1);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("width cannot be zero o negative");
	}

	@Test
	public void testGameSettingZeroWidth() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(0, 1, 1);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("width cannot be zero o negative");
	}

	@Test
	public void testGameSettingNegativeObstacles() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(1, 1, 0);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("number of obstacles cannot be zero o negative");
	}

	@Test
	public void testGameSettingZeroObstacles() {
		assertThatThrownBy(() -> {
			ModelFactory.gameSetting(1, 1, 0);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("number of obstacles cannot be zero o negative");
	}
	
	@Test
	public void testGameSettingToomanyObstacles(){
		assertThatThrownBy(()-> ModelFactory.gameSetting(2, 1, 2)).isInstanceOf(IllegalArgumentException.class).hasMessage("number of obstacles must be less than the surface of the map");
		
	}

	@Test
	public void testGameSettingMaxObstacles() {
		assertThatCode(() -> {
			GameSetting setting = ModelFactory.gameSetting(2, 1, 1);
			assertThat(setting.getHeight()).isEqualTo(1);
			assertThat(setting.getWidth()).isEqualTo(2);
			assertThat(setting.getObstacleNumber()).isEqualTo(1);
		}).doesNotThrowAnyException();
	}

	@Test
	public void testGameRecordNegativeScore() {
		LocalDate date = LocalDate.now();
		GameSetting setting = new GameSetting();
		assertThatThrownBy(() -> {
			ModelFactory.gameRecord(-1, date, setting);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("score cannot be negative");
	}

	@Test
	public void testGameRecordNullDate() {
		GameSetting setting = new GameSetting();
		assertThatThrownBy(() -> {
			ModelFactory.gameRecord(0, null, setting);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("date cannot be null");
	}

	@Test
	public void testGameRecordNullSetting() {
		LocalDate date = LocalDate.now();
		assertThatThrownBy(() -> {
			ModelFactory.gameRecord(0, date, null);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("setting cannot be null");
	}

	@Test
	public void testGameRecordNoException() {
		assertThatCode(() -> {
			LocalDate date = LocalDate.now();
			GameSetting setting = new GameSetting();
			GameRecord gameRecord = ModelFactory.gameRecord(0, date, setting);
			assertThat(gameRecord.getScore()).isZero();
			assertThat(gameRecord.getDate()).isEqualTo(date);
			assertThat(gameRecord.getSetting()).isEqualTo(setting);
		}).doesNotThrowAnyException();
	}
}
