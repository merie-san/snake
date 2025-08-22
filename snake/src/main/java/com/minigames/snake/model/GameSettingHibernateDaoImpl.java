package com.minigames.snake.model;

import java.util.Collection;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class GameSettingHibernateDaoImpl implements GameSettingHibernateDao {
	private EntityManagerFactory emf;

	@Override
	public Collection<GameSetting> findAll() {
		return emf.callInTransaction(em -> {
			TypedQuery<GameSetting> query = em.createQuery("SELECT g from GameSetting g", GameSetting.class);
			return query.getResultList();
		});
	}

	@Override
	public void create(GameSetting setting) {
		emf.runInTransaction(em -> em.persist(setting));

	}

	@Override
	public void delete(GameSetting setting) {
		emf.runInTransaction(em -> {
			setting.setDeleted(true);
			em.merge(setting);
		});
	}

	@Override
	public void rename(GameSetting setting, String string) {
		emf.runInTransaction(em -> {
			setting.setName(string);
			em.merge(setting);
		});
	}

	@Generated
	@Override
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

}
