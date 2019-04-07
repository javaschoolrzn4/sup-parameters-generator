package common.sup;

import org.codehaus.jackson.map.ObjectMapper;
import common.sup.da.AbstractDaService;
import common.sup.da.XlsDaService;
import common.sup.template.Bundle;
import common.sup.template.JsonFileClass;
import common.sup.template.JsonParameters;
import common.sup.template.Path;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class SupGenerator {


    /**
     * Пока работает след. образом
     * Есть три класса
     * XlsDaService - класс для работы с XLSX файлами
     * OraDaService - класс для работы с БД Oracle
     * FallbackDaService - класс для работы с FallBackParameters.json
     *
     * Сейчас везде практически хард-код
     * В первом случае, имя файла передается как аргумент командной строки
     * Во втором, адрес БД зашит пока в классе
     * В третьем, имя файла передается как аргумент командной строки
     *
     * Сохранение результата идет в файл по пути json/parameters.json
     * */
    public static void main(String... args) throws URISyntaxException, IOException {

        //todo нужно сделать какой то пользовательский выбор запуска приложения
        //todo в заполнении либо убрать хард код столбцов, либо жестко специфицировать входные данные
        //todo убрать depricated
        AbstractDaService service = new XlsDaService();
//        AbstractDaService service = new OraDaService();

//        AbstractDaService service = new FallbackDaService();
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
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("..\\json\\parameters.json"), jsonFileClass);

    }

    private static List<List<String>> getListsParams() {
        return new ArrayList<List<String>>() {
                {
                    add( new ArrayList(){{
                        add("efs.payments.business.ap.card.cold");//0
                        add("Авотплатежи. Количество дней холодного периода карты");//1
                        add("0");//2
                        add("EFS_PAYMENTS_BUSINESS_ADMIN");//3
                        add("LONG");//4
                        add("BANK_FIX_BRANCH");//5
                        add("UFS_PAYMENTS");//6
                        add("1");//7
                    }});
                }
            };
    }

    public static ArrayList<JsonParameters> fillJsonParameters(List<List<String>> arrayList) {
        ArrayList<JsonParameters> jsonParametersList = new ArrayList<>();

        for (List<String> list: arrayList) {
            jsonParametersList.add(fillJsonParam(list));
        }
        return jsonParametersList;
    }

    private static JsonParameters fillJsonParam(List<String> arrayList){
        JsonParameters parameters = new JsonParameters();
        parameters.setName(arrayList.get(0));
        parameters.setType(arrayList.get(4));
        parameters.setDescription(arrayList.get(1));
        parameters.setList(new Boolean(arrayList.get(2)));
        parameters.setBundle(createBundle(arrayList));
        parameters.setRoles(createRoleList(arrayList));
        return parameters;
    }

    private static List<Bundle> createBundle(List<String> arrayList) {
        List<Bundle> bundleList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.setPath(fillPaths(arrayList));
        bundle.setPath(fillPaths(arrayList));
        bundle.setValues(fillValues(arrayList));
        bundleList.add(bundle);
        return bundleList;
    }

    private static List<String> fillValues(List<String> arrayList) {
        List<String> valueList = new ArrayList<>();

        Integer isList = Integer.valueOf(arrayList.get(2));
        if (isList != 0) {
            for(int i = 7; i<arrayList.size(); ++i)
            {
                valueList.add(arrayList.get(i));
            }
        } else {
            valueList.add(arrayList.get(7));
        }
        return valueList;
    }

    private static List<Path> fillPaths(List<String> arrayList) {
        //todo заполнять path возможно получится через получение значений от входного списка
        List<Path> pathList = new ArrayList<>();
        pathList.add(fillPath("VERSION", "false"));
        pathList.add(fillPath("CHANNEL", arrayList.get(5)));
        pathList.add(fillPath("SUBSYSTEM", arrayList.get(6)));
        pathList.add(fillPath("TERBANK", ""));
        pathList.add(fillPath("OSB", ""));
        pathList.add(fillPath("VSP", ""));
        return pathList;
    }

    private static Path fillPath(String st1, String st2) {
        Path path = new Path();
        path.setCode(st1);
        path.setValue(st2);
        return path;
    }

    private static List<String> createRoleList(List<String> arrayList) {
        List<String> roleList = new ArrayList<>();
        roleList.add(arrayList.get(3));
        return roleList;
    }
}
