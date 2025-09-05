package com.minigames.snake.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;

public class EntityTest {

	@Test
	public void testSameObjEntity() {
		BaseEntity entity = new GameSetting("1", 10, 10, 10);
		// instead of using directly isEqual() from AssertJ we call equals() so Jacoco
		// knows
		boolean result = entity.equals(entity);
		assertThat(result).isTrue();
	}

	@Test
	public void testNullEntity() {
		BaseEntity entity = new GameSetting("1", 10, 10, 10);
		boolean result = entity.equals(null);
		assertThat(result).isFalse();
	}

	@Test
	public void testDiffObjDiffEntity() {
		BaseEntity entity1 = new GameSetting("1", 10, 10, 10);
		BaseEntity entity2 = new GameSetting("2", 10, 10, 10);
		assertThat(entity1).isNotEqualTo(entity2);
	}

	@Test
	public void testDiffObjSameEntity() {
		BaseEntity entity1 = new GameSetting("1", 10, 10, 10);
		BaseEntity entity2 = new GameSetting("1", 10, 10, 10);
		assertThat(entity1).isEqualTo(entity2);
	}

	@Test
	public void testDiffClassEntity() {
		BaseEntity entity1 = new GameSetting("1", 10, 10, 10);
		Object obj = "";
		assertThat(entity1).isNotEqualTo(obj);
	}

	@Test
	public void testHash() {
		BaseEntity entity1 = new GameSetting("1", 10, 10, 10);
		BaseEntity entity2 = new GameSetting("1", 10, 10, 10);
		BaseEntity entity3 = new GameSetting("2", 10, 10, 10);
		assertThat(entity1).hasSameHashCodeAs(entity2);
		assertThat(entity1.hashCode()).isNotEqualTo(entity3.hashCode());
	}

	@Test
	public void testRecordToString() {
		assertThat(new GameRecord("1", 50, LocalDate.of(2025, 8, 25), new GameSetting("2", 20, 20, 10)))
				.hasToString("game: date 2025-08-25 - score 50 - SETTING_2");
	}

	@Test
	public void testSettingToString() {
		assertThat(new GameSetting("1", 20, 20, 10)).hasToString("SETTING_1: dimension (20×20) - obstacles 10");
	}
}
