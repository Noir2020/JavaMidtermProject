package Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataManager.DataManager;
import Entities.Course;

public class CoursesController {

    private HashMap<String, Course> registeredCourses;
    private DataManager dataManager;
    public CoursesController() {
        registeredCourses = new HashMap<>();
        dataManager = new DataManager();
    }

    public HashMap<String, Course> getRegisteredCourses() {
        return registeredCourses;
    }
    
    public void addCourse(Course course) {
        //Add all of the constraints
        if(course.getCreditHrs() == 3 || course.getCreditHrs() == 4){
            registeredCourses.put(course.getCOURSE_ID(), course);
            System.out.println("The course was successfully registered");
        }else{
            System.out.println("The number of credits is invalid");
        }
    }

    public void deleteCourseById(String id) {
        registeredCourses.remove(id);
    }

    public boolean isCourseRegistered(String id){
        return registeredCourses.containsKey(id);
    }

    public Course searchCourseById(String id) {
        return registeredCourses.get(id); //this could be null
    }

    public Course searchCoursesByCourseName(String courseName) {
        Course courseFound = new Course();
        for (Course course : registeredCourses.values()) {
            if (course.getCourseName().toLowerCase().equals(courseName.toLowerCase())) {
                courseFound = course;
            }
        }
        return courseFound;
    }

    public void readFromFile(){
        List<Course> recoveredCourses = dataManager.ReadCoursesFromExcel();
        for(Course course: recoveredCourses){
            registeredCourses.put(course.getCOURSE_ID(),course);
        }
    }

    public void saveToFile() {
		dataManager.WriteCoursesToExcel(new ArrayList<Course>(registeredCourses.values()));
    }
    //Writes the courses stored in the hashmap 'registeredCourses' into the excel file using the
    //datamanager method WriteCoursesToExcel();
    
}
