package com.andreasbur.tools;

import com.andreasbur.gui.ParentPane;

public class ToolEventHandlerFactory {

	private final ParentPane parentPane;

	public ToolEventHandlerFactory(ParentPane parentPane) {
		this.parentPane = parentPane;
	}

	public ToolEventHandler createHandToolHandler() {
		return new HandToolEventHandler(parentPane.getScrollHandler());
	}

	public ToolEventHandler createPenToolHandler() {
		return new PenToolEventHandler(parentPane.getActionHandler());
	}

	public ToolEventHandler createEraserToolHandler() {
		return new EraserToolEventHandler();
	}

}
