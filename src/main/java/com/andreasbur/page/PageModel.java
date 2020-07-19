package com.andreasbur.page;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PageModel {
	private final ObjectProperty<PageLayout> pageLayout;
	private final IntegerProperty pageNumber;


	public PageModel(PageLayout pageLayout) {
		this.pageLayout = new SimpleObjectProperty<>(pageLayout);
		this.pageNumber = new SimpleIntegerProperty(-1);
	}

	public PageLayout getPageLayout() {
		return pageLayout.get();
	}

	public ObjectProperty<PageLayout> pageLayoutProperty() {
		return pageLayout;
	}

	public void setPageLayout(PageLayout pageLayout) {
		this.pageLayout.set(pageLayout);
	}

	public int getPageNumber() {
		return pageNumber.get();
	}

	public IntegerProperty pageNumberProperty() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber.set(pageNumber);
	}
}
