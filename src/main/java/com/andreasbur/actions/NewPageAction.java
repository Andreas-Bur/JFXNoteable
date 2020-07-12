package com.andreasbur.actions;

import com.andreasbur.gui.DocumentPane;
import com.andreasbur.gui.page.Page;

public class NewPageAction extends Action {

	private final DocumentPane documentPane;
	private final Page page;
	private final int index;

	public NewPageAction(DocumentPane documentPane) {
		this(documentPane, null);
	}

	public NewPageAction(DocumentPane documentPane, Page page) {
		this(documentPane, page, documentPane.getSelectedPage() != null ? documentPane.getSelectedPage().getPageIndex() : documentPane.getPageList().size());
	}

	public NewPageAction(DocumentPane documentPane, Page page, int index) {
		this.documentPane = documentPane;
		this.page = (page == null) ? getPage() : page;
		this.index = index;
	}

	private Page getPage() {
		Page page;
		if (documentPane.getSelectedPage() != null) {
			page = new Page(documentPane.getSelectedPage().getPageLayout());
		} else if (!documentPane.getPageList().isEmpty()) {
			page = new Page(documentPane.getPageList().get(documentPane.getPageList().size() - 1).getPageLayout());
		} else {
			page = new Page(Page.A4);
		}

		return page;
	}

	@Override
	void execute() {
		documentPane.getPageList().add(index, page);
	}

	@Override
	void undo() {
		documentPane.getPageList().remove(page);
	}
}
