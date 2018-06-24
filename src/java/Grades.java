/*Simple grade class that contains the allowable grade scale for UChicago.*/
import java.util.*;
public class Grades {
    private List<String> grades = new ArrayList<>(Arrays.asList("A","B","C","D","F","P","I"," "));
    List<String> AcceptableValues(){
        return grades;
    }
}
