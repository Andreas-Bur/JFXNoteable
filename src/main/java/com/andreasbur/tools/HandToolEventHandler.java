package com.andreasbur.tools;

import com.andreasbur.gui.ParentPane;
import com.andreasbur.util.ScrollHandler;
import javafx.scene.input.MouseEvent;

public class HandToolEventHandler implements ToolEventHandler {

	private final ScrollHandler scrollHandler;

	public HandToolEventHandler(ScrollHandler scrollHandler) {
		this.scrollHandler = scrollHandler;
	}

	@Override
	public void handlePageEvent(MouseEvent event) {

	}

	@Override
	public void handleDocumentEvent(MouseEvent event) {

	}

	@Override
	public void deselect() {
		scrollHandler.setPannable(false);
	}

	@Override
	public void select() {
		scrollHandler.setPannable(true);
	}
}
