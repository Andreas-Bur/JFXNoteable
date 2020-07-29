package com.andreasbur.tools;

import com.andreasbur.page.PagePane;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class ToolEventDistributor implements EventHandler<MouseEvent> {

	private final ToggleGroup toggleGroup;

	public ToolEventDistributor() {

		toggleGroup = new ToolToggleGroup();

		toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != null) {
				((ToolEventHandler) oldValue.getUserData()).deselect();
			}
			if (newValue != null) {
				((ToolEventHandler) newValue.getUserData()).select();
			}
		});
	}

	@Override
	public void handle(MouseEvent event) {
		if (toggleGroup.getSelectedToggle() != null) {
			PagePane pagePane = (PagePane) event.getSource();

			if (pagePane.getContentPane().getLayoutBounds().contains(event.getX(), event.getY())) {
				((ToolEventHandler) toggleGroup.getSelectedToggle().getUserData()).handleEvent(event);
			}
		}
	}

	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}
}
