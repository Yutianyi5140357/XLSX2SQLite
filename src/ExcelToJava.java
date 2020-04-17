import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExcelToJava {
     public ArrayList<Map<String,String>> list = null;
     public int[] flag = null;
     public int char_max[];
     public String fieldname[];
     
     public ExcelToJava(String filePath, String sheetname){
    	 Workbook wb =null;
    	 Sheet sheet = null;
         Row row = null;
         Row row0 = null;
         
         String cellData = null;
         wb = readExcel(filePath);
         if(wb != null){
        	    list = new ArrayList<Map<String,String>>();
 	            sheet = wb.getSheet(sheetname);
 	            int rownum = sheet.getPhysicalNumberOfRows();
 	            row = sheet.getRow(0);
 	            row0 = sheet.getRow(0);
 	            int colnum = row.getPhysicalNumberOfCells();
 	            fieldname = new String[colnum];
             
 	            flag = new int[colnum];
 	            char_max = new int[colnum];
 	            int symbol = 0;
             
           for (int i=1; i<rownum; i++) {
                 Map<String,String> map = new LinkedHashMap<String,String>();
                 row = sheet.getRow(i);
                 if(row !=null){
                     for (int j=0; j<colnum; j++){
                         cellData = (String) getCellFormatValue(row.getCell(j));
                         fieldname[j] = (String) getCellFormatValue(row0.getCell(j));
                         map.put(fieldname[j], cellData);
                         symbol = getcellType(row.getCell(j));
                         if(symbol ==2){
                        	 if(cellData.length() >= char_max[j])
                        		 char_max[j] = cellData.length();
                         }
                         if((symbol == 2) && (flag[j] != 2)){
                         	flag[j] = 2;
                         }
                         else if(symbol == 1 && (flag[j] == 0)){
                         	flag[j] = 1;
                         }
                     }
                 }else{
                     break;
                 }
                 list.add(map);
             }
         }
    	 
     }
     @SuppressWarnings("resource")
 	public static Workbook readExcel(String filePath){
         Workbook wb = null;
         if(filePath==null){
             return null;
         }
         String extString = filePath.substring(filePath.lastIndexOf("."));
         InputStream is = null;
         try {
             is = new FileInputStream(filePath);
              if(".xlsx".equals(extString)){
                 return wb = new XSSFWorkbook(is);
             }else{
                 return wb = null;
             }
             
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         return wb;
     }
     @SuppressWarnings("deprecation")
 	public static Object getCellFormatValue(Cell cell){
         Object cellValue = null;
         if(cell!=null){
             switch(cell.getCellType()){
             case Cell.CELL_TYPE_NUMERIC:{
                 cellValue = String.valueOf(cell.getNumericCellValue());
     	        Long longVal = Math.round(cell.getNumericCellValue());
     	        Double doubleVal = cell.getNumericCellValue();
     	        if(Double.parseDouble(longVal + ".0") == doubleVal){  
     	              cellValue = String.valueOf(longVal);
     	        }
                 break;
             }
             case Cell.CELL_TYPE_STRING:{
                 cellValue = cell.getRichStringCellValue().getString();
                 break;
             }
             default:
                 cellValue = "";
             }
         }else{
             cellValue = "";
         }
         return cellValue;
     }
     
     @SuppressWarnings("deprecation")
 	public static int getcellType(Cell cell){
     	int cellType = 0;
     	if(cell!=null){
     		switch(cell.getCellType()){
     		case Cell.CELL_TYPE_NUMERIC:{
     	        Long longVal = Math.round(cell.getNumericCellValue());
     	        Double doubleVal = cell.getNumericCellValue();
     	        if(Double.parseDouble(longVal + ".0") == doubleVal){   
     	              cellType = 0;
     	          }
     	          else{
     	              cellType = 1;
     	          }
     			break;
     		}
     		default:
     			cellType = 2;
     			break;
     		}
     	}else{
     		cellType = 2;
     	}
     	return cellType;
     }
     
}