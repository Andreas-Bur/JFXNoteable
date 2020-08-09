package com.andreasbur.shapes;

import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class FreehandLine extends Polyline implements Erasable {

	public FreehandLine() {
	}

	public FreehandLine(double... points) {
		super(points);
	}

	@Override
	public List<Shape> erase(Shape shape) {
		ArrayList<Shape> newLines = new ArrayList<>();
		FreehandLine currentLine = new FreehandLine();

		for (int i = 0; i < getPoints().size(); i += 2) {
			double x = getPoints().get(i);
			double y = getPoints().get(i + 1);

			if (shape.getBoundsInLocal().contains(x, y)) {
				if (currentLine.getPoints().size() > 0) {
					newLines.add(currentLine);
					currentLine = new FreehandLine();
				}
			} else {
				currentLine.getPoints().addAll(x, y);
			}
		}
		if (currentLine.getPoints().size() > 0) {
			newLines.add(currentLine);
		}
		return newLines;
	}

	@Override
	public boolean isIntersecting(Shape shape) {
		Shape intersectShape = Shape.intersect(this, shape);

		return intersectShape.getLayoutBounds().getWidth() != -1;
	}
}
