package sample.Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

import static java.lang.System.exit;


public class ClientHandler {
    private Server server;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String nick;
    private boolean isAuth;
    private DatabaseHandler db;

    public ClientHandler(Server srv, Socket sock) {
        try {
            db = new DatabaseHandler();
            this.server = srv;
            this.socket = sock;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.isAuth = false;
            new Thread(() -> {
                System.out.println("Пользователь подключен: " + socket.getInetAddress() + ":" + socket.getPort() + "(" + socket.getLocalPort() + ")");
                try {
                    DataObject d;
                    try {
                        while(true) {
                            d = (DataObject) in.readObject();
                            if (d.getCommand().equals("Enter")) {
                                d = checkEnter(d);
                                System.out.println(d.getLogin());
                                sendMsg(d);
                            } else if (d.getCommand().equals("Reg")) {
                                d = regUser(d);
                                sendMsg(d);
                            } else if (d.getCommand().equals("newIncome")) {
                                d = addIncome(d);
                                sendMsg(d);
                            } else if (d.getCommand().equals("getIncomes")) {
                                DataObject[] arr;
                                arr = getIncomes(d);
                                sendBigMsg(arr);
                                System.out.println("отпр");
                            } else if (d.getCommand().equals("getCons")) {
                                DataObject[] arr;
                                arr = getCons(d);
                                sendBigMsg(arr);
                                System.out.println("qweqwe");
                            } else if (d.getCommand().equals("getUsers")) {
                                DataObject[] arr;
                                arr = getUsers(d);
                                sendBigMsg(arr);
                                System.out.println("getUsers");
                            } else if (d.getCommand().equals("editIncome")) {
                                editIncome(d);
                                System.out.println("done");
                            } else if (d.getCommand().equals("editUser")) {
                                editUser(d);
                                System.out.println("done");
                            } else if (d.getCommand().equals("delIncome")) {
                                delIncome(d);
                                System.out.println("del");
                            } else if (d.getCommand().equals("newCon")) {
                                newCon(d);
                                System.out.println("newCon");
                            } else if (d.getCommand().equals("editCon")) {
                                editCon(d);
                                System.out.println("done");
                            } else if (d.getCommand().equals("delCon")) {
                                delCon(d);
                                System.out.println("del");
                            } else if (d.getCommand().equals("delUser")) {
                                delUser(d);
                                System.out.println("del");
                            } else if (d.getCommand().equals("newReport")) {
                                newReport(d);
                                System.out.println("addReport");
                            } else if (d.getCommand().equals("getIncomesAndCons")) {
                                d = getIncomesAndCons(d);
                                sendMsg(d);
                                System.out.println("getIncomesAndCons");
                            }
                            else if (d.getCommand().equals("createPlan")) {
                                createPlan(d);
                            }
                            else if (d.getCommand().equals("averageAll")) {
                                d = averageAll(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("averageSingle")) {
                                d = averageSingle(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("maxCon")) {
                                d = maxCon(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("maxInc")) {
                                d = maxInc(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("planGrate")) {
                                d = planGrate(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("percentInc")) {
                                d = percentInc(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("percentCon")) {
                                d = percentCon(d);
                                sendMsg(d);
                            }
                            System.out.println("Пользователь отключен: операция прошла успешно");
                        }
                    } catch (EOFException e) {

                    } catch (SocketException e) {

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(DataObject msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendBigMsg(DataObject[] msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataObject checkEnter(DataObject msg) {
        return db.ChechEnter(msg);
    }

    private DataObject regUser(DataObject msg){
        return db.RegUser(msg);
    }

    private DataObject addIncome(DataObject msg){
        return db.AddIncome(msg);
    }

    private DataObject getIncomesAndCons(DataObject msg){
        System.out.println(msg);
        return db.GetIncomesAndCons(msg);
    }

    private DataObject[] getIncomes(DataObject msg) throws SQLException { return db.GetIncomes(); }

    private DataObject[] getCons(DataObject msg) throws SQLException { return db.GetCons(); }

    private DataObject[] getUsers(DataObject msg) throws SQLException { return db.GetUsers(); }

    private void editIncome(DataObject msg){ db.EditIncome(msg);}

    private void editUser(DataObject msg){ db.EditUser(msg);}

    private void delIncome(DataObject msg){ db.DelIncome(msg);}

    private void newCon(DataObject msg){ db.AddCon(msg);}

    private void editCon(DataObject msg){ db.EditCon(msg);}

    private void delCon(DataObject msg){ db.DelCon(msg);}

    private void delUser(DataObject msg){ db.DelUser(msg);}

    private void newReport(DataObject msg){ db.AddReport(msg);}

    private void createPlan(DataObject msg){ db.createPlan(msg); };

    private DataObject averageAll(DataObject msg) { return db.averageAll(msg); }

    private DataObject maxCon(DataObject msg) { return db.maxCon(msg); };

    private DataObject maxInc(DataObject msg) { return db.maxInc(msg); };

    private DataObject planGrate(DataObject msg) { return db.planGrate(msg); };

    private DataObject percentInc(DataObject msg) { return db.percentInc(msg); };

    private DataObject percentCon(DataObject msg) { return db.percentCon(msg); };

    private DataObject averageSingle(DataObject msg) { return db.averageSingle(msg); };
}