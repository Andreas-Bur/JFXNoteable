package com.andreasbur.tools;

import com.andreasbur.gui.ParentPane;
import javafx.scene.control.ToggleButton;

public class ToolFactory {

	private final ParentPane parentPane;
	private final ToolEventHandlerFactory toolEventHandlerFactory;

	public ToolFactory(ParentPane parentPane) {
		this.parentPane = parentPane;
		toolEventHandlerFactory = new ToolEventHandlerFactory(parentPane);
	}

	public ToggleButton createHandToolButton() {
		ToggleButton handToolButton = new ToggleButton("Hand");
		handToolButton.setUserData(toolEventHandlerFactory.createHandToolHandler());
		handToolButton.setToggleGroup(parentPane.getToolEventDistributor().getToggleGroup());

		return handToolButton;
	}

	public ToggleButton createPenToolButton() {
		ToggleButton penToolButton = new ToggleButton("Pen");
		penToolButton.setUserData(toolEventHandlerFactory.createPenToolHandler());
		penToolButton.setToggleGroup(parentPane.getToolEventDistributor().getToggleGroup());

		return penToolButton;
	}

	public ToggleButton createEraserToolButton() {
		ToggleButton eraserToolButton = new ToggleButton("Eraser");
		eraserToolButton.setUserData(toolEventHandlerFactory.createEraserToolHandler());
		eraserToolButton.setToggleGroup(parentPane.getToolEventDistributor().getToggleGroup());

		return eraserToolButton;
	}

}
