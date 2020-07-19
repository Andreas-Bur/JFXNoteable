package com.andreasbur.page;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class  PagePreview extends VBox {

	private static final DropShadow pagePreviewShadow = new DropShadow(5, 2, 2, Color.BLACK);
	private final ImageView imageView;
	private final StackPane imageViewPane;
	private final PagePane pagePane;

	public PagePreview(PagePane pagePane) {

		this.pagePane = pagePane;

		imageView = new ImageView();
		imageView.setPreserveRatio(true);
		imageView.setEffect(pagePreviewShadow);
		imageView.imageProperty().bind(pagePane.previewImageProperty());

		imageViewPane = new StackPane(imageView);

		Text pageIndexText = new Text();
		pageIndexText.textProperty().bind(pagePane.getPageModel().pageNumberProperty().asString());

		setAlignment(Pos.TOP_CENTER);
		setSpacing(10);
		maxWidthProperty().bind(imageView.fitWidthProperty());

		getChildren().addAll(imageViewPane, pageIndexText);

	}

	public PagePane getPagePane() {
		return pagePane;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public StackPane getImageViewPane() {
		return imageViewPane;
	}
}
