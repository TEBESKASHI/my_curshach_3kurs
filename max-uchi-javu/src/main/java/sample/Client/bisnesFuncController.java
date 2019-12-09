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
import javafx.stage.Stage;
import sample.Server.DataObject;


public class bisnesFuncController extends Connection{
    private DataObject d;

    public bisnesFuncController() {
        d = new DataObject();
        d.setCommand("getIncomesAndCons");
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
            closeConnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ResourceBundle resources;


    @FXML
    private URL location;

    @FXML
    private Button exit;

    @FXML
    private Button averageAll;

    @FXML
    private Button averageSingle;

    @FXML
    private Button maxCon;

    @FXML
    private Button maxInc;

    @FXML
    private Button planGrate;

    @FXML
    private Button percentInc;

    @FXML
    private Button percentCon;

    @FXML
    void initialize() {
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            newScene("sample");
        });
        averageAll.setOnAction(event -> {
            averageAll.getScene().getWindow().hide();
            newScene("averageAll");
        });

        averageSingle.setOnAction(event -> {
            averageSingle.getScene().getWindow().hide();
            newScene("averageSingle");
        });
        maxCon.setOnAction(event -> {
            maxCon.getScene().getWindow().hide();
            newScene("maxCon");
        });
        maxInc.setOnAction(event -> {
            maxInc.getScene().getWindow().hide();
            newScene("maxInc");
        });
        planGrate.setOnAction(event -> {
            planGrate.getScene().getWindow().hide();
            newScene("planGrate");
        });
        percentInc.setOnAction(event -> {
            percentInc.getScene().getWindow().hide();
            newScene("percentInc");
        });
        percentCon.setOnAction(event -> {
            percentCon.getScene().getWindow().hide();
            newScene("percentCon");
        });
    }
}