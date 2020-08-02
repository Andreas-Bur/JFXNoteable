package com.andreasbur.gui;

import com.andreasbur.actions.ActionHandler;
import com.andreasbur.document.DocumentController;
import com.andreasbur.document.DocumentModel;
import com.andreasbur.document.DocumentPane;
import com.andreasbur.document.DocumentScalePane;
import com.andreasbur.tools.ToolEventDistributor;
import com.andreasbur.tools.ToolEventHandlerFactory;
import com.andreasbur.tools.ToolFactory;
import com.andreasbur.util.ScrollHandler;
import com.andreasbur.util.ZoomHandler;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ParentPane extends BorderPane {

	private final StatusBar statusBar;
	private final MyMenuBar menuBar;
	private final ToolbarPane toolbarPane;
	private final DocumentSideBar documentSideBar;
	private final DocumentScalePane documentScalePane;

	private final ZoomHandler zoomHandler;
	private final ScrollHandler scrollHandler;
	private final ActionHandler actionHandler;
	private final ToolEventDistributor toolEventDistributor;
	private final ToolEventHandlerFactory toolEventHandlerFactory;
	private final ToolFactory toolFactory;

	private final DocumentPane documentPane;
	private final DocumentModel documentModel;
	private final DocumentController documentController;

	public ParentPane() {

		zoomHandler = new ZoomHandler();
		scrollHandler = new ScrollHandler();
		actionHandler = new ActionHandler();
		toolEventHandlerFactory = new ToolEventHandlerFactory(this);
		toolFactory = new ToolFactory(toolEventHandlerFactory);
		toolEventDistributor = new ToolEventDistributor(toolFactory.getToggleGroup());

		documentModel = new DocumentModel();

		documentPane = new DocumentPane(documentModel);
		documentPane.setToolEventDistributor(toolEventDistributor);

		documentController = new DocumentController(documentModel, documentPane);

		menuBar = new MyMenuBar(actionHandler);
		toolbarPane = new ToolbarPane(documentController, actionHandler, toolFactory);
		documentScalePane = new DocumentScalePane(documentPane, zoomHandler, scrollHandler);
		documentSideBar = new DocumentSideBar(documentPane.getPagePaneList());
		statusBar = new StatusBar(this);

		SplitPane middleSplitPane = new SplitPane();
		middleSplitPane.setDividerPosition(0, 0.1);
		SplitPane.setResizableWithParent(documentSideBar, false);
		middleSplitPane.getItems().addAll(documentSideBar, documentScalePane);

		VBox topPane = new VBox(menuBar, toolbarPane);

		setTop(topPane);
		setBottom(statusBar);
		setCenter(middleSplitPane);

		documentController.addPageSelector(documentPane);
		documentController.addPageSelector(documentSideBar);

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

	public DocumentController getDocumentController() {
		return documentController;
	}
}
