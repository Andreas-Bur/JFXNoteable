package com.andreasbur.page;

import com.andreasbur.util.ScreenUtil;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

public class PagePane extends FlowPane {

	private static final DropShadow pageShadow = new DropShadow(10, 3, 3, Color.BLACK);

	private final SimpleObjectProperty<Image> previewImage;
	private final StackPane contentPane;
	private final PagePreview pagePreview;
	private final PageModel pageModel;
	private final Canvas canvas;
	private final FlowPane shapePane;

	private SimpleObjectProperty<Shape> currentDrawnShape = new SimpleObjectProperty<>();

	public enum Orientation {
		PORTRAIT, LANDSCAPE
	}

	public PagePane(PageModel pageModel) {

		this.pageModel = pageModel;
		this.previewImage = new SimpleObjectProperty<>();
		this.contentPane = new StackPane();
		this.pagePreview = new PagePreview(this);

		contentPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		setEffect(pageShadow);

		double widthPx = ScreenUtil.convertMmToPx(pageModel.getPageLayout().getPageSize().getWidth());
		double heightPx = ScreenUtil.convertMmToPx(pageModel.getPageLayout().getPageSize().getHeight());

		canvas = new Canvas(widthPx, heightPx);
		contentPane.getChildren().add(canvas);
		getChildren().add(contentPane);

		shapePane = new FlowPane();
		Bindings.bindContent(shapePane.getChildren(), pageModel.getShapes());
		contentPane.getChildren().add(shapePane);

		Polyline polyline1 = new Polyline(0, 0, 1, 1);
		polyline1.setManaged(false);
		pageModel.getShapes().add(polyline1);

		pageModel.pageLayoutProperty().addListener((observable, oldValue, newValue) -> {
			canvas.setWidth(ScreenUtil.convertMmToPx(getPageModel().getPageLayout().getPageSize().getWidth()));
			canvas.setHeight(ScreenUtil.convertMmToPx(getPageModel().getPageLayout().getPageSize().getHeight()));
			updatePreviewImage();
		});

		currentDrawnShape.addListener((observable, oldValue, newValue) -> {
			if (oldValue != null) {
				shapePane.getChildren().remove(oldValue);
			}
			if (newValue != null) {
				newValue.setManaged(false);
				shapePane.getChildren().add(newValue);
			}
		});
	}

	public void updatePreviewImage() {
		Platform.runLater(() -> previewImage.set(contentPane.snapshot(null, null)));
	}

	public PagePreview getPagePreview() {
		return pagePreview;
	}

	public ObservableValue<Image> previewImageProperty() {
		return previewImage;
	}

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setCurrentDrawnShape(Shape shape) {
		currentDrawnShape.setValue(shape);
	}

	public StackPane getContentPane() {
		return contentPane;
	}

}
