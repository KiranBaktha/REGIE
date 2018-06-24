/*  Purpose of this class is to provide the functionality of a faculty to view students of the course he is teaching. It takes the coursecode and the
faculty as parameters and displays students in that class only if the faculty is teaching that course. Otherwise it warns the faculty.
* */


import java.util.*;
public class View_Students {
    private List<Integer> StudentIDs = new ArrayList<Integer>(); // List of Student Names in the Course
    private Course course;
    private Faculty faculty;


    public View_Students(Course ccourse, Faculty fac) {
        course = ccourse;
        faculty = fac;
    }
    // Execute the Operation
    public List<Integer> execute(){
        if (faculty.getCourseNames().contains(course.viewName())){ // If faculty is teaching he the course
            for(int i =0;i<course.getStudents().size();i++) {
                StudentIDs.add(course.getStudents().get(i).getID());	// Add names to the list
            }
            return StudentIDs; // Return Student Names
        }
        else{
            System.out.println("Faculty " + faculty.getName() + " is not teaching the course " + course.viewName());
            return StudentIDs; // Return empty list
        }

    }
}