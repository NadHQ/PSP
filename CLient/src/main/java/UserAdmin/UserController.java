package UserAdmin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.client.HelloController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private TextField ZNEdit;

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
    }

    public String isDouble(String str){
        try{
            Double doubl = Double.parseDouble(str);
            return str;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Reg Error");
            alert.setHeaderText("ERROR");
            alert.setContentText("Число должно быть корректным");
            alert.show();
        }
        return str;
    }


}
