package com.andreasbur.gui;

import com.andreasbur.actions.ActionHandler;
import com.andreasbur.actions.NewPageAction;
import com.andreasbur.actions.RotatePageAction;
import com.andreasbur.document.DocumentController;
import com.andreasbur.document.DocumentModel;
import com.andreasbur.tools.ToolFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class ToolbarPane extends VBox {

	private final DocumentController documentController;
	private final ActionHandler actionHandler;

	private final ToolFactory toolFactory;

	public ToolbarPane(DocumentController documentController, ActionHandler actionHandler, ToolFactory toolFactory) {

		this.documentController = documentController;
		this.actionHandler = actionHandler;
		this.toolFactory = toolFactory;

		setFillWidth(true);

		ToolBar topToolBar = createTopToolbar();
		ToolBar bottomToolBar = createBottomToolbar();

		this.getChildren().addAll(topToolBar, bottomToolBar);
	}

	private ToolBar createTopToolbar() {
		ToolBar toolBar = new ToolBar();

		Button addPageButton = new Button("add page");
		addPageButton.setOnAction(event -> {
			NewPageAction newPageAction = new NewPageAction(documentController);
			actionHandler.execute(newPageAction);
		});

		Button rotatePageButton = new Button("rotate Page");
		rotatePageButton.disableProperty().bind(documentController.getDocumentModel().isPageSelected().not());
		rotatePageButton.setOnAction(event -> {
			RotatePageAction rotatePageAction = new RotatePageAction(documentController);
			actionHandler.execute(rotatePageAction);
		});

		toolBar.getItems().addAll(addPageButton, rotatePageButton);

		return toolBar;
	}

	private ToolBar createBottomToolbar() {
		ToolBar toolBar = new ToolBar();

		ToggleButton handToolButton = toolFactory.createHandToolButton();
		handToolButton.setSelected(true);

		ToggleButton penToolButton = toolFactory.createPenToolButton();

		ToggleButton eraserToolButton = toolFactory.createEraserToolButton();

		toolBar.getItems().addAll(handToolButton, penToolButton, eraserToolButton);

		return toolBar;
	}
}
