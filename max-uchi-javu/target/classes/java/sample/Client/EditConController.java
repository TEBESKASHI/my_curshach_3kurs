package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Server.DataObject;


public class EditConController extends Connection{
    private ObservableList<DataObject> list = FXCollections.observableArrayList();
    public EditConController(){
        DataObject d = new DataObject();
        d.setCommand("getCons");
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
                list.add(arr[i]);
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
    private Button change;

    @FXML
    private Button back;

    @FXML
    private TableColumn<DataObject, String> Data;

    @FXML
    private TableColumn<DataObject, String> Sum;

    @FXML
    private TableView<DataObject> table;

    @FXML
    private TableColumn<DataObject, String> Sourse;

    @FXML
    void initialize() {
        Data.setCellValueFactory(new PropertyValueFactory<DataObject, String>("data"));
        Sum.setCellValueFactory(new PropertyValueFactory<DataObject, String>("sum"));
        Sourse.setCellValueFactory(new PropertyValueFactory<DataObject, String>("sourse"));
        table.setItems(list);
        change.setOnAction(event -> {
            try {
                selectionModel = table.getSelectionModel();
                change.getScene().getWindow().hide();
                newScene("editCon2");
            }catch (NullPointerException e){
                change.getScene().getWindow().hide();
                newScene("error3");
            }
        });
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
    }

}
