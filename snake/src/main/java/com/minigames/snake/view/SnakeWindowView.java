package com.minigames.snake.view;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
	private transient Runnable closeAction;

	public SnakeWindowView(SnakeLobbyPresenter lobbyPresenter, SnakeMatchPresenter matchPresenter) {
		this.presenter = lobbyPresenter;
		setTitle(ViewComponentNames.WINDOW_TITLE);
		setBounds(100, 100, 600, 400);
		cards = new JPanel();
		cards.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cards);
		cards.setName(ViewComponentNames.CARDS_PANEL);
		cards.setLayout(new CardLayout(0, 0));
		welcomeCard = new SnakeWelcomePanel(cards);
		historyCard = new SnakeHistoryPanel(this, lobbyPresenter, cards);
		matchCard = new SnakeMatchPanel(this, cards, matchPresenter);
		settingsCard = new SnakeSettingsPanel(this, lobbyPresenter, matchPresenter, cards, matchCard);
		welcomeCard.initializeComponents();
		historyCard.initializeComponents();
		settingsCard.initializeComponents();
		matchCard.initializeComponents();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
	}

	public void close() {
		presenter.close();
		closeAction.run();
	}

	@Override
	public void updateLobby() {
		historyCard.refresh(presenter.loadHistory());
		settingsCard.refresh(presenter.loadConfigurations());
	}

	@Override
	public void updateMatch() {
		matchCard.refresh();
	}

	@Generated
	public void setCloseAction(Runnable closeAction) {
		this.closeAction = closeAction;
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
