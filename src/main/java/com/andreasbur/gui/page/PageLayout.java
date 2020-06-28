package com.andreasbur.gui.page;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;

import java.util.Objects;

public class PageLayout {

	private final SimpleObjectProperty<Dimension2D> pageSize = new SimpleObjectProperty<>();
	private final SimpleObjectProperty<Page.Orientation> orientation = new SimpleObjectProperty<>();

	public PageLayout(Dimension2D dimension, Page.Orientation orientation) {

		Bindings.createDoubleBinding(() -> pageSize.get().getWidth(), pageSize);

		pageSize.set(dimension);

		pageSize.addListener((observable, oldValue, newValue) -> {
			if (isInvalid(newValue.getWidth(), newValue.getHeight(), getOrientation())) {
				swapOrientation();
			}
		});

		this.orientation.addListener((observable, oldValue, newValue) -> {
			if (isInvalid(pageSize.get().getWidth(), pageSize.get().getHeight(), newValue)) {
				pageSize.set(new Dimension2D(pageSize.get().getHeight(), pageSize.get().getWidth()));
			}
		});

		this.orientation.set(orientation);
	}

	private boolean isInvalid(double width, double height, Page.Orientation orientation) {
		return (orientation == Page.Orientation.PORTRAIT && (width > height)) || (orientation == Page.Orientation.LANDSCAPE && (height > width));
	}

	public void swapOrientation() {
		if (getOrientation() == Page.Orientation.PORTRAIT) {
			setOrientation(Page.Orientation.LANDSCAPE);
		} else {
			setOrientation(Page.Orientation.PORTRAIT);
		}
	}

	public Dimension2D getPageSize() {
		return pageSize.get();
	}

	public SimpleObjectProperty<Dimension2D> pageSizeProperty() {
		return pageSize;
	}

	public void setPageSize(Dimension2D pageSize) {
		this.pageSize.set(pageSize);
	}

	public Page.Orientation getOrientation() {
		return orientation.get();
	}

	public SimpleObjectProperty<Page.Orientation> orientationProperty() {
		return orientation;
	}

	public void setOrientation(Page.Orientation orientation) {
		this.orientation.set(orientation);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PageLayout that = (PageLayout) o;
		return getPageSize().equals(that.getPageSize()) &&
				getOrientation().equals(that.getOrientation());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPageSize(), getOrientation());
	}
}