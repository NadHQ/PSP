package UserAdmin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ModalControllers.StatByUserController;
import com.example.client.HelloController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserController extends HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField AgeEdit;

    @FXML
    private TextField BlckEdit;

    @FXML
    private TextField ChasEdit;

    @FXML
    private TextField CrimEdit;

    @FXML
    private TextField DisEdit;

    @FXML
    private TextField IndusEdit;

    @FXML
    private TextField LstatEdit;

    @FXML
    private TextField NoxEdit;

    @FXML
    private TextField PtratioEdit;

    @FXML
    private TextField RadEdit;

    @FXML
    private TextArea ResultArea;

    @FXML
    private TextField RmEdit;

    @FXML
    private Button StartButton;

    @FXML
    private TextField TaxEdit;

    @FXML
    private Button StatButton;

    @FXML
    private TextField ZNEdit;

    @FXML
    private Button ValuesButton;
    @FXML
    void initialize() {
            StartButton.setOnAction(actionEvent -> {
                serv.sendInt(1);
                String crim =  isDouble(CrimEdit.getText());
                String zn = isDouble(ZNEdit.getText());
                String indus = isDouble(IndusEdit.getText());
                String chas = isDouble(ChasEdit.getText());
                String nox = isDouble(NoxEdit.getText());
                String rm = isDouble(RmEdit.getText());
                String age = isDouble(AgeEdit.getText());
                String dis = isDouble(DisEdit.getText());
                String rad = isDouble(RadEdit.getText());
                String Tax = isDouble(TaxEdit.getText());
                String ptratio = isDouble(PtratioEdit.getText());
                String blck = isDouble(BlckEdit.getText());
                String lstat = isDouble(LstatEdit.getText());
                try {
                    serv.WriteCharToStream(crim);
                    serv.WriteCharToStream(zn);
                    serv.WriteCharToStream(indus);
                    serv.WriteCharToStream(chas);
                    serv.WriteCharToStream(nox);
                    serv.WriteCharToStream(rm);
                    serv.WriteCharToStream(age);
                    serv.WriteCharToStream(dis);
                    serv.WriteCharToStream(rad);
                    serv.WriteCharToStream(Tax);
                    serv.WriteCharToStream(ptratio);
                    serv.WriteCharToStream(blck);
                    serv.WriteCharToStream(lstat);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    String resultVar = serv.ReadCharToString();
                    ResultArea.setText("Ваш результат: " + resultVar + "." + "\n Результат указат в тысячах долларов");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            StatButton.setOnAction(actionEvent -> {
                System.out.println("DONE");
                serv.sendInt(2);
                ArrayList<String> arrayList = new ArrayList<>();
                Stage stage = new Stage();
                Parent root = null;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/client/StatByUser.fxml"));
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
            ValuesButton.setOnAction(actionEvent -> {
                Stage stage = new Stage();
                Parent root = null;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/client/Values.fxml"));
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

    public String isDouble(String str){
        try{
            Double doubl = Double.parseDouble(str);
            return str;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Reg Error");
            alert.setHeaderText("ERROR");
            alert.setContentText("Число должно быть корректным \nНеправильное число считается равное нулю");
            alert.show();
            return "0";
        }
    }


}
