package sample.Client;

import java.io.Serializable;

public class DataObject implements Serializable {
    private String command;
    private String name;
    private String lastname;
    private String login;
    private String password;
    private String rolle;
    private String data;
    private int sum;
    private String sourse;
    private String reason;
    private int total;
    private boolean result;
    private int counter;
    private int id;
    private int income;
    private int per_income;
    private int per_con;
    private int second_id;
    private int bool;
    private int plan_inc;
    private int plan_con;
    private boolean plan_result;
    public int getBool() {
        return bool;
    }

    public void setBool(int bool) {
        this.bool = bool;
    }

    public int getPer_income() {
        return per_income;
    }

    public void setPer_income(int per_income) {
        this.per_income = per_income;
    }

    public int getPer_con() {
        return per_con;
    }

    public void setPer_con(int per_con) {
        this.per_con = per_con;
    }

    public int getSecond_id() {
        return second_id;
    }

    public void setSecond_id(int second_id) {
        this.second_id = second_id;
    }


    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getCon() {
        return con;
    }

    public void setCon(int con) {
        this.con = con;
    }

    private int con;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getSourse() {
        return sourse;
    }

    public void setSourse(String sourse) {
        this.sourse = sourse;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public DataObject(){

    }

    public int getPlan_inc() {
        return plan_inc;
    }

    public void setPlan_inc(int plan_inc) {
        this.plan_inc = plan_inc;
    }

    public int getPlan_con() {
        return plan_con;
    }

    public void setPlan_con(int plan_con) {
        this.plan_con = plan_con;
    }

    public boolean isPlan_result() {
        return plan_result;
    }

    public void setPlan_result(boolean plan_result) {
        this.plan_result = plan_result;
    }
}
