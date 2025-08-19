package com.minigames.snake.model;

import java.util.Collection;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class GameRecordDAO {

	private EntityManagerFactory emf;

	@Generated
	public GameRecordDAO(EntityManagerFactory entityManagerFactory) {
		emf = entityManagerFactory;
	}

	public Collection<GameRecord> findAll() {
		return emf.callInTransaction(em -> {
			TypedQuery<GameRecord> query = em.createQuery("SELECT g from GameRecord g", GameRecord.class);
			return query.getResultList();
		});
	}

	public void create(GameRecord gameRecord) {
		emf.runInTransaction(em -> em.persist(gameRecord));
	}

	public void delete(GameRecord gameRecord) {
		emf.runInTransaction(em -> {
			GameRecord managedRecord = em.merge(gameRecord);
			em.remove(managedRecord);
		});
	}

}
