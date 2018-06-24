/*  Purpose of this class is to provide the functionality of a faculty to assign student grade. It takes the student, course code and grade
 as parameters and sets the grade if the student is registered in the course. Otherwise, it warns the faculty.
 It implements Composite design pattern. A faculty can assign grade to a single student or a group of students by calling the same execute method. So the faculty class
 does not care where it passes a leaf(single student) or a composite (group of students - say group project for instance). The passed object is assigned the grade.
* */

import java.util.*;
import java.sql.*;


public class assign_grade {
    private Student student; // Student
    private List<Student> students; // List of students for which the grade needs to be assigned
    private String grade; // Grade to be assigned.
    private String Coursecode; //Course for which grade needs to be assigned.
    private Connection con;

    // Leaf constructor for a single student
    public assign_grade(Student s, String coursecode,String Grade, Connection co) {
        student = s;
        grade = Grade;
        Coursecode = coursecode;
        con = co;  // Get Database Connection
    }

    // Composite constructor for a group of students
    public assign_grade(List<Student> studs, String coursecode, String Grade,Connection co){
        students = studs;
        grade = Grade;
        Coursecode = coursecode;
        con = co;  // Get Database Connection
    }



    // Execute the operation
    public int execute() {
       if (students == null){
           return leaf(); // Return leaf method if leaf(single student) passed
       }
       else{
           return composite(); // Return Composite method if composite(list of students) passed
       }
    }

    public int leaf(){
        Grades check = new Grades();
        if(check.AcceptableValues().contains(grade) ){
            if (student.setGrade(Coursecode,grade)){  // student has course
                int rs=0; // no of sql updates
                try {   // Update database
                    Statement stmt=con.createStatement();
                    rs=stmt.executeUpdate("update course_student set grade = '" + grade + "' where course_code = '" + Coursecode + "' and StudentID = "+ String.valueOf(student.getID()) + ";");
                    System.out.println("Completed");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return rs;
            }
            else{
                return 0;}
        }
        else{
            System.out.println("Grade not in acceptable scale");
            return 0;
        }
    }

    public int composite(){
        Grades check = new Grades();
        int rs=0; // no of sql updates
        if(check.AcceptableValues().contains(grade) ){
            for(int i=0;i<students.size();i++){
            if (students.get(i).setGrade(Coursecode,grade)){  // student has course
                try {   // Update database
                    Statement stmt=con.createStatement();
                    rs+=stmt.executeUpdate("update course_student set grade = '" + grade + "' where course_code = '" + Coursecode + "' and StudentID = "+ String.valueOf(students.get(i).getID()) + ";");
                    System.out.println("Completed");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                return 0;
            }
        }}
        else{
            System.out.println("Grade not in acceptable scale");
            return 0;
        }
        return rs;
    }

}