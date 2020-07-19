package com.andreasbur.statusbar;

import com.andreasbur.util.ZoomHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScaleSlider extends HBox {

	public ScaleSlider(ZoomHandler zoomHandler) {
		Slider scaleSlider = new Slider(ZoomHandler.MIN_SCALE_VALUE, ZoomHandler.MAX_SCALE_VALUE, ZoomHandler.DEFAULT_SCALE_VALUE);
		scaleSlider.setBlockIncrement(10);
		scaleSlider.setMajorTickUnit(10);
		scaleSlider.setMinorTickCount(9);
		scaleSlider.setShowTickMarks(false);
		scaleSlider.setSnapToTicks(true);
		scaleSlider.valueProperty().bindBidirectional(zoomHandler.scaleProperty());

		Text minusButton = new Text("-");
		minusButton.setFont(new Font(22));
		minusButton.setOnMouseClicked(event -> zoomHandler.zoomOut());
		Text plusButton = new Text("+");
		plusButton.setFont(new Font(16));
		plusButton.setOnMouseClicked(event -> zoomHandler.zoomIn());

		setAlignment(Pos.CENTER);
		getChildren().addAll(minusButton, scaleSlider, plusButton);

	}
}
