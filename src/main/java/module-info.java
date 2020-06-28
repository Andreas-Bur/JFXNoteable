module com.andreasbur {
    requires javafx.controls;
    requires javafx.fxml;
    requires slf4j.api;

    opens com.andreasbur to javafx.fxml;
    exports com.andreasbur;
    exports com.andreasbur.actions;
    exports com.andreasbur.gui;
    exports com.andreasbur.gui.components;
    exports com.andreasbur.gui.page;
    exports com.andreasbur.util;
}