package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Server.DataObject;

public class DelIncomeController extends Connection{
    private ObservableList<DataObject> list = FXCollections.observableArrayList();
    public DelIncomeController(){
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
        Data.setCellValueFactory(new PropertyValueFactory<DataObject, String>("data"));
        Sum.setCellValueFactory(new PropertyValueFactory<DataObject, String>("sum"));
        Sourse.setCellValueFactory(new PropertyValueFactory<DataObject, String>("sourse"));
        table.setItems(list);
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
                DataObject d = table.getSelectionModel().getSelectedItem();
                d.setCommand("delIncome");
                out.writeObject(d);
                del.getScene().getWindow().hide();
                newScene("accept4");
                closeConnect();
            }catch (NullPointerException e){
                del.getScene().getWindow().hide();
                newScene("error3");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

