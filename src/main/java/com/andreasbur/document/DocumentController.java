package com.andreasbur.document;

import com.andreasbur.page.PageLayout;
import com.andreasbur.page.PageModel;
import javafx.collections.ListChangeListener;

import java.util.ArrayList;
import java.util.EventListener;

public class DocumentController {

	private final DocumentModel documentModel;
	private final DocumentPane documentPane;

	private final ArrayList<PageSelector> pageSelectors;
	private final PageSelectionListener pageSelectionListener;

	public DocumentController(DocumentModel documentModel, DocumentPane documentPane) {
		this.documentModel = documentModel;
		this.documentPane = documentPane;

		pageSelectors = new ArrayList<>();
		pageSelectionListener = new PageSelectionListener();

		documentModel.getPageModels().addListener((ListChangeListener<? super PageModel>) c -> {
			for (int i = 0; i < documentModel.getPageModels().size(); i++) {
				documentModel.getPageModels().get(i).setPageNumber(i + 1);
			}

			while (c.next()) {
				if (c.getRemoved().contains(documentModel.getSelectedPageModel())) {
					documentModel.setSelectedPageIndex(-1);
				}
			}
		});
	}

	public PageModel createPageModel() {
		PageModel pageModel;
		if (documentModel.isPageSelected().get()) {
			pageModel = new PageModel(documentModel.getSelectedPageModel().getPageLayout());
		} else if (!documentModel.getPageModels().isEmpty()) {
			pageModel = new PageModel(documentModel.getPageModels().get(documentModel.getPageModels().size() - 1).getPageLayout());
		} else {
			pageModel = new PageModel(PageLayout.DEFAULT);
		}

		return pageModel;
	}

	public void addPage(int index, PageModel pageModel, boolean selectPage) {
		addPage(index, pageModel);
		if (selectPage) {
			setSelectedPage(index);
		}
	}

	public void addPage(int index, PageModel pageModel) {
		if (pageModel == null) {
			pageModel = createPageModel();
		}
		if (index == -1) {
			index = documentModel.getPageModels().size();
		}
		documentModel.getPageModels().add(index, pageModel);
	}

	public void removePage(int index) {
		documentModel.getPageModels().remove(index);
	}

	public void rotatePage(int index) {
		PageModel pageModel = documentModel.getPageModels().get(index);
		pageModel.setPageLayout(pageModel.getPageLayout().toRotatedLayout());
	}

	public void addPageSelector(PageSelector pageSelector) {
		pageSelector.setPageSelectionListener(pageSelectionListener);
		pageSelectors.add(pageSelector);
	}

	public void setSelectedPage(int index) {
		documentModel.setSelectedPageIndex(index);
	}

	public DocumentModel getDocumentModel() {
		return documentModel;
	}

	public int getActivePageIndex() {
		if (documentModel.isPageSelected().get()) {
			return documentModel.getSelectedPageIndex();
		} else {
			return documentModel.getPageModels().size() - 1;
		}
	}

	public class PageSelectionListener implements EventListener {
		public void changed(int newIndex) {
			setSelectedPage(newIndex);
		}
	}
}
