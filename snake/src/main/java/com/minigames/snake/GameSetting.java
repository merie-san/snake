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

}
