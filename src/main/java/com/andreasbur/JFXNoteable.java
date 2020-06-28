package com.andreasbur;

import com.andreasbur.gui.ParentPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JFXNoteable extends Application {

    private static Scene scene = null;
    public static final Logger LOGGER = LoggerFactory.getLogger(JFXNoteable.class);

    @Override
    public void start(Stage stage) {

        ParentPane parentPane = new ParentPane();
        parentPane.requestLayout();

        scene = new Scene(parentPane, 1920, 1080);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}