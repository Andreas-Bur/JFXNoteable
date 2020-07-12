package com.andreasbur.tools;

import javafx.scene.control.ToggleGroup;

public class ToolToggleGroup extends ToggleGroup {

	public ToolToggleGroup() {
		selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				oldValue.setSelected(true);
			}
		});
	}
}
