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


public class WorkWithIncomeController extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private Button delIncome;

    @FXML
    private Button editIncome;

    @FXML
    private Button exit;

    @FXML
    private Button newIncome;


    @FXML
    void initialize() {
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        newIncome.setOnAction(event -> {
            newIncome.getScene().getWindow().hide();
            newScene("newIncome");
        });
        editIncome.setOnAction(event -> {
            editIncome.getScene().getWindow().hide();
            newScene("editIncome");
        });
        delIncome.setOnAction(event -> {
            delIncome.getScene().getWindow().hide();
            newScene("delIncome");
        });

    }
}