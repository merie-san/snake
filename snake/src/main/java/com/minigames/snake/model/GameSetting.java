package com.minigames.snake.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;


@Entity
// we suppress warnings on equals overriding since equality between entities is always implemented with UUID,
// so there is no need to override the base method.
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
	@Generated
	protected GameSetting() {
		super();
	}

	@Generated
	public GameSetting(String uuid, int width, int height, int obstacleN) {
		super(uuid);
		this.height = height;
		this.width = width;
		this.obstacleNumber = obstacleN;
		this.name = "SETTING_" + uuid;
	}

	@Generated
	public int getHeight() {
		return height;
	}

	@Generated
	public int getWidth() {
		return width;
	}

	@Generated
	public int getObstacleNumber() {
		return obstacleNumber;
	}

	@Generated
	public boolean isDeleted() {
		return deleted;
	}

	@Generated
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Generated
	public String getName() {
		return name;
	}

	@Generated
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName() + ": dimension (" + width + "×" + height + ") - obstacles " + obstacleNumber;
	}

}
