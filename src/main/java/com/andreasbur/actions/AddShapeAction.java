package com.andreasbur.actions;

import com.andreasbur.page.PageModel;
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
		pageModel.getShapes().add(shape);
	}

	@Override
	protected void undo() {
		pageModel.getShapes().remove(shape);
	}
}
