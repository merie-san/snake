package com.minigames.snake.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class ComponentInitializer {

	private ComponentInitializer() {

	}

	public static void initializePanel(JPanel panel, String canvasName, boolean enabled) {
		panel.setName(canvasName);
		panel.setEnabled(enabled);
	}

	public static NumberFormatter createIntFormatter() {
		NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		formatter.setValueClass(Integer.class);
		formatter.setOverwriteMode(true);
		formatter.setAllowsInvalid(true);
		return formatter;
	}

	public static void initializeFormattedTextField(JFormattedTextField textBox, String boxName, int boxValue,
			boolean enabled, int size, NumberFormatter formatter) {
		textBox.setFormatterFactory(new DefaultFormatterFactory(formatter));
		textBox.setName(boxName);
		textBox.setValue(Integer.valueOf(boxValue));
		textBox.setHorizontalAlignment(SwingConstants.CENTER);
		textBox.setEnabled(enabled);
		textBox.setColumns(size);
		textBox.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
	}

	public static void initializeTextField(JTextField textBox, String boxName, boolean editable, boolean enabled,
			int size) {
		textBox.setName(boxName);
		textBox.setEditable(editable);
		textBox.setEnabled(enabled);
		textBox.setColumns(size);
	}

	public static <T> JScrollPane initializeList(JList<T> list, String listName, int selectionModel,
			int visibleRowCount, Dimension dimension) {
		list.setName(listName);
		list.setSelectionMode(selectionModel);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectedIndex(-1);
		list.setVisibleRowCount(visibleRowCount);
		JScrollPane scroller = new JScrollPane(list);
		scroller.setPreferredSize(dimension);
		return scroller;
	}

	public static void initializeLabel(JLabel label, String labelName, Font font, Component component) {
		label.setName(labelName);
		if (font != null) {
			label.setFont(font);
		}
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setLabelFor(component);
	}

	public static void initializeButton(JButton button, String buttonName, boolean enabled) {
		button.setName(buttonName);
		button.setEnabled(enabled);
	}

}
