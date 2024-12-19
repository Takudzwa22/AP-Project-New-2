import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AttendanceRecord {
    private List<Student> students;
    //final because the file name will stay the same no matter what
    private static final String FILE_NAME = "attendance_data.txt";
    //  for future expansion ref: it is also possible to load a file from a path on your laptop so we can create a database and kind of query it like this Scanner scFile = new Scanner (new File("servers.txt"));

    
    public AttendanceRecord() {
        this.students = new ArrayList<>();
        loadFromFile(); //load file with all information
    }

    // Add new student or get existing
    public Student AddOrGetStudent(String name) 
    {
        //loop through students in list checking if student exists
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        //if student doesnt exist we add them 
        Student newStudent = new Student(name);
        students.add(newStudent);
        return newStudent;
    }

    // Recording absences for a list of students given by input
    public void recordAbsences(String date, List<String> absentNames) 
    {
        //current so we can get a return of all students added with the input - kind of tracks it ig
        List<Student> currentAbsentees = new ArrayList<>();

        for (String name : absentNames) 
        {
            Student student = AddOrGetStudent(name);
            if (student.addAbsence(date)) {
                currentAbsentees.add(student);
            }
        }

        // Saves to file after recording, this happens everytime with the same file so we can have a record of eveything and query it to search or delete
        saveToFile();

        
        //Builds a string, linking object strings together 
        StringBuilder summary = new StringBuilder("Today's Absentees:\n");
        //loop through student object current absentes as i said so it tracks those just entered 
        for (Student student : currentAbsentees) 
        {
            summary.append(student.getName())//append to actually build the striing 
                   .append(" - Total Absences: ")
                   .append(student.getTotalAbsences())
                   .append("\n");

            // if students are late 3 or more times in a month and alert is sent to the teacher and the students parent would get in trouble
            if (student.getTotalAbsences() >= 3) 
            {
                String Alert = "Alert: " + student.getName() +
                        " has 3 or more absences.\nDates: " + student.getAbsenceDates();// lists alll of their absent dates for ref
                //show message dialog gives a pop notification as an aleter
                JOptionPane.showMessageDialog(null, Alert, "Absence Alert", JOptionPane.WARNING_MESSAGE);//cool feature got off stackoverflow 
            }
        }
        System.out.println(summary);//display
    }
  // Search absences by student name
    public String searchAbsencesByName(String name) 
    {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return "Student: " + student.getName() + "\nAbsence Dates: " + student.getAbsenceDates();
            }
        }
        return "No record found for student: " + name;
    }
    // Search absences by date
    public List<String> getAbsentesByDate(String date) 
    {
        List<String> absentes = new ArrayList<>();
        for (Student student : students) {
            if (student.getAbsenceDates().contains(date)) {
                absentes.add(student.getName());
            }
        }
        return absentes;
    }
    
     // Delete a absence date for a student
    public String deleteAbsence(String name, String date) 
    {
        LocalDate absenceDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sDate = absenceDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//parse into a string because parameter in student class needs string and list of absent dates is also string
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                
                boolean removed = student.removeAbsence(sDate);
                if (removed) {
                    saveToFile();//updates file
                    return "Absence on " + sDate + " removed for student: " + name;
                } else {
                    return "Student " + name + " does not have an absence recorded on " + sDate;
                }
            }
        }
        return "No record found for student: " + name;
    }

    // Save attendance data to file
    //stackover flow code from here
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Error saving attendance data: " + e.getMessage());
        }
    }

    // Loading data from file
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                students = (List<Student>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading attendance data: " + e.getMessage());
            }
        }
    }
}
