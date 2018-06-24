import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import java.sql.*;

public class Register_Class_Test {
    private static Student s1;
    private static Course MPCS_52011;
    private static Course MPCS_53110;
    private static Course MPCS_51410;
    //private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static databaseconn connector = new Connect();  // Sets database connection
    private static Connection con = connector.conn();
    private static AbstractFactory factory;

    @BeforeClass
    public static void setUp() throws Exception{
        s1 = new Student("Jubil D",38957483); // Student who wishes to register
        factory = new CourseAbstractFactory(con,"MPCS_51410","MPCS_52011","MPCS_53110");
        MPCS_51410 = factory.getClasses().get(0);
        MPCS_52011 = factory.getClasses().get(1);
        MPCS_53110 = factory.getClasses().get(2);

    }
    @AfterClass
    public static void tearDown() throws Exception{
        System.out.println("Test completed");
        //Reset the database to it's previous state before the two tests
        try{
            Statement stmt2=con.createStatement();
            stmt2.executeUpdate("delete from  Course_Student where course_code = '" + MPCS_52011.getcode()  +"' and StudentID = " + String.valueOf(s1.getID()) + ";");
            stmt2.executeUpdate("delete from  pending_requests where course_code = '" + MPCS_51410.getcode()  +"' and StudentID = " + String.valueOf(s1.getID()) + ";");
        }catch(Exception e){
            System.out.println(e);
        }
    }
  // Both Test 1 and Test 2 are executed on the notion that there are no timetable clashes.
    @Test
    public void test1() {
        // Test 1 Checks if student can register for class when permission required is false
        RegisterforClass rc1 = new RegisterforClass(s1,MPCS_52011,con);
        int sqlUpdates = rc1.execute();
        assertTrue(s1.getCourseNames().contains(MPCS_52011.viewName())); // Check if course present
        List<Integer> students = new ArrayList<Integer>();
        try { // Check database
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select StudentID from Course_Student where course_code = '" + String.valueOf(MPCS_52011.getcode())+"';");
            while(rs.next()){
                students.add(rs.getInt(1)); // Add students of course in array list
            } }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        assertTrue(students.contains(s1.getID())); // Check if student present for the course
    }
    @Test
    // Test 2 Checks if student is added to the pending requests list and not to the course list when permission required is true.
    public void test2(){
        RegisterforClass rc2 = new RegisterforClass(s1,MPCS_51410,con);
        int sqlUpdates = rc2.execute();
        assertFalse(s1.getCourseNames().contains(MPCS_51410.viewName())); // Check if course not present for student
        assertTrue(MPCS_51410.get_PendingStudents().contains(s1)); // Check if student added to pending request list
        List<Integer> students = new ArrayList<Integer>();
        List<Integer> pending_students = new ArrayList<Integer>();
        try { // Check database
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select StudentID from Course_Student where course_code = '" + String.valueOf(MPCS_51410.getcode())+"';");
            while(rs.next()){ // Add actual students to students list
                students.add(rs.getInt(1)); // Add students of course in array list
            }
            ResultSet rs2 = stmt.executeQuery("select StudentID from pending_requests where course_code = '" + String.valueOf(MPCS_51410.getcode())+"';");
            while(rs2.next()){ // Add pending students to pending_students list
                pending_students.add(rs2.getInt(1)); // Add students of course in array list
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        assertFalse(students.contains(s1.getID())); // Check if student not present for the course
        assertTrue(pending_students.contains(s1.getID())); // Check if student present in pending requests
    }

    @Test
    // Test 3 checks if the course is not added when the course schedule clashes with student's timetable.
    public void test3(){
        RegisterforClass rc3 = new RegisterforClass(s1,MPCS_53110,con);
        int sqlUpdates = rc3.execute();
        assertEquals(0,sqlUpdates); // Check if no updates were made to the database.
    }
}

