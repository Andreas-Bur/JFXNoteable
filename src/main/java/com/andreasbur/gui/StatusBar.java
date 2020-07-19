package com.andreasbur.gui;

import com.andreasbur.statusbar.MemoryProgressPane;
import com.andreasbur.statusbar.ScaleSlider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class StatusBar extends HBox {

    private final Text statusMessageText = new Text();
    private final MemoryProgressPane memoryProgressPane = new MemoryProgressPane();

    public StatusBar(ParentPane parentPane) {

        Region placeholder = new Region();
        setHgrow(placeholder, Priority.ALWAYS);
        ScaleSlider scaleSlider = new ScaleSlider(parentPane.getZoomHandler());

        getChildren().addAll(memoryProgressPane, statusMessageText, placeholder, scaleSlider);

        //setStatusMessage("Status message...");

    }

    public void setStatusMessage(String text) {
        statusMessageText.setText(text);
    }
}
