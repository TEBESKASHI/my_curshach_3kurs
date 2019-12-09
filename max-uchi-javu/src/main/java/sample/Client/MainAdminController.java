package sample.Client;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import sample.Server.DataObject;

public class MainAdminController extends Connection{
    private DataObject d;

    public MainAdminController(){
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
    private Button workWithIncome;

    @FXML
    private Button exit;

    @FXML
    private Button workWithUsers;

    @FXML
    private PieChart diag;

    @FXML
    private Button workWithCon;

    @FXML
    private Button workWithReport;

    @FXML
    void initialize() {
        if (d.getBool()==1 && d.isResult()){
            diag.getData().add(new PieChart.Data("Сальдо", d.getPer_income()));
            diag.getData().add(new PieChart.Data("Расходы", d.getPer_con()));
            diag.setTitle("Отнощение расходов к доходам");

        }else{
            diag.getData().add(new PieChart.Data("Дефицит бюджета", d.getPer_income()));
            diag.getData().add(new PieChart.Data("Доходы", d.getPer_con()));
            diag.setTitle("Отнощение доходов к расходам");
        }
        diag.setLegendVisible(true);
        diag.setLegendSide(Side.BOTTOM);

        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            newScene("sample");
        });
        workWithCon.setOnAction(event -> {
            workWithCon.getScene().getWindow().hide();
            newScene("workWithCon");
        });

        workWithIncome.setOnAction(event -> {
            workWithIncome.getScene().getWindow().hide();
            newScene("workWithIncome");
        });
        workWithReport.setOnAction(event -> {
            workWithReport.getScene().getWindow().hide();
            newScene("newReport");
        });
        workWithUsers.setOnAction(event -> {
            workWithUsers.getScene().getWindow().hide();
            newScene("workWithUsers");
        });
    }
}