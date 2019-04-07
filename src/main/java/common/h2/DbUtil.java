package common.h2;

import javax.sql.DataSource;
import java.sql.*;

public class DbUtil {

    private DataSource dataSource;

    //примеры возможных SQL-запросов
    public static final String SQL_CREATE_CHANNEL = "";
    public static final String SQL_ADD_CHANNEL = "";
    public static final String SQL_SELECT_CHANNEL = "";
    public static final String SQL_CREATE_SUB_SYSTEM = "";
    public static final String SQL_ADD_SUB_SYSTEM = "";
    public static final String SQL_SELECT_SUB_SYSTEM = "";
    public static final String SQL_CREATE_PATH = "CREATE TABLE path (id_path number(10) NOT NULL, id_subsystem number(10) NOT NULL, id_chanel number(10) NOT NULL, id_slice number(10) NOT NULL, paramValue varchar)";
    public static final String SQL_ADD_PATH = "";
    public static final String SQL_SELECT_PATH = "";
    public static final String SQL_CREATE_PARAM = "";
    public static final String GET_SQL_ADD_PARAM = "";
    public static final String SQL_SELECT_PARAM = "";
    
    
    //todo нужно сделать параметризируемый запрос для каждого метода
    //параметризовать необходимо указанные выше строки
    
    public void createTable(String sqlString) throws SQLException {
        Statement s = dataSource.getConnection().createStatement();
        s.execute(sqlString);
        s.close();
    }

    public void insertChannel(String sqlString) throws SQLException{
        Statement s = dataSource.getConnection().createStatement();
        s.execute(sqlString);
        s.close();
    }
    public void selectChannel(String sqlString) throws SQLException{
        Statement s = dataSource.getConnection().createStatement();
        ResultSet rs = s.executeQuery(sqlString);

        System.out.println("Выводим все доступные каналы");
        while (rs.next()) {
            String result =
                    "id: " + rs.getString("id_channel") + "\n" +
                    "name: " + rs.getString("name") + "\n" +
                    "description: " + rs.getString("description") + "\n";
            System.out.println(result);
        }

        s.close();
    }

    public void insertSubsystem(String sqlString) throws SQLException{
        Statement s = dataSource.getConnection().createStatement();
        s.execute(sqlString);
        s.close();
    }
    public void selectSubsystem(String sqlString) throws SQLException{
        Statement s = dataSource.getConnection().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        System.out.println("Выводим все доступные подсистемы");
        while (rs.next()) {
            String result =
                    "id: " + rs.getString("id_subsystem") + "\n" +
                            "name: " + rs.getString("name") + "\n" +
                            "description: " + rs.getString("description") + "\n";
            System.out.println(result);
        }

        s.close();
    }

    public void insertPath(String sqlString) throws SQLException{
        Statement s = dataSource.getConnection().createStatement();
        s.execute(sqlString);
        s.close();
    }
    public void selectPath(String sqlString) throws SQLException{
        Statement s = dataSource.getConnection().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        System.out.println("Выводим все доступные разрезы");
        while (rs.next()) {
            String result =
                    "id_slice: " + rs.getString("id_slice") + "\n" +
                            "id_channel: " + rs.getString("id_channel") + "\n" +
                            "id_subsystem: " + rs.getString("id_subsystem") + "\n" +
                            "id_path: " + rs.getString("id_path") + "\n" +
                            "param_value: " + rs.getString("param_value") + "\n" +
                            "value_type: " + rs.getString("value_type") + "\n";
            System.out.println(result);
        }
        s.close();
    }

    public void insertParam(String sqlString) throws SQLException{
        Statement s = dataSource.getConnection().createStatement();
        s.execute(sqlString);
        s.close();
    }
    public void selectParam(String sqlString) throws SQLException{
        Statement s = dataSource.getConnection().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        System.out.println("Выводим все доступные параметры");

        while (rs.next()) {
            String result =
                    "id_param: " + rs.getString("id_param") + "\n" +
                            "name: " + rs.getString("name") + "\n" +
                            "id_path: " + rs.getString("id_path") + "\n" +
                            "description: " + rs.getString("description");
            System.out.println(result);
        }

        s.close();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
