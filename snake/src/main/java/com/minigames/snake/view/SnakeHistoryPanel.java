package com.minigames.snake.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.Generated;

public class SnakeHistoryPanel extends JPanel {

	public static final String SETTINGS_BUTTON_TEXT_H = "Settings";
	public static final String SETTINGS_BUTTON_NAME_H = "settingsButton";
	public static final String MATCH_BUTTON_TEXT_H = "Play";
	public static final String MATCH_BUTTON_NAME_H = "matchButton";

	public static final String HISTORY_LIST_NAME = "historyList";
	public static final String HIGH_SCORE_LABEL_TEXT = "All time high score: ";
	public static final String HIGH_SCORE_LABEL_NAME = "highScoreLabel";
	public static final String DELETE_ALL_BUTTON_TEXT = "Clear history";
	public static final String DELETE_ALL_BUTTON_NAME = "deleteAllButton";
	public static final String DELETE_SELECTED_TEXT = "Delete selected";
	public static final String DELETE_SELECTED_NAME = "deleteSelectedButton";

	private static final long serialVersionUID = 1L;
	private JPanel parentCards;
	private JScrollPane scroller;
	private JButton settingsButtonH;
	private JButton matchButtonH;
	private JButton deleteAllButton;
	private JList<GameRecord> historyList;
	private JLabel highScoreLabel;
	private JButton deleteSelectedButton;
	private DefaultListModel<GameRecord> listModel;
	private SnakeWindowView parentview;

	public SnakeHistoryPanel(SnakeWindowView parentview, JPanel parentCards, String cardName) {
		this.parentview = parentview;
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
		ComponentInitializer.initializeButton(settingsButtonH, SETTINGS_BUTTON_NAME_H, true);
		ComponentInitializer.initializeButton(matchButtonH, MATCH_BUTTON_NAME_H, true);
		scroller = ComponentInitializer.initializeList(historyList, HISTORY_LIST_NAME,
				ListSelectionModel.SINGLE_SELECTION, 12, new Dimension(400, 200));
		ComponentInitializer.initializeLabel(highScoreLabel, HIGH_SCORE_LABEL_NAME,
				new Font("New times roman", Font.PLAIN, 12), null);
		ComponentInitializer.initializeButton(deleteAllButton, DELETE_ALL_BUTTON_NAME, true);
		ComponentInitializer.initializeButton(deleteSelectedButton, DELETE_SELECTED_NAME, false);
	}

	private void createComponents() {
		settingsButtonH = new JButton(SETTINGS_BUTTON_TEXT_H);
		matchButtonH = new JButton(MATCH_BUTTON_TEXT_H);
		listModel = new DefaultListModel<>();
		historyList = new JList<>(listModel);
		highScoreLabel = new JLabel(HIGH_SCORE_LABEL_TEXT + "0");
		deleteAllButton = new JButton(DELETE_ALL_BUTTON_TEXT);
		deleteSelectedButton = new JButton(DELETE_SELECTED_TEXT);
	}

	private void positionComponents() {
		this.add(settingsButtonH, new GridBagConstraintsBuilder().withGridx(0).withGridy(0).withGridwidth(3)
				.withGridheight(1).withWeightx(1).withWeighty(1).withInsets(new Insets(20, 0, 0, 0)).build());
		this.add(matchButtonH, new GridBagConstraintsBuilder().withGridx(5).withGridy(0).withGridwidth(3)
				.withGridheight(1).withWeightx(1).withWeighty(1).withInsets(new Insets(20, 0, 0, 0)).build());
		this.add(scroller, new GridBagConstraintsBuilder().withGridx(0).withGridwidth(8).withGridheight(2)
				.withWeightx(1).withWeighty(2).withFill(GridBagConstraints.BOTH).build());
		this.add(highScoreLabel, new GridBagConstraintsBuilder().withGridx(3).withGridy(3).withGridwidth(2)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
		this.add(deleteAllButton, new GridBagConstraintsBuilder().withGridy(4).withGridwidth(2).withGridheight(1)
				.withWeightx(1).withWeighty(1).build());
		this.add(deleteSelectedButton, new GridBagConstraintsBuilder().withGridx(5).withGridy(4).withGridwidth(2)
				.withGridheight(1).withWeightx(1).withWeighty(1).build());
	}

	private void initializeListeners() {
		settingsButtonH.addMouseListener(new PanelSwitchButtonListener(parentCards, SnakeWindowView.SETTINGS_PANEL));
		matchButtonH.addMouseListener(new PanelSwitchButtonListener(parentCards, SnakeWindowView.MATCH_PANEL));
		historyList.addListSelectionListener(new PanelListSelectionButtonListener(deleteSelectedButton));
		deleteAllButton.addMouseListener(new DeleteAllRecordButtonListener(parentview));
		deleteSelectedButton.addMouseListener(new DeleteRecordButtonListener(historyList, parentview));
	}

	public void refresh(Collection<GameRecord> newRecordList) {
		listModel.clear();
		listModel.addAll(newRecordList);
		highScoreLabel.setText(HIGH_SCORE_LABEL_TEXT
				+ Integer.toString(newRecordList.stream().map(GameRecord::getScore).max((x, y) -> x - y).orElse(0)));
	}

	// for testing
	@Generated
	public DefaultListModel<GameRecord> getListModel() {
		return listModel;
	}

}
