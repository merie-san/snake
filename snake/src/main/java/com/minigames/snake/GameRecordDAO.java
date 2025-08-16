package com.minigames.snake;

import java.util.Collection;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class GameRecordDAO {

	private EntityManagerFactory emf;

	public GameRecordDAO(EntityManagerFactory entityManagerFactory) {
		emf = entityManagerFactory;
	}

	public Collection<GameRecord> findAll() {
		return emf.callInTransaction(em -> {
			TypedQuery<GameRecord> query = em.createQuery("SELECT g from GameRecord g", GameRecord.class);
			return query.getResultList();
		});
	}

}
