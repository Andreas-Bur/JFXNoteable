package com.andreasbur.gui.page;

import javafx.geometry.Dimension2D;

import java.util.Objects;

public class PageLayout {

	private final Dimension2D pageSize;

	public PageLayout(Dimension2D pageSize) {
		this(pageSize, null);
	}

	public PageLayout(Dimension2D pageSize, Page.Orientation orientation) {
		if (pageSize == null) {
			throw new IllegalArgumentException("pageSize must not be null");
		}
		if (orientation == null) {
			this.pageSize = pageSize;
		} else {
			this.pageSize = calcPageSize(pageSize, orientation);
		}
	}

	private Dimension2D calcPageSize(Dimension2D pageSize, Page.Orientation orientation) {
		double max = Math.max(pageSize.getWidth(), pageSize.getHeight());
		double min = Math.min(pageSize.getWidth(), pageSize.getHeight());

		if (orientation == Page.Orientation.PORTRAIT) {
			return new Dimension2D(min, max);
		} else {
			return new Dimension2D(max, min);
		}
	}

	public Dimension2D getPageSize() {
		return pageSize;
	}

	public Page.Orientation getOrientation() {
		if (pageSize.getWidth() > pageSize.getHeight()) {
			return Page.Orientation.LANDSCAPE;
		} else {
			return Page.Orientation.PORTRAIT;
		}
	}

	public PageLayout toRotatedLayout() {
		return new PageLayout(new Dimension2D(pageSize.getHeight(), pageSize.getWidth()));
	}

	public PageLayout toPortraitLayout() {
		return new PageLayout(calcPageSize(pageSize, Page.Orientation.PORTRAIT));
	}

	public PageLayout toLandscapeLayout() {
		return new PageLayout(calcPageSize(pageSize, Page.Orientation.LANDSCAPE));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PageLayout that = (PageLayout) o;
		return pageSize.equals(that.pageSize);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pageSize);
	}
}
