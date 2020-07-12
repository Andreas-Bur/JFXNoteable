package com.andreasbur.tools;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class ToolEventDistributor implements EventHandler<MouseEvent> {

	private final ToggleGroup toggleGroup;

	public ToolEventDistributor() {

		toggleGroup = new ToolToggleGroup();

		toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != null) {
				((ToolEventHandler) oldValue.getUserData()).resetState();
			}
			if (newValue != null) {
				((ToolEventHandler) newValue.getUserData()).select();
			}
		});
	}

	@Override
	public void handle(MouseEvent event) {
		if (toggleGroup.getSelectedToggle() != null) {
			((ToolEventHandler) toggleGroup.getSelectedToggle().getUserData()).handleEvent(event);
		}
	}

	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}
}
