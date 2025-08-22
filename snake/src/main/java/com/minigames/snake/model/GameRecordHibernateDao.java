package com.minigames.snake.model;

import java.util.Collection;

import jakarta.persistence.EntityManagerFactory;

public interface GameRecordHibernateDao {

	Collection<GameRecord> findAll();

	void create(GameRecord gameRecord);

	void delete(GameRecord gameRecord);

	void setEmf(EntityManagerFactory emf);

}