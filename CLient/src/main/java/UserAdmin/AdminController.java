package UserAdmin;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.client.HelloController;
import com.example.client.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Button UnBlockButton;

    @FXML
    private Button ViewButton;

    @FXML
    private Button CheckStat;

    @FXML
    private Button CheckUser;

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
                    observableList.add(new User(tempID, tempLogin, tempPass, tempStatus, tempRole));

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

        BlockButton.setOnAction(actionEvent -> {
            serv.sendInt(5);
            User BlockData = TableVar.getSelectionModel().getSelectedItem();
            serv.sendInt(BlockData.getId());
        });

        UnBlockButton.setOnAction(actionEvent -> {
            serv.sendInt(6);
            User UnBlockData = TableVar.getSelectionModel().getSelectedItem();
            serv.sendInt(UnBlockData.getId());
        });
        EditButton.setOnAction(actionEvent -> {
            User EditData;
            if (EditButton.getText().equals("Изменить")) {
                serv.sendInt(2);
                EditData = TableVar.getSelectionModel().getSelectedItem();
                try {
                    boolean b = EditData.getId() == (int) EditData.getId();
                    idEdit.setText(String.valueOf(EditData.getId()));
                    LoginEdit.setText(EditData.getLogin());
                    PassEdit.setText(EditData.getPass());
                    RoleEdit.setText(EditData.getRole());
                    StatusEdit.setText(EditData.getStatus());
                    EditButton.setText("Сохранить");
                } catch (NullPointerException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Ошибка");
                    alert.setContentText("Вы не выбрали пользователя");
                    alert.show();

                }
            } else {
                EditButton.setText("Изменить");
                String IdVar = idEdit.getText();
                System.out.println(idEdit.getText());
                String LoginVar = LoginEdit.getText();
                String PassVar = PassEdit.getText();
                String StatusVar = StatusEdit.getText();
                String RoleVar = RoleEdit.getText();
                try {
                    serv.WriteCharToStream(IdVar);
                    serv.WriteCharToStream(LoginVar);
                    serv.WriteCharToStream(PassVar);
                    serv.WriteCharToStream(StatusVar);
                    serv.WriteCharToStream(RoleVar);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        AddButton.setOnAction(actionEvent -> {
            if (LoginEdit.getText().length() != 0) {
                if (PassEdit.getText().length() != 0) {
                    if (StatusEdit.getText().length() != 0) {
                        if (RoleEdit.getText().length() != 0) {
                            try {

                                serv.sendInt(4);
                                serv.WriteCharToStream(LoginEdit.getText());
                                serv.WriteCharToStream(PassEdit.getText());
                                serv.WriteCharToStream(StatusEdit.getText());
                                serv.WriteCharToStream(RoleEdit.getText());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Error");

                alert.showAndWait();
            }
        });
        CheckStat.setOnAction(actionEvent -> {
            serv.sendInt(7);
            ArrayList<String> arrayList = new ArrayList<>();
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/StatApp.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            root = fxmlLoader.getRoot();
            stage.setScene(new Scene(root));
            stage.setTitle("My modal window");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node)actionEvent.getSource()).getScene().getWindow() );
            stage.show();
        });

    }

}
