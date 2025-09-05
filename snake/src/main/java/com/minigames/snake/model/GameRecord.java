package com.minigames.snake.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
//we suppress warnings on equals overriding since equality between entities is always implemented with UUID,
//so there is no need to override the base method. 
@SuppressWarnings("java:S2160")
public class GameRecord extends BaseEntity {

	@Column(nullable = false)
	private int score;
	@Column(nullable = false)
	private LocalDate date;
	@ManyToOne
	@JoinColumn(nullable = false)
	private GameSetting setting;

	// for JPA
	@Generated
	protected GameRecord() {
		super();
	}

	@Generated
	public GameRecord(String uuid, int score, LocalDate date, GameSetting setting) {
		super(uuid);
		this.score = score;
		this.date = date;
		this.setting = setting;
	}

	@Generated
	public int getScore() {
		return score;
	}

	@Generated
	public LocalDate getDate() {
		return date;
	}

	@Generated
	public GameSetting getSetting() {
		return setting;
	}

	@Override
	public String toString() {
		return "game: date " + date.toString() + " - score " + score + " - " + setting.getName();
	}

}
