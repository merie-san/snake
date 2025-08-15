package com.minigames.snake;

import java.time.LocalDate;
import jakarta.persistence.*;

@Generated
@Entity
public class GameRecord extends BaseEntity {

	private int score;
	private LocalDate date;
	@ManyToOne
	private GameSetting setting;

	// for JPA
	protected GameRecord() {
		super();
	}

	public GameRecord(String uuid) {
		super(uuid);
	}

}
