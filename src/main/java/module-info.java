module com.andreasbur {
    requires javafx.controls;
    requires javafx.fxml;
    requires slf4j.api;

    opens com.andreasbur to javafx.fxml;
    exports com.andreasbur;
    exports com.andreasbur.actions;
    exports com.andreasbur.document;
    exports com.andreasbur.gui;
    exports com.andreasbur.page;
    exports com.andreasbur.statusbar;
    exports com.andreasbur.tools;
    exports com.andreasbur.util;
}