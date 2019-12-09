package sample.Client;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Server.DataObject;


public class percentInc extends Connection{
    private DataObject d;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exit;


    @FXML
    private Label labelAv;

    @FXML
    void initialize() {
        DataObject d = new DataObject();
        d.setCommand("percentInc");
        d.setSecond_id(current_id);
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeObject(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            d  = (DataObject) in.readObject();
            labelAv.setText(d.getData() + "% от общих доходов");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        closeConnect();
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            newScene("bisnesFunc");
        });
    }
}