package framework.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class ExcelReader {

    private static final String FILE_PATH = "testdata/LoginData.xlsx";
    private static final String SHEET_NAME = "LoginData";

    public String getCellData(int rowNumber, int columnNumber) {

        try (InputStream input = ExcelReader.class.getClassLoader()
                .getResourceAsStream(FILE_PATH)) {

            if (input == null) {
                throw new RuntimeException("File not found: " + FILE_PATH);
            }

            try (XSSFWorkbook workbook = new XSSFWorkbook(input)) {

                Sheet sheet = workbook.getSheet(SHEET_NAME);

                if (sheet == null) {
                    throw new RuntimeException(
                            "Sheet not found: " + SHEET_NAME
                    );
                }

                Row row = sheet.getRow(rowNumber);

                if (row == null) {
                    throw new RuntimeException(
                            "Row not found: " + rowNumber
                    );
                }

                Cell cell = row.getCell(columnNumber);

                if (cell == null) {
                    throw new RuntimeException(
                            "Cell not found. Row: " + rowNumber +
                                    ", Column: " + columnNumber
                    );
                }

                return cell.getStringCellValue();
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read Excel file", e
            );
        }
    }

    public int getRowCount() {

        try (InputStream input = ExcelReader.class.getClassLoader()
                .getResourceAsStream(FILE_PATH)) {

            if (input == null) {
                throw new RuntimeException("File not found: " + FILE_PATH);
            }

            try (XSSFWorkbook workbook = new XSSFWorkbook(input)) {

                Sheet sheet = workbook.getSheet(SHEET_NAME);

                if (sheet == null) {
                    throw new RuntimeException(
                            "Sheet not found: " + SHEET_NAME
                    );
                }

                return sheet.getLastRowNum() + 1;
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read Excel file", e
            );
        }
    }

    public int getColumnCount() {

        try (InputStream input = ExcelReader.class.getClassLoader()
                .getResourceAsStream(FILE_PATH)) {

            if (input == null) {
                throw new RuntimeException("File not found: " + FILE_PATH);
            }

            try (XSSFWorkbook workbook = new XSSFWorkbook(input)) {

                Sheet sheet = workbook.getSheet(SHEET_NAME);

                if (sheet == null) {
                    throw new RuntimeException(
                            "Sheet not found: " + SHEET_NAME
                    );
                }

                Row headerRow = sheet.getRow(0);

                if (headerRow == null) {
                    throw new RuntimeException("Header row not found");
                }

                return headerRow.getLastCellNum();
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read Excel file", e
            );
        }
    }
}