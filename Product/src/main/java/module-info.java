module protasker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;

    exports protasker;
    opens protasker to javafx.fxml;
    exports protasker.Controller;
    opens protasker.Controller to javafx.fxml;
    exports protasker.Model;
    opens protasker.Model to javafx.fxml;
}