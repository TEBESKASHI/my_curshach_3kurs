package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class WorkWithConController extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private Button editCon;

    @FXML
    private Button exit;

    @FXML
    private Button del;

    @FXML
    private Button newCon;


    @FXML
    void initialize() {
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        newCon.setOnAction(event -> {
            newCon.getScene().getWindow().hide();
            newScene("newCon");
        });
        del.setOnAction(event -> {
            del.getScene().getWindow().hide();
            newScene("delCon");
        });
        editCon.setOnAction(event -> {
            editCon.getScene().getWindow().hide();
            newScene("editCon");
        });
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            newScene("sample");
        });
    }

}
