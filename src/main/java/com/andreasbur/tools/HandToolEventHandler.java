package com.andreasbur.tools;

import com.andreasbur.gui.ParentPane;
import com.andreasbur.util.ScrollHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class HandToolEventHandler implements ToolEventHandler {

	private final ScrollHandler scrollHandler;

	private final ParentPane parentPane;

	public HandToolEventHandler(ParentPane parentPane) {
		this.scrollHandler = parentPane.getScrollHandler();
		this.parentPane = parentPane;
	}

	@Override
	public void handleEvent(MouseEvent event) {
		if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)
				&& event.getButton().equals(MouseButton.PRIMARY)) {
			scrollHandler.setPannable(true);
		} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			scrollHandler.setPannable(false);
		}
	}

	@Override
	public void resetState() {
	}

	@Override
	public void select() {
	}
}
