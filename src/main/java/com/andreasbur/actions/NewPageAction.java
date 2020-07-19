package com.andreasbur.actions;

import com.andreasbur.document.DocumentController;
import com.andreasbur.page.PageModel;

public class NewPageAction extends Action {

	private final DocumentController documentController;
	private final PageModel pageModel;
	private final int index;
	private int previouslySelectedIndex;

	public NewPageAction(DocumentController documentController) {
		this(documentController, null);
	}

	public NewPageAction(DocumentController documentController, PageModel pageModel) {
		this(documentController, pageModel, documentController.getDocumentModel().isPageSelected().get() ?
				documentController.getDocumentModel().getSelectedPageIndex() + 1 :
				documentController.getDocumentModel().getPageModels().size());
	}

	public NewPageAction(DocumentController documentController, PageModel pageModel, int index) {
		this.documentController = documentController;
		this.pageModel = pageModel;
		this.index = index;
	}

	@Override
	void execute() {

		previouslySelectedIndex = documentController.getDocumentModel().getSelectedPageIndex();

		documentController.addPage(index, pageModel);
		documentController.setSelectedPage(index);
	}

	@Override
	void undo() {
		documentController.removePage(index);
		documentController.setSelectedPage(previouslySelectedIndex);
	}
}
