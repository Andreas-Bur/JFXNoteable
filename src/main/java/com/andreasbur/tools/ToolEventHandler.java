package com.andreasbur.tools;

import javafx.scene.input.MouseEvent;

public interface ToolEventHandler {

	void handleEvent(MouseEvent event);

	void resetState();

	void select();
}
