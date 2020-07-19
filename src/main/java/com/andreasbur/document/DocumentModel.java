package com.andreasbur.document;

import com.andreasbur.page.PageModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections ;
import javafx.collections.ObservableList;

public class DocumentModel {
	private final ObservableList<PageModel> pageModels;
	private final IntegerProperty selectedPageIndex = new SimpleIntegerProperty(-1);

	public DocumentModel() {
		pageModels = FXCollections.observableArrayList();
	}

	public ObservableList<PageModel> getPageModels() {
		return pageModels;
	}

	public PageModel getSelectedPageModel() {
		return pageModels.get(selectedPageIndex.get());
	}

	public BooleanBinding isPageSelected(){
		return selectedPageIndexProperty().greaterThanOrEqualTo(0);
	}

	public int getSelectedPageIndex() {
		return selectedPageIndex.get();
	}

	public IntegerProperty selectedPageIndexProperty() {
		return selectedPageIndex;
	}

	public void setSelectedPageIndex(int selectedPageIndex) {
		this.selectedPageIndex.set(selectedPageIndex);
	}
}
