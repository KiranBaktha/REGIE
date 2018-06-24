/* Check Compatibility checks if 2 class schedules are compatible with each other. If they are then a student can register for both the classes. If they are not
then the student would have a timetable clash. Hourly Precision is used.
* */
public class Check_Compatibility {

    private boolean result = true;

    public boolean execute(String schedule1,String schedule2){
        result = true;
        String split1[] = schedule1.split("\\s+");
        String split2[] = schedule2.split("\\s+");
        if (split1[0].compareTo(split2[0])==0){
            int start = Integer.parseInt(split2[1].substring(0,2));
            int end= Integer.parseInt(split2[1].substring(6,8));
            if(Integer.parseInt(split1[1].substring(0,2)) >=start && Integer.parseInt(split1[1].substring(0,2)) <end){
                result = false;
            }
            if (Integer.parseInt(split1[1].substring(6,8)) >start && Integer.parseInt(split1[1].substring(6,8)) <=end){
                result = false;
            }
        }
        return result;
    }


}
