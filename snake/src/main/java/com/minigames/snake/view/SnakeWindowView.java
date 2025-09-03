package com.minigames.snake.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.Generated;
import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeMatchPresenter;

public class SnakeWindowView extends JFrame implements SnakeView {

	public static final long serialVersionUID = 1L;
	public static final String WINDOW_TITLE = "Turn-based Snake";
	public static final String CARDS_PANEL = "Cards";
	public static final String WELCOME_PANEL = "Welcome panel";
	public static final String HISTORY_PANEL = "History panel";
	public static final String SETTINGS_PANEL = "Settings panel";
	public static final String MATCH_PANEL = "Match panel";

	private JPanel cards;
	private SnakeWelcomePanel welcomeCard;
	private SnakeHistoryPanel historyCard;
	private SnakeSettingsPanel settingsCard;
	private SnakeMatchPanel matchCard;

	private transient SnakeLobbyPresenter presenter;

	public SnakeWindowView(SnakeLobbyPresenter lobbyPresenter, SnakeMatchPresenter matchPresenter) {
		this.presenter = lobbyPresenter;
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		cards = new JPanel();
		cards.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cards);
		cards.setName(CARDS_PANEL);
		cards.setLayout(new CardLayout(0, 0));
		welcomeCard = new SnakeWelcomePanel(cards, WELCOME_PANEL);
		historyCard = new SnakeHistoryPanel(this, cards, HISTORY_PANEL);
		settingsCard = new SnakeSettingsPanel(this, cards, SETTINGS_PANEL);
		matchCard = new SnakeMatchPanel(this, cards, MATCH_PANEL, matchPresenter);
		welcomeCard.initializeComponents();
		historyCard.initializeComponents();
		settingsCard.initializeComponents();
		matchCard.initializeComponents();
	}

	public void deleteRecord(GameRecord gameRecord) {
		presenter.removeRecord(gameRecord, this);
	}

	public void clearHistory() {
		presenter.clearGameHistory(this);
	}

	public void deleteSetting(GameSetting setting) {
		presenter.removeConfiguration(setting, this);
	}

	public void renameSetting(GameSetting setting, String newName) {
		presenter.renameConfiguration(setting, newName, this);
	}

	public void createSetting(int width, int height, int obstacleN, String name) {
		presenter.saveConfiguration(width, height, obstacleN, name, this);
	}

	@Override
	public void update() {
		historyCard.refresh(presenter.loadHistory());
		settingsCard.refresh(presenter.loadConfigurations());
		matchCard.refresh(null);
	}

	public void updateSetting(GameSetting setting) {
		matchCard.refresh(setting);
	}

	// for testing
	@Generated
	void show(String panelName) {
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, panelName);
	}

	// for testing
	@Generated
	SnakeHistoryPanel getHistoryPanel() {
		return historyCard;
	}

	// for testing
	@Generated
	SnakeSettingsPanel getSettingsPanel() {
		return settingsCard;
	}

	// for testing
	@Generated
	SnakeMatchPanel getMatchPanel() {
		return matchCard;
	}

}
