/*Tests the Assign Grade Functionality*/
import static org.junit.Assert.*;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.*;


public class assign_grade_test {
    private Student s1; // For leaf student test
    private Student Oliver,Luke,Dipanshu;
    private List<Student> students; // For composite student test
    private Course MPCS_51410;
    private Course MPCS_51200;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private databaseconn connector=  new Connect(); // Sets database connection
    private Connection con = connector.conn();
    private AbstractFactory factory; // Factory Creater

    @Before
    public void setUp() throws Exception{
        s1 = new Student("Kiran Baktha",12745243); // Student grade needs to be assigned to
        factory = new CourseAbstractFactory(con,"MPCS_51410","MPCS_51200");
        MPCS_51410 = factory.getClasses().get(0);
        MPCS_51200 = factory.getClasses().get(1);
        s1.addCourse(MPCS_51410);
        MPCS_51410.addStudent(s1);
        Oliver = new Student("Oliver Montana",97643646);
        Luke = new Student("Luke Harington",48563547);
        Dipanshu = new Student("Dipanshu Sehjal",15683472);
        Oliver.addCourse(MPCS_51200);
        MPCS_51200.addStudent(Oliver);
        Luke.addCourse(MPCS_51200);
        MPCS_51200.addStudent(Luke);
        Dipanshu.addCourse(MPCS_51200);
        MPCS_51200.addStudent(Dipanshu);
        students = new ArrayList<Student>();
        students.add(Oliver);
        students.add(Luke);
        students.add(Dipanshu);
        System.setOut(new PrintStream(outContent)); // Set up to read printed message
    }
    @After
    public void tearDown() throws Exception{
        System.out.println("Test completed");
    }

    @Test
    public void test1() {
        // Test 1 (Checks if course grade can be assigned accurately for a leaf object (single student))
        assign_grade ag = new assign_grade(s1, "MPCS_51410", "A",con); // Assign grade to single student
        int sqlUpdates = ag.execute(); // Execute Operation
        assertEquals("A", s1.viewGrades().get("MPCS_51410")); // Check if grade is accurate
        assertEquals(1,sqlUpdates); // Ensure only 1 update was made
        try {   // check database
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select grade from course_student where course_code = '" + "MPCS_51410"  + "' and StudentID = "+ String.valueOf(s1.getID()) + ";");
            while(rs.next()){
                assertEquals("A",rs.getString(1)); // Check for the correct grade in Database
            }
        } catch(Exception e){
            System.out.println(e.getStackTrace());
        }
    }
    @Test
    public void test2(){
        // Test 3 checks if faculty assigns grade to a composite list of students, all the students in that list have the grade.
        assign_grade ag = new assign_grade(students, "MPCS_51200", "P", con); // Assign grade to a list of students
        int sqlUpdates = ag.execute(); // Execute Operation
        for(int i=0;i<students.size();i++){
            assertEquals("P",students.get(i).viewGrades().get("MPCS_51200")); // Check for correct grade in every student
        }
        assertEquals(3,sqlUpdates); // Ensure 3 updates (1 for each student) was made
        con = connector.conn();
        try {   // check database
            Statement stmt = con.createStatement();
            for(int i=0;i<students.size();i++){
                ResultSet rs=stmt.executeQuery("select grade from course_student where course_code = '" + "MPCS_51200"  + "' and StudentID = "+ String.valueOf(students.get(i).getID()) + ";");
                while(rs.next()){
                    assertEquals("P",rs.getString(1)); // Check for the correct grade in Database
                }
            }

        } catch(Exception e){
            System.out.println(e.getStackTrace());
        }
    }
    @Test
    public void test3(){
        // Test 3  (Checks if faculty cannot assign grade to a student if the student does not have that class.)
        assign_grade ag2 = new assign_grade(s1,"MPCS_53110","A",con);
        int sqlUpdates = ag2.execute(); // Checks no of updates in Database
        // Check if faculty is alerted because Kiran does not have that course.
        assertEquals("Course MPCS_53110 Not Present For Student Kiran Baktha\nNo updates Made.".replaceAll("\n","").replaceAll("\r",""),outContent.toString().replaceAll("\n","").replaceAll("\r",""));
        assertEquals(0,sqlUpdates); // Ensure no updates were made in the database.
    }
}