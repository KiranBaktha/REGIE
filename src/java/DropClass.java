/*
* DropClass allows a student to drop a course from his timetable.
* */

import java.util.*;
import java.sql.*;


public class DropClass {

    private Student stud;
    private Course cor;
    private Connection con;
    private int updates = 0;

    public DropClass(Student student, Course course, Connection co){
        stud = student;
        cor = course;
        con = co; // Gets the database connection
    }

    public int execute(){
        stud.removeCourse(cor); // Remove course from student
        cor.removeStudent(stud); // Remove student from course
        try{
            Statement stmt2=con.createStatement();
            // Update database
            updates = stmt2.executeUpdate("delete from  Course_Student where course_code = '" + cor.getcode()  +"' and StudentID = " + String.valueOf(stud.getID()) + ";");
        }catch(Exception e){
            System.out.println(e);
        }
        return updates;
    }
}
