module com.example.demo5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.demo5 to javafx.fxml;
    exports com.example.demo5;
}