package com.minigames.snake;

import jakarta.persistence.*;

@Generated
@Entity
public class GameSetting extends BaseEntity {

	private int height;
	private int width;
	private float velocity;

	// for JPA
	protected GameSetting() {
		super();
	}

	public GameSetting(String uuid) {
		super(uuid);
	}

}
