package com.andreasbur.statusbar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MemoryProgressPane extends BorderPane {

    private final ProgressBar progressBar = new ProgressBar();
    private final Text text = new Text();

    public MemoryProgressPane() {

        BorderPane.setAlignment(text, Pos.CENTER);
        BorderPane.setAlignment(progressBar, Pos.CENTER);
        setLeft(text);
        setRight(progressBar);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> updateStats()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        updateStats();

    }

    private void updateStats() {
        double totalMemory = Runtime.getRuntime().totalMemory();
        double usedMemory = totalMemory - Runtime.getRuntime().freeMemory();

        progressBar.setProgress(usedMemory / totalMemory);
        text.setText(String.format("%.1f", usedMemory / 1024 / 1024) + " MB / " + totalMemory / 1024 / 1024 +" MB");
    }

}
