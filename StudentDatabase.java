import java.io.*;
import java.util.*;

public class StudentDatabase {
    private static final String FILE_NAME = "students.csv";
    private List<Student> students;
    
    public StudentDatabase() {
        this.students = new ArrayList<>();
        loadFromFile();
    }
    
    // Add a new student
    public boolean addStudent(Student student) {
        if (student != null && !studentExists(student.getStudentId())) {
            students.add(student);
            saveToFile();
            return true;
        }
        return false;
    }
    
    // Update existing student
    public boolean updateStudent(String studentId, Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(studentId)) {
                students.set(i, updatedStudent);
                saveToFile();
                return true;
            }
        }
        return false;
    }
    
    // Delete student by ID
    public boolean deleteStudent(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                students.remove(student);
                saveToFile();
                return true;
            }
        }
        return false;
    }
    
    // Search student by name or ID
    public List<Student> searchStudent(String query) {
        List<Student> results = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(query.toLowerCase()) ||
                student.getStudentId().toLowerCase().contains(query.toLowerCase())) {
                results.add(student);
            }
        }
        return results;
    }
    
    // Get student by ID
    public Student getStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }
    
    // Get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
    
    // Check if student exists
    private boolean studentExists(String studentId) {
        return getStudentById(studentId) != null;
    }
    
    // Get total student count
    public int getTotalStudents() {
        return students.size();
    }
    
    // Load students from CSV file
    private void loadFromFile() {
        students.clear();
        File file = new File(FILE_NAME);
        
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Student student = new Student(parts[0], parts[1], parts[2], parts[3]);
                    students.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Save students to CSV file
    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                pw.println(student.getStudentId() + "," + student.getName() + "," + 
                          student.getEmail() + "," + student.getPhone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Clear all data
    public void clearAll() {
        students.clear();
        saveToFile();
    }
}
