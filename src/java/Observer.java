/*Interface Observer sets the methods required to be implemented by the Concrete Observers. Here we have only 1 Concrete Observer called the
* Permission_Modifier_Observer that modifies Course permission based on it's subject(Modify_Features). When the feature is 'Instructor Approval Required',
 * only then does the observer get updated.*/

import java.util.*;
import java.sql.*;

interface  Observer {
    int update(int stat_); // stat_ is the status code. '1' indicates the observer must update the course to faculty permission required, '0' sets it to not required.
}
class Permission_Modifier_Observer implements Observer{
    private Course course;
    private Connection con;

    Permission_Modifier_Observer(Course course_,Connection co){
        course = course_;
        con = co; // Gets the database Connection
    }

    public int update(int stat_){
        try{
            Statement stmt = con.createStatement();
        if (stat_==0){ // Course does not require Instructor Approval anymore
           return stmt.executeUpdate("update Course set permission_required = 0 where course_code = '" + course.getcode()+ "';");
        }
        else{  // Course requires Instructor Approval
           return stmt.executeUpdate("update Course set permission_required = 1 where course_code = '" + course.getcode()+ "';");
        }}catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }

}




