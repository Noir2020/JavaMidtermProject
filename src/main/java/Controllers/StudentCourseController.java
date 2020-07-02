package Controllers;

import DataManager.DataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentCourseController {
    public HashMap<Integer, List<String>> studentCourseRegister;
    DataManager dataManager;


    public StudentCourseController(){
        studentCourseRegister = new HashMap<>();
        dataManager = new DataManager();
    }

    public void addNewStudent(int studentID){
        studentCourseRegister.put(studentID,new ArrayList<String>());
    }

    public void addCourseToStudent(int studentID, String courseID){
        if(studentCourseRegister.containsKey(studentID)){

            if(studentCourseRegister.get(studentID)!=null){
                if(studentCourseRegister.get(studentID).contains(courseID)){
                    System.out.println("The student is already registered to the course");
                }else{

                    studentCourseRegister.get(studentID).add(courseID);
                }
            }else{
                studentCourseRegister.put(studentID,new ArrayList<>());
                studentCourseRegister.get(studentID).add(courseID);
            }
        }else{
            System.out.println("There's no registered student with such ID. First enroll the student");
        }
    }

    public  void removeCourseFromStudent(int studentID, String courseID){
        if(studentCourseRegister.containsKey(studentID)){
            if(!studentCourseRegister.get(studentID).contains(courseID)){
                System.out.println("The student is not registered in the provided course");
            }else{
                if(studentCourseRegister.get(studentID)!=null){
                    studentCourseRegister.get(studentID).remove(courseID);
                }
            }
        }else{
            System.out.println("There's no registered student with such ID");
        }
    }

    public void readFromFile(){
        studentCourseRegister = dataManager.ReadStudentCoursesFromExcel();
    }

    public void saveToFile(){
        dataManager.WriteStudentCoursesToExcel(studentCourseRegister);
    }

    public List<String> getCourses(int studentID) {
        return studentCourseRegister.get(studentID);
    }
}
