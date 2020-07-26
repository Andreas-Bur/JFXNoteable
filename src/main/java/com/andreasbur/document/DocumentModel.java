package com.andreasbur.document;

import com.andreasbur.page.PageModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DocumentModel {
	private final ObservableList<PageModel> pageModels;
	private final IntegerProperty selectedPageIndex = new SimpleIntegerProperty(-1);
	private final ObjectProperty<PageModel> selectedPageModel = new SimpleObjectProperty<>();

	public DocumentModel() {
		pageModels = FXCollections.observableArrayList();
	}

	public ObservableList<PageModel> getPageModels() {
		return pageModels;
	}

	public BooleanBinding isPageSelected() {
		return selectedPageIndexProperty().greaterThanOrEqualTo(0);
	}

	public PageModel getSelectedPageModel() {
		return selectedPageModel.get();
	}

	public ReadOnlyObjectProperty<PageModel> selectedPageModelProperty() {
		return selectedPageModel;
	}

	public int getSelectedPageIndex() {
		return selectedPageIndex.get();
	}

	public ReadOnlyIntegerProperty selectedPageIndexProperty() {
		return selectedPageIndex;
	}

	public void setSelectedPageIndex(int selectedPageIndex) {
		this.selectedPageIndex.set(selectedPageIndex);
		if (selectedPageIndex < 0) {
			selectedPageModel.set(null);
		} else {
			selectedPageModel.set(pageModels.get(selectedPageIndex));
		}
	}
}
