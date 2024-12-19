import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Teacher {
    private AttendanceRecord attendanceRecord;

    
    public Teacher(AttendanceRecord attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }

    // Input absences for a specific date
    public void inputAbsences() 
    {
        Scanner scanner = new Scanner(System.in); //asking user for info

        
        System.out.println("Enter the date (YYYY-MM-DD): ");
        String date = scanner.nextLine();//reads date they put in

       
        System.out.println("Enter the names of absent students for " + date + " (seperated by comma): ");
        String absentInput = scanner.nextLine();//reads names they put in
        List<String> absentNames = Arrays.asList(absentInput.split(","));//delimmiter

        // Recording absences
        attendanceRecord.recordAbsences(date, absentNames);
    }

    // Search absences by date
    public void searchAbsencesByDate() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the date to search absentees (YYYY-MM-DD): ");
        String date = scanner.nextLine();//reads date to search by

        List<String> absentees = attendanceRecord.getAbsentesByDate(date);
        if (absentees.isEmpty()) {
            System.out.println("No absences recorded on " + date);
        } else {
            System.out.println("Absentees on " + date + ": " + absentees);
        }
    }
}
