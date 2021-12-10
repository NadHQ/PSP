package ModalControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.client.HelloController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class StatByUserController extends HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea TextStat;

    @FXML
    void initialize() {
        StringBuilder str = new StringBuilder();
        try {
            str.append("Логин: " + serv.ReadCharToString() + "\n");
            str.append("Пароль: " + serv.ReadCharToString() + "\n");
            if (serv.ReadCharToString().equals("f")){
                str.append("Статус: Заблокирован\n");
            }
            else{
                str.append("Статус: Разблокирован\n");
            }
            if(serv.ReadCharToString().equals("1")){
                str.append("Роль: Администратор\n");
            }
            else{
                str.append("Роль: Пользователь\n");
            }
            str.append("Количество запросов на прогнозирование: " + serv.ReadCharToString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextStat.setText(str.toString());

    }

}
