package com.minigames.snake;

import java.util.Collection;

public class SnakeSQLRepository implements SnakeRepository {

	private GameSettingDAO settingDao;
	private GameRecordDAO recordDao;

	public SnakeSQLRepository(GameSettingDAO settingDao, GameRecordDAO recordDao) {
		this.settingDao = settingDao;
		this.recordDao = recordDao;
	}

	@Override
	public Collection<GameRecord> findAllRecords() {
		return recordDao.findAll();
	}

	@Override
	public Collection<GameSetting> findAllSettings() {
		return settingDao.findAll();
	}

	@Override
	public void createRecord(GameRecord record) {
		recordDao.create(record);
	}

	@Override
	public void createSetting(GameSetting setting) {
		settingDao.create(setting);
	}

	@Override
	public void deleteRecord(GameRecord record) {
		recordDao.delete(record);
	}

	@Override
	public void clearHistory() {
		recordDao.findAll().stream().forEach(record -> {
			recordDao.delete(record);
		});
	}

	@Override
	public void deleteSetting(GameSetting setting) {
		settingDao.delete(setting);
	}

	@Override
	public void renameSetting(GameSetting setting, String newName) {
		settingDao.rename(setting, newName);
	}

}
