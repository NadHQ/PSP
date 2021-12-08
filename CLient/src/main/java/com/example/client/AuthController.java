package com.example.client;

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

public class AuthController extends HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginEdit;

    @FXML
    private PasswordField passEdit;

    @FXML
    private Button authButton;

    @FXML
    void initialize() {
        authButton.setOnAction(actionEvent -> {
            Integer userRole;
            String UserStatus;
            String login = loginEdit.getText();
            String pass = passEdit.getText();
            if (login.length() < 8) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login error");
                alert.setHeaderText("ERROR");
                alert.setContentText("Некоректно введен логин\n" + "Слишком мало символов(<8)");
                alert.show();
                loginEdit.setText("");
                return;
            }
            if (pass.length() < 8) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login error");
                alert.setHeaderText("ERROR");
                alert.setContentText("Некоректно введен пароль\n" + "Слишком мало символов(<8)");
                alert.show();
                passEdit.setText("");
                return;
            }
            serv.sendInt(2);
            try {
                serv.WriteCharToStream(login);
                serv.WriteCharToStream(pass);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                UserStatus = serv.ReadCharToString();
                String UserRole = serv.ReadCharToString();
                System.out.println(UserStatus);
                if (UserStatus.equals("f")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Auth error");
                    alert.setHeaderText("ERROR");
                    alert.setContentText("Данный пользователь заблокирован");
                    alert.show();
                } else if (UserStatus.equals("error")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Auth error");
                    alert.setHeaderText("ERROR");
                    alert.setContentText("Такого пользователя не существует");
                    alert.show();
                } else {
                    if (UserRole.equals("0")) {
                        authButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("User.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                    if (UserRole.equals("1")) {
                        authButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("Admin.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                }
//                userRole = serv.getInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
