package com.example.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController extends HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddButton;

    @FXML
    private Button BlockButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button EditButton;

    @FXML
    private TableColumn<User, String> LoginCol;

    @FXML
    private TextField LoginEdit;

    @FXML
    private TableColumn<User, String> PassCol;

    @FXML
    private TextField PassEdit;

    @FXML
    private TableColumn<User, String> RoleCol;

    @FXML
    private TextField RoleEdit;

    @FXML
    private TableColumn<User, String> StatusCol;
    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TextField StatusEdit;

    @FXML
    private TableView<User> TableVar;

    @FXML
    private Button ViewButton;

    @FXML
    private TextField idEdit;
    ObservableList<User> observableList = FXCollections.observableArrayList();
    @FXML
    void initialize() {
        ViewButton.setOnAction(actionEvent -> {
            observableList.clear();
            serv.sendInt(1);
            System.out.println(123);
            Integer count = serv.getInt();
            for (int i = 0; i < count; i++) {
                try {
                    Integer tempID = Integer.parseInt(serv.ReadCharToString());
                    String tempLogin = serv.ReadCharToString();
                    String tempPass = serv.ReadCharToString();
                    String tempStatus = serv.ReadCharToString();
                    String tempRole = serv.ReadCharToString();
                    observableList.add(new User(tempID,tempLogin,tempPass,tempStatus,tempRole));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            id.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
            LoginCol.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
            PassCol.setCellValueFactory(new PropertyValueFactory<User, String>("Pass"));
            StatusCol.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
            RoleCol.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
            TableVar.setItems(observableList);

        });
        DeleteButton.setOnAction(actionEvent -> {
            serv.sendInt(3);
            User deleteData = TableVar.getSelectionModel().getSelectedItem();
            serv.sendInt(deleteData.getId());
        });
    }

}
