package Menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public class Connect{
	
	// Nom du pilote JDBC et URL de la base de données
	 static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mariadb://localhost:3306/ecole";
	
	 //  Identifiants de la base de données
	 static final String USER = "root";
	 static final String PASS = "";
	 ResultSet rs;
	 Connection conn = null;
	 Statement stmt = null;

	 
	public String traiterLigne (String sql){
		String out = "";
		try {

			// Enregister le pilote JDBC
		    Class.forName("org.mariadb.jdbc.Driver");
	
		    // Ouvrir une connection
		    Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    	    
			// Création de la déclaration;
		    Statement stmt = conn.createStatement();
		    
		    ResultSet rs = stmt.executeQuery(sql);
		    ResultSetMetaData meta = rs.getMetaData();
		    int colCount = meta.getColumnCount();
		    
		    //System.out.println(colCount);

		    while (rs.next()){
		    	String ligne = "";
		    	
		        for (int col=1; col <= colCount; col++){
		        	Object value = rs.getObject(col);
		            if (value != null){
		            	// Pour aligner les colonnes avec des tabulations
		            	int tabcount=3;
		            	ligne += value.toString();
		            	tabcount -= value.toString().length()/8;
		            	for (int tab=0; tab<tabcount; tab++)
		            		ligne += "\t";
		            }
		        }
		       out += ligne + "\n";
		    }
    		rs.close();
 		    stmt.close();
 		    conn.close();
		}
		catch(SQLException se){
		    //Handle errors for JDBC
		    	se.printStackTrace();
		     
		}
		catch(Exception e){
		    //Handle errors for Class.forName
		    e.printStackTrace();
		}
		finally{
		    //finally block used to close resources
		    try{
		       if(stmt!=null)
		          stmt.close();
		    }
		    catch(SQLException se2){
		    }// nothing we can do
		    try{
		       if(conn!=null)
		          conn.close();
		    }
		    catch(SQLException se){
		       se.printStackTrace();
		    }//end finally try
		 }
		return out;
	}
	
	
	public ArrayList<String> traiterColonne(String sql){
		
		
		ArrayList<String> outs = new ArrayList<String>();
		
		try {
			
		    //Enregister le pilote JDBC
		    Class.forName("org.mariadb.jdbc.Driver");
	
		    //Ouvrir une connection
		    Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    	    
			// Création de la déclaration
		    Statement stmt = conn.createStatement();
			
		    
		    ResultSet rs = stmt.executeQuery(sql);
		    String ligne = null;
		    
		    while (rs.next()){
		    	
		    	Object value = rs.getObject(1);
		    	if (value != null)
		    		ligne = value.toString();
		    	outs.add(ligne);
		    }

    		rs.close();
 		    stmt.close();
 		    conn.close();
 		    
		}
		catch(SQLException se){
		    //Handle errors for JDBC
		    se.printStackTrace();
		}
		catch(Exception e){
		    //Handle errors for Class.forName
		    e.printStackTrace();
		}
		finally{
		    //finally block used to close resources
		    try{
		       if(stmt!=null)
		          stmt.close();
		    }
		    catch(SQLException se2){
		    }// nothing we can do
		    try{
		       if(conn!=null)
		          conn.close();
		    }
		    catch(SQLException se){
		       se.printStackTrace();
		    }//end finally try
		 }
		return outs;
	}
	
	
	
	 public static void main(String[] args) {			
	 }
	

		    
}
        
        
   

