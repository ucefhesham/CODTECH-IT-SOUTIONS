import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    String name;
    int rollNumber;
    List<Integer> marks;

    Student(String name, int rollNumber, List<Integer> marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    double calculatePercentage() {
        int totalMarks = 0;
        for (int mark : marks) {
            totalMarks += mark;
        }
        return (double) totalMarks / marks.size();
    }

    char calculateGrade() {
        double percentage = calculatePercentage();
        if (percentage >= 90) return 'A';
        else if (percentage >= 80) return 'B';
        else if (percentage >= 70) return 'C';
        else if (percentage >= 60) return 'D';
        else return 'F';
    }

    // Getters and setters for name, rollNumber, and marks
}

public class StudentManagementSystem {
	private List<Student> students = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private String filename = "students.txt";

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("1. Add Student\n2. Update Student\n3. Delete Student\n4. View Students\n5. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    viewStudents();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        scanner.nextLine(); // Consume the leftover newline
        String name = scanner.nextLine();
        System.out.print("Enter roll number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline after the number
        List<Integer> marks = new ArrayList<>();
        System.out.println("Enter marks for 5 subjects:");
        for (int i = 0; i < 5; i++) {
            marks.add(scanner.nextInt());
        }
        scanner.nextLine(); // Consume the newline after the last number
        students.add(new Student(name, rollNumber, marks));
        System.out.println("Student added successfully.");
    }

    private void updateStudent() {
        System.out.print("Enter roll number of student to update: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline after the number
        for (Student student : students) {
            if (student.rollNumber == rollNumber) {
                System.out.print("Enter new name: ");
                student.name = scanner.nextLine();
                System.out.println("Enter new marks for 5 subjects:");
                student.marks.clear();
                for (int i = 0; i < 5; i++) {
                    student.marks.add(scanner.nextInt());
                }
                scanner.nextLine(); // Consume the newline after the last number
                System.out.println("Student updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private void deleteStudent() {
        System.out.print("Enter roll number of student to delete: ");
        int rollNumber = scanner.nextInt();
        students.removeIf(student -> student.rollNumber == rollNumber);
        System.out.println("Student deleted successfully.");
    }

    private void viewStudents() {
        for (Student student : students) {
            System.out.println("Name: " + student.name + ", Roll Number: " + student.rollNumber + ", Percentage: " + student.calculatePercentage() + ", Grade: " + student.calculateGrade());
        }
    }
    
    private void saveToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (Student student : students) {
                out.println(student.rollNumber + "," + student.name + "," + listToString(student.marks));
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                int rollNumber = Integer.parseInt(parts[0]);
                String name = parts[1];
                List<Integer> marks = stringToList(parts[2]);
                students.add(new Student(name, rollNumber, marks));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. A new file will be created.");
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    private String listToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) sb.append(",");
        }
        return sb.toString();
    }

    private List<Integer> stringToList(String str) {
        List<Integer> list = new ArrayList<>();
        String[] items = str.split(",");
        for (String item : items) {
            list.add(Integer.parseInt(item.trim()));
        }
        return list;
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.loadFromFile();
        sms.start();
        sms.saveToFile();
    }
}
