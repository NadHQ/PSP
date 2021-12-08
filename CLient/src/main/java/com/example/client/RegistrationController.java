package com.example.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController extends HelloController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginEdit;

    @FXML
    private PasswordField passEdit;

    @FXML
    private Button regButton;

    @FXML
    void initialize() {
        regButton.setOnAction(actionEvent -> {
            if (loginEdit.getText().length() < 8) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login error");
                alert.setHeaderText("ERROR");
                alert.setContentText("Некоректно введен логин\n" + "Слишком мало символов(<8)");
                alert.show();
                loginEdit.setText("");
                return;
            }
            if (passEdit.getText().length() < 8) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login error");
                alert.setHeaderText("ERROR");
                alert.setContentText("Некоректно введен пароль\n" + "Слишком мало символов(<8)");
                alert.show();
                passEdit.setText("");
                return;
            }
            String login = loginEdit.getText();
            String pass = passEdit.getText();
            System.out.println(login);
            System.out.println(pass);
            serv.sendInt(1);
            try {
                serv.WriteCharToStream(login);
                serv.WriteCharToStream(pass);
                if (serv.getInt() == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Reg Error");
                    alert.setHeaderText("ERROR");
                    alert.setContentText("Такой пользователь уже существует");
                    alert.show();
                    passEdit.setText("");
                    loginEdit.setText("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            regButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Auth.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

    }

}