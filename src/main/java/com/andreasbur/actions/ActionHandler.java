package com.andreasbur.actions;

import java.util.Stack;

public class ActionHandler {

	private final Stack<Action> undoActionStack = new Stack<>();
	private final Stack<Action> redoActionStack = new Stack<>();

	public void execute(Action action){
		action.execute();
		undoActionStack.push(action);
	}

	public void undo() {
		if (!undoActionStack.empty()) {
			Action action = undoActionStack.pop();
			action.undo();
			redoActionStack.push(action);
		}
	}

	public void redo() {
		if (!redoActionStack.empty()) {
			Action action = redoActionStack.pop();
			action.execute();
		}
	}

}
