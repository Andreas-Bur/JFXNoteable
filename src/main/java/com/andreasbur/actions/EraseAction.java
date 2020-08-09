package com.andreasbur.actions;

import com.andreasbur.page.PageModel;
import com.andreasbur.shapes.Erasable;
import javafx.application.Platform;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class EraseAction extends Action {

	private final PageModel pageModel;
	private final Polyline eraserShape;
	private final List<ShapeReplacementExecute> shapeReplacementsExecute;
	private final List<ShapeReplacementUndo> shapeReplacementsUndo;

	public EraseAction(PageModel pageModel, Polyline eraserShape) {
		this.pageModel = pageModel;
		this.eraserShape = eraserShape;
		this.shapeReplacementsExecute = new ArrayList<>();
		this.shapeReplacementsUndo = new ArrayList<>();
	}

	@Override
	protected void execute() {
		for (Shape shape : pageModel.getShapes()) {
			if (shape instanceof Erasable erasable) {
				if (erasable.isIntersecting(eraserShape)) {
					List<Shape> newShapes = erasable.erase(eraserShape);
					newShapes.forEach(shape1 -> shape1.setManaged(false));
					shapeReplacementsExecute.add(new ShapeReplacementExecute(shape, newShapes));
				}
			}
		}

		Platform.runLater(() -> {
			for (ShapeReplacementExecute shapeReplacement : shapeReplacementsExecute) {
				int index = pageModel.getShapes().indexOf(shapeReplacement.oldShape());
				pageModel.getShapes().remove(shapeReplacement.oldShape());
				pageModel.getShapes().addAll(index, shapeReplacement.newShapes());
				shapeReplacementsUndo.add(new ShapeReplacementUndo(index, shapeReplacement.oldShape(), shapeReplacement.newShapes()));
			}
		});
	}

	@Override
	protected void undo() {
		Platform.runLater(() -> {
			for (int i = shapeReplacementsUndo.size() - 1; i >= 0; i--) {
				pageModel.getShapes().removeAll(shapeReplacementsUndo.get(i).newShapes());
				pageModel.getShapes().add(shapeReplacementsUndo.get(i).index(), shapeReplacementsUndo.get(i).oldShape());
			}
		});
	}

	private record ShapeReplacementExecute(Shape oldShape, List<Shape>newShapes) {
	}

	private record ShapeReplacementUndo(int index, Shape oldShape, List<Shape>newShapes) {
	}
}
