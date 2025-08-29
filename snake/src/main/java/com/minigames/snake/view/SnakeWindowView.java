package com.minigames.snake.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.minigames.snake.model.GameSetting;

public class SnakeWindowView extends JFrame implements SnakeView {

	public static final long serialVersionUID = 1L;
	public static final String WINDOW_TITLE = "Turn-based Snake";
	public static final String CARDS_PANEL = "Cards";
	public static final String WELCOME_PANEL = "Welcome panel";
	public static final String HISTORY_PANEL = "History panel";
	public static final String SETTINGS_PANEL = "Settings panel";
	public static final String MATCH_PANEL = "Match panel";

	private JPanel cards;
	private SnakeWindowPanel welcomeCard;
	private SnakeWindowPanel historyCard;
	private SnakeWindowPanel settingsCard;
	private SnakeWindowPanel matchCard;

	private GameSetting activeSetting;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SnakeWindowView frame = new SnakeWindowView();
					frame.pack();
					frame.setMinimumSize(new Dimension(600, 500));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SnakeWindowView() {
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		cards = new JPanel();
		cards.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cards);
		cards.setName(CARDS_PANEL);
		cards.setLayout(new CardLayout(0, 0));
		welcomeCard = new SnakeWelcomePanel(cards, WELCOME_PANEL);
		historyCard = new SnakeHistoryPanel(cards, HISTORY_PANEL);
		settingsCard = new SnakeSettingsPanel(cards, SETTINGS_PANEL);
		matchCard = new SnakeMatchPanel(cards, MATCH_PANEL);
		welcomeCard.initializeComponents();
		historyCard.initializeComponents();
		settingsCard.initializeComponents();
		matchCard.initializeComponents();
	}

	@Override
	public void update() {

	}

	// for testing
	void show(String panelName) {
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, panelName);
	}
}
