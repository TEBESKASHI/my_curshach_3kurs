package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Server.DataObject;


public class NewReportController extends Connection{
    private ObservableList<DataObject> list1 = FXCollections.observableArrayList();
    private ObservableList<DataObject> list2 = FXCollections.observableArrayList();
    private int s1=0, s2=0;
    public NewReportController(){
        DataObject d = new DataObject();
        d.setCommand("getIncomes");
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeObject(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            DataObject arr[] = (DataObject[]) in.readObject();
            for (int i=0; i<arr[arr.length-1].getCounter()-1; i++){
                list1.add(arr[i]);
                s1 += arr[i].getSum();
            }
            closeConnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DataObject d2 = new DataObject();
        d2.setCommand("getCons");
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeObject(d2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            DataObject arr2[] = (DataObject[]) in.readObject();
            for (int i=0; i<arr2[arr2.length-1].getCounter()-1; i++){
                list2.add(arr2[i]);
                s2 += arr2[i].getSum();
            }
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
    private Button back;

    @FXML
    private TableColumn<DataObject, String> data1;

    @FXML
    private TableColumn<DataObject, String> data2;

    @FXML
    private TableColumn<DataObject, String> lastname1;

    @FXML
    private TableColumn<DataObject, String> lastname2;

    @FXML
    private TableColumn<DataObject, String> name1;

    @FXML
    private TableColumn<DataObject, String> name2;

    @FXML
    private TableColumn<DataObject, String> rolle;

    @FXML
    private TableColumn<DataObject, String> rolle2;

    @FXML
    private Button save;

    @FXML
    private TableColumn<DataObject, String> sourse1;

    @FXML
    private TableColumn<DataObject, String> sourse2;

    @FXML
    private TableColumn<DataObject, String> sum1;

    @FXML
    private TableColumn<DataObject, String> sum2;

    @FXML
    private Label sumCon;

    @FXML
    private Label sumIncome;

    @FXML
    private Label total;

    @FXML
    private TableView<DataObject> table1;

    @FXML
    private TableView<DataObject> table2;

    @FXML
    void initialize() {
        data1.setCellValueFactory(new PropertyValueFactory<DataObject, String>("data"));
        sum1.setCellValueFactory(new PropertyValueFactory<DataObject, String>("sum"));
        sourse1.setCellValueFactory(new PropertyValueFactory<DataObject, String>("sourse"));
        name1.setCellValueFactory(new PropertyValueFactory<DataObject, String>("name"));
        rolle.setCellValueFactory(new PropertyValueFactory<DataObject, String>("rolle"));
        lastname1.setCellValueFactory(new PropertyValueFactory<DataObject, String>("lastname"));
        table1.setItems(list1);
        data2.setCellValueFactory(new PropertyValueFactory<DataObject, String>("data"));
        sum2.setCellValueFactory(new PropertyValueFactory<DataObject, String>("sum"));
        sourse2.setCellValueFactory(new PropertyValueFactory<DataObject, String>("sourse"));
        name2.setCellValueFactory(new PropertyValueFactory<DataObject, String>("name"));
        rolle2.setCellValueFactory(new PropertyValueFactory<DataObject, String>("rolle"));
        lastname2.setCellValueFactory(new PropertyValueFactory<DataObject, String>("lastname"));
        table2.setItems(list2);
        sumIncome.setText(String.valueOf(s1));
        sumCon.setText(String.valueOf(s2));
        total.setText(String.valueOf(s1-s2));
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        save.setOnAction(event -> {
            DataObject d = new DataObject();
            d.setIncome(Integer.parseInt(sumIncome.getText()));
            d.setCon(Integer.parseInt(sumCon.getText()));
            d.setTotal(Integer.parseInt(total.getText()));
            d.setData(String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
            d.setCommand("newReport");
            d.setSecond_id(current_id);
            if (socket == null || socket.isClosed()) {
                connect();
            }
            try {
                out.writeObject(d);
                save.getScene().getWindow().hide();
                closeConnect();
                newScene("acceptAllAdmin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
