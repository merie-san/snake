package com.minigames.snake.model;

import java.util.Collection;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class GameSettingDAO {
	private EntityManagerFactory emf;

	@Generated
	public GameSettingDAO(EntityManagerFactory entityManagerFactory) {
		emf = entityManagerFactory;
	}

	public Collection<GameSetting> findAll() {
		return emf.callInTransaction(em -> {
			TypedQuery<GameSetting> query = em.createQuery("SELECT g from GameSetting g", GameSetting.class);
			return query.getResultList();
		});
	}

	public void create(GameSetting setting) {
		emf.runInTransaction(em -> {
			em.persist(setting);
		});

	}

	public void delete(GameSetting setting) {
		emf.runInTransaction(em -> {
			setting.setDeleted(true);
			em.merge(setting);
		});
	}

	public void rename(GameSetting setting, String string) {
		emf.runInTransaction(em -> {
			setting.setName(string);
			em.merge(setting);
		});
	}

}
