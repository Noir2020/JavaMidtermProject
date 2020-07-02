package Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import DataManager.DataManager;
import Entities.Student;

public class StudentsController {
	
    private HashMap<Integer, Student> enrolledStudents;
    private DataManager dataManager;
    public StudentsController() {
        enrolledStudents = new HashMap<>();
        dataManager = new DataManager();
    }

    public HashMap<Integer, Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void enrollStudent(Student student) {

        if (Validate(student)) {
            enrolledStudents.put(student.getSTUDENT_ID(), student);
        } else {
            System.out.println("Student could not be enrolled");
        }
    }

    public void deleteStudentById(int id) {
        enrolledStudents.remove(id);
    }

    public boolean isStudentEnrolled(int id){
        return enrolledStudents.containsKey(id);
    }

    public Student searchStudentById(int id) {
        return enrolledStudents.get(id); //this could be null
    }

    public List<Student> searchStudentsByFullName(String firstName, String lastName) {
        List<Student> students = new ArrayList<>();

        for (Student student : enrolledStudents.values()) {
            if (student.getFirstName().toLowerCase().equals(firstName.toLowerCase()) && student.getLastName().toLowerCase().equals(lastName.toLowerCase())) {
                students.add(student);
            }
        }
        return students;

    }

    private boolean Validate(Student student) {

        if(student.getFirstName().toLowerCase().trim().equals("") ||
            student.getLastName().toLowerCase().trim().equals("") ||
            student.getDocumentNumber().toLowerCase().trim().equals("")||
            student.getStudentStatus().toLowerCase().trim().equals(""))
        {
            System.out.println("No fields can be empty");
            return false;
        }
        if(student.getStudentStatus().toLowerCase().trim().equals("in state student")){

            System.out.println("You were successfully registered as In State Student");
            return true;
        }
        if(student.getStudentStatus().toLowerCase().trim().equals("out of state student")){
            System.out.println("You were successfully registered as Out of State Student");
            return true;
        }
        if(student.getStudentStatus().toLowerCase().trim().equals("international student")){
            System.out.println("You were successfully registered as International Student");
            return true;
        }
        System.out.println("Student status is not valid");
        return false;
        
    }

    public void readFromFile(){
        List<Student> recoveredStudents = dataManager.ReadStudentsFromExcel();
        for(Student student: recoveredStudents){
            enrolledStudents.put(student.getSTUDENT_ID(),student);
        }
    }

	public void saveToFile() {
		dataManager.WriteStudentsToExcel(new ArrayList<Student>(enrolledStudents.values()));
	}
    //Writes the students stored in the hashmap 'enrolledStudents' into the excel file using the
    //datamanager method WriteCoursesToExcel();

}
