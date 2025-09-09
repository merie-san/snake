package com.minigames.snake.model;

import java.util.Collection;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class GameRecordHibernateDaoImpl implements GameRecordHibernateDao {

	private EntityManagerFactory emf;

	@Override
	public Collection<GameRecord> findAll() {
		return emf.callInTransaction(em -> {
			TypedQuery<GameRecord> query = em.createQuery("SELECT g FROM GameRecord g", GameRecord.class);
			return query.getResultList();
		});
	}

	@Override
	public void create(GameRecord gameRecord) {
		emf.runInTransaction(em -> em.persist(gameRecord));
	}

	@Override
	public void delete(GameRecord gameRecord) {
		emf.runInTransaction(em -> {
			GameRecord managedRecord = em.merge(gameRecord);
			em.remove(managedRecord);
		});
	}

	@Generated
	@Override
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

}
