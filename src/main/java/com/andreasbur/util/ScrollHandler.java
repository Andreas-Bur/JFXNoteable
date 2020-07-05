package com.andreasbur.util;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ScrollHandler {

	private final BooleanProperty pannable = new SimpleBooleanProperty(false);

	public BooleanProperty pannableProperty() {
		return pannable;
	}

	public void setPannable(boolean isPannable) {
		this.pannable.set(isPannable);
	}
}
