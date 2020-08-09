package com.andreasbur.tools;

import com.andreasbur.actions.ActionHandler;
import com.andreasbur.actions.EraseAction;
import com.andreasbur.page.PagePane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class EraserToolEventHandler implements ToolEventHandler {

	private final ActionHandler actionHandler;
	private PagePane pressedPagePane = null;
	private Polyline eraseShape = null;

	public EraserToolEventHandler(ActionHandler actionHandler) {
		this.actionHandler = actionHandler;

	}

	@Override
	public void handlePageEvent(MouseEvent event) {
		if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			initEraseShape((PagePane) event.getSource());
			addPointToEraseShape(event.getX(), event.getY());
		} else if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
			addPointToEraseShape(event.getX(), event.getY());
		} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			doErase();
		}
	}

	@Override
	public void handleDocumentEvent(MouseEvent event) {
		if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			doErase();
		}
	}

	@Override
	public void deselect() {

	}

	@Override
	public void select() {

	}

	private void initEraseShape(PagePane pagePane) {
		pressedPagePane = pagePane;
		eraseShape = new Polyline();
		eraseShape.setStroke(Color.RED);
		eraseShape.setStrokeWidth(20);
		pressedPagePane.setTemporaryDrawnShape(eraseShape);
	}

	private void addPointToEraseShape(double x, double y) {
		eraseShape.getPoints().addAll(x, y);
	}

	private void doErase() {
		if (eraseShape != null) {
			EraseAction eraseAction = new EraseAction(pressedPagePane.getPageModel(), eraseShape);
			actionHandler.execute(eraseAction);

			pressedPagePane.setTemporaryDrawnShape(null);
			eraseShape = null;
			pressedPagePane = null;
		}
	}
}
