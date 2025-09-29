package com.minigames.snake.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	@Column(unique = true)
	private String uuid;

	// for JPA
	@Generated
	protected BaseEntity() {
	}

	// for Jackson
	@Generated
	public String getUuid() {
		return uuid;
	}

	// for Jackson
	@Generated
	public void setUuid(String uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("uuid cannot be null");
		this.uuid = uuid;
	}

	@Generated
	protected BaseEntity(String uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("uuid cannot be null");
		}
		this.uuid = uuid;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		return uuid.equals(((BaseEntity) obj).uuid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Generated
	public long getId() {
		return id;
	}

}
