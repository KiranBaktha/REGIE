/*This file implements Courses in REGIE to have 2 states using the State Pattern. Open State is when the number of students in the course is less than 60.
* If more than 60, the course is set to closed state and students are added to the waitlist.
* CourseContext Interface is the interface required to be maintained by a state. CourseOpen and CourseClosed Classes are the 2 states. CourseAdd Class allows states to change.*/
import java.sql.*;
public interface CourseContext {
    int addStudent(CourseAdd context,Course course,Student student);
}

class CourseOpen implements CourseContext{ // Course Open State
    private Connection con;

    public CourseOpen(Connection co){
        con = co; // Get the database Connection
    }

   public int addStudent(CourseAdd context,Course course,Student student){
       RegisterforClass RC = new RegisterforClass(student,course,con);
       int updates = RC.execute(); // Add the course to student
       course.setStudentCount(course.getStudentCount()+1);
        if(course.getStudentCount()==60){ // If state needs to be changed
            context.setState(course,student,con);
        }
        return updates; //return no of updates in the database
    }
}

class CourseClosed implements CourseContext{ // Course Closed State
    private Connection con;
    public CourseClosed(Connection co){
        con = co; // Get the database Connection
    }
    public int addStudent(CourseAdd context,Course course,Student student){
        int updates=0;
        if(!(course.getWaitingList().contains(student))) {  // If student not already present in the waitlist
            course.addtoWaitingList(student);
            try {
                Statement stmt = con.createStatement();
                updates = stmt.executeUpdate("insert into Waitlisted_Students values('" + course.getcode() + "'," + String.valueOf(student.getID()) + ");");
            }catch (Exception e){
                System.out.println(e);
            }
        }
        if(course.getStudentCount()<60){ // If state needs to be changed
            context.setState(course,student,con);
        }
        return updates;
    }
}

class CourseAdd{ // Class that initializes context and allows states to change
    private CourseContext CurrentState;
    CourseAdd(Course course,Student student, Connection co){
        setState(course,student,co);
    }

    void setState(Course course,Student student, Connection co){
        if (course.getStudentCount()<60){
            CurrentState = new CourseOpen(co);
        }
        else{
            CurrentState = new CourseClosed(co);
        }
    }
    public CourseContext getState(){
        return CurrentState;
    }

}


