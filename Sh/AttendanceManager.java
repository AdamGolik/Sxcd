import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AttendanceManager {
    private Map<String, Student> students = new HashMap<>();
    private ArrayList<String[]> checkInLog = new ArrayList<>();

    public void addStudent(String firstName, String lastName, String cardPin, String classNumber, String schoolDiaryNumber) {
        students.put(cardPin, new Student(firstName, lastName, cardPin, classNumber, schoolDiaryNumber));
        System.out.println("Student added: " + firstName + " " + lastName + " with pin " + cardPin);
    }

    public void checkInStudent(String cardPin) {
        Student student = students.get(cardPin);
        if (student != null) {
            if (!isDuplicateCheckIn(student)) {
                student.lastCheckIn = getCurrentTime();
                checkInLog.add(new String[]{
                        new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(new Date(student.lastCheckIn)),
                        student.cardPin, student.firstName, student.lastName, student.classNumber, student.schoolDiaryNumber
                });
                System.out.println(student.firstName + " " + student.lastName + " checked in successfully.");
            } else {
                System.out.println("Warning: " + student.firstName + " " + student.lastName
                        + " already checked in within the last 2 hours.");
            }
        } else {
            System.out.println("Student with card PIN " + cardPin + " not found.");
        }
    }

    public ArrayList<String[]> getAttendanceLog() {
        return checkInLog;
    }

    public Map<String, Student> getStudents() {
        return students;
    }

    public void loadStudentsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 5) {
                    addStudent(values[0], values[1], values[2], values[3], values[4]);
                } else {
                    System.err.println("Insufficient data in line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Could not open the file: " + e.getMessage());
        }
    }

    private boolean isDuplicateCheckIn(Student student) {
        final long TWO_HOURS_IN_MILLIS = 7200000L;
        return System.currentTimeMillis() - student.lastCheckIn < TWO_HOURS_IN_MILLIS;
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }
}