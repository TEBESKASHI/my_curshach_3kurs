package sample.Client;

import java.io.IOException;
import java.net.DatagramPacket;
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

public class WorkWithUsersController extends Connection{
    private ObservableList<DataObject> list = FXCollections.observableArrayList();

    public WorkWithUsersController(){
        DataObject d = new DataObject();
        d.setCommand("getUsers");
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
    private TableColumn<DataObject, String> rolle;

    @FXML
    private Button change;

    @FXML
    private TableColumn<DataObject, String> name;

    @FXML
    private Button back;

    @FXML
    private Button del;

    @FXML
    private TableView<DataObject> table;

    @FXML
    private TableColumn<DataObject, String> lastname;

    @FXML
    void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<DataObject, String>("name"));
        rolle.setCellValueFactory(new PropertyValueFactory<DataObject, String>("rolle"));
        lastname.setCellValueFactory(new PropertyValueFactory<DataObject, String>("lastname"));
        table.setItems(list);
        del.setOnAction(event -> {
            try {
                if (socket == null || socket.isClosed()) {
                    connect();
                }
                DataObject d = table.getSelectionModel().getSelectedItem();
                System.out.println(d.getId());
                d.setCommand("delUser");
                out.writeObject(d);
                closeConnect();
                del.getScene().getWindow().hide();
                newScene("acceptAllAdmin");
            }catch (NullPointerException e){
                del.getScene().getWindow().hide();
                newScene("error3");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            newScene("mainAdmin");
        });
        change.setOnAction(event -> {
            try {
                selectionModel = table.getSelectionModel();
                change.getScene().getWindow().hide();
                newScene("editUser");
            } catch (NullPointerException e){
                change.getScene().getWindow().hide();
                newScene("error3");
            }
        });
    }
}
