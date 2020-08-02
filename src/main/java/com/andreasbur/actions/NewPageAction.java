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
		this(documentController, pageModel, -1);
	}

	public NewPageAction(DocumentController documentController, PageModel pageModel, int index) {
		this.documentController = documentController;
		if (pageModel != null) {
			this.pageModel = pageModel;
		} else {
			this.pageModel = documentController.createPageModel();
		}

		if (index >= 0) {
			this.index = index;
		} else {
			this.index = documentController.getActivePageIndex() + 1;
		}
	}

	@Override
	protected void execute() {
		previouslySelectedIndex = documentController.getDocumentModel().getSelectedPageIndex();
		documentController.addPage(index, pageModel, true);
	}

	@Override
	protected void undo() {
		documentController.removePage(index);
		documentController.setSelectedPage(previouslySelectedIndex);
	}
}
