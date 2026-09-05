package framework.utils;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {

        ExcelReader reader = new ExcelReader();

        int rowCount = reader.getRowCount();

        Object[][] data = new Object[rowCount - 1][2];

        for (int i = 1; i < rowCount; i++) {

            data[i - 1][0] = reader.getCellData(i, 0);
            data[i - 1][1] = reader.getCellData(i, 1);
        }

        return data;
    }
}