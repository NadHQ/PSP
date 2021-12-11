package ModalContollers

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Values {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea ValuesText;

    @FXML
    void initialize() {
       StringBuilder stringBuilder = new StringBuilder();
       stringBuilder.append(

              " CRIM: Уровень преступности на душу населения.\n" +
                      "ZN: Доля жилой земли, выделенной для участков более 25 000 квадратных футов.\n" +
                      "INDUS: Доля не торговых бизнес акров на город.\n" +
                      "CHAS: Переменная реки Чарлз (равна 1 если рядом река, 0 если нет).\n" +
                      "NOX: Концентрация оксидов азота (частей на 10 миллионов)\n" +
                      "RM: Среднее количество комнат на жилье.\n" +
                      "AGE: Пропорция домов частных владельцев, построенных до 1940 года.\n" +
                      "DIS: Взвешенные расстояния до пяти рабочих центров Бостона.\n" +
                      "RAD: Индекс доступности близлежащих автомагистралей.\n" +
                      "TAX: Полная стоимость налога на имущество на 10 000 долларов США.\n" +
                      "PTRATIO: Среднее количество учителей для детей на город.\n" +
                      "BLCK: 1000 * (Bk - 0.63) ** 2, где Bk - это пропорция афроамериканцев на город.\n" +
                      "LSTAT: Процент населения с низким социальным статусом.\n")
       ValuesText.setText(stringBuilder.toString());

    }

}
