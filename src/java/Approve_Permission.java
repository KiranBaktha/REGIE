/* Purpose of this class is to provide faculty with the functionality to approve permission for students who want to join the class. It takes in
faculty, student and the course as parameters.
* */


import java.util.*;
import java.sql.*;
public class Approve_Permission  {

    private Faculty faculty;
    private Student student;
    private Course course;
    private boolean condition = false;
    private Connection cone;

    public Approve_Permission(Faculty fac, Student stud, Course cor, Connection co) {
        faculty = fac;
        student = stud;
        course = cor;
        cone = co; // Get Database Connection
    }

    public int execute() {
        if (faculty.getCourseNames().contains(course.viewName())) { // If faculty is teaching he the course
           if (course.get_PendingStudents().contains(student)){ // If student has a request in the course
               condition = true;
               if (!(course.getStudents().contains(student))) { // If student not present in course already
                   course.addStudent(student); // Add student to course
                   student.addCourse(course); // Add course to student
                   int rs=0; // no of sql updates
                   try {   // Update database
                       Statement stmt=cone.createStatement();
                       rs=stmt.executeUpdate("insert into Course_Student values('" + course.getcode()  +"'," + String.valueOf(student.getID()) + ",null);");
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   return rs;
               }
            }
        }
        if (!condition){  // Approve Permission request fails.
         System.out.println("Either Faculty not teaching the course or Student not requested for the course.");
         return 0;
        }
        return 0;
    }
}