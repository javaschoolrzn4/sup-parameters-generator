package common.sup.da;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OraDaService extends AbstractDaService {
    
    private DataSource h2DataSource = null;

    public OraDaService() {
    }

    @Override
    public List<List<String>> read(String Url) throws SQLException{

        //todo уйти от хардкода как при коннекте, так и при выполнении запроса, хотя бы надо задавать канал и подсистему
        List<List<String>> result = new ArrayList<>();
        try (Connection conn = h2DataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            String sql;
            sql = "SELECT \n" +
                    "param.name as \"Name\",\n" +
                    "param.description as \"Desc\",\n" +
                    "param.isList as \"Is List\",\n" +
                    "path.value_type as \"Data Type\",\n" +
                    "channel.name as \"Channel\",\n" +
                    "subsystem.name as \"Sub System\",\n" +
                    "path.param_value as \"Value\"\n" +
                    "FROM path \n" +
                    "INNER JOIN param on path.id_path = param.id_path\n" +
                    "INNER JOIN channel on path.id_channel = channel.id_channel\n" +
                    "INNER JOIN subsystem on path.id_subsystem = subsystem.id_subsystem";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                List<String> row = new ArrayList<>();
                row.add(rs.getString("Name"));
                row.add(rs.getString("Desc"));
                row.add(String.valueOf(rs.getInt("Is List")));
                row.add("EFS_APPLICATION_ADMIN");
//                row.add(rs.getString("Parameter Role Name"));
                row.add(rs.getString("Data Type"));
                row.add(rs.getString("Channel"));
                row.add(rs.getString("Sub System"));
                row.add(rs.getString("Value"));
                result.add(row);
            }

        }
        return result;
    }
    
    public void setDataSource(DataSource h2DataSource) {
        this.h2DataSource = h2DataSource;
    }
    public DataSource getDataSource() {
        return h2DataSource;
    }
}
