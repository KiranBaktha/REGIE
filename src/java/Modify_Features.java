/*This class provides the functionality of adding features to a course by faculty. The observer is asked to update only when the feature = 'Instructor Approval Required'.*/

import java.util.*;
import java.sql.*;

public class Modify_Features{
    private Course course; // Course for which a feature needs to be added
    private String feature;  // Feature to be added
    private Faculty faculty; // Faculty adding the feature
    private Observer Observing_Agent; // The Observer of subject Modify_Features
    private Connection con;

    public Modify_Features(Course course_, Faculty faculty_,String feature_, Connection co) {
        course = course_;
        feature = feature_;
        faculty = faculty_;
        Observing_Agent = new Permission_Modifier_Observer(course_,co);
        con = co; // Gets the database Connection
    }
    // Execute Operation
    public int executeAdd() {
        int updates = 0; // Number of database updates
      if (faculty.getCourseNames().contains(course.viewName())){ // If faculty is teaching the course
          if(course.viewFeatures().contains(feature)){  // If feature already present
              System.out.println("Feature already present!");
          }
          else{
              course.addFeature(feature);
              try{
                  Statement stmt = con.createStatement();
                  updates = stmt.executeUpdate("insert into Course_Features values('"+ course.getcode()  + "','" + feature+"');");
                  if (feature.compareTo("Instructor Approval Required")==0){
                      updates+=Observing_Agent.update(1); // Update Observer
                  }
                  return updates;
              }catch(Exception e){
                  System.out.println(e);
              }
          }
      }
      else{
          System.out.println("Faculty " + faculty.getName() + " is not teaching the course " + course.viewName());
      }
      return 0;
    }

    public int executeDelete() {
        int updates = 0; // Number of database updates
        if (faculty.getCourseNames().contains(course.viewName())){ // If faculty is teaching the course
            if(!course.viewFeatures().contains(feature)){ // If feature not present
                System.out.println("Feature not present!");
            }
            else{
                course.removefeature(feature);
                try{
                    Statement stmt = con.createStatement();
                    updates = stmt.executeUpdate("delete from Course_Features where course_code ='" +course.getcode()+ "' and feature = '" + feature+"';");
                    if (feature.compareTo("Instructor Approval Required")==0){
                        updates+=Observing_Agent.update(0);
                    }
                    return updates;
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
        else{
            System.out.println("Faculty " + faculty.getName() + " is not teaching the course " + course.viewName());
        }
        return 0;
    }


}
