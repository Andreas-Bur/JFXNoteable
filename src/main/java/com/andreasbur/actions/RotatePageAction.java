package com.andreasbur.actions;

import com.andreasbur.document.DocumentController;
import com.andreasbur.document.DocumentModel;
import com.andreasbur.page.PageModel;

public class RotatePageAction extends Action {

	private final PageModel pageModel;

	public RotatePageAction(DocumentController documentController) {
		this(documentController.getDocumentModel().getSelectedPageModel());
	}

	public RotatePageAction(PageModel pageModel) {
		if(pageModel == null){
			throw new IllegalArgumentException("pageModel must not be null.");
		}

		this.pageModel = pageModel;
	}

	@Override
	void execute() {
		pageModel.setPageLayout(pageModel.getPageLayout().toRotatedLayout());
	}

	@Override
	void undo() {
		pageModel.setPageLayout(pageModel.getPageLayout().toRotatedLayout());
	}
}
