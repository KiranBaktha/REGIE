/*Connect establishes the database connection. It is created as a separate class to maintain the principle of dependency inversion. The high level classes
* are only dependent on the connection not on the type of database in the connection.*/
import java.sql.*;

interface databaseconn{
    Connection conn();
    void close(Connection con);

}

class Connect implements databaseconn{
    private String URL = "jdbc:mysql://localhost:3306/" +  "kiranDB";
    private String uname = "root";
    private String pass = "greatbluesky";
    private Connection con;


    public Connection conn(){
        try{
            con = DriverManager.getConnection(URL,uname,pass);
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        return con;
    }

    public void close(Connection con){
        try{
            con.close();
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }

}
