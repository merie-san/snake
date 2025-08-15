package com.minigames.snake;

import java.util.Objects;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String uuid;

	// for JPA
	@Generated
	protected BaseEntity() {
	}

	@Generated
	public BaseEntity(String uuid) {
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

}
