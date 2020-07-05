package com.andreasbur.tools;

import javafx.scene.input.MouseEvent;

public class PenToolEventHandler implements ToolEventHandler {

	private boolean isPressed;

	public PenToolEventHandler() {
		resetState();
	}

	@Override
	public void handleEvent(MouseEvent event) {
		if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			isPressed = true;
		} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			resetState();
		}
	}

	@Override
	public void resetState() {
		isPressed = false;
	}

	@Override
	public void select() {

	}
}
