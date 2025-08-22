package com.minigames.snake.model;

import java.util.Collection;

import jakarta.persistence.EntityManagerFactory;

public interface GameSettingHibernateDao {

	Collection<GameSetting> findAll();

	void create(GameSetting setting);

	void delete(GameSetting setting);

	void rename(GameSetting setting, String string);

	void setEmf(EntityManagerFactory emf);

}