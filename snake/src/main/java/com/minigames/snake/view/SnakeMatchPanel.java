package com.minigames.snake.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.Generated;
import com.minigames.snake.presenter.SnakeMatchPresenter;

public class SnakeMatchPanel extends JPanel {

	public static final String HISTORY_BUTTON_TEXT_M = "History";
	public static final String HISTORY_BUTTON_NAME_M = "historyButton";
	public static final String SETTINGS_BUTTON_TEXT_M = "Settings";
	public static final String SETTINGS_BUTTON_NAME_M = "settingsButton";

	public static final String SCORE_LABEL_TEXT = "Current score: ";
	public static final String START_BUTTON_TEXT = "Start";
	public static final String QUIT_BUTTON_TEXT = "Quit";
	public static final String START_BUTTON_NAME = "startButton";
	public static final String QUIT_BUTTON_NAME = "quitButton";
	public static final String SCORE_LABEL_NAME = "scoreLabel";
	public static final String MESSAGE_LABEL_NAME = "messageLabel";
	public static final String MATCH_CANVAS_NAME = "matchCanvas";
	public static final String MESSAGE_LABEL_TEXT = "No message";

	private static final long serialVersionUID = 1L;
	private JPanel parentCards;

	private JButton historyButtonM;
	private JButton settingsButtonM;

	private SnakeCanvas matchCanvas;
	private JLabel scoreLabel;
	private JLabel messageLabel;
	private JButton startButton;
	private JButton quitButton;
	private transient GameSetting currentSetting;

	public SnakeMatchPanel(SnakeWindowView rootPanel, JPanel parentCards, String cardName,
			SnakeMatchPresenter presenter) {
		this.parentCards = parentCards;
		parentCards.add(this, cardName);
		this.setName(cardName);
		this.setLayout(new GridBagLayout());
		matchCanvas = new SnakeCanvas(rootPanel, presenter, this);
	}

	public void initializeComponents() {
		createComponents();
		configureComponents();
		positionComponents();
		initializeListeners();
	}

	private void positionComponents() {
		this.add(historyButtonM, new GridBagConstraintsBuilder().withGridx(0).withGridy(0).withGridwidth(3)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(settingsButtonM, new GridBagConstraintsBuilder().withGridx(10).withGridy(0).withGridwidth(3)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(matchCanvas, new GridBagConstraintsBuilder().withGridx(0).withGridwidth(9).withGridheight(6)
				.withWeightx(1).withWeighty(1).build());
		this.add(scoreLabel, new GridBagConstraintsBuilder().withGridx(9).withGridwidth(4).withGridheight(1)
				.withWeightx(1).withWeighty(1).withAnchor(GridBagConstraints.WEST).build());
		this.add(messageLabel,
				new GridBagConstraintsBuilder().withGridx(9).withGridy(2).withGridwidth(4).withGridheight(1)
						.withWeightx(1).withWeighty(1).withAnchor(GridBagConstraints.WEST)
						.withInsets(new Insets(0, 0, 175, 0)).build());
		this.add(startButton, new GridBagConstraintsBuilder().withGridx(0).withGridy(7).withGridwidth(3)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(quitButton, new GridBagConstraintsBuilder().withGridx(6).withGridy(7).withGridwidth(3)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
	}

	private void configureComponents() {
		ComponentInitializer.initializeButton(historyButtonM, HISTORY_BUTTON_NAME_M, true);
		ComponentInitializer.initializeButton(settingsButtonM, SETTINGS_BUTTON_NAME_M, true);
		ComponentInitializer.initializeButton(startButton, START_BUTTON_NAME, false);
		ComponentInitializer.initializeButton(quitButton, QUIT_BUTTON_NAME, false);
		ComponentInitializer.initializeLabel(scoreLabel, SCORE_LABEL_NAME, null, null);
		ComponentInitializer.initializeLabel(messageLabel, MESSAGE_LABEL_NAME, null, null);
		ComponentInitializer.initializePanel(matchCanvas, MATCH_CANVAS_NAME, true);
	}

	private void createComponents() {
		historyButtonM = new JButton(HISTORY_BUTTON_TEXT_M);
		settingsButtonM = new JButton(SETTINGS_BUTTON_TEXT_M);
		scoreLabel = new JLabel(SCORE_LABEL_TEXT + "0");
		messageLabel = new JLabel(MESSAGE_LABEL_TEXT);
		startButton = new JButton(START_BUTTON_TEXT);
		quitButton = new JButton(QUIT_BUTTON_TEXT);
	}

	private void initializeListeners() {
		historyButtonM.addMouseListener(new PanelSwitchButtonListener(parentCards, SnakeWindowView.HISTORY_PANEL));
		settingsButtonM.addMouseListener(new PanelSwitchButtonListener(parentCards, SnakeWindowView.SETTINGS_PANEL));
		startButton.addMouseListener(
				new MatchButtonsListener(historyButtonM, settingsButtonM, startButton, quitButton, true, matchCanvas));
		quitButton.addMouseListener(
				new MatchButtonsListener(historyButtonM, settingsButtonM, startButton, quitButton, false, matchCanvas));
	}

	public void refresh(GameSetting setting) {
		if (setting != null) {
			currentSetting = setting;
			matchCanvas.newSetting(currentSetting);
		}
		historyButtonM.setEnabled(true);
		settingsButtonM.setEnabled(true);
		startButton.setEnabled(true);
		quitButton.setEnabled(false);

	}

	public void updateMatchMessage(int score, String message) {
		scoreLabel.setText(SCORE_LABEL_TEXT + score);
		messageLabel.setText(message);
	}

	// for testing
	@Generated
	GameSetting getCurrentSetting() {
		return currentSetting;
	}

	// for testing
	@Generated
	SnakeCanvas getCanvas() {
		return matchCanvas;
	}

}
