/*Allows a student to register for a course. The result entered into the database depends if the course requires instructor approval or not.*/
import java.util.*;
import java.sql.*;
public class RegisterforClass {

    private Student stud;
    private Course cor;
    private Check_Compatibility Compatible;
    private boolean check = true;
    private Connection con;

    public RegisterforClass(Student student, Course course,Connection co){
        stud = student;
        cor = course;
        Compatible = new Check_Compatibility();
        con = co; // Gets the database Connection
    }

    public int execute(){
        for(int i=0;i<stud.getCourses().size();i++){
            check = check & Compatible.execute(stud.getCourses().get(i).getSchedule(),cor.getSchedule());
        }
        if (stud.getCourseNames().contains(cor.viewName())){
            System.out.println("Student already has the course: " +cor.viewName());
            return 0;
        }
        if (!check){
            System.out.println("Courses clash in your timetable.");
        }
        else{
            try {
                Statement stmt = con.createStatement();
                if (cor.getPermission()) { // If course requires faculty permission
                    cor.add_PendingStudent(stud); // Add student to pending course list
                    int rs=stmt.executeUpdate("insert into pending_requests values('" + cor.getcode()  +"'," + String.valueOf(stud.getID()) + ");");
                    return rs; // No of table updates
                } else { // Add student to course
                    stud.addCourse(cor); // Add course to student
                    cor.addStudent(stud); // Add student to course
                    int rs=stmt.executeUpdate("insert into Course_Student values('" + cor.getcode()  +"'," + String.valueOf(stud.getID()) + ",null);");
                    return rs; // No of table updates
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        return 0;
    }

}
