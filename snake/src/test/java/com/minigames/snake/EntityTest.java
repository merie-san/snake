package com.minigames.snake;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EntityTest {

	@Test
	public void testSameObjEntity() {
		BaseEntity entity = new GameSetting("1");
		//instead of using directly isEqual() from AssertJ we call equals() so Jacoco knows
	    boolean result = entity.equals(entity);
	    assertThat(result).isTrue();
	}

	@Test
	public void testNullEntity() {
		BaseEntity entity = new GameSetting("1");
		boolean result = entity.equals(null);
		assertThat(result).isFalse();
	}

	@Test
	public void testDiffObjDiffEntity() {
		BaseEntity entity1 = new GameSetting("1");
		BaseEntity entity2 = new GameSetting("2");
		assertThat(entity1).isNotEqualTo(entity2);
	}

	@Test
	public void testDiffObjSameEntity() {
		BaseEntity entity1 = new GameSetting("1");
		BaseEntity entity2 = new GameSetting("1");
		assertThat(entity1).isEqualTo(entity2);
	}

	@Test
	public void testDiffClassEntity() {
		BaseEntity entity1 = new GameSetting("1");
		Object obj = "";
		assertThat(entity1).isNotEqualTo(obj);
	}

	@Test
	public void testHash() {
		BaseEntity entity1 = new GameSetting("1");
		BaseEntity entity2 = new GameSetting("1");
		BaseEntity entity3 = new GameSetting("2");
		assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
		assertThat(entity1.hashCode()).isNotEqualTo(entity3.hashCode());
	}

}
