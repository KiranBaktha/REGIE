/*CourseStatus_Test tests if Courses are set to open and closed states accurately.*/
import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

public class CourseStatus_Test {
    private static Student Amanda;
    private static Student Sophia;
    private static Course MPCS_52011;
    private static databaseconn connector = new Connect(); // Sets database connection
    private static Connection con = connector.conn();
    private static AbstractFactory factory; // Factory Creater

    @BeforeClass
    public static void setUp() throws Exception{
        Amanda = new Student ("Amanda Generes",24375345);
        Sophia = new Student("Sophia K",67457383);
        factory = new CourseAbstractFactory(con,"MPCS_52011");
        MPCS_52011 = factory.getClasses().get(0);
    }
    @AfterClass
    public static void tearDown() throws Exception{
        System.out.println("Test completed");
        try{ // Reset the database to it's initial state
            Statement stmt=con.createStatement();
            stmt.executeUpdate("delete from course_student where StudentID =" + String.valueOf(Amanda.getID()) + " and course_code = '" + MPCS_52011.getcode() + "';"); // Delete Amanda from course
            stmt.executeUpdate("delete from Waitlisted_Students where StudentID =" + String.valueOf(Sophia.getID()) + " and course_code = '" + MPCS_52011.getcode() + "';"); // Delete Sophia from waitlist
        }catch (Exception e){
            System.out.println(e);
        }
    }
    @Test
    public void test1() {
        // Test 1 (Checks if Course is accurately set as active state initially and student is added to the course)
        CourseAdd Course_Adder = new CourseAdd(MPCS_52011,Amanda,con);
        int updates = Course_Adder.getState().addStudent(Course_Adder,MPCS_52011,Amanda);
        assertEquals(1,updates); // Assert that an update was made
        List<Integer> StudentIDs = new ArrayList<Integer>();
        assertTrue(MPCS_52011.getStudents().contains(Amanda)); // Check if Amanda Present in course
        try{  // Check Database
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select StudentID from Course_Student where course_code = '" + MPCS_52011.getcode() + "';");
            while(rs.next()){
                StudentIDs.add(rs.getInt(1)); // Add each student ID from database
            }
        }catch (Exception e){
            System.out.println(e);
        }
        assertTrue(StudentIDs.contains(Amanda.getID())); // Check if Amanda present in course
    }
    @Test
    public void test2() {
        // Test 1 (Checks if Course is accurately set as closed state when it is full and student is added to the waitlist)
        MPCS_52011.setStudentCount(60); // Set the course as full for testing purposes.
        CourseAdd Course_Adder = new CourseAdd(MPCS_52011,Sophia,con);
        int updates = Course_Adder.getState().addStudent(Course_Adder,MPCS_52011,Sophia);
        assertEquals(1,updates); // Assert that an update was made
        assertTrue(MPCS_52011.getWaitingList().contains(Sophia)); // Check if Sophia present in waitlist
        List<Integer> StudentIDs = new ArrayList<Integer>();
        try{  // Check Database
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select StudentID from Waitlisted_Students where course_code = '" + MPCS_52011.getcode() + "';");
            while(rs.next()){
                StudentIDs.add(rs.getInt(1)); // Add each student ID from database
            }
        }catch (Exception e){
            System.out.println(e);
        }
        assertTrue(StudentIDs.contains(Sophia.getID())); // Check is Sophia present in waitlist
    }

}