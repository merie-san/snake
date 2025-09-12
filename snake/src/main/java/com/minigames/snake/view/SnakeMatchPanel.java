package com.minigames.snake.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.minigames.snake.model.Generated;
import com.minigames.snake.presenter.SnakeMatchPresenter;

public class SnakeMatchPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel parentCards;

	private JButton historyButtonM;
	private JButton settingsButtonM;

	private SnakeCanvas matchCanvas;
	private JLabel scoreLabel;
	private JLabel messageLabel;
	private JButton startButton;
	private JButton quitButton;
	private transient SnakeMatchPresenter presenter;
	private transient SnakeView snakeView;

	public SnakeMatchPanel(SnakeView snakeView, JPanel parentCards, SnakeMatchPresenter presenter) {
		this.snakeView = snakeView;
		this.parentCards = parentCards;
		this.presenter = presenter;
		parentCards.add(this, ViewComponentNames.MATCH_PANEL);
		this.setName(ViewComponentNames.MATCH_PANEL);
		this.setLayout(new GridBagLayout());
		matchCanvas = new SnakeCanvas(presenter);
		matchCanvas.setFocusable(true);
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
		ComponentInitializer.initializeButton(historyButtonM, ViewComponentNames.HISTORY_BUTTON_NAME_M, true);
		ComponentInitializer.initializeButton(settingsButtonM, ViewComponentNames.SETTINGS_BUTTON_NAME_M, true);
		ComponentInitializer.initializeButton(startButton, ViewComponentNames.START_BUTTON_NAME, false);
		ComponentInitializer.initializeButton(quitButton, ViewComponentNames.QUIT_BUTTON_NAME, false);
		ComponentInitializer.initializeLabel(scoreLabel, ViewComponentNames.SCORE_LABEL_NAME, null, null);
		ComponentInitializer.initializeLabel(messageLabel, ViewComponentNames.MESSAGE_LABEL_NAME, null, null);
		ComponentInitializer.initializePanel(matchCanvas, ViewComponentNames.MATCH_CANVAS_NAME, true);
	}

	private void createComponents() {
		historyButtonM = new JButton(ViewComponentNames.HISTORY_BUTTON_TEXT_M);
		settingsButtonM = new JButton(ViewComponentNames.SETTINGS_BUTTON_TEXT_M);
		scoreLabel = new JLabel(ViewComponentNames.SCORE_LABEL_TEXT + "0");
		messageLabel = new JLabel(ViewComponentNames.MESSAGE_LABEL_TEXT);
		startButton = new JButton(ViewComponentNames.START_BUTTON_TEXT);
		quitButton = new JButton(ViewComponentNames.QUIT_BUTTON_TEXT);
	}

	private void initializeListeners() {
		historyButtonM.addMouseListener(new PanelSwitchButtonListener(parentCards, ViewComponentNames.HISTORY_PANEL));
		settingsButtonM.addMouseListener(new PanelSwitchButtonListener(parentCards, ViewComponentNames.SETTINGS_PANEL));
		startButton.addMouseListener(new HighLightableButtonListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				historyButtonM.setEnabled(false);
				settingsButtonM.setEnabled(false);
				startButton.setEnabled(false);
				quitButton.setEnabled(true);
				presenter.startMatch(snakeView);
				matchCanvas.addKeyListener(new SnakeCanvasKeyListener(presenter, snakeView));
				matchCanvas.requestFocusInWindow();
			}
		});
		quitButton.addMouseListener(new EndGameButtonListener(presenter, snakeView, matchCanvas));
	}

	public void enableButtons() {
		historyButtonM.setEnabled(true);
		settingsButtonM.setEnabled(true);
		startButton.setEnabled(true);
		quitButton.setEnabled(false);
	}

	public void refresh() {
		if (presenter.hasSetting() && !presenter.isPlaying()) {
			enableButtons();
		}
		if (!presenter.hasSetting() && presenter.isPlaying()) {
			throw new IllegalStateException("Player cannot be in game while having no setting");
		}
		scoreLabel.setText(ViewComponentNames.SCORE_LABEL_TEXT + presenter.currentScore());
		messageLabel.setText(presenter.isPlaying() ? "In game" : "No game");
		matchCanvas.refresh();
	}

	// for testing
	@Generated
	SnakeCanvas getCanvas() {
		return matchCanvas;
	}

}
