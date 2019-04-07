package common.h2.spring.jdbc;

import common.h2.DbUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import common.sup.da.AbstractDaService;
import common.sup.da.OraDaService;
import common.sup.template.JsonFileClass;

import java.io.File;
import java.util.List;

import static common.sup.SupGenerator.fillJsonParameters;

/**
 * //Приложения ПАО Сбербанк действуют в трех каналах обслуживания (три версии вашего ПО)
 *
 *     //Каналы
 *     //branch - Отделение сбербанка
 *     //remote channel - Удаленный канал обслуживания
 *     //call center - Контактный центр Сбербанка
 *
 *     //В кажом канале могу действовать какая либо подсистема из списка
 *     //Аутентификация клиента банка (ClientAuth)
 *     //История операций клиента (Operation)
 *     //Открытие вклада
 *     //Подключение автоплатежа
 *     //Перевод средств клиента
 *
 *     //У подсистем могут использоваться одни и те же параметры
 *     //Например,
 *     //Имя параметра: http.base.url
 *     //Значение зависит от пары канал + система(далее "разрез")
 *     //т.е. http.base.url
 *     //для разреза branch + Открытие вклада value = branch.http.sberbank.ru
 *     //для разреза remote + Открытие вклада value = remote.http.sberbank.ru
 *     //
 *
 *     //Добавить методы заполнения БД и создать запрос на выборку
 *     //все параметры
 *     //выбрать параметры из одного канала
 *     //выбрать параметры из одной подсистемы
 *
 *     //Должно быть как минимум 10 Параметров в БД (не значений, но у параметра должно быть как минимум 1 значение)
 *     //Оптимизировать количество методов, которые у нас работают с БД
 * */

public class MainApp {

    /**
     * Основной метод, который внутри которого выполняется
     * Инициализация контекста
     * Создание структуры БД
     * Исполнение DML SQL-скриптов
     * По результатам выборки генерируется JSON-файл по заданному шаблону
     * */
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

            h2mem.getDataSource().getConnection();

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
