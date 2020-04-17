import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;
 
public class SQLiteTest
{
	
  public static void JavaToSQLite(String dbname, String tablename, ExcelToJava etj) throws ClassNotFoundException
  {


	  String st;
	  int index = 1;
    Class.forName("org.sqlite.JDBC");
 
    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection("jdbc:sqlite:" + dbname + ".db");
      Statement statement = connection.createStatement();
 
      st = "create table " + tablename + " (PK int primary key not null, ";
      for(int i=0; i<etj.fieldname.length; i++){
    	  st += etj.fieldname[i];
    	  if(etj.flag[i] == 0){
    		 st += " int "; 
    	  }
    	  else if(etj.flag[i] == 1){
    		  st += " real ";
    	  }
    	  else if(etj.flag[i] == 2){
    		  st += " char(" + etj.char_max[i] +")";
    	  }
    	  if(i != etj.fieldname.length-1){
    		  st += ", "; 
    	  }
    	 
      }
      st += ")";
      statement.executeUpdate(st);

      
      for(Map<String,String> map : etj.list){
    	  st = "insert into " + tablename +" (PK, ";
    	  for(int j=0; j<etj.fieldname.length; j++){
    		  st += etj.fieldname[j];
    		  if(j != etj.fieldname.length-1){
    			  st += ", ";
    		  }
    	  }
    	  st += ") " + "values( " + index + ", ";
    	  int k = 0;
    	  for (Entry<String,String> entry : map.entrySet()) {
             if(etj.flag[k] == 2){
            	 st += "'" + entry.getValue() + "'";
             }
             else {
            	 st += entry.getValue();
             }
             if(k != etj.fieldname.length-1){
            	 st += ", ";
             }
             k++;
          }
    	  st += "); ";
    	  statement.executeUpdate(st);
    	  index++;
//          System.out.println(st);
      }
      
       st = "select * from sqlite_master where type = \"table\";";
       ResultSet rs = statement.executeQuery(st);
       String s;
       while(rs.next()){
    	   for(int i = 1;i<=5;i++){
    		   s = rs.getString(i);
    		   if(i == 5){
    			   System.out.println("TABLE Structure: ");
    			   System.out.println(s);
    		   }
    	   }
       }
       System.out.println("Line Number: " + index);

    }
    catch(SQLException e)
    {

      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }
}
