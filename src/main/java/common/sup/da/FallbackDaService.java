package common.sup.da;

import org.codehaus.jackson.map.ObjectMapper;
import common.sup.da.data.Fallback;
import common.sup.da.data.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FallbackDaService extends AbstractDaService {
    @Override
    public List<List<String>> read(String Url) throws IOException{

        ObjectMapper mapper = new ObjectMapper();
        final Fallback fallback = mapper.readValue(new File(Url), Fallback.class);
        List<List<String>> result = new ArrayList<>();

        for (Parameter param: fallback.getParameters()) {
            List<String> row = new ArrayList<>();

            //todo хардкод нужно убрать из заполнения
            row.add(param.getName());
            row.add("");
            row.add("0");
            row.add("EFS_APPLICATION_ADMIN");
            row.add(param.getType());
            row.add(param.getPath().get(1).getValue());
            row.add(param.getPath().get(2).getValue());
            row.add(param.getValues().get(0));

            result.add(row);
        }


        return result;
    }
}
