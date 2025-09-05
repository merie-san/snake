package com.minigames.snake.view;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import com.minigames.snake.model.Generated;

@Generated
public class GridBagConstraintsBuilder {
	private int gridx = GridBagConstraints.RELATIVE;
	private int gridy = GridBagConstraints.RELATIVE;
	private int gridwidth = 1;
	private int gridheight = 1;
	private int weightx = 0;
	private int weighty = 0;
	private int anchor = GridBagConstraints.CENTER;
	private int fill = GridBagConstraints.NONE;
	private Insets insets = new Insets(0, 0, 0, 0);
	private int ipadx = 0;
	private int ipady = 0;

	public GridBagConstraintsBuilder withGridx(int gridx) {
		this.gridx = gridx;
		return this;
	}

	public GridBagConstraintsBuilder withGridy(int gridy) {
		this.gridy = gridy;
		return this;
	}

	public GridBagConstraintsBuilder withGridwidth(int gridwidth) {
		this.gridwidth = gridwidth;
		return this;
	}

	public GridBagConstraintsBuilder withGridheight(int gridheight) {
		this.gridheight = gridheight;
		return this;
	}

	public GridBagConstraintsBuilder withWeightx(int weightx) {
		this.weightx = weightx;
		return this;
	}

	public GridBagConstraintsBuilder withWeighty(int weighty) {
		this.weighty = weighty;
		return this;
	}

	public GridBagConstraintsBuilder withAnchor(int anchor) {
		this.anchor = anchor;
		return this;
	}

	public GridBagConstraintsBuilder withFill(int fill) {
		this.fill = fill;
		return this;
	}

	public GridBagConstraintsBuilder withInsets(Insets insets) {
		this.insets = insets;
		return this;
	}

	public GridBagConstraintsBuilder withIpadx(int ipadx) {
		this.ipadx = ipadx;
		return this;
	}

	public GridBagConstraintsBuilder withIpady(int ipady) {
		this.ipady = ipady;
		return this;
	}

	public GridBagConstraints build() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.gridwidth = gridwidth;
		constraints.gridheight = gridheight;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		constraints.anchor = anchor;
		constraints.fill = fill;
		constraints.insets = insets;
		constraints.ipadx = ipadx;
		constraints.ipady = ipady;
		return constraints;
	}

}
