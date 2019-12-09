package sample.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Server.DataObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DelCon2Controller extends Connection {

    @FXML
    private TableColumn<DataObject, String> Data;

    @FXML
    private TableColumn<DataObject, String> Sum;

    @FXML
    private TableView<DataObject> table;

    @FXML
    private TableColumn<DataObject, String> Sourse;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private Button del;

    @FXML
    void initialize() {
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        del.setOnAction(event -> {
            try {
                if (socket == null || socket.isClosed()) {
                    connect();
                }
                newScene("accept4");
                closeConnect();
            }catch (NullPointerException e){
                del.getScene().getWindow().hide();
                newScene("error3");
            }
        });
    }
}
