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
	public void handleEvent(MouseEvent event) {
		if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			pressedPagePane = (PagePane) event.getSource();
			polyline = new Polyline();
			pressedPagePane.setCurrentDrawnShape(polyline);
			addPointToPolyline(event.getX(), event.getY());
			System.out.println(event.getX() + " : " + event.getY());
		} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			pressedPagePane.setCurrentDrawnShape(null);

			AddShapeAction addShapeAction = new AddShapeAction(pressedPagePane.getPageModel(), polyline);
			actionHandler.execute(addShapeAction);

			polyline = null;
			pressedPagePane = null;
		} else if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED) && event.getSource() == pressedPagePane) {
			addPointToPolyline(event.getX(), event.getY());
			System.out.println(event.getX() + " : " + event.getY());
		}
	}

	private void addPointToPolyline(double x, double y) {
		polyline.getPoints().addAll(x, y);
	}

	@Override
	public void deselect() {

	}

	@Override
	public void select() {

	}
}
