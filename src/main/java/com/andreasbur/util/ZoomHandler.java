package com.andreasbur.util;

import javafx.beans.property.SimpleDoubleProperty;

public class ZoomHandler {

	public static final int MIN_SCALE_VALUE = 20;
	public static final int DEFAULT_SCALE_VALUE = 100;
	public static final int MAX_SCALE_VALUE = 400;

	private final SimpleDoubleProperty zoomProperty = new SimpleDoubleProperty(DEFAULT_SCALE_VALUE);

	public void zoomIn() {
		double newValue = scaleProperty().get() + 10;
		if (newValue > MAX_SCALE_VALUE) {
			newValue = MAX_SCALE_VALUE;
		}
		zoomProperty.set(newValue);
	}

	public void zoomOut() {
		double newValue = scaleProperty().get() - 10;
		if (newValue < MIN_SCALE_VALUE) {
			newValue = MIN_SCALE_VALUE;
		}
		zoomProperty.set(newValue);
	}

	public SimpleDoubleProperty scaleProperty() {
		return zoomProperty;
	}

	public double getZoomFactor() {
		return scaleProperty().get() / 100;
	}

}
