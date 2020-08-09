package com.andreasbur.actions;

import com.andreasbur.page.PageModel;
import javafx.application.Platform;
import javafx.scene.shape.Shape;

public class AddShapeAction extends Action {

	private final PageModel pageModel;
	private final Shape shape;

	public AddShapeAction(PageModel pageModel, Shape shape) {
		this.pageModel = pageModel;
		this.shape = shape;
	}

	@Override
	protected void execute() {
		Platform.runLater(() -> {
			pageModel.getShapes().add(shape);
		});
	}

	@Override
	protected void undo() {
		Platform.runLater(() -> {
			pageModel.getShapes().remove(shape);
		});
	}
}
