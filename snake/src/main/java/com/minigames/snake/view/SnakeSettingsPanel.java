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
import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeMatchPresenter;

public class SnakeSettingsPanel extends JPanel {

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
	private transient SnakeView lobbyView;
	private transient SnakeLobbyPresenter lobbyPresenter;
	private transient SnakeMatchPresenter matchPresenter;

	public SnakeSettingsPanel(SnakeView lobbyView, SnakeLobbyPresenter lobbyPresenter,
			SnakeMatchPresenter matchPresenter, JPanel parentCards) {
		this.lobbyView = lobbyView;
		this.lobbyPresenter = lobbyPresenter;
		this.matchPresenter = matchPresenter;
		this.parentCards = parentCards;
		parentCards.add(this, ViewComponentNames.SETTINGS_PANEL);
		this.setName(ViewComponentNames.SETTINGS_PANEL);
		this.setLayout(new GridBagLayout());
	}

	public void initializeComponents() {
		createComponents();
		configureComponents();
		positionComponents();
		initializeListeners();
	}

	private void configureComponents() {
		ComponentInitializer.initializeButton(historyButtonS, ViewComponentNames.HISTORY_BUTTON_NAME_S, true);
		ComponentInitializer.initializeButton(matchButtonS, ViewComponentNames.MATCH_BUTTON_NAME_S, true);
		scroller = ComponentInitializer.initializeList(settingsList, ViewComponentNames.SETTINGS_LIST_NAME,
				ListSelectionModel.SINGLE_SELECTION, 6, new Dimension(400, 100));
		ComponentInitializer.initializeButton(useSettingButton, ViewComponentNames.USE_SETTING_BUTTON_NAME, false);
		ComponentInitializer.initializeButton(deleteSettingButton, ViewComponentNames.DELETE_SETTING_BUTTON_NAME,
				false);
		ComponentInitializer.initializeTextField(newNameTextBox, ViewComponentNames.NEW_NAME_TEXTBOX_NAME, true, false,
				10);
		ComponentInitializer.initializeButton(renameButton, ViewComponentNames.RENAME_BUTTON_NAME, false);
		ComponentInitializer.initializeLabel(createSettingLabel, ViewComponentNames.CREATE_SETTING_LABEL_NAME, null,
				null);
		ComponentInitializer.initializeLabel(nameLabel, ViewComponentNames.NAME_LABEL_NAME, null, nameTextBox);
		ComponentInitializer.initializeLabel(widthLabel, ViewComponentNames.WIDTH_LABEL_NAME, null, widthTextBox);
		ComponentInitializer.initializeLabel(heightLabel, ViewComponentNames.HEIGHT_LABEL_NAME, null, heightTextBox);
		ComponentInitializer.initializeLabel(obstaclesLabel, ViewComponentNames.OBSTACLES_LABEL_NAME, null,
				obstaclesTextBox);
		ComponentInitializer.initializeTextField(nameTextBox, ViewComponentNames.NAME_TEXTBOX_NAME, true, true, 10);
		ComponentInitializer.initializeFormattedTextField(obstaclesTextBox, ViewComponentNames.OBSTACLES_TEXTBOX_NAME,
				ViewComponentNames.OBSTACLES_TEXTBOX_VALUE, true, 5, ComponentInitializer.createIntFormatter());
		ComponentInitializer.initializeFormattedTextField(widthTextBox, ViewComponentNames.WIDTH_TEXTBOX_NAME,
				ViewComponentNames.WIDTH_TEXTBOX_VALUE, true, 5, ComponentInitializer.createIntFormatter());
		ComponentInitializer.initializeFormattedTextField(heightTextBox, ViewComponentNames.HEIGHT_TEXTBOX_NAME,
				ViewComponentNames.HEIGHT_TEXTBOX_VALUE, true, 5, ComponentInitializer.createIntFormatter());
		ComponentInitializer.initializeButton(submitButton, ViewComponentNames.SUBMIT_BUTTON_NAME, true);
	}

	private void createComponents() {
		historyButtonS = new JButton(ViewComponentNames.HISTORY_BUTTON_TEXT_S);
		matchButtonS = new JButton(ViewComponentNames.MATCH_BUTTON_TEXT_S);
		listModel = new DefaultListModel<>();
		settingsList = new JList<>(listModel);
		useSettingButton = new JButton(ViewComponentNames.USE_SETTING_BUTTON_TEXT);
		deleteSettingButton = new JButton(ViewComponentNames.DELETE_SETTING_BUTTON_TEXT);
		newNameTextBox = new JTextField(ViewComponentNames.NEW_NAME_TEXTBOX_TEXT);
		renameButton = new JButton(ViewComponentNames.RENAME_BUTTON_TEXT);
		createSettingLabel = new JLabel(ViewComponentNames.CREATE_SETTING_LABEL_TEXT);
		nameLabel = new JLabel(ViewComponentNames.NAME_LABEL_TEXT);
		widthLabel = new JLabel(ViewComponentNames.WIDTH_LABEL_TEXT);
		heightLabel = new JLabel(ViewComponentNames.HEIGHT_LABEL_TEXT);
		obstaclesLabel = new JLabel(ViewComponentNames.OBSTACLES_LABEL_TEXT);
		nameTextBox = new JTextField(ViewComponentNames.NAME_TEXTBOX_TEXT);
		widthTextBox = new JFormattedTextField();
		heightTextBox = new JFormattedTextField();
		obstaclesTextBox = new JFormattedTextField();
		submitButton = new JButton(ViewComponentNames.SUBMIT_BUTTON_TEXT);
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
		historyButtonS.addMouseListener(new PanelSwitchButtonListener(parentCards, ViewComponentNames.HISTORY_PANEL));
		matchButtonS.addMouseListener(new PanelSwitchButtonListener(parentCards, ViewComponentNames.MATCH_PANEL));
		settingsList.addListSelectionListener(
				new PanelListSelectionButtonListener(renameButton, useSettingButton, deleteSettingButton));
		settingsList.addListSelectionListener(new PanelListSelectionTextBoxListener(newNameTextBox, settingsList));
		useSettingButton.addMouseListener(new UseSettingButtonListener(lobbyView, matchPresenter, settingsList));
		deleteSettingButton.addMouseListener(new DeleteSettingButtonListener(settingsList, lobbyView, lobbyPresenter));
		renameButton.addMouseListener(
				new RenameSettingButtonListener(lobbyView, lobbyPresenter, newNameTextBox, settingsList));
		submitButton.addMouseListener(new SubmitSettingButtonListener(lobbyView, this, lobbyPresenter, nameTextBox,
				widthTextBox, heightTextBox, obstaclesTextBox));
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
