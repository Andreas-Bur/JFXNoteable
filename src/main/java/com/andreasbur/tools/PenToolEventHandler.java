package com.andreasbur.tools;

import com.andreasbur.actions.ActionHandler;
import com.andreasbur.actions.AddShapeAction;
import com.andreasbur.page.PagePane;
import com.andreasbur.shapes.FreehandLine;
import javafx.scene.input.MouseEvent;

public class PenToolEventHandler implements ToolEventHandler {

	private final ActionHandler actionHandler;

	private PagePane pressedPagePane;
	private FreehandLine freehandLine;

	public PenToolEventHandler(ActionHandler actionHandler) {
		this.actionHandler = actionHandler;
	}

	@Override
	public void handlePageEvent(MouseEvent event) {
		if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			initPolyline((PagePane) event.getSource());
			addPointToPolyline(event.getX(), event.getY());
		} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			finishPolyline();
		} else if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED) && event.getSource() == pressedPagePane) {
			addPointToPolyline(event.getX(), event.getY());
		}
	}

	@Override
	public void handleDocumentEvent(MouseEvent event) {
		if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			finishPolyline();
		}
	}

	private void initPolyline(PagePane pagePane) {
		pressedPagePane = pagePane;
		freehandLine = new FreehandLine();
		pressedPagePane.setTemporaryDrawnShape(freehandLine);
	}

	private void addPointToPolyline(double x, double y) {
		if (freehandLine != null) {
			freehandLine.getPoints().addAll(x, y);
		}
	}

	private void finishPolyline() {
		if (freehandLine != null) {
			pressedPagePane.setTemporaryDrawnShape(null);

			AddShapeAction addShapeAction = new AddShapeAction(pressedPagePane.getPageModel(), freehandLine);
			actionHandler.execute(addShapeAction);

			freehandLine = null;
			pressedPagePane = null;
		}
	}

	@Override
	public void deselect() {

	}

	@Override
	public void select() {

	}
}
