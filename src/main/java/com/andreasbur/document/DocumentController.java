package com.andreasbur.document;

import com.andreasbur.gui.DocumentSideBar;
import com.andreasbur.page.PageLayout;
import com.andreasbur.page.PageModel;
import javafx.collections.ListChangeListener;

import java.util.EventListener;

public class DocumentController {

	private final DocumentModel documentModel;
	private final DocumentPane documentPane;
	private final DocumentSideBar documentSideBar;

	public DocumentController(DocumentModel documentModel, DocumentPane documentPane, DocumentSideBar documentSideBar) {
		this.documentModel = documentModel;
		this.documentPane = documentPane;
		this.documentSideBar = documentSideBar;

		PageSelectionListener pageSelectionListener = new PageSelectionListener();
		documentPane.setPageSelectionListener(pageSelectionListener);
		documentSideBar.setPageSelectionListener(pageSelectionListener);

		documentModel.getPageModels().addListener((ListChangeListener<? super PageModel>) c -> {
			for (int i = 0; i < documentModel.getPageModels().size(); i++) {
				documentModel.getPageModels().get(i).setPageNumber(i + 1);
			}
		});
	}

	private PageModel createPageModel() {
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

	public void addPage(int index, PageModel pageModel) {
		if (pageModel == null) {
			pageModel = createPageModel();
		}
		documentModel.getPageModels().add(index, pageModel);
	}

	public void removePage(int index) {
		documentModel.getPageModels().remove(index);
	}

	public void setSelectedPage(int index) {
		documentModel.setSelectedPageIndex(index);
	}

	public DocumentModel getDocumentModel() {
		return documentModel;
	}

	public class PageSelectionListener implements EventListener {
		public void changed(int newIndex) {
			setSelectedPage(newIndex);
		}
	}
}
