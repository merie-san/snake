package com.minigames.snake;

import jakarta.persistence.EntityManagerFactory;

public class GameSettingDAO {
	private EntityManagerFactory emf;

	public GameSettingDAO(EntityManagerFactory entityManagerFactory) {
		emf = entityManagerFactory;
	}

	

}
