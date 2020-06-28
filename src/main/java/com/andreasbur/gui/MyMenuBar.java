package com.andreasbur.gui;

import com.andreasbur.actions.ActionHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class MyMenuBar extends MenuBar {

	ActionHandler actionHandler;

	public MyMenuBar(ActionHandler actionHandler) {
		super();

		this.actionHandler = actionHandler;

		getMenus().add(createFileMenu());
		getMenus().add(createEditMenu());
		getMenus().add(createViewMenu());

	}

	private Menu createFileMenu(){
		Menu fileMenu = new Menu("_File");

		return fileMenu;
	}

	private Menu createEditMenu(){
		Menu editMenu = new Menu("_Edit");

		MenuItem undoItem = new MenuItem("Undo");
		undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
		undoItem.disableProperty().bind(actionHandler.canUndoProperty().not());
		undoItem.setOnAction(event -> actionHandler.undo());

		MenuItem redoItem = new MenuItem("Redo");
		redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
		redoItem.disableProperty().bind(actionHandler.canRedoProperty().not());
		redoItem.setOnAction(event -> actionHandler.redo());

		editMenu.getItems().addAll(undoItem, redoItem);

		return editMenu;
	}

	private Menu createViewMenu(){
		Menu viewMenu = new Menu("_View");

		return viewMenu;
	}
}
