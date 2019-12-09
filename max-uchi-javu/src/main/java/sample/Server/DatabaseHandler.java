package sample.Server;

import sample.Client.Configs;

import java.sql.*;

import static java.lang.Math.round;

public class DatabaseHandler extends Configs {
    Connection dbConn;


    public Connection getDbConn() throws ClassNotFoundException, SQLException{
        String conntString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?serverTimezone=UTC";
        dbConn = DriverManager.getConnection(conntString, dbUser, dbPass);
        return dbConn;
    }


    public DataObject RegUser(DataObject msg){
        ResultSet rs = null;
        String enter = "SELECT * FROM " + Const.USER_TABLE +
                " WHERE " + Const.USER_LOGIN + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(enter);
            ps.setString(1, msg.getLogin());
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(Const.USER_LOGIN));
                System.out.println(msg.getLogin());
                if (rs.getString(Const.USER_LOGIN).equals(msg.getLogin())) {
                    System.out.println("keke");
                    msg.setResult(false);
                    return msg;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USER_FIRSTNAME + "," + Const.USER_LASTNAME + "," +
                Const.USER_LOGIN + "," + Const.USER_PASSWORD + "," +
                Const.USER_ROLE + ")" + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(insert);
            ps.setString(1, msg.getName());
            ps.setString(2, msg.getLastname());
            ps.setString(3, msg.getLogin());
            ps.setString(4, msg.getPassword());
            ps.setString(5, msg.getRolle());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        msg.setResult(true);
        return msg;
    }


    public DataObject ChechEnter(DataObject msg) {
        ResultSet rs = null;
        String enter = "SELECT * FROM " + Const.USER_TABLE +
                " WHERE " + Const.USER_LOGIN + "=? AND " + Const.USER_PASSWORD + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(enter);
            ps.setString(1, msg.getLogin());
            ps.setString(2, msg.getPassword());
            rs = ps.executeQuery();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs.next()) {
                msg.setResult(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) { }
        if (msg.isResult()) {
            try {
                msg.setId(rs.getInt(Const.USER_ID));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }

    public DataObject averageAll(DataObject msg) {
        ResultSet rs = null;
        String get = "SELECT * FROM " + Const.CON_TABLE + "";
        double sum = 0;
        int counter = 0;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(get);
            rs = ps.executeQuery();
            while (rs.next()) {
                sum += Double.valueOf(rs.getString(Const.CON_MONEY));
                counter++;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sum = sum / counter;
        msg.setData(String.valueOf(sum));
        return msg;
    }

    public DataObject averageSingle(DataObject msg) {
        ResultSet rs = null;
        String get = "SELECT * FROM " + Const.CON_TABLE + " WHERE " + Const.CON_ID_USER + "=?";
        double sum = 0;
        int counter = 0;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(get);
            ps.setInt(1, msg.getSecond_id());
            rs = ps.executeQuery();
            while (rs.next()) {
                sum += Double.valueOf(rs.getString(Const.CON_MONEY));
                counter++;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sum = sum / counter;
        msg.setData(String.valueOf(sum));
        return msg;
    }

    public DataObject maxCon(DataObject msg) {
        ResultSet rs = null;
        String get = "SELECT * FROM " + Const.CON_TABLE + "";
        int sum = 0;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(get);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (sum < Integer.parseInt(rs.getString(Const.CON_MONEY))) {
                    sum = Integer.parseInt(rs.getString(Const.CON_MONEY));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        msg.setData(String.valueOf(sum));
        return msg;
    }

    public DataObject maxInc(DataObject msg) {
        ResultSet rs = null;
        String get = "SELECT * FROM " + Const.INCOME_TABLE + " ";
        int sum = 0;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(get);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (sum < Integer.valueOf(rs.getString(Const.CON_MONEY)))
                sum = Integer.valueOf(rs.getString(Const.CON_MONEY));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        msg.setData(String.valueOf(sum));
        return msg;
    }

    public DataObject planGrate(DataObject msg) {
        ResultSet rs = null;
        String get = "SELECT * FROM " + Const.PLAN_TABLE + " WHERE " + Const.PLAN_USER_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(get);
            ps.setInt(1, msg.getSecond_id());
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString(Const.PLAN_RESULT).equals("1")) {
                    msg.setPlan_result(true);
                }
                else {
                    msg.setPlan_result(false);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public DataObject percentInc(DataObject msg) {
        ResultSet rs = null;
        String get = "SELECT * FROM " + Const.INCOME_TABLE + "";
        double sum = 0;
        double entry = 0;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(get);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(Const.INCOME_ID_USER) == msg.getSecond_id()) {
                    entry += Double.valueOf(rs.getString(Const.INCOME_MONEY));
                }
                sum += Double.valueOf(rs.getString(Const.INCOME_MONEY));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(entry);
        System.out.println(sum);
        double percent = (entry / sum) * 100;
        percent = Math.round(percent);
        msg.setData(String.valueOf(percent));
        return msg;
    }

    public DataObject percentCon(DataObject msg) {
        ResultSet rs = null;
        String get = "SELECT * FROM " + Const.CON_TABLE + "";
        double sum = 0;
        double entry = 0;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(get);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(Const.CON_ID_USER) == msg.getSecond_id()) {
                    entry += Double.valueOf(rs.getString(Const.CON_MONEY));
                }
                sum += Double.valueOf(rs.getString(Const.CON_MONEY));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        double percent = (entry / sum) * 100;
        percent = Math.round(percent);
        msg.setData(String.valueOf(percent));
        return msg;
    }

    public DataObject GetIncomesAndCons(DataObject msg) {
        ResultSet rs = null;
        String get = "SELECT * FROM " + Const.REPORT_TABLE + " WHERE " + Const.REPORT_ID + "=(" +
                "SELECT MAX(" + Const.REPORT_ID + ") FROM " + Const.REPORT_TABLE + ")";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(get);
            rs = ps.executeQuery();
            System.out.println(rs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            msg.setResult(false);
            msg.setIncome(0);
            msg.setCon(0);
            if (rs.next()) {
                System.out.println(rs.getInt(Const.REPORT_ID));
                msg.setIncome(msg.getIncome() + rs.getInt(Const.REPORT_INCOME));
                msg.setCon(msg.getCon() + rs.getInt(Const.REPORT_CON));
                System.out.println(msg.getIncome() + "   " + msg.getCon());
                msg.setResult(true);
                if (msg.getIncome() > msg.getCon()) {
                    msg.setBool(1);
                    msg.setPer_income(100);
                    msg.setPer_con(round(100 * msg.getCon() / msg.getIncome()));
                } else {
                    msg.setBool(0);
                    msg.setPer_con(100);
                    msg.setPer_income(round(100 * msg.getIncome() / msg.getCon()));
                }
            }
            else {
                msg.setResult(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            msg.setResult(false);
        } catch (NullPointerException e) {
            msg.setResult(false);
        }
        return msg;
    }

    public DataObject AddIncome(DataObject msg) {
        String insert = "INSERT INTO " + Const.INCOME_TABLE + "(" +
                Const.INCOME_DATE + "," + Const.INCOME_SOURSE + "," +
                Const.INCOME_MONEY + "," + Const.INCOME_ID_USER  + ")" + "VALUES(?,?,?,?)";
        try {
            String root = "Select iduser FROM " + Const.USER_TABLE + "WHERE";
            System.out.println(msg.getLogin() + "AddIncome");
            System.out.println(msg.getId() + "AddIncome");
            System.out.println(msg.getSecond_id() + "AddIncome");
            PreparedStatement ps = getDbConn().prepareStatement(insert);
            ps.setString(1, msg.getData());
            ps.setString(2, msg.getSourse());
            ps.setString(3, String.valueOf(msg.getSum()));
            ps.setString(4, String.valueOf(msg.getSecond_id()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        msg.setResult(true);
        return msg;
    }

    public void createPlan(DataObject msg) {
        ResultSet rs = null;
        String getIncomes = "SELECT * FROM " + Const.INCOME_TABLE + " WHERE " + Const.INCOME_ID_USER + "=?";
        double sum1 = 0;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setInt(1, msg.getSecond_id());
            rs = ps.executeQuery();
            while (rs.next()) {
                sum1+=Double.valueOf(rs.getString(Const.INCOME_MONEY));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rsd = null;
        double sum2 = 0;
        String getcom = "SELECT * FROM " + Const.CON_TABLE + " WHERE " + Const.CON_ID_USER + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getcom);
            ps.setInt(1, msg.getSecond_id());
            rsd = ps.executeQuery();
            while (rsd.next()) {
                sum2+=Double.valueOf(rsd.getString(Const.CON_MONEY));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(sum2 + "dengi");
        int result = 0;
        if (msg.getPlan_inc() <= sum1 && msg.getPlan_con() >= sum2) {
            result = 1;
        }
    String insert = "INSERT INTO " + Const.PLAN_TABLE + "(" +
            Const.PLAN_IN + ", " + Const.PLAN_CON + ", "  + Const.PLAN_INCOMES  + ", " + Const.PLAN_CONS + ", " + Const.PLAN_USER_ID + ", " + Const.PLAN_RESULT + ")" + " VALUES(?,?,?,?,?,?) ON DUPLICATE KEY UPDATE " + Const.PLAN_IN + "=?, " + Const.PLAN_CON + "=?, "
            + Const.PLAN_INCOMES + "=?, " + Const.PLAN_CONS + "=?, " + Const.PLAN_RESULT + "=?";
    try {
        PreparedStatement ps = getDbConn().prepareStatement(insert);
        ps.setString(1, String.valueOf(msg.getPlan_inc()));
        ps.setString(2, String.valueOf(msg.getPlan_con()));
        ps.setString(3, String.valueOf(sum1));
        ps.setString(4, String.valueOf(sum2));
        ps.setInt(5, msg.getSecond_id());
        ps.setString(6, String.valueOf(result));
        ps.setString(7, String.valueOf(msg.getPlan_inc()));
        ps.setString(8,String.valueOf(msg.getPlan_con()));
        ps.setString(9, String.valueOf(sum1));
        ps.setString(10, String.valueOf(sum2));
        ps.setString(11, String.valueOf(result));
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}

    public DataObject[] GetIncomes() {
        ResultSet rs = null;
       String getIncomes = "SELECT * FROM " + Const.INCOME_TABLE + " INNER JOIN " + Const.USER_TABLE + " ON "
               + Const.USER_TABLE + "." + Const.USER_ID + "=" + Const.INCOME_TABLE + "." + Const.INCOME_ID_USER;
//        String getIncomes = "SELECT * FROM " + Const.INCOME_TABLE + " INNER JOIN " + Const.USER_TABLE + " ON "
//                + Const.USER_TABLE + "." + Const.USER_ID;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            rs = ps.executeQuery();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataObject arr[] = new DataObject[100];
        int counter=0;
        try {
            while (rs.next()) {
                arr[counter] = new DataObject();
                arr[counter].setId(rs.getInt(Const.INCOME_ID));
                arr[counter].setData(rs.getString(Const.INCOME_DATE));
                arr[counter].setSourse(rs.getString(Const.INCOME_SOURSE));
                arr[counter].setSum(rs.getInt(Const.INCOME_MONEY));
                arr[counter].setName(rs.getString(Const.USER_FIRSTNAME));
                arr[counter].setLastname(rs.getString(Const.USER_LASTNAME));
                arr[counter].setRolle(rs.getString(Const.USER_ROLE));
                arr[counter].setSecond_id(rs.getInt(Const.USER_ID));
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){ }
        DataObject res[] = new DataObject[counter+1];
        for (int i=0; i<res.length-1; i++){
            res[i] = new DataObject();
            res[i] = arr[i];
        }
        res[res.length-1] = new DataObject();
        res[res.length-1].setCounter(counter+1);
        return res;
    }

    public DataObject[] GetCons() {
        ResultSet rs = null;
       String getIncomes = "SELECT * FROM " + Const.CON_TABLE + " INNER JOIN " + Const.USER_TABLE + " ON "
               + Const.USER_TABLE + "." + Const.USER_ID + "=" + Const.CON_TABLE + "." + Const.CON_ID_USER;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            rs = ps.executeQuery();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataObject arr[] = new DataObject[100];
        int counter=0;
        try {
            while (rs.next()) {
                arr[counter] = new DataObject();
                arr[counter].setId(rs.getInt(Const.CON_ID));
                arr[counter].setData(rs.getString(Const.CON_DATE));
                arr[counter].setSourse(rs.getString(Const.CON_SOURSE));
                arr[counter].setSum(rs.getInt(Const.CON_MONEY));
                arr[counter].setName(rs.getString(Const.USER_FIRSTNAME));
                arr[counter].setLastname(rs.getString(Const.USER_LASTNAME));
                arr[counter].setRolle(rs.getString(Const.USER_ROLE));
                arr[counter].setSecond_id(rs.getInt(Const.USER_ID));
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){ }
        DataObject res[] = new DataObject[counter+1];
        for (int i=0; i<res.length-1; i++){
            res[i] = new DataObject();
            res[i] = arr[i];
        }
        res[res.length-1] = new DataObject();
        res[res.length-1].setCounter(counter+1);
        return res;
    }


    public DataObject[] GetUsers() {
        ResultSet rs = null;
        String getIncomes = "SELECT * FROM " + Const.USER_TABLE;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            rs = ps.executeQuery();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataObject arr[] = new DataObject[100];
        int counter=0;
        try {
            while (rs.next()) {
                arr[counter] = new DataObject();
                arr[counter].setId(rs.getInt(Const.USER_ID));
                arr[counter].setName(rs.getString(Const.USER_FIRSTNAME));
                arr[counter].setLastname(rs.getString(Const.USER_LASTNAME));
                arr[counter].setRolle(rs.getString(Const.USER_ROLE));
                arr[counter].setLogin(rs.getString(Const.USER_LOGIN));
                arr[counter].setPassword(rs.getString(Const.USER_PASSWORD));
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){ }
        DataObject res[] = new DataObject[counter+1];
        for (int i=0; i<res.length-1; i++){
            res[i] = new DataObject();
            res[i] = arr[i];
        }
        res[res.length-1] = new DataObject();
        res[res.length-1].setCounter(counter+1);
        return res;
    }

    public void EditIncome(DataObject msg){
        String getIncomes = "UPDATE " + Const.INCOME_TABLE + " SET " + Const.INCOME_DATE + "=?, "
                + Const.INCOME_MONEY + "=?, " + Const.INCOME_SOURSE + "=? WHERE " + Const.INCOME_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setString(1, msg.getData());
            ps.setString(2, String.valueOf(msg.getSum()));
            ps.setString(3, msg.getSourse());
            ps.setString(4, String.valueOf(msg.getId()));
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void DelIncome(DataObject msg){
        String getIncomes = "DELETE FROM " + Const.INCOME_TABLE + " WHERE " + Const.INCOME_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setString(1, String.valueOf(msg.getId()));
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void AddCon(DataObject msg) {
        String insert = "INSERT INTO " + Const.CON_TABLE + "(" +
                Const.CON_DATE + "," + Const.CON_SOURSE + "," +
                Const.CON_MONEY + "," + Const.CON_ID_USER +")" + "VALUES(?,?,?,?)";
        try {
            System.out.println(msg.getLogin() + "AddCon");
            System.out.println(msg.getId() + "AddCon");
            System.out.println(msg.getSecond_id() + "AddCon");

            PreparedStatement ps = getDbConn().prepareStatement(insert);
            ps.setString(1, msg.getData());
            ps.setString(2, msg.getSourse());
            ps.setString(3, String.valueOf(msg.getSum()));
            ps.setString(4, String.valueOf(msg.getSecond_id()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void AddReport(DataObject msg) {
        String insert = "INSERT INTO " + Const.REPORT_TABLE + "(" +
                Const.REPORT_DATE + "," + Const.REPORT_INCOME + "," +
                Const.REPORT_CON + "," + Const.REPORT_TOTAL  + "," + Const.REPORT_ID_USER + ")" + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(insert);
            ps.setString(1, msg.getData());
            ps.setString(2, String.valueOf(msg.getIncome()));
            ps.setString(3, String.valueOf(msg.getCon()));
            ps.setString(4, String.valueOf(msg.getTotal()));
            ps.setString(5, String.valueOf(msg.getSecond_id()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void EditCon(DataObject msg){
        String getIncomes = "UPDATE " + Const.CON_TABLE + " SET " + Const.CON_DATE + "=?, "
                + Const.CON_MONEY + "=?, " + Const.CON_SOURSE + "=? WHERE " + Const.CON_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setString(1, msg.getData());
            ps.setString(2, String.valueOf(msg.getSum()));
            ps.setString(3, msg.getSourse());
            ps.setString(4, String.valueOf(msg.getId()));
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void EditUser(DataObject msg){
        String getIncomes = "UPDATE " + Const.USER_TABLE + " SET " + Const.USER_LOGIN + "=?, "
                + Const.USER_PASSWORD + "=?, " + Const.USER_FIRSTNAME + "=?," + Const.USER_LASTNAME + "?," +
                Const.USER_ROLE + "=? WHERE " + Const.CON_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setString(1, msg.getLogin());
            ps.setString(2, msg.getPassword());
            ps.setString(3, msg.getName());
            ps.setString(4, msg.getLastname());
            ps.setString(5, msg.getRolle());
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DelCon(DataObject msg){
        String getIncomes = "DELETE FROM " + Const.CON_TABLE + " WHERE " + Const.CON_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setString(1, String.valueOf(msg.getId()));
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DelUser(DataObject msg){
        String getIncomes = "DELETE FROM " + Const.USER_TABLE + " WHERE " + Const.USER_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setString(1, String.valueOf(msg.getId()));
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
