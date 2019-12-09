package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Server.DataObject;

public class EditUserController extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField roole;

    @FXML
    private TextField password;

    @FXML
    private Button save;

    @FXML
    private TextField name;

    @FXML
    private Button back;

    @FXML
    private TextField login;

    @FXML
    private TextField lastname;


    @FXML
    void initialize() {
        name.setText(selectionModel.getSelectedItem().getName());
        lastname.setText(selectionModel.getSelectedItem().getLastname());
        login.setText(selectionModel.getSelectedItem().getLogin());
        password.setText(selectionModel.getSelectedItem().getPassword());
        roole.setText(selectionModel.getSelectedItem().getRolle());
        save.setOnAction(event -> {
            DataObject d = new DataObject();
            d.setCommand("editUser");
            d.setId(selectionModel.getSelectedItem().getId());
            d.setName(name.getText());
            d.setLastname(name.getText());
            d.setLogin(login.getText());
            d.setPassword(password.getText());
            d.setRolle(roole.getText());
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
            newScene("acceptAllAdmin");
        });

        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            newScene("mainAdmin");
        });
    }
}
