import java.util.*;

/*
* Course class represents the actual Course object in REGIE. It has a name attribute (the course name), Students attribute(the list of
* students enrolled in the course) and Instructors attribute(the list of instructors for the course). REGIE_Course interface states
* the methods that need to be implemented by a course in REGIE.
* */

interface REGIE_Course{
    String viewName();  // View the course name
    void addStudent(Student s);  // Add a Student to the Course
    void removeStudent(Student s); // Remove a Student from the Course
    List<Student> getStudents();   // View the Students in the Course
    void addFeature(String feature);  // Add a feature to the Course
    List<String> viewFeatures();   // View the course features
    void addInstructor(Faculty f);   // Add an instructor to the course
    void add_PendingStudent(Student stud); // Add a students request to the course for faculty approval. Not part of my domain so implemented as a model here.
    List<Student> get_PendingStudents();  // View the list of students whose request needs to be approved
    String getcode(); // Get the course code
    boolean getPermission(); // Get if permission is required for the course.
    String getSchedule(); // Get the schedule of the course which is required to perform slot clash check with students.
    void removefeature(String feature); // Removes a feature from the course
    int getStudentCount(); // Student count is used for the State Pattern while Course Registration. (See CourseAdd.java for more info)
    void setStudentCount(int count);  // This method has been created for test purposes.
    void addtoWaitingList(Student stud); // Add student to waiting list when full
    List<Student> getWaitingList(); // Returns the students in the waitlist.
}

public class Course implements REGIE_Course {
    private String name;  // Course Name
    private List<String> Features = new ArrayList<String>();  // Course Features
    private List<Student> Students = new ArrayList<Student>(); // Students in Course
    private List<Student> Pending_Students = new ArrayList<Student>(); // Students in Course
    private List<Faculty> Instructors = new ArrayList<Faculty>(); // Course Instructors[Includes TA]
    private boolean feat = false; // Whether course features can be set
    private String code; // Course code
    private boolean permission; // If permission required
    private String schedule;
    private int NumberOfStudents;
    private List<Student> WaitingList = new ArrayList<Student>();


    // The course name goes here. Course class name to be created with course code.
    // Example: Course MPCS_51410 = new Course('Object Oriented Programming')
    public Course(String coursename,String coursecode,String Schedule, boolean permi) {
        name = coursename; code = coursecode;
        permission = permi;
        schedule = Schedule;
    }

    public String viewName() {
        return name;
    }

    public boolean getPermission(){
        return permission;
    }

    public void addStudent(Student s) {
        Students.add(s);
    }

    public List<Student> getStudents(){
        return Students;
    }

    public void addFeature(String feature) {
        Features.add(feature);
    }

    public List<String> viewFeatures(){
        return Features;
    }

    public void addInstructor(Faculty f){
        Instructors.add(f);
    }

    public void add_PendingStudent(Student stud){
        Pending_Students.add(stud);
    }

    public List<Student> get_PendingStudents(){
        return Pending_Students;
    }

    public String getcode() { return code;}

    public String getSchedule(){
        return schedule;
    }

    public void removeStudent(Student s){
        Students.remove(s);
    }

    public void removefeature(String feature){
        Features.remove(feature);
    }

    public int getStudentCount(){
        return NumberOfStudents;
    }

    public void setStudentCount(int count){
        NumberOfStudents = count;
    }

    public void addtoWaitingList(Student s){
        WaitingList.add(s);
    }

    public List<Student> getWaitingList(){
        return WaitingList;
    }

}
