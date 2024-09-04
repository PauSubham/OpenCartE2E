package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtility {

	public FileInputStream fileInput;
	public FileOutputStream fileOutput;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle cellStyle;
	String path;

	public ExcelUtility(String path) {
		this.path = path;
	}

	public int getRowCount(String sheetName) throws IOException {
		fileInput = new FileInputStream(path);
		workbook = new XSSFWorkbook(fileInput);
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		workbook.close();
		fileInput.close();
		return rowCount;
	}

	public int getCellCount(String sheetName, int rowNum) throws IOException {
		fileInput = new FileInputStream(path);
		workbook = new XSSFWorkbook(fileInput);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		int cellCount = row.getLastCellNum();
		workbook.close();
		fileInput.close();
		return cellCount;
	}

	public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
		fileInput = new FileInputStream(path);
		workbook = new XSSFWorkbook(fileInput);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(colNum);

		DataFormatter dataFormatter = new DataFormatter();
		String data = null;
		try {
			data = dataFormatter.formatCellValue(cell);
		} catch (Exception ex) {
			data = "";
		}

		workbook.close();
		fileInput.close();
		return data;
	}

}
