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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Server.DataObject;


public class createPlan extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField incomePlan;

    @FXML
    private TextField conPlan;

    @FXML
    private Button back;

    @FXML
    private Button save;


    @FXML
    void initialize() {
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        save.setOnAction(event -> {
            DataObject d = new DataObject();
            d.setCommand("createPlan");
            d.setPlan_inc(Integer.parseInt(incomePlan.getText()));
            d.setPlan_con(Integer.parseInt(conPlan.getText()));
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
            newScene("acceptNewCon");
        });
    }

}
