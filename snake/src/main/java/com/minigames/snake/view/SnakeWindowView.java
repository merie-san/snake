package com.minigames.snake.view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.text.NumberFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;

public class SnakeWindowView extends JFrame implements SnakeView {

	private static final long serialVersionUID = 1L;
	private JPanel cards;
	private static final String WINDOW_TITLE = "Turn-based Snake";
	private static final String CARDS_PANEL = "Cards";
	private static final String WELCOME_PANEL = "Welcome panel";
	private static final String HISTORY_PANEL = "History panel";
	private static final String SETTINGS_PANEL = "Settings panel";
	private static final String MATCH_PANEL = "Match panel";
	private static final String WELCOME_LABEL_TEXT = "Turn-based Snake";
	private static final String WELCOME_LABEL_NAME = "welcomeLabel";
	private static final String HISTORY_BUTTON_TEXT = "History";
	private static final String HISTORY_BUTTON_NAME = "historyButton";
	private static final String SETTINGS_BUTTON_TEXT = "Settings";
	private static final String SETTINGS_BUTTON_NAME = "settingsButton";
	private static final String MATCH_BUTTON_TEXT = "Play";
	private static final String MATCH_BUTTON_NAME = "matchButton";
	private static final String HISTORY_LIST_NAME = "historyList";
	private static final String HIGH_SCORE_LABEL_TEXT = "All time high score: ";
	private static final String HIGH_SCORE_LABEL_NAME = "highScoreLabel";
	private static final String DELETE_ALL_BUTTON_TEXT = "Clear history";
	private static final String DELETE_ALL_BUTTON_NAME = "deleteAllButton";
	private static final String DELETE_SELECTED_TEXT = "Delete selected";
	private static final String DELETE_SELECTED_NAME = "deleteSelectedButton";
	private static final String SETTINGS_LIST_NAME = "settingsList";
	private static final String USE_SETTING_BUTTON_TEXT = "Use setting";
	private static final String USE_SETTING_BUTTON_NAME = "useSettingButton";
	private static final String DELETE_SETTING_BUTTON_TEXT = "Delete setting";
	private static final String DELETE_SETTING_BUTTON_NAME = "deleteSettingButton";
	private static final String NEW_NAME_TEXTBOX_TEXT = "New name";
	private static final String NEW_NAME_TEXTBOX_NAME = "newNameTextBox";
	private static final String RENAME_BUTTON_TEXT = "Rename";
	private static final String RENAME_BUTTON_NAME = "renameButton";
	private static final String CREATE_SETTING_LABEL_TEXT = "Setting form";
	private static final String CREATE_SETTING_LABEL_NAME = "createSettingLabel";
	private static final String NAME_LABEL_TEXT = "Name";
	private static final String WIDTH_LABEL_TEXT = "Width";
	private static final String HEIGHT_LABEL_TEXT = "Height";
	private static final String OBSTACLES_LABEL_TEXT = "N. obstacles";
	private static final String NAME_LABEL_NAME = "nameLabel";
	private static final String WIDTH_LABEL_NAME = "widthLabel";
	private static final String HEIGHT_LABEL_NAME = "heightLabel";
	private static final String OBSTACLES_LABEL_NAME = "obstaclesLabel";
	private static final String NAME_TEXTBOX_TEXT = "New setting";
	private static final String NAME_TEXTBOX_NAME = "nameTextBox";
	private static final int WIDTH_TEXTBOX_VALUE = 10;
	private static final String WIDTH_TEXTBOX_NAME = "widthTextBox";
	private static final int HEIGHT_TEXTBOX_VALUE = 10;
	private static final String HEIGHT_TEXTBOX_NAME = "heightTextBox";
	private static final int OBSTACLES_TEXTBOX_VALUE = 2;
	private static final String OBSTACLES_TEXTBOX_NAME = "obstaclesTextBox";
	private static final String SUBMIT_BUTTON_TEXT = "Submit";
	private static final String SUBMIT_BUTTON_NAME = "submitButton";

	private JPanel welcomeCard;
	private JPanel historyCard;
	private JPanel settingsCard;
	private JPanel matchCard;
	private JLabel welcomeLabel;
	private JButton historyButtonW;
	private JButton settingsButtonW;
	private JButton matchButtonW;
	private JButton settingsButtonH;
	private JButton matchButtonH;
	private JButton historyButtonS;
	private JButton matchButtonS;
	private JButton historyButtonM;
	private JButton settingsButtonM;
	private JButton deleteAllButton;
	private JList<GameRecord> historyList;
	private JLabel highScoreLabel;
	private GameSetting activeSetting;
	private JButton deleteSelectedButton;
	private JList<GameSetting> settingsList;
	private JButton useSettingButton;
	private JButton deleteSettingButton;
	private JTextField newNameTextBox;
	private JButton renameButton;
	private JLabel createSettingLabel;
	private JLabel nameLabel;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JLabel obstaclesLabel;
	private JTextField nameTextBox;
	private JFormattedTextField widthTextBox;
	private JFormattedTextField heightTextBox;
	private JFormattedTextField obstaclesTextBox;
	private JButton submitButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SnakeWindowView frame = new SnakeWindowView();
					frame.show(SETTINGS_PANEL);
					frame.pack();
					frame.setMinimumSize(new Dimension(600, 400));
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
		welcomeCard = new JPanel();
		historyCard = new JPanel();
		settingsCard = new JPanel();
		matchCard = new JPanel();
		welcomeCard.setLayout(new GridBagLayout());
		settingsCard.setLayout(new GridBagLayout());
		historyCard.setLayout(new GridBagLayout());
		matchCard.setLayout(new GridBagLayout());
		cards.add(welcomeCard, WELCOME_PANEL);
		welcomeCard.setName(WELCOME_PANEL);
		cards.add(historyCard, HISTORY_PANEL);
		historyCard.setName(HISTORY_PANEL);
		cards.add(settingsCard, SETTINGS_PANEL);
		settingsCard.setName(SETTINGS_PANEL);
		cards.add(matchCard, MATCH_PANEL);
		matchCard.setName(MATCH_PANEL);
		initializeWelcomeCard();
		initializeHistoryCard();
		initializeSettingsCard();

	}

	private void initializeSettingsCard() {
		historyButtonS = new JButton(HISTORY_BUTTON_TEXT);
		matchButtonS = new JButton(MATCH_BUTTON_TEXT);
		settingsList = new JList<>(new DefaultListModel<>());
		useSettingButton = new JButton(USE_SETTING_BUTTON_TEXT);
		deleteSettingButton = new JButton(DELETE_SETTING_BUTTON_TEXT);
		newNameTextBox = new JTextField(NEW_NAME_TEXTBOX_TEXT);
		renameButton = new JButton(RENAME_BUTTON_TEXT);
		createSettingLabel = new JLabel(CREATE_SETTING_LABEL_TEXT);
		nameLabel = new JLabel(NAME_LABEL_TEXT);
		widthLabel = new JLabel(WIDTH_LABEL_TEXT);
		heightLabel = new JLabel(HEIGHT_LABEL_TEXT);
		obstaclesLabel = new JLabel(OBSTACLES_LABEL_TEXT);
		nameTextBox = new JTextField(NAME_TEXTBOX_TEXT);
		widthTextBox = new JFormattedTextField(NumberFormat.getIntegerInstance());
		heightTextBox = new JFormattedTextField(NumberFormat.getIntegerInstance());
		obstaclesTextBox = new JFormattedTextField(NumberFormat.getIntegerInstance());
		submitButton = new JButton(SUBMIT_BUTTON_TEXT);
		initializeButton(historyButtonS, HISTORY_BUTTON_NAME, true);
		initializeButton(matchButtonS, MATCH_BUTTON_NAME, true);
		JScrollPane scroller = initializeList(settingsList, SETTINGS_LIST_NAME, ListSelectionModel.SINGLE_SELECTION, 6,
				new Dimension(400, 100));
		initializeButton(useSettingButton, USE_SETTING_BUTTON_NAME, false);
		initializeButton(deleteSettingButton, DELETE_SETTING_BUTTON_NAME, false);
		initializeTextField(newNameTextBox, NEW_NAME_TEXTBOX_NAME, true, false, 10);
		initializeButton(renameButton, RENAME_BUTTON_NAME, false);
		initializeLabel(createSettingLabel, CREATE_SETTING_LABEL_NAME, null);
		initializeLabel(nameLabel, NAME_LABEL_NAME, null);
		initializeLabel(widthLabel, WIDTH_LABEL_NAME, null);
		initializeLabel(heightLabel, HEIGHT_LABEL_NAME, null);
		initializeLabel(obstaclesLabel, OBSTACLES_LABEL_NAME, null);
		initializeTextField(nameTextBox, NAME_TEXTBOX_NAME, true, true, 10);
		initializeFormattedTextField(obstaclesTextBox, OBSTACLES_TEXTBOX_NAME, OBSTACLES_TEXTBOX_VALUE, true, true, 5);
		initializeFormattedTextField(widthTextBox, WIDTH_TEXTBOX_NAME, WIDTH_TEXTBOX_VALUE, true, true, 5);
		initializeFormattedTextField(heightTextBox, HEIGHT_TEXTBOX_NAME, HEIGHT_TEXTBOX_VALUE, true, true, 5);
		initializeButton(submitButton, SUBMIT_BUTTON_NAME, true);
		positionComponent(settingsCard, historyButtonS, 0, 0, 3, 1, 1, 1, 0, null);
		positionComponent(settingsCard, matchButtonS, 5, 0, 3, 1, 1, 1, 0, null);
		positionComponent(settingsCard, scroller, 0, 1, 8, 2, 1, 2, GridBagConstraints.BOTH, null);
		positionComponent(settingsCard, deleteSettingButton, 0, 3, 2, 2, 1, 1, 0, null);
		positionComponent(settingsCard, useSettingButton, 3, 3, 2, 2, 1, 1, 0, null);
		positionComponent(settingsCard, newNameTextBox, 6, 3, 2, 1, 1, 1, 0, null);
		positionComponent(settingsCard, renameButton, 6, 4, 2, 1, 1, 1, 0, null);
		positionComponent(settingsCard, createSettingLabel, 3, 5, 2, 1, 1, 1, 0, null);
		positionComponent(settingsCard, nameLabel, 0, 6, 1, 1, 1, 1, 0, null);
		positionComponent(settingsCard, nameTextBox, 1, 6, 1, 1, 1, 1, 0, null);
		positionComponent(settingsCard, widthLabel, 2, 6, 1, 1, 1, 1, 0, null);
		positionComponent(settingsCard, widthTextBox, 3, 6, 1, 1, 1, 1, 0, null);
		positionComponent(settingsCard, heightLabel, 4, 6, 1, 1, 1, 1, 0, null);
		positionComponent(settingsCard, heightTextBox, 5, 6, 1, 1, 1, 1, 0, null);
		positionComponent(settingsCard, obstaclesLabel, 6, 6, 1, 1, 1, 1, 0, null);
		positionComponent(settingsCard, obstaclesTextBox, 7, 6, 1, 1, 1, 1, 0, null);
		positionComponent(settingsCard, submitButton, 3, 7, 2, 1, 1, 1, 0, new Insets(0, 0, 2, 0));
	}

	private void initializeHistoryCard() {
		settingsButtonH = new JButton(SETTINGS_BUTTON_TEXT);
		matchButtonH = new JButton(MATCH_BUTTON_TEXT);
		historyList = new JList<>(new DefaultListModel<>());
		highScoreLabel = new JLabel(HIGH_SCORE_LABEL_TEXT + "0");
		deleteAllButton = new JButton(DELETE_ALL_BUTTON_TEXT);
		deleteSelectedButton = new JButton(DELETE_SELECTED_TEXT);
		initializeButton(settingsButtonH, SETTINGS_BUTTON_NAME, true);
		initializeButton(matchButtonH, MATCH_BUTTON_NAME, true);
		JScrollPane scroller = initializeList(historyList, HISTORY_LIST_NAME,
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, 12, new Dimension(400, 200));
		initializeLabel(highScoreLabel, HIGH_SCORE_LABEL_NAME, new Font("New times roman", Font.PLAIN, 12));
		initializeButton(deleteAllButton, DELETE_ALL_BUTTON_NAME, true);
		initializeButton(deleteSelectedButton, DELETE_SELECTED_NAME, false);
		positionComponent(historyCard, settingsButtonH, 0, 0, 3, 1, 1, 1, 0, new Insets(20, 0, 0, 0));
		positionComponent(historyCard, matchButtonH, 5, 0, 3, 1, 1, 1, 0, new Insets(20, 0, 0, 0));
		positionComponent(historyCard, scroller, 0, 1, 8, 2, 1, 2, GridBagConstraints.BOTH, null);
		positionComponent(historyCard, highScoreLabel, 3, 3, 2, 1, 1, 1, 0, null);
		positionComponent(historyCard, deleteAllButton, 1, 4, 2, 1, 1, 1, 0, null);
		positionComponent(historyCard, deleteSelectedButton, 5, 4, 2, 1, 1, 1, 0, null);
	}

	private void initializeWelcomeCard() {
		welcomeLabel = new JLabel(WELCOME_LABEL_TEXT);
		historyButtonW = new JButton(HISTORY_BUTTON_TEXT);
		settingsButtonW = new JButton(SETTINGS_BUTTON_TEXT);
		matchButtonW = new JButton(MATCH_BUTTON_TEXT);
		initializeLabel(welcomeLabel, WELCOME_LABEL_NAME, new Font("New times roman", Font.PLAIN, 28));
		initializeButton(historyButtonW, HISTORY_BUTTON_NAME, true);
		initializeButton(settingsButtonW, SETTINGS_BUTTON_NAME, true);
		initializeButton(matchButtonW, MATCH_BUTTON_NAME, true);
		positionComponent(welcomeCard, welcomeLabel, 0, 0, 8, 1, 1, 1, 0, new Insets(40, 0, 0, 0));
		positionComponent(welcomeCard, historyButtonW, 0, 2, 2, 1, 1, 1, 0, new Insets(0, 30, 20, 0));
		positionComponent(welcomeCard, settingsButtonW, 6, 2, 2, 1, 1, 1, 0, new Insets(0, 0, 20, 30));
		positionComponent(welcomeCard, matchButtonW, 3, 2, 2, 1, 1, 1, 0, new Insets(0, 0, 20, 10));
	}

	private void initializeFormattedTextField(JFormattedTextField textBox, String boxName, int boxValue,
			boolean enabled, boolean editable, int size) {
		textBox.setName(boxName);
		textBox.setValue(boxValue);
		textBox.setHorizontalAlignment(SwingConstants.CENTER);
		textBox.setEnabled(enabled);
		textBox.setEditable(editable);
		textBox.setColumns(size);
	}

	private void initializeTextField(JTextField textBox, String boxName, boolean editable, boolean enabled, int size) {
		textBox.setName(boxName);
		textBox.setEditable(editable);
		textBox.setEnabled(enabled);
		textBox.setColumns(size);
	}

	private <T> JScrollPane initializeList(JList<T> list, String listName, int selectionModel, int visibleRowCount,
			Dimension dimension) {
		list.setName(listName);
		list.setSelectionMode(selectionModel);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectedIndex(-1);
		list.setVisibleRowCount(visibleRowCount);
		JScrollPane scroller = new JScrollPane(list);
		scroller.setPreferredSize(dimension);
		return scroller;
	}

	private void initializeLabel(JLabel label, String labelName, Font font) {
		label.setName(labelName);
		if (font != null) {
			label.setFont(font);
		}
		label.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private void positionComponent(JPanel parentPanel, Component component, int gridx, int gridy, int gridwidth,
			int gridheight, int weightx, int weighty, int fill, Insets insets) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.gridwidth = gridwidth;
		constraints.gridheight = gridheight;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		constraints.fill = fill;
		if (insets != null) {
			constraints.insets = insets;
		}
		parentPanel.add(component, constraints);
	}

	private void initializeButton(JButton button, String buttonName, boolean enabled) {
		button.setName(buttonName);
		button.setEnabled(enabled);
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
