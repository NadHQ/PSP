package ModalControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.client.HelloController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class StatByAdminController extends HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea StatByAdminEdit;

    @FXML
    void initialize() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("ID пользователя: " + serv.ReadCharToString() + "\n");
            stringBuilder.append("Логин пользователя: " + serv.ReadCharToString() + "\n");
            stringBuilder.append("Пароль пользователя: " + serv.ReadCharToString() + "\n");
            if (serv.ReadCharToString().equals("f")){
                stringBuilder.append("Статус: Заблокирован\n");
            }
            else{
                stringBuilder.append("Статус: Разблокирован\n");
            }
            if(serv.ReadCharToString().equals("1")){
                stringBuilder.append("Роль: Администратор\n");
            }
            else{
                stringBuilder.append("Роль: Пользователь\n");
            }
            stringBuilder.append("Количество запросов на прогнозирование: " + serv.getInt());
            StatByAdminEdit.setText(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
