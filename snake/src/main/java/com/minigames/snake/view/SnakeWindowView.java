package com.minigames.snake.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.minigames.snake.model.Generated;
import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeMatchPresenter;

public class SnakeWindowView extends JFrame implements SnakeView {

	public static final long serialVersionUID = 1L;
	private JPanel cards;
	private SnakeWelcomePanel welcomeCard;
	private SnakeHistoryPanel historyCard;
	private SnakeSettingsPanel settingsCard;
	private SnakeMatchPanel matchCard;

	private transient SnakeLobbyPresenter presenter;

	public SnakeWindowView(SnakeLobbyPresenter lobbyPresenter, SnakeMatchPresenter matchPresenter) {
		this.presenter = lobbyPresenter;
		setTitle(ViewComponentNames.WINDOW_TITLE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		cards = new JPanel();
		cards.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cards);
		cards.setName(ViewComponentNames.CARDS_PANEL);
		cards.setLayout(new CardLayout(0, 0));
		welcomeCard = new SnakeWelcomePanel(cards);
		historyCard = new SnakeHistoryPanel(this, lobbyPresenter, cards);
		settingsCard = new SnakeSettingsPanel(this, lobbyPresenter, matchPresenter, cards);
		matchCard = new SnakeMatchPanel(this, cards, matchPresenter);
		welcomeCard.initializeComponents();
		historyCard.initializeComponents();
		settingsCard.initializeComponents();
		matchCard.initializeComponents();
	}

	@Override
	public void update() {
		historyCard.refresh(presenter.loadHistory());
		settingsCard.refresh(presenter.loadConfigurations());
		matchCard.refresh();
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
