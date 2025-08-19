package com.minigames.snake.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Generated
@Entity
public class GameRecord extends BaseEntity {

	@Column(nullable = false)
	private int score;
	@Column(nullable = false)
	private LocalDate date;
	@ManyToOne
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

	public int getScore() {
		return score;
	}

	public LocalDate getDate() {
		return date;
	}

	public GameSetting getSetting() {
		return setting;
	}

}
