package com.minigames.snake.view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ComponentInitializer {
	

	public static void initializePanel(JPanel panel,String canvasName, boolean enabled) {
		panel.setName(canvasName);
		panel.setEnabled(enabled);
	}

	public static void initializeFormattedTextField(JFormattedTextField textBox, String boxName, int boxValue,
			boolean enabled, boolean editable, int size) {
		textBox.setName(boxName);
		textBox.setValue(boxValue);
		textBox.setHorizontalAlignment(SwingConstants.CENTER);
		textBox.setEnabled(enabled);
		textBox.setEditable(editable);
		textBox.setColumns(size);
	}

	public static void initializeTextField(JTextField textBox, String boxName, boolean editable, boolean enabled, int size) {
		textBox.setName(boxName);
		textBox.setEditable(editable);
		textBox.setEnabled(enabled);
		textBox.setColumns(size);
	}

	public static <T> JScrollPane initializeList(JList<T> list, String listName, int selectionModel, int visibleRowCount,
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

	public static void initializeLabel(JLabel label, String labelName, Font font) {
		label.setName(labelName);
		if (font != null) {
			label.setFont(font);
		}
		label.setHorizontalAlignment(SwingConstants.CENTER);
	}

	public static void initializeButton(JButton button, String buttonName, boolean enabled) {
		button.setName(buttonName);
		button.setEnabled(enabled);
	}


}
