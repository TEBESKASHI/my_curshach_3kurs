package sample.Client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.Server.DataObject;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
    protected static TableView.TableViewSelectionModel<DataObject> selectionModel;
    protected static String current;
    protected static int current_id;

    final String SERVER_IP = "localhost";
    final int SERVER_PORT = 8180;
    protected Socket socket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    protected void connect() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void closeConnect() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void newScene(String s){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/java/sample/resourses/"+s+".fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent r = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(r));
        stage.show();
    }


}
