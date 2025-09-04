package com.minigames.snake.view;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SnakeWelcomePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel parentCards;

	private JLabel welcomeLabel;
	private JButton historyButtonW;
	private JButton settingsButtonW;
	private JButton matchButtonW;

	public SnakeWelcomePanel(JPanel parentCards) {
		this.parentCards = parentCards;
		parentCards.add(this, ViewComponentNames.WELCOME_PANEL);
		this.setName(ViewComponentNames.WELCOME_PANEL);
		this.setLayout(new GridBagLayout());
	}

	public void initializeComponents() {
		createComponents();
		configureComponents();
		positionComponents();
		initializeListeners();

	}

	private void configureComponents() {
		ComponentInitializer.initializeLabel(welcomeLabel, ViewComponentNames.WELCOME_LABEL_NAME,
				new Font("New times roman", Font.PLAIN, 28), null);
		ComponentInitializer.initializeButton(historyButtonW, ViewComponentNames.HISTORY_BUTTON_NAME_W, true);
		ComponentInitializer.initializeButton(settingsButtonW, ViewComponentNames.SETTINGS_BUTTON_NAME_W, true);
		ComponentInitializer.initializeButton(matchButtonW, ViewComponentNames.MATCH_BUTTON_NAME_W, true);
	}

	private void createComponents() {
		welcomeLabel = new JLabel(ViewComponentNames.WELCOME_LABEL_TEXT);
		historyButtonW = new JButton(ViewComponentNames.HISTORY_BUTTON_TEXT_W);
		settingsButtonW = new JButton(ViewComponentNames.SETTINGS_BUTTON_TEXT_W);
		matchButtonW = new JButton(ViewComponentNames.MATCH_BUTTON_TEXT_W);
	}

	private void positionComponents() {
		this.add(welcomeLabel, new GridBagConstraintsBuilder().withGridx(0).withGridy(0).withGridwidth(8)
				.withGridheight(1).withWeightx(1).withWeighty(1).withInsets(new Insets(40, 0, 0, 0)).build());
		this.add(historyButtonW, new GridBagConstraintsBuilder().withGridx(0).withGridy(2).withGridwidth(2)
				.withGridheight(1).withWeightx(1).withWeighty(1).withInsets(new Insets(0, 30, 20, 0)).build());
		this.add(settingsButtonW, new GridBagConstraintsBuilder().withGridx(6).withGridy(2).withGridwidth(2)
				.withGridheight(1).withWeightx(1).withWeighty(1).withInsets(new Insets(0, 0, 20, 30)).build());
		this.add(matchButtonW, new GridBagConstraintsBuilder().withGridx(3).withGridy(2).withGridwidth(2)
				.withGridheight(1).withWeightx(1).withWeighty(1).withInsets(new Insets(0, 0, 20, 10)).build());
	}

	private void initializeListeners() {
		historyButtonW.addMouseListener(new PanelSwitchButtonListener(parentCards, ViewComponentNames.HISTORY_PANEL));
		settingsButtonW.addMouseListener(new PanelSwitchButtonListener(parentCards, ViewComponentNames.SETTINGS_PANEL));
		matchButtonW.addMouseListener(new PanelSwitchButtonListener(parentCards, ViewComponentNames.MATCH_PANEL));

	}

}
