package com.andreasbur.gui.page;

import com.andreasbur.util.ScreenUtil;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Page extends StackPane {

	public static final Dimension2D A3 = new Dimension2D(297, 420);
	public static final Dimension2D A4 = new Dimension2D(210, 297);
	public static final Dimension2D A5 = new Dimension2D(148, 210);

	private static final DropShadow pageShadow = new DropShadow(10, 3, 3, Color.BLACK);

	private final SimpleObjectProperty<Image> previewImage;
	private final PageLayout pageLayout;
	private final IntegerProperty pageIndex;

	private final StackPane snapshotPane;
	private final PagePreview pagePreview;

	public enum Orientation {
		PORTRAIT, LANDSCAPE
	}

	public Page(Dimension2D dimension) {
		this(dimension, dimension.getWidth() > dimension.getHeight() ? Orientation.LANDSCAPE : Orientation.PORTRAIT);
	}

	public Page(Dimension2D dimension, Orientation orientation) {

		pageLayout = new PageLayout(dimension, orientation);
		previewImage = new SimpleObjectProperty<>();
		pageIndex = new SimpleIntegerProperty();
		snapshotPane = new StackPane();
		pagePreview = new PagePreview(this);

		snapshotPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		setEffect(pageShadow);

		double widthPx = ScreenUtil.convertMmToPx(pageLayout.getPageSize().getWidth());
		double heightPx = ScreenUtil.convertMmToPx(pageLayout.getPageSize().getHeight());

		Canvas canvas = new Canvas(widthPx, heightPx);
		snapshotPane.getChildren().add(canvas);
		getChildren().add(snapshotPane);

		pageLayout.pageSizeProperty().addListener((observable, oldValue, newValue) -> {
			canvas.setWidth(ScreenUtil.convertMmToPx(newValue.getWidth()));
			canvas.setHeight(ScreenUtil.convertMmToPx(newValue.getHeight()));
			updatePreviewImage();
		});
	}

	public void updatePreviewImage() {
		Platform.runLater(() -> previewImage.set(snapshotPane.snapshot(null, null)));
	}

	public PagePreview getPagePreview() {
		return pagePreview;
	}

	public ObservableValue<Image> previewImageProperty() {
		return previewImage;
	}

	public PageLayout getPageLayout() {
		return pageLayout;
	}

	public int getPageIndex() {
		return pageIndex.get();
	}

	public IntegerProperty pageIndexProperty() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex.set(pageIndex);
	}


}
