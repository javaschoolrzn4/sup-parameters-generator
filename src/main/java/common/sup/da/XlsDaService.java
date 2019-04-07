package common.sup.da;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XlsDaService extends AbstractDaService {

    @Override
    public List<List<String>> read(String Url) throws IOException{
        Workbook workbook = null;
        InputStream stream = new FileInputStream(Url);
        workbook = new XSSFWorkbook(stream);
        Sheet sheet = workbook.getSheetAt(0);

        List<List<String>> result = new ArrayList<>();

        Iterator<Row> rows = sheet.rowIterator();
        rows.next();
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            List<String> line = new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                CellType cellType = cell.getCellTypeEnum();
                switch (cellType) {
                    case STRING:
                        line.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        //throw new RuntimeException("Значение для поля " + line.get(0) + " не заполнено");
                        line.add(Integer.valueOf((int) cell.getNumericCellValue()).toString());
                        break;
                }
//                System.out.println(UUID.randomUUID().toString());
            }
            result.add(line);
        }
        result.forEach(System.out::println);
        return result;
    }
}
