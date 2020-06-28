package com.andreasbur.actions;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Stack;

public class ActionHandler {

	private final Stack<Action> undoActionStack = new Stack<>();
	private final Stack<Action> redoActionStack = new Stack<>();

	private final BooleanProperty canUndo = new SimpleBooleanProperty(false);
	private final BooleanProperty canRedo = new SimpleBooleanProperty(false);

	public ActionHandler() {
	}

	private void updateStates() {
		canUndo.set(!undoActionStack.empty());
		canRedo.set(!redoActionStack.empty());
	}

	public void execute(Action action) {
		action.execute();
		undoActionStack.push(action);
		updateStates();
	}

	public void undo() {
		if (!undoActionStack.empty()) {
			Action action = undoActionStack.pop();
			action.undo();
			redoActionStack.push(action);
		}
		updateStates();
	}

	public void redo() {
		if (!redoActionStack.empty()) {
			Action action = redoActionStack.pop();
			action.execute();
			undoActionStack.push(action);
		}
		updateStates();
	}

	public boolean isCanUndo() {
		return canUndo.get();
	}

	public BooleanProperty canUndoProperty() {
		return canUndo;
	}

	public boolean isCanRedo() {
		return canRedo.get();
	}

	public BooleanProperty canRedoProperty() {
		return canRedo;
	}
}
