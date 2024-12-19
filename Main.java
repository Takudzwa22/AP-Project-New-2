
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        Teacher teacher = new Teacher(attendanceRecord);

        // Input absences for today
        teacher.inputAbsences();

         //Search absentees by date
        teacher.searchAbsencesByDate();
        
       // Search absence by name
        String studentName = JOptionPane.showInputDialog("Please enter the name of the Student to check their absences");
        String result = attendanceRecord.searchAbsencesByName(studentName);
        System.out.println(result);
       // delete abscnes
        String deleteName = JOptionPane.showInputDialog("Please enter the name ofthe student you'd like to delete an absence for");
        String deleteDate = JOptionPane.showInputDialog("Please enter the date of the absence you'd like to delete");
        
        String deletionResult = attendanceRecord.deleteAbsence(deleteName, deleteDate);
        System.out.println(deletionResult);
    }
}
