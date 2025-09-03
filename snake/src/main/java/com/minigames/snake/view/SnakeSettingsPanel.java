package com.minigames.snake.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.Generated;

public class SnakeSettingsPanel extends JPanel {

	public static final String HISTORY_BUTTON_TEXT_S = "History";
	public static final String HISTORY_BUTTON_NAME_S = "historyButton";
	public static final String MATCH_BUTTON_TEXT_S = "Play";
	public static final String MATCH_BUTTON_NAME_S = "matchButton";
	public static final String SETTINGS_LIST_NAME = "settingsList";
	public static final String USE_SETTING_BUTTON_TEXT = "Use setting";
	public static final String USE_SETTING_BUTTON_NAME = "useSettingButton";
	public static final String DELETE_SETTING_BUTTON_TEXT = "Delete setting";
	public static final String DELETE_SETTING_BUTTON_NAME = "deleteSettingButton";
	public static final String NEW_NAME_TEXTBOX_TEXT = "New name";
	public static final String NEW_NAME_TEXTBOX_NAME = "newNameTextBox";
	public static final String RENAME_BUTTON_TEXT = "Rename";
	public static final String RENAME_BUTTON_NAME = "renameButton";
	public static final String CREATE_SETTING_LABEL_TEXT = "Setting form";
	public static final String CREATE_SETTING_LABEL_NAME = "createSettingLabel";
	public static final String NAME_LABEL_TEXT = "Name";
	public static final String WIDTH_LABEL_TEXT = "Width";
	public static final String HEIGHT_LABEL_TEXT = "Height";
	public static final String OBSTACLES_LABEL_TEXT = "N. obstacles";
	public static final String NAME_LABEL_NAME = "nameLabel";
	public static final String WIDTH_LABEL_NAME = "widthLabel";
	public static final String HEIGHT_LABEL_NAME = "heightLabel";
	public static final String OBSTACLES_LABEL_NAME = "obstaclesLabel";
	public static final String NAME_TEXTBOX_TEXT = "New setting";
	public static final String NAME_TEXTBOX_NAME = "nameTextBox";
	public static final int WIDTH_TEXTBOX_VALUE = 5;
	public static final String WIDTH_TEXTBOX_NAME = "widthTextBox";
	public static final int HEIGHT_TEXTBOX_VALUE = 5;
	public static final String HEIGHT_TEXTBOX_NAME = "heightTextBox";
	public static final int OBSTACLES_TEXTBOX_VALUE = 5;
	public static final String OBSTACLES_TEXTBOX_NAME = "obstaclesTextBox";
	public static final String SUBMIT_BUTTON_TEXT = "Submit";
	public static final String SUBMIT_BUTTON_NAME = "submitButton";

	private static final long serialVersionUID = 1L;
	private JScrollPane scroller;
	private JPanel parentCards;
	private JButton historyButtonS;
	private JButton matchButtonS;
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
	private DefaultListModel<GameSetting> listModel;
	private SnakeWindowView parentView;

	public SnakeSettingsPanel(SnakeWindowView parentView, JPanel parentCards, String cardName) {
		this.parentView = parentView;
		this.parentCards = parentCards;
		parentCards.add(this, cardName);
		this.setName(cardName);
		this.setLayout(new GridBagLayout());
	}

	public void initializeComponents() {
		createComponents();
		configureComponents();
		positionComponents();
		initializeListeners();
	}

	private void configureComponents() {
		ComponentInitializer.initializeButton(historyButtonS, HISTORY_BUTTON_NAME_S, true);
		ComponentInitializer.initializeButton(matchButtonS, MATCH_BUTTON_NAME_S, true);
		scroller = ComponentInitializer.initializeList(settingsList, SETTINGS_LIST_NAME,
				ListSelectionModel.SINGLE_SELECTION, 6, new Dimension(400, 100));
		ComponentInitializer.initializeButton(useSettingButton, USE_SETTING_BUTTON_NAME, false);
		ComponentInitializer.initializeButton(deleteSettingButton, DELETE_SETTING_BUTTON_NAME, false);
		ComponentInitializer.initializeTextField(newNameTextBox, NEW_NAME_TEXTBOX_NAME, true, false, 10);
		ComponentInitializer.initializeButton(renameButton, RENAME_BUTTON_NAME, false);
		ComponentInitializer.initializeLabel(createSettingLabel, CREATE_SETTING_LABEL_NAME, null, null);
		ComponentInitializer.initializeLabel(nameLabel, NAME_LABEL_NAME, null, nameTextBox);
		ComponentInitializer.initializeLabel(widthLabel, WIDTH_LABEL_NAME, null, widthTextBox);
		ComponentInitializer.initializeLabel(heightLabel, HEIGHT_LABEL_NAME, null, heightTextBox);
		ComponentInitializer.initializeLabel(obstaclesLabel, OBSTACLES_LABEL_NAME, null, obstaclesTextBox);
		ComponentInitializer.initializeTextField(nameTextBox, NAME_TEXTBOX_NAME, true, true, 10);
		ComponentInitializer.initializeFormattedTextField(obstaclesTextBox, OBSTACLES_TEXTBOX_NAME,
				OBSTACLES_TEXTBOX_VALUE, true, 5, ComponentInitializer.createIntFormatter());
		ComponentInitializer.initializeFormattedTextField(widthTextBox, WIDTH_TEXTBOX_NAME, WIDTH_TEXTBOX_VALUE, true,
				5, ComponentInitializer.createIntFormatter());
		ComponentInitializer.initializeFormattedTextField(heightTextBox, HEIGHT_TEXTBOX_NAME, HEIGHT_TEXTBOX_VALUE,
				true, 5, ComponentInitializer.createIntFormatter());
		ComponentInitializer.initializeButton(submitButton, SUBMIT_BUTTON_NAME, true);
	}

	private void createComponents() {
		historyButtonS = new JButton(HISTORY_BUTTON_TEXT_S);
		matchButtonS = new JButton(MATCH_BUTTON_TEXT_S);
		listModel = new DefaultListModel<>();
		settingsList = new JList<>(listModel);
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
		widthTextBox = new JFormattedTextField();
		heightTextBox = new JFormattedTextField();
		obstaclesTextBox = new JFormattedTextField();
		submitButton = new JButton(SUBMIT_BUTTON_TEXT);
	}

	private void positionComponents() {
		this.add(historyButtonS, new GridBagConstraintsBuilder().withGridx(0).withGridy(0).withGridwidth(3)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(matchButtonS, new GridBagConstraintsBuilder().withGridx(5).withGridy(0).withGridwidth(3)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(scroller, new GridBagConstraintsBuilder().withGridx(0).withGridwidth(8).withGridheight(2)
				.withWeightx(1).withWeighty(2).withFill(GridBagConstraints.BOTH).build());
		this.add(deleteSettingButton, new GridBagConstraintsBuilder().withGridx(0).withGridy(3).withGridwidth(2)
				.withGridheight(2).withWeightx(1).withWeighty(1).build());
		this.add(useSettingButton, new GridBagConstraintsBuilder().withGridx(3).withGridy(3).withGridwidth(2)
				.withGridheight(2).withWeightx(1).withWeighty(1).build());
		this.add(newNameTextBox, new GridBagConstraintsBuilder().withGridx(6).withGridy(3).withGridwidth(2)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(renameButton, new GridBagConstraintsBuilder().withGridx(6).withGridy(4).withGridwidth(2)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(createSettingLabel, new GridBagConstraintsBuilder().withGridx(3).withGridy(5).withGridwidth(2)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(nameLabel, new GridBagConstraintsBuilder().withGridx(0).withGridy(6).withGridwidth(1).withGridheight(1)
				.withWeightx(1).withWeighty(1).build());
		this.add(nameTextBox, new GridBagConstraintsBuilder().withGridy(6).withGridwidth(1).withGridheight(1)
				.withWeightx(1).withWeighty(1).build());
		this.add(widthLabel, new GridBagConstraintsBuilder().withGridx(2).withGridy(6).withGridwidth(1)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(widthTextBox, new GridBagConstraintsBuilder().withGridx(3).withGridy(6).withGridwidth(1)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(heightLabel, new GridBagConstraintsBuilder().withGridx(4).withGridy(6).withGridwidth(1)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(heightTextBox, new GridBagConstraintsBuilder().withGridx(5).withGridy(6).withGridwidth(1)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(obstaclesLabel, new GridBagConstraintsBuilder().withGridx(6).withGridy(6).withGridwidth(1)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(obstaclesTextBox, new GridBagConstraintsBuilder().withGridx(7).withGridy(6).withGridwidth(1)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(submitButton, new GridBagConstraintsBuilder().withGridx(3).withGridy(7).withGridwidth(2)
				.withGridheight(1).withWeightx(1).withWeighty(1).withInsets(new Insets(0, 0, 2, 0)).build());
	}

	private void initializeListeners() {
		historyButtonS.addMouseListener(new PanelSwitchButtonListener(parentCards, SnakeWindowView.HISTORY_PANEL));
		matchButtonS.addMouseListener(new PanelSwitchButtonListener(parentCards, SnakeWindowView.MATCH_PANEL));
		settingsList.addListSelectionListener(
				new PanelListSelectionButtonListener(renameButton, useSettingButton, deleteSettingButton));
		settingsList.addListSelectionListener(new PanelListSelectionTextBoxListener(newNameTextBox, settingsList));
		useSettingButton.addMouseListener(new UseSettingButtonListener(parentView, settingsList));
		deleteSettingButton.addMouseListener(new DeleteSettingButtonListener(settingsList, parentView));
		renameButton.addMouseListener(new RenameSettingButtonListener(parentView, newNameTextBox, settingsList));
		submitButton.addMouseListener(new SubmitSettingButtonListener(parentView, nameTextBox, widthTextBox,
				heightTextBox, obstaclesTextBox));
	}

	public void refresh(Collection<GameSetting> newSettingList) {
		DefaultListModel<GameSetting> model = (DefaultListModel<GameSetting>) settingsList.getModel();
		model.clear();
		model.addAll(newSettingList);
	}

	// for tests
	@Generated
	DefaultListModel<GameSetting> getListModel() {
		return listModel;
	}

}
