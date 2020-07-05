package com.andreasbur.gui;

import com.andreasbur.actions.ActionHandler;
import com.andreasbur.tools.ToolEventDistributor;
import com.andreasbur.tools.ToolFactory;
import com.andreasbur.util.ScrollHandler;
import com.andreasbur.util.ZoomHandler;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ParentPane extends BorderPane {

	private final StatusBar statusBar;
	private final MyMenuBar menuBar;
	private final ToolbarPane toolbarPane;
	private final DocumentPane documentPane;
	private final DocumentScalePane documentScalePane;
	private final DocumentSideBar documentSideBar;

	private final ZoomHandler zoomHandler;
	private final ScrollHandler scrollHandler;
	private final ActionHandler actionHandler;
	private final ToolEventDistributor toolEventDistributor;
	private final ToolFactory toolFactory;

	public ParentPane() {

		zoomHandler = new ZoomHandler();
		scrollHandler = new ScrollHandler();
		actionHandler = new ActionHandler();
		toolEventDistributor = new ToolEventDistributor();
		toolFactory = new ToolFactory(this);

		documentPane = new DocumentPane(this);
		documentPane.addEventHandler(MouseEvent.ANY, toolEventDistributor);

		menuBar = new MyMenuBar(actionHandler);
		toolbarPane = new ToolbarPane(documentPane, actionHandler, toolFactory);
		documentScalePane = new DocumentScalePane(documentPane, zoomHandler, scrollHandler);
		documentSideBar = new DocumentSideBar(documentPane);
		statusBar = new StatusBar(this);

		SplitPane middleSplitPane = new SplitPane();
		middleSplitPane.setDividerPosition(0, 0.1);
		SplitPane.setResizableWithParent(documentSideBar, false);
		middleSplitPane.getItems().addAll(documentSideBar, documentScalePane);

		VBox topPane = new VBox(menuBar, toolbarPane);

		setTop(topPane);
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

	public ScrollHandler getScrollHandler() {
		return scrollHandler;
	}

	public ActionHandler getActionHandler() {
		return actionHandler;
	}

	public ToolEventDistributor getToolEventDistributor() {
		return toolEventDistributor;
	}
}
