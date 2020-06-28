package com.andreasbur.gui;

import com.andreasbur.actions.ActionHandler;
import com.andreasbur.util.ZoomHandler;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

public class ParentPane extends BorderPane {

	private final StatusBar statusBar;
	private final ToolbarPane toolbarPane;
	private final DocumentPane documentPane;
	private final DocumentScalePane documentScalePane;
	private final DocumentSideBar documentSideBar;

	private final ZoomHandler zoomHandler;
	private final ActionHandler actionHandler;

	public ParentPane() {

		zoomHandler = new ZoomHandler();
		actionHandler = new ActionHandler();

		documentPane = new DocumentPane(this);
		toolbarPane = new ToolbarPane(documentPane, actionHandler);
		documentScalePane = new DocumentScalePane(zoomHandler,documentPane);
		documentSideBar = new DocumentSideBar(documentPane);
		statusBar = new StatusBar(this);


		SplitPane middleSplitPane = new SplitPane();
		middleSplitPane.setDividerPosition(0, 0.1);
		SplitPane.setResizableWithParent(documentSideBar, false);
		middleSplitPane.getItems().addAll(documentSideBar, documentScalePane);

		setTop(toolbarPane);
		setBottom(statusBar);
		setCenter(middleSplitPane);

	}

	public StatusBar getStatusBar() {
		return statusBar;
	}

	public ToolbarPane getToolbarPane() {
		return toolbarPane;
	}

	public DocumentPane getDocumentPane() {
		return documentPane;
	}

	public DocumentScalePane getDocumentScalePane() {
		return documentScalePane;
	}

	public DocumentSideBar getDocumentSideBar() {
		return documentSideBar;
	}

	public ZoomHandler getZoomHandler() {
		return zoomHandler;
	}

	public ActionHandler getActionHandler() {
		return actionHandler;
	}

}
