import java.io.Serializable; //to load the objects into the file otherwsie it doesnt work
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable  {
    private static final long serialVersionUID = 1L; // For version control (chatgpt)
    private String name;
    private int totalAbsences;
    private List<String> absenceDates;

    public Student(String name) {
        this.name = name;
        this.totalAbsences = 0;
        this.absenceDates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getTotalAbsences() {
        return totalAbsences;
    }

    public List<String> getAbsenceDates() {
        return absenceDates;
    }

    public boolean addAbsence(String date) 
    {
        //if the student doesn't already have an absnet on the day
        if (!absenceDates.contains(date)) 
        {
            absenceDates.add(date);
            totalAbsences++; //increment the tottal absent days 
            return true;
        }
        return false;
    }
    
     public boolean removeAbsence(String date) {
        for (int i = 0; i < absenceDates.size(); i++) {
            if (absenceDates.get(i).equals(date)) {
                absenceDates.remove(i); // Removing absent day from list using it s postion
                return true;
            }
        }
        return false; // they dont have an absent day on that date
    }

    @Override
    public String toString() {
        return "Student{name= " + name + ", totalAbsences= " + totalAbsences + ", absenceDates= " + absenceDates + "}";
    }
}
