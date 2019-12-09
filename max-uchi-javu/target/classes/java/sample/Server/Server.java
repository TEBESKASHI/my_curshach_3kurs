package sample.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class Server {


    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(8180)) {

            System.out.println("Сервер запущен. Ожидаю клиентов...");
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}