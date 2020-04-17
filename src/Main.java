

public class Main {
	public static void main(String[] args) throws ClassNotFoundException{
   	 String dbname = args[0];
   	 String filename = args[1];
   	 String sheetname = null;
   	 String tablename = null;
   	 
   	 if(args.length == 2){
   		 sheetname = "sheet1";
   		 tablename = filename.substring(0, filename.lastIndexOf("."));
   	 }
   	 else if(args.length == 3){
   		 sheetname = args[2];
   		 tablename = sheetname;
   	 }
   	 else if(args.length == 4){
   		 sheetname = args[2];
   		 tablename = args[3];
   	 }
   	 ExcelToJava etj = new ExcelToJava(filename,sheetname);
   	 SQLiteTest.JavaToSQLite(dbname,tablename,etj);
    }
}
