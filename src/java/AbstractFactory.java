/**Interface AbstractFactory establishes the required methods to be implemented by an Abstract Factory. CourseAbstractFactory creates courses for the test classes.
 *This ensures that the test class is not aware of the course details and all the required courses for the test are created. They only know the course code.
 * It also increases convenience by not having to repeat the constructor parameters again.
 */

import java.util.*;
import java.sql.*;
public abstract class AbstractFactory{
    abstract List<Course> getClasses();
}

class CourseAbstractFactory extends AbstractFactory{

    private List<Course> classes = new ArrayList<Course>();
    private Connection con ;

    public CourseAbstractFactory(Connection co,String ... strings){
        con = co;
        try{
            Statement stmt=con.createStatement();
            for(String code:strings){
                ResultSet rs = stmt.executeQuery("select * from Course where course_code = '" + code +"';");
                while(rs.next()){ // Create a course object based on details in the database
                    classes.add(new Course(rs.getString(2),rs.getString(1),rs.getString(4),rs.getBoolean(5)));
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public List<Course> getClasses(){
        return classes;
    } // Return created Course Objects.


}

















