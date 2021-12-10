package ModalControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.client.HelloController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class StatApp extends HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea StatArea;

    @FXML
    void initialize() {
        StringBuilder str = new StringBuilder();
        try {
            str.append( serv.ReadCharToString()+ "\n");
            str.append(serv.ReadCharToString()+ "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        StatArea.setText(str.toString());
    }

}
