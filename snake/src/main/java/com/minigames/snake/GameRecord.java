package com.minigames.snake;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Generated
@Entity
public class GameRecord extends BaseEntity {

	@Column(nullable = false)
	private int score;
	@Column(nullable = false)
	private LocalDate date;
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false)
	private GameSetting setting;

	// for JPA
	protected GameRecord() {
		super();
	}

	public GameRecord(String uuid, int score, LocalDate date, GameSetting setting) {
		super(uuid);
		this.score = score;
		this.date = date;
		this.setting = setting;
	}

}
