package com.andreasbur.tools;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ToolFactory {

	private final ToggleGroup toggleGroup;
	private final ToolEventHandlerFactory toolEventHandlerFactory;

	public ToolFactory(ToolEventHandlerFactory toolEventHandlerFactory) {
		this.toolEventHandlerFactory = toolEventHandlerFactory;
		toggleGroup = new ToolToggleGroup();
	}

	public ToggleButton createHandToolButton() {
		ToggleButton handToolButton = new ToggleButton("Hand");
		handToolButton.setUserData(toolEventHandlerFactory.createHandToolHandler());
		handToolButton.setToggleGroup(toggleGroup);

		return handToolButton;
	}

	public ToggleButton createPenToolButton() {
		ToggleButton penToolButton = new ToggleButton("Pen");
		penToolButton.setUserData(toolEventHandlerFactory.createPenToolHandler());
		penToolButton.setToggleGroup(toggleGroup);

		return penToolButton;
	}

	public ToggleButton createEraserToolButton() {
		ToggleButton eraserToolButton = new ToggleButton("Eraser");
		eraserToolButton.setUserData(toolEventHandlerFactory.createEraserToolHandler());
		eraserToolButton.setToggleGroup(toggleGroup);

		return eraserToolButton;
	}

	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}
}
