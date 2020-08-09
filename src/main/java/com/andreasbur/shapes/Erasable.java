package com.andreasbur.shapes;

import javafx.scene.shape.Shape;

import java.util.List;

public interface Erasable {

	List<Shape> erase(Shape shape);

	boolean isIntersecting(Shape shape);

}
