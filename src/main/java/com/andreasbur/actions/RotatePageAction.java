package com.andreasbur.actions;

import com.andreasbur.gui.DocumentPane;
import com.andreasbur.gui.page.Page;

public class RotatePageAction extends Action {

	Page page;

	public RotatePageAction(DocumentPane documentPane) {
		this(documentPane.getSelectedPage());
	}

	public RotatePageAction(Page page) {
		this.page = page;

		if(page == null){
			throw new IllegalArgumentException("Page argument must not be null.");
		}
	}

	@Override
	void execute() {
		page.getPageLayout().swapOrientation();
	}

	@Override
	void undo() {
		page.getPageLayout().swapOrientation();
	}
}
