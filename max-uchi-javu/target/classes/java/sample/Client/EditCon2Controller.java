package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Server.DataObject;

public class EditCon2Controller extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField dataField;

    @FXML
    private TextField sumField;

    @FXML
    private Button save;

    @FXML
    private Button back;

    @FXML
    private TextField sourseField;

    @FXML
    void initialize() {
        sumField.setText(String.valueOf(selectionModel.getSelectedItem().getSum()));
        sourseField.setText(selectionModel.getSelectedItem().getSourse());
        dataField.setText(selectionModel.getSelectedItem().getData());
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        save.setOnAction(event -> {
            DataObject d = new DataObject();
            d.setCommand("editCon");
            d.setId(selectionModel.getSelectedItem().getId());
            d.setSum(Integer.parseInt(sumField.getText()));
            d.setSourse(sourseField.getText());
            d.setData(dataField.getText());
            d.setSecond_id(current_id);
            if (socket == null || socket.isClosed()) {
                connect();
            }
            try {
                out.writeObject(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
            closeConnect();
            save.getScene().getWindow().hide();
            newScene("acceptEditCon");
        });
    }
}
