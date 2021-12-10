module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.client to javafx.fxml;
    exports com.example.client;
    opens AuthReg to javafx.fxml;
    exports AuthReg;
    exports UserAdmin;
    opens UserAdmin to javafx.fxml;
    opens ModalControllers to javafx.fxml;
}