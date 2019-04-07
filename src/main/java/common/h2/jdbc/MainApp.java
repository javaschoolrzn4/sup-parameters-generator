package common.h2.jdbc;

import common.h2.DbUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import common.sup.da.AbstractDaService;
import common.sup.da.OraDaService;
import common.sup.template.JsonFileClass;

import java.io.File;
import java.sql.*;
import java.util.List;

import static common.sup.SupGenerator.fillJsonParameters;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        DbUtil h2mem = (DbUtil)applicationContext.getBean("parametersJDBCTemplate");

        try {
            h2mem.createTable(DbUtil.SQL_CREATE_CHANNEL);
            h2mem.insertChannel(DbUtil.SQL_ADD_CHANNEL);
            h2mem.selectChannel(DbUtil.SQL_SELECT_CHANNEL);

            h2mem.createTable(DbUtil.SQL_CREATE_SUB_SYSTEM);
            h2mem.insertSubsystem(DbUtil.SQL_ADD_SUB_SYSTEM);
            h2mem.selectSubsystem(DbUtil.SQL_SELECT_SUB_SYSTEM);

            h2mem.createTable(DbUtil.SQL_CREATE_PATH);
            h2mem.insertPath(DbUtil.SQL_ADD_PATH);
            h2mem.selectPath(DbUtil.SQL_SELECT_PATH);

            h2mem.insertChannel(DbUtil.SQL_CREATE_PARAM);
            h2mem.insertParam(DbUtil.GET_SQL_ADD_PARAM);
            h2mem.selectParam(DbUtil.SQL_SELECT_PARAM);


            AbstractDaService service = (OraDaService)applicationContext.getBean("OraDaService");

            List<List<String>> business = null;
            try {
                business = service.read(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();

            JsonFileClass jsonFileClass = new JsonFileClass();
            jsonFileClass.setVersion("1.0");
            jsonFileClass.setParameters(fillJsonParameters(business));
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("json\\parameters.json"), jsonFileClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
