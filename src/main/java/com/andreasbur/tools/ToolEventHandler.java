package com.andreasbur.tools;

import javafx.scene.input.MouseEvent;

public interface ToolEventHandler {

	void handlePageEvent(MouseEvent event);

	void handleDocumentEvent(MouseEvent event);

	void deselect();

	void select();
}
