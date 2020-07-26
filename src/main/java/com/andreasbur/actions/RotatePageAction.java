package com.andreasbur.actions;

import com.andreasbur.document.DocumentController;

public class RotatePageAction extends Action {

	private final DocumentController documentController;
	private final int index;

	public RotatePageAction(DocumentController documentController) {

		if (documentController.getDocumentModel().isPageSelected().not().get()) {
			throw new IllegalStateException("No page to rotate is selected!");
		}

		this.documentController = documentController;
		this.index = documentController.getDocumentModel().getSelectedPageIndex();
	}

	@Override
	protected void execute() {
		documentController.rotatePage(index);
	}

	@Override
	protected void undo() {
		documentController.rotatePage(index);
	}
}
