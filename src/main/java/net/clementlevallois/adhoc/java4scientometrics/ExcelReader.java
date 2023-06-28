/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.clementlevallois.adhoc.java4scientometrics;

import com.monitorjbl.xlsx.StreamingReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.ERROR;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author LEVALLOIS
 */
public class ExcelReader {

    public static List<AbstractItem> read(Path pathInput, int col0, int col1, int col2) throws IOException, Exception {
        InputStream is = new FileInputStream(pathInput.toFile());
        List<AbstractItem> abstractItems = new ArrayList();
        Set<AbstractItem> keywordSearchRefs = new HashSet();
        Set<AbstractItem> humphreysRefs = new HashSet();
        Set<AbstractItem> bergerRefs = new HashSet();
        Set<AbstractItem> hartmannRefs = new HashSet();
        Set<AbstractItem> rambocasRefs = new HashSet();
        Set<AbstractItem> zoteroRefs = new HashSet();

        try (Workbook wb = StreamingReader.builder()
                .rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096) // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is)) {
            for (Sheet sheet : wb) {
                int rowNumber = 0;

                for (Row r : sheet) {

                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }

                    AbstractItem ai = new AbstractItem();

                    Cell cell = r.getCell(21);
                    String returnStringValue = ExcelReader.returnStringValue(cell);
                    returnStringValue = returnStringValue.replace("’", "'");
                    if (returnStringValue.isBlank()){
                        continue;
                    }
                    ai.setContent(returnStringValue);

                    cell = r.getCell(73);
                    returnStringValue = ExcelReader.returnStringValue(cell);
                    returnStringValue = returnStringValue.replace("’", "'");
                    if (!returnStringValue.equals("yes")) {
                        continue;
                    } else {
                        ai.setOnTopic(true);
                    }
                    cell = r.getCell(74);
                    returnStringValue = ExcelReader.returnStringValue(cell);
                    returnStringValue = returnStringValue.replace("’", "'");
                    if (returnStringValue.equals("no")) {
                        continue;
                    } else {
                        ai.setOnTopic(true);
                    }

                    cell = r.getCell(72);
                    returnStringValue = ExcelReader.returnStringValue(cell);
                    returnStringValue = returnStringValue.replace("’", "'");
                    if (returnStringValue.equalsIgnoreCase("zotero")) {
                        ai.setSource(AbstractItem.SOURCE.ZOTERO);
                        if (ai.getContent() != null && !ai.getContent().isBlank() && ai.isOnTopic()) {
                            zoteroRefs.add(ai);
                        }
                    } else if (returnStringValue.equalsIgnoreCase("berger")) {
                        ai.setSource(AbstractItem.SOURCE.BERGER);
                        if (ai.getContent() != null && !ai.getContent().isBlank() && ai.isOnTopic()) {
                            bergerRefs.add(ai);
                        }
                    } else if (returnStringValue.equalsIgnoreCase("wos")) {
                        ai.setSource(AbstractItem.SOURCE.WOS);
                        if (ai.getContent() != null && !ai.getContent().isBlank() && ai.isOnTopic()) {
                            keywordSearchRefs.add(ai);
                        }
                    } else if (returnStringValue.equalsIgnoreCase("hartman_2023")) {
                        ai.setSource(AbstractItem.SOURCE.HARTMANN_2023);
                        if (ai.getContent() != null && !ai.getContent().isBlank() && ai.isOnTopic()) {
                            hartmannRefs.add(ai);
                        }

                    } else if (returnStringValue.equalsIgnoreCase("humphreys")) {
                        ai.setSource(AbstractItem.SOURCE.HUMPHREYS);
                        if (ai.getContent() != null && !ai.getContent().isBlank() && ai.isOnTopic()) {
                            humphreysRefs.add(ai);
                        }
                    } else if (returnStringValue.equalsIgnoreCase("rambocas")) {
                        ai.setSource(AbstractItem.SOURCE.RAMBOCAS);
                        if (ai.getContent() != null && !ai.getContent().isBlank() && ai.isOnTopic()) {
                            rambocasRefs.add(ai);
                        }
                    }
                    else {
                        System.out.println("source: " + returnStringValue);
                        throw new Exception("error source not found");
                    }
                    if (ai.isOnTopic() && ai.getContent() != null && !ai.getContent().isBlank() && ai.getSource() != null) {
                        abstractItems.add(ai);
                    }
                    rowNumber++;
                }
            }

        }
        // report
        System.out.println("humphreys: " + humphreysRefs.size());
        System.out.println("berger: " + bergerRefs.size());
        System.out.println("rambocas: " + rambocasRefs.size());
        System.out.println("hartmann: " + hartmannRefs.size());
        System.out.println("zotero: " + zoteroRefs.size());
        System.out.println("wos: " + keywordSearchRefs.size());

        List<Source> sources = List.of(new Source("humphreys", humphreysRefs), new Source("Berger", bergerRefs), new Source("Rambocas", rambocasRefs), new Source("Zotero", zoteroRefs), new Source("WoS", keywordSearchRefs), new Source("Hartmann", hartmannRefs));
        for (Source source : sources) {
            for (Source otherSource : sources) {
                if (source.equals(otherSource)) {
                    continue;
                }
                Set<AbstractItem> temp = new HashSet(source.getRefs());
                temp.retainAll(otherSource.getRefs());
                System.out.println(source.getName() + " - " + otherSource.getName() + " : " + temp.size());
            }

        }
        return abstractItems;
    }

    public static String returnStringValue(Cell cell) {
        if (cell == null){
            return "";
        }
        CellType cellType = cell.getCellType();

        switch (cellType) {
            case NUMERIC -> {
                double doubleVal = cell.getNumericCellValue();
                if (doubleVal == (int) doubleVal) {
                    int value = Double.valueOf(doubleVal).intValue();
                    return String.valueOf(value);
                } else {
                    return String.valueOf(doubleVal);
                }
            }
            case STRING -> {
                return cell.getStringCellValue();
            }
            case ERROR -> {
                return String.valueOf(cell.getErrorCellValue());
            }
            case BLANK -> {
                return "";
            }
            case FORMULA -> {
                return cell.getCellFormula();
            }
            case BOOLEAN -> {
                return String.valueOf(cell.getBooleanCellValue());
            }

        }
        return "error decoding string value of the cell";
    }

}
