package com.andreasbur.page;

import com.andreasbur.util.ScreenUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PagePane extends StackPane {

	private static final DropShadow pageShadow = new DropShadow(10, 3, 3, Color.BLACK);

	private final SimpleObjectProperty<Image> previewImage;

	private final StackPane snapshotPane;
	private final PagePreview pagePreview;
	private final PageModel pageModel;

	public enum Orientation {
		PORTRAIT, LANDSCAPE
	}

	public PagePane(PageModel pageModel) {

		this.pageModel = pageModel;
		this.previewImage = new SimpleObjectProperty<>();
		this.snapshotPane = new StackPane();
		this.pagePreview = new PagePreview(this);

		snapshotPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		setEffect(pageShadow);

		double widthPx = ScreenUtil.convertMmToPx(pageModel.getPageLayout().getPageSize().getWidth());
		double heightPx = ScreenUtil.convertMmToPx(pageModel.getPageLayout().getPageSize().getHeight());

		Canvas canvas = new Canvas(widthPx, heightPx);
		snapshotPane.getChildren().add(canvas);
		getChildren().add(snapshotPane);

		pageModel.pageLayoutProperty().addListener((observable, oldValue, newValue) -> {
			canvas.setWidth(ScreenUtil.convertMmToPx(getPageModel().getPageLayout().getPageSize().getWidth()));
			canvas.setHeight(ScreenUtil.convertMmToPx(getPageModel().getPageLayout().getPageSize().getHeight()));
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

	public PageModel getPageModel() {
		return pageModel;
	}




}
