package com.andreasbur.page;

import javafx.geometry.Dimension2D;

import java.util.Objects;

public class PageLayout {

	public static final PageLayout A3 = new PageLayout(new Dimension2D(297, 420));
	public static final PageLayout A4 = new PageLayout(new Dimension2D(210, 297));
	public static final PageLayout A5 = new PageLayout(new Dimension2D(148, 210));

	public static final PageLayout DEFAULT = A4;

	private final Dimension2D pageSize;

	public PageLayout(Dimension2D pageSize) {
		this(pageSize, null);
	}

	public PageLayout(Dimension2D pageSize, PagePane.Orientation orientation) {
		if (pageSize == null) {
			throw new IllegalArgumentException("pageSize must not be null");
		}
		if (orientation == null) {
			this.pageSize = pageSize;
		} else {
			this.pageSize = calcPageSize(pageSize, orientation);
		}
	}

	private Dimension2D calcPageSize(Dimension2D pageSize, PagePane.Orientation orientation) {
		double max = Math.max(pageSize.getWidth(), pageSize.getHeight());
		double min = Math.min(pageSize.getWidth(), pageSize.getHeight());

		if (orientation == PagePane.Orientation.PORTRAIT) {
			return new Dimension2D(min, max);
		} else {
			return new Dimension2D(max, min);
		}
	}

	public Dimension2D getPageSize() {
		return pageSize;
	}

	public PagePane.Orientation getOrientation() {
		if (pageSize.getWidth() > pageSize.getHeight()) {
			return PagePane.Orientation.LANDSCAPE;
		} else {
			return PagePane.Orientation.PORTRAIT;
		}
	}

	public PageLayout toRotatedLayout() {
		return new PageLayout(new Dimension2D(pageSize.getHeight(), pageSize.getWidth()));
	}

	public PageLayout toPortrait() {
		return new PageLayout(calcPageSize(pageSize, PagePane.Orientation.PORTRAIT));
	}

	public PageLayout toLandscape() {
		return new PageLayout(calcPageSize(pageSize, PagePane.Orientation.LANDSCAPE));
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
