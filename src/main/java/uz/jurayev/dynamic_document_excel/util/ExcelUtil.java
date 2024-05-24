package uz.jurayev.dynamic_document_excel.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class ExcelUtil {

    public static ByteArrayInputStream objectsToExcel(Object[] objects) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Sheet");
            Class<?> aClass = objects[0].getClass();
            Field[] fields = aClass.getDeclaredFields();

            CellStyle cellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 13);
            font.setFontName("Calibri");
            font.setBold(true);
            cellStyle.setFont(font);
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            sheet.setSelected(true);

            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 1; col < fields.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellStyle(cellStyle);

                String absoluteFieldName = String.valueOf(fields[col]);
                String fieldName = absoluteFieldName.substring(absoluteFieldName.lastIndexOf(".") + 1);
                cell.setCellValue(camelToSnake(fieldName));
            }

            int rowIdx = 1;
            ObjectMapper objectMapper = new ObjectMapper();
            for (Object object : objects) {
                Row row = sheet.createRow(rowIdx++);

                String s = objectMapper.writeValueAsString(object);
                JsonNode jsonNode = objectMapper.readTree(s);
                for (int col = 1; col < fields.length; col++) {
                    String absoluteFieldName = String.valueOf(fields[col]);
                    String fieldName = absoluteFieldName.substring(absoluteFieldName.lastIndexOf(".") + 1);
                    JsonNode data = jsonNode.get(fieldName);
                    if (data != NullNode.getInstance()) {
                        String customerName = data.get("customer_name").toString();
                        row.createCell(col).setCellValue(customerName);
                    }
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }


    public static List<Map<Object, Object>> excelToObject(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<Map<Object, Object>> objects = new ArrayList<>();

            // first row that is header excel
            Row rowHeader = sheet.getRow(0);

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Map<Object, Object> map = new HashMap<>();
                Iterator<Cell> cellsInBody = currentRow.iterator();
                while (cellsInBody.hasNext()) {
                    for (int i = 0; i < currentRow.getPhysicalNumberOfCells(); i++) {

                        // cell header excel
                        Cell cellHeader = rowHeader.getCell(i);
                        Cell currentCell = cellsInBody.next();
                        switch (currentCell.getCellType()) {
                            case STRING:
                                map.put(cellHeader.getStringCellValue(), currentCell.getStringCellValue());
                                break;
                            case NUMERIC:
                                map.put(cellHeader.getStringCellValue(), currentCell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                map.put(cellHeader.getStringCellValue(), currentCell.getBooleanCellValue());
                                break;
                            case ERROR:
                                map.put(cellHeader.getStringCellValue(), currentCell.getErrorCellValue());
                                break;
                            case _NONE, BLANK:
                                map.put("col " + i, "");
                                break;
                            default:
                                map.put(cellHeader.getStringCellValue(), currentCell.getDateCellValue());
                        }
                    }
                }
                objects.add(map);
            }
            workbook.close();
            return objects;
        } catch (Exception e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static String camelToSnake(String str) {

        StringBuilder result = new StringBuilder();

        // Iterating through each character in the string
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            // If the character is uppercase, append an underscore and convert to lowercase
            if (Character.isUpperCase(ch)) {
                // Avoid adding an underscore at the beginning
                if (i > 0) result.append('_');
                result.append(Character.toLowerCase(ch));
            } else {
                // Append lowercase characters directly
                result.append(ch);
            }
        }
        return result.toString();
    }

}
