public class Student {
    String firstName;
    String lastName;
    String cardPin;
    String classNumber;
    String schoolDiaryNumber;
    long lastCheckIn;

    public Student(String firstName, String lastName, String cardPin, String classNumber, String schoolDiaryNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cardPin = cardPin;
        this.classNumber = classNumber;
        this.schoolDiaryNumber = schoolDiaryNumber;
        this.lastCheckIn = 0;
    }
}