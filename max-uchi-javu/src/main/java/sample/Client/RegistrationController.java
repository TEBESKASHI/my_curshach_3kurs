package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Server.DataObject;
import sample.Server.DatabaseHandler;


public class RegistrationController extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LastNameField;

    @FXML
    private TextField LoginField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField PasswordField;

    @FXML
    private ComboBox<String> Rolle;

    @FXML
    private Button SignInButton;


    @FXML
    void initialize() {
        DatabaseHandler db = new DatabaseHandler();

        SignInButton.setOnAction(event -> {
            DataObject d = new DataObject();
            int n = LoginField.getText().length();
            int error = 0;
            if (n <= 0) {
                error = 1;
            }
            n = PasswordField.getText().length();
            if (n <= 0) {
                error = 1;
            }
            n = NameField.getText().length();
            if (n <= 0) {
                error = 1;
            }
            n = LastNameField.getText().length();
            if (n <= 0) {
                error = 1;
            }
            if (error == 1) {
                SignInButton.getScene().getWindow().hide();
                newScene("error2");
            } else {
                d.setCommand("Reg");
                d.setLogin(LoginField.getText());
                d.setPassword(PasswordField.getText());
                d.setName(NameField.getText());
                d.setLastname(LastNameField.getText());
                d.setRolle(Rolle.getSelectionModel().getSelectedItem());
                current = d.getLogin();
                current_id = d.getId();
                if (socket == null || socket.isClosed()) {
                    connect();
                }
                try {
                    out.writeObject(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    d = (DataObject) in.readObject();
                    if (d.isResult()) {
                        closeConnect();
                        SignInButton.getScene().getWindow().hide();
                        newScene("accept");
                    } else {
                        SignInButton.getScene().getWindow().hide();
                        newScene("error2");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    }