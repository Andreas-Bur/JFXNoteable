package com.andreasbur.tools;

import com.andreasbur.actions.ActionHandler;
import com.andreasbur.actions.AddShapeAction;
import com.andreasbur.page.PagePane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polyline;

public class PenToolEventHandler implements ToolEventHandler {

	private final ActionHandler actionHandler;

	private PagePane pressedPagePane;
	private Polyline polyline;

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
		polyline = new Polyline();
		pressedPagePane.setCurrentDrawnShape(polyline);
	}

	private void addPointToPolyline(double x, double y) {
		if (polyline != null) {
			polyline.getPoints().addAll(x, y);
		}
	}

	private void finishPolyline() {
		if (polyline != null) {
			pressedPagePane.setCurrentDrawnShape(null);

			AddShapeAction addShapeAction = new AddShapeAction(pressedPagePane.getPageModel(), polyline);
			actionHandler.execute(addShapeAction);

			polyline = null;
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
