package ppvis.lab2s4.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ppvis.lab2s4.controller.Save;

public class DatabaseHandler {

    Connection connection;

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String connectionsString = "jdbc:mysql://" + Save.host + ":" + Save.port + "/" + Save.name + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
        // Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(connectionsString, Save.user, Save.password);
        return connection;
    }

    public void user(String name, String group, int disease, int other, int without, int total) {
        String insert = "INSERT INTO " + Const.TABLE + "(" + Const.STUDENT + ", `" + Const.GROUP + "`, "
                + Const.DISEASE + ", " + Const.OTHER_REASON + ", " + Const.WITHOUT + ", " + Const.TOTAL + ")" +
                " Values (?,?,?,?,?,?)";


        try {
            PreparedStatement pr = getConnection().prepareStatement(insert);

            pr.setString(1, name);
            pr.setString(2, group);
            pr.setInt(3, disease);
            pr.setInt(4, other);
            pr.setInt(5, without);
            pr.setInt(6, total);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
