package com.minigames.snake.model;

import java.util.Collection;
import java.util.stream.Collectors;

public class SnakeSQLRepository implements SnakeRepository {

	private GameSettingDAO settingDao;
	private GameRecordDAO recordDao;

	@Generated
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
		return settingDao.findAll().stream().filter(setting -> {
			return !setting.isDeleted();
		}).collect(Collectors.toList());
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
