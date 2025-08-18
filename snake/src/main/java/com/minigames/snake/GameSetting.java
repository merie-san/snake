package com.minigames.snake;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Generated
@Entity
public class GameSetting extends BaseEntity {

	@Column(nullable = false)
	private int height;
	@Column(nullable = false)
	private int width;
	@Column(nullable = false)
	private float velocity;
	@Column(nullable = false)
	private boolean deleted = false;
	@Column(nullable = false)
	private String name = "";

	// for JPA
	protected GameSetting() {
		super();
	}

	public GameSetting(String uuid, int height, int width, float velocity) {
		super(uuid);
		this.height = height;
		this.width = width;
		this.velocity = velocity;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public float getVelocity() {
		return velocity;
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
