package com.minigames.snake.model;

import java.util.Collection;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManagerFactory;

public class SnakeHibernateRepository implements SnakeRepository {

	private GameSettingHibernateDao settingDao;
	private GameRecordHibernateDao recordDao;
	private EntityManagerFactory emf;

	@Generated
	public SnakeHibernateRepository(GameSettingHibernateDao settingDao, GameRecordHibernateDao recordDao, EntityManagerFactory emf) {
		this.emf=emf;
		this.settingDao = settingDao;
		this.recordDao = recordDao;
		this.settingDao.setEmf(emf);
		this.recordDao.setEmf(emf);
	}

	@Override
	public Collection<GameRecord> findAllRecords() {
		return recordDao.findAll();
	}

	@Override
	public Collection<GameSetting> findAllSettings() {
		return settingDao.findAll().stream().filter(setting -> !setting.isDeleted()).collect(Collectors.toList());
	}

	@Override
	public void createRecord(GameRecord gameRecord) {
		recordDao.create(gameRecord);
	}

	@Override
	public void createSetting(GameSetting setting) {
		settingDao.create(setting);
	}

	@Override
	public void deleteRecord(GameRecord gameRecord) {
		recordDao.delete(gameRecord);
	}

	@Override
	public void clearHistory() {
		recordDao.findAll().stream().forEach(gameRecord -> recordDao.delete(gameRecord));
	}

	@Override
	public void deleteSetting(GameSetting setting) {
		settingDao.delete(setting);
	}

	@Override
	public void renameSetting(GameSetting setting, String newName) {
		settingDao.rename(setting, newName);
	}

	@Override
	public void close() {
		emf.close();
	}

}
