package com.minigames.snake.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Generated
@Entity
// we suppress warnings on equals overriding since equality between entities is always implemented with UUID,
// so there is no need to override the base method.
@SuppressWarnings("java:S2160")
public class GameSetting extends BaseEntity {

	@Column(nullable = false)
	private int height;
	@Column(nullable = false)
	private int width;
	@Column(nullable = false)
	private int obstacleNumber;
	@Column(nullable = false)
	private boolean deleted = false;
	@Column(nullable = false)
	private String name;

	// for JPA
	protected GameSetting() {
		super();
	}

	public GameSetting(String uuid, int height, int width, int obstacleN) {
		super(uuid);
		this.height = height;
		this.width = width;
		this.obstacleNumber = obstacleN;
		this.name = "SETTING_" + uuid;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getObstacleNumber() {
		return obstacleNumber;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
