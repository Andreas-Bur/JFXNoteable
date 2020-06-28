package com.andreasbur.gui;

import com.andreasbur.actions.ActionHandler;
import com.andreasbur.actions.NewPageAction;
import com.andreasbur.actions.RotatePageAction;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

public class ToolbarPane extends AnchorPane {

	public ToolbarPane(DocumentPane documentPane, ActionHandler actionHandler) {
		ToolBar toolBar = new ToolBar();

		AnchorPane.setLeftAnchor(toolBar, 0d);
		AnchorPane.setRightAnchor(toolBar, 0d);

		Button addPageButton = new Button("add page");
		addPageButton.setOnAction(event -> {
			NewPageAction newPageAction = new NewPageAction(documentPane);
			actionHandler.execute(newPageAction);
		});

		Button rotatePageButton = new Button("rotate Page");
		rotatePageButton.disableProperty().bind(documentPane.selectedPageProperty().isNull());
		rotatePageButton.setOnAction(event -> {
			RotatePageAction rotatePageAction = new RotatePageAction(documentPane);
			actionHandler.execute(rotatePageAction);
		});

		toolBar.getItems().addAll(addPageButton, rotatePageButton);

		this.getChildren().add(toolBar);
	}
}
