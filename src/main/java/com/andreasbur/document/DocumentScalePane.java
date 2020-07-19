package com.andreasbur.document;

import com.andreasbur.document.DocumentPane;
import com.andreasbur.util.ScrollHandler;
import com.andreasbur.util.ZoomHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class DocumentScalePane extends ScrollPane {

	private final ZoomHandler zoomHandler;
	private final ScrollHandler scrollHandler;

	public DocumentScalePane(DocumentPane documentPane, ZoomHandler zoomHandler, ScrollHandler scrollHandler) {
		super();

		this.zoomHandler = zoomHandler;
		this.scrollHandler = scrollHandler;

		setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		setFitToHeight(true);
		setFitToWidth(true);
		setStyle("-fx-background: #D3D3D3;"); // Color.LIGHTGRAY

		setContent(documentPane);

		pannableProperty().bind(scrollHandler.pannableProperty());

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

		documentPane.getDocumentModel().selectedPageIndexProperty().addListener((observable, oldValue, newValue) ->
				scrollToNode(documentPane.getPagePaneList().get(newValue.intValue())));

		AtomicBoolean keepOldVvalue = new AtomicBoolean(false);

		zoomHandler.scaleProperty().addListener((observable, oldValue, newValue) -> {
			documentPane.getPageBox().setScaleX(zoomHandler.getZoomFactor());
			documentPane.getPageBox().setScaleY(zoomHandler.getZoomFactor());
			keepOldVvalue.set(true);
		});

		vvalueProperty().addListener((observable, oldValue, newValue) -> {
			if (keepOldVvalue.get()) {
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