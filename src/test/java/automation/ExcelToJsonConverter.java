package automation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelToJsonConverter {

    public static void convertExcelToJson(String excelFilePath, String jsonFilePath) {
        try (FileInputStream excelFile = new FileInputStream(new File(excelFilePath))) {
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheet("Limitation");

            Map<String, Map<String, String>> dataMap = new LinkedHashMap<>();
            Row keyRow = sheet.getRow(1);  // Assuming that the first row (index 1) contains keys.

            for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;
                String objectName = row.getCell(0).getStringCellValue();
                Map<String, String> keyValueMap = new LinkedHashMap<>();

                for (int colIndex = 1; colIndex < row.getLastCellNum(); colIndex++) {
                    Cell keyCell = keyRow.getCell(colIndex);
                    Cell valueCell = row.getCell(colIndex);
                    if (keyCell != null && valueCell != null) {
                        String key = keyCell.getStringCellValue();
                        String value = getProcessedValue(valueCell);
                        keyValueMap.put(key, value);
                    }
                }
                if (!keyValueMap.isEmpty()) {
                    dataMap.put(objectName, keyValueMap);
                }
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(jsonFilePath), dataMap);
        } catch (IOException e) {
            System.err.println("Error while converting Excel to JSON: " + e.getMessage());
        }
    }

    private static String getProcessedValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return processStringValue(cell.getStringCellValue());
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.format("%.0f", cell.getNumericCellValue());  // Remove decimal part if it's a whole number
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return evaluateFormulaCell(cell);
            default:
                return "";
        }
    }

    private static String processStringValue(String value) {
        // Process string to normalize true/false values or extract numbers
        if (value.equalsIgnoreCase("TRUE")) {
            return "true";
        } else if (value.equalsIgnoreCase("FALSE")) {
            return "false";
        } else {
            // Extract numbers from strings if needed
            Matcher matcher = Pattern.compile("\\d+").matcher(value);
            return matcher.find() ? matcher.group() : value;
        }
    }

    private static String evaluateFormulaCell(Cell cell) {
        switch (cell.getCachedFormulaResultType()) {
            case NUMERIC:
                return String.format("%.0f", cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
