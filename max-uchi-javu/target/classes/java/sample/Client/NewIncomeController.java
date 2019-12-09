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


public class NewIncomeController extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Data;

    @FXML
    private ComboBox<?> Sourse;

    @FXML
    private TextField Sum;

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
            d.setCommand("newIncome");
            d.setData(Data.getText());
            if (!Sum.getText().equals("")) d.setSum(Integer.parseInt(Sum.getText()));
            else d.setSum(0);
            d.setSourse(String.valueOf(Sourse.getSelectionModel().getSelectedItem()));
            System.out.println(current_id + "current");
            d.setSecond_id(current_id);
            if (socket == null || socket.isClosed()) {
                connect();
            }
            try {
                if (!d.getData().equals("") && d.getSum()>0 && !d.getSourse().equals("")) out.writeObject(d);
                else {
                    save.getScene().getWindow().hide();
                    newScene("error3");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                d = (DataObject) in.readObject();
                if (d.isResult()) {
                    closeConnect();
                    save.getScene().getWindow().hide();
                    newScene("accept3");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
