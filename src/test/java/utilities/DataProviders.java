package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name = "loginData")
	public String[][] getLoginData() throws IOException
	{
		String path = ".\\testData\\TestData.xlsx";
		
		ExcelUtility xlutil = new ExcelUtility (path);
		
		int totalRows = xlutil.getRowCount("Sheet1");
		int totalCols = xlutil.getCellCount("Sheet1", 1);
		
		String loginData[][] = new String[totalRows][totalCols];
		
		for (int i = 1; i<= totalRows; i++) // Starting with 1 Because the 0 index contains the Header part
		{
			for (int j=0; j<totalCols; j++)
			{
				loginData[i-1][j] = xlutil.getCellData("Sheet1", i, j); // Starting with 0 as the array starts with 0)
			}
		}
		
		return loginData;
	}

}
