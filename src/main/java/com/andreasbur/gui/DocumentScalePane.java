package com.andreasbur.gui;

import com.andreasbur.util.ZoomHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicBoolean;

public class DocumentScalePane extends ScrollPane {

	ZoomHandler zoomHandler;

	public DocumentScalePane(ZoomHandler zoomHandler, VBox content) {
		super();

		this.zoomHandler = zoomHandler;

		setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		setFitToHeight(true);
		setFitToWidth(true);
		setStyle("-fx-background: #D3D3D3;"); // Color.LIGHTGRAY

		Group zoomGroup = new Group(content);
		content.setManaged(true);

		StackPane centeredPane = new StackPane(zoomGroup);
		centeredPane.setAlignment(Pos.TOP_CENTER);
		setContent(centeredPane);

		addEventFilter(ScrollEvent.ANY, e -> {
			if (e.isControlDown()) {
				e.consume();
				if (e.getDeltaY() > 0) {
					zoomHandler.zoomIn();
				} else if (e.getDeltaY() < 0) {
					zoomHandler.zoomOut();
				}
			}
		});

		AtomicBoolean keepOldVvalue = new AtomicBoolean(false);

		zoomHandler.scaleProperty().addListener((observable, oldValue, newValue) -> {
			content.setScaleX(zoomHandler.getZoomFactor());
			content.setScaleY(zoomHandler.getZoomFactor());
			keepOldVvalue.set(true);
		});

		vvalueProperty().addListener((observable, oldValue, newValue) -> {
			if(keepOldVvalue.get()){
				keepOldVvalue.set(false);
				setVvalue(oldValue.doubleValue());
			}
		});
	}

	public void scrollToNode(Node node) {
		if (node != null) {
			Bounds nodeBounds = node.localToScene(node.getBoundsInLocal());
			if (!isNodeVisible(nodeBounds)) {
				double scrollPaneHeight = getContent().getBoundsInLocal().getHeight() - getViewportBounds().getHeight();
				setVvalue(node.getBoundsInParent().getMinY() / scrollPaneHeight * zoomHandler.getZoomFactor());
			}
		}
	}

	private boolean isNodeVisible(Bounds bounds) {
		return localToScene(getBoundsInParent()).intersects(bounds);
	}
}