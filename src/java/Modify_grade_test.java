/*Tests the Modify Grade Functionality.*/
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.*;



@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Run tests in order of their name
public class Modify_grade_test{
    private Student s1;
    private Course MPCS_51200;
    private Student Oliver,Luke,Dipanshu;
    private List<Student> students; // For composite student test
    private Course MPCS_51410;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private databaseconn connector = new Connect();  // Sets database connection
    private Connection con = connector.conn();
    private AbstractFactory factory;

    @Before
    public void setUp() throws Exception{
        s1 = new Student("Kiran Baktha",12745243); // Student grade needs to be modified to
        factory = new CourseAbstractFactory(con,"MPCS_51410","MPCS_51200");
        MPCS_51410 = factory.getClasses().get(0);
        MPCS_51200 = factory.getClasses().get(1);
        s1.addCourse(MPCS_51410);
        assign_grade ag = new assign_grade(s1,"MPCS_51410","B",con); // Assign grade first
        ag.execute(); // Execute Grade Assignment Operation
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
        assign_grade ag1 = new assign_grade(students, "MPCS_51200", "F",con); // Assign grade to a list of students
        ag1.execute(); // Execute Operation
        System.setOut(new PrintStream(outContent)); // Set up to read printed message
    }
    @After
    public void tearDown() throws Exception{
        System.out.println("Test completed");
    }

    @Test
    public void test1(){

        // Test 1  (Checks if faculty cannot modify grade to a student if the student does not have that class.)
        Modify_grade mg2 = new Modify_grade(s1,"MPCS_53110","A","Error in grade entered",con);
        int sqlUpdates = mg2.execute(); // Execute the Operation
        // Check if faculty is alerted because Jason does not have that course.
        assertEquals("Course MPCS_53110 Not Present For Student Kiran Baktha\nNo updates Made.".replaceAll("\n","").replaceAll("\r",""),outContent.toString().replaceAll("\n","").replaceAll("\r",""));
        assertEquals(0,sqlUpdates); // Ensure no database updates
    }
    @Test
    public void test2() {
        // Test 2 (Checks if course grade can be modified accurately for a leaf (single student))
        Modify_grade mg = new Modify_grade(s1, "MPCS_51410", "A", "re-evaluation approved",con);
        int sqlUpdates = mg.execute(); // Execute the Operation
        assertEquals("A", s1.viewGrades().get("MPCS_51410")); // Check if grade has been modified
        assertEquals(1,sqlUpdates); // Ensure only 1 update was made to the Database
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select grade from course_student where course_code = '" + MPCS_51410.getcode()  + "' and StudentID = "+ String.valueOf(s1.getID()) + ";");
            while(rs.next()){
                assertEquals("A",rs.getString(1)); // Check for the correct grade in Database
            } }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }

    @Test
    public void test3(){
        // Test 3 Checks if grade can be modified for a list of students(Composite).
        Modify_grade mg = new Modify_grade(students, "MPCS_51200", "P","grading error",con); // Modify grade to a list of students
        int sqlUpdates = mg.execute(); // Execute Operation
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


}