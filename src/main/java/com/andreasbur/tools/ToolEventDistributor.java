package com.andreasbur.tools;

import com.andreasbur.page.PagePane;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class ToolEventDistributor implements EventHandler<MouseEvent> {

	private final ToggleGroup toggleGroup;

	public ToolEventDistributor(ToggleGroup toggleGroup) {

		this.toggleGroup = toggleGroup;

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

			boolean isEventOnPage = false;

			if (event.getSource() instanceof PagePane) {
				PagePane pagePane = (PagePane) event.getSource();
				if (pagePane.getContentPane().getLayoutBounds().contains(event.getX(), event.getY())) {
					isEventOnPage = true;
				}
			}

			if (isEventOnPage) {
				((ToolEventHandler) toggleGroup.getSelectedToggle().getUserData()).handlePageEvent(event);
			} else {
				((ToolEventHandler) toggleGroup.getSelectedToggle().getUserData()).handleDocumentEvent(event);
			}
		}
	}
}
