package App; //APP is any class that interacts directly with the user

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;

import Controllers.*;
import Entities.*;

public final class Registration {
	static StudentsController studentsController;
	static CoursesController coursesController;
	static StudentCourseController studentCourseController;
	
	public static void main(String[] args) {
		studentsController = new StudentsController();
		coursesController = new CoursesController();
		studentCourseController = new StudentCourseController();
		loadData();//Loads data from files
		runApp();
	}

	//Main App
	public static void runApp(){
		Scanner userInputScanner = new Scanner(System.in);
		String userInput = "";
		System.out.println("Welcome to the registration module");
		while (!userInput.equals("quit")) {
			System.out.println("\n");
			System.out.println("----------------");
			System.out.println("Choose an option");
			System.out.println("----------------");
			System.out.println("\n");
			System.out.println("[STUDENTS ADMIN]");
			System.out.println("1. Enroll student");
			System.out.println("2. Get list of enrolled students");
			System.out.println("3. Search students by ID");
			System.out.println("4. Search students by full name");
			System.out.println("5. Delete student by ID");
			System.out.println("6. Show list of courses of a student");
			System.out.println("7. Get student's bill");
			System.out.println("[COURSES ADMIN]");
			System.out.println("8. Add course");
			System.out.println("9. Get list of all courses");
			System.out.println("10. Add course to student");
			System.out.println("11. Search course by ID");
			System.out.println("12. Search course by Name");
			System.out.println("13. Delete course by ID");
			System.out.println("14. Delete course from student");
			System.out.println("quit. Quit.");
			userInput = userInputScanner.nextLine().toLowerCase().trim();//Takes out blank spaces and makes everything lowercase

			switch (userInput) {
				case "1":
					enrollStudentApp();
					break;
				case "2":
					showListOfEnrolledStudentsApp();
					break;
				case "3":
					searchStudentByIDApp();
					break;
				case "4":
					searchStudentsByFullNameApp();
					break;
				case "5":
					deleteStudentByIDApp();
					break;
				case "6":
					getCoursesOfStudentApp();
					break;
				case "7":
					getStudentBillApp();
					break;
				case "8":
					addCourseApp();
					break;
				case "9":
					showListOfCoursesApp();
					break;
				case "10":
					addCourseToStudentApp();
					break;
				case "11":
					searchCourseByIDApp();
					break;
				case "12":
					searchCourseByName();
					break;
				case "13":
					deleteCourseByIDApp();
					break;
				case "14":
					deleteCourseFromStudent();
					break;
				case "quit":
					quit();
					break;
				default:
					break;

			}
		}
	}
	//Student methods

	//1
	public static void enrollStudentApp() {
		Scanner enrollStudenScanner = new Scanner(System.in);
		System.out.println("Input student's firstname: ");
		String firstName = enrollStudenScanner.nextLine();
		System.out.println("Input student's lastname: ");
		String lastName = enrollStudenScanner.nextLine();
		System.out.println("Input student's document number: ");
		String docNumber = enrollStudenScanner.nextLine();
		System.out.println("Input student's address: ");
		String address = enrollStudenScanner.nextLine();
		System.out.println("Input student's status: ");
		String status = enrollStudenScanner.nextLine();
		Student student = new Student(firstName,lastName, docNumber,address,status,0);
		studentsController.enrollStudent(student);
		studentCourseController.addNewStudent(student.STUDENT_ID);
	}
	//2
	public static void showListOfEnrolledStudentsApp() {
		 HashMap<Integer, Student> enrolledStudents=studentsController.getEnrolledStudents();
		 System.out.println("The currently enrolled students are: ");
		 for(Student student : enrolledStudents.values()) {
			 System.out.println(student.getFirstName()+" "+student.getLastName()+". ID: "+student.getSTUDENT_ID()+". Status: "+student.getStudentStatus());
		 }
	}
	//3
	public static void searchStudentByIDApp() {
		Scanner searchStudentScanner = new Scanner(System.in);
		System.out.println("Input student's ID: ");
		int id = searchStudentScanner.nextInt();
		Student student = studentsController.searchStudentById(id);
		if(student == null) {
			System.out.println("No student was found with the given ID");
		}else {
			System.out.println("Student found: ");
			System.out.println(student.getFirstName()+" "+student.getLastName()+". ID: "+student.getSTUDENT_ID()+". Status: "+student.getStudentStatus());
		}
	}
	//4
	public static void searchStudentsByFullNameApp() {
		Scanner searchStudenScanner = new Scanner(System.in);
		System.out.println("Input student's firstname: ");
		String firstName = searchStudenScanner.nextLine();
		System.out.println("Input student's lastname: ");
		String lastName = searchStudenScanner.nextLine();
		List<Student> foundStudents = studentsController.searchStudentsByFullName(firstName,lastName);
		//We use Lists here since we might have several students with the same full name
		if(foundStudents.size()==0) {
			System.out.println("No students were found with the given full name");	
		}else {
			System.out.println("Students found: ");
			for(Student student: foundStudents) {
				System.out.println(student.getFirstName()+" "+student.getLastName()+". ID: "+student.getSTUDENT_ID()+". Document: "+student.getDocumentNumber()+". Status: "+student.getStudentStatus());
			}
		}
	}
	//5
	public static void deleteStudentByIDApp() {
		Scanner searchStudenScanner = new Scanner(System.in);
		System.out.println("Input student's ID: ");
		int id = searchStudenScanner.nextInt();
		Student foundStudent = studentsController.searchStudentById(id);
		if(foundStudent==null) {
			System.out.println("No student with such id was found");
		}else {
			studentsController.deleteStudentById(id);
			System.out.println("The student "+foundStudent.getFirstName()+" "+foundStudent.getLastName()+ " with ID: "+foundStudent.getSTUDENT_ID()+" was successfully deleted from the system");
		}
	}
	//6
	private static void getCoursesOfStudentApp() {
		//System.out.println(studentCourseController.studentCourseRegister.size());
		Scanner getCoursesOfStudentScanner = new Scanner(System.in);
		System.out.println("Input student's ID: ");
		int studentID = getCoursesOfStudentScanner.nextInt();
		if(studentsController.isStudentEnrolled(studentID)){
			Student student = studentsController.searchStudentById(studentID);
			List<String> courses = studentCourseController.getCourses(studentID);
			if(courses.size()==0){
				System.out.println("The student "+student.getFirstName()+" "+student.getLastName()+" is not enrolled in a course");
			}else{
				System.out.println("The student "+student.getFirstName()+" "+student.getLastName()+" is enrolled in the following courses: ");
				for(String course: courses){
					System.out.println(course);
				}
			}
		}else{
			System.out.println("Student not found");
		}
	}
	//7
	public static void getStudentBillApp(){

	    //1. Check the student's standard bill
	    Scanner getStudentBillScanner = new Scanner(System.in);
		System.out.println("Input student's ID: ");
		int studentID = getStudentBillScanner.nextInt();
		if(studentsController.isStudentEnrolled(studentID)){
		    Student student = studentsController.searchStudentById(studentID);
		    List<String> coursesIDs = studentCourseController.getCourses(studentID);
		    List<Course> courses = new ArrayList<>();
		    for(String id: coursesIDs){
		    	courses.add(coursesController.searchCourseById(id));
			}
            System.out.println("Name: "+student.getFirstName()+" "+student.getLastName() + " ID: "+
			student.STUDENT_ID+" Document: "+student.getDocumentNumber()+ "\nStatus: "+student.getStudentStatus());
			String bill = student.getTuitionPay().getBasicTuitionBill(courses,"", false);
			System.out.println(bill);
			getStudentBillScanner.nextLine();
			String payAnswer = "";

			//2. Ask the user whether s/he wants to pay the current bill
			while(!payAnswer.toLowerCase().equals("y") &&  !payAnswer.toLowerCase().equals("n")){
				System.out.println("\nDo you want to pay this bill? (y/n)");
				payAnswer = getStudentBillScanner.nextLine();
			}
			if(payAnswer.toLowerCase().equals("y")){
				String payMethodAnswer = "";
				while(!payMethodAnswer.toLowerCase().equals("1") && !payMethodAnswer.toLowerCase().equals("2") && !payMethodAnswer.toLowerCase().equals("3") ){
					System.out.println("Choose a payment method: ");
					System.out.println("\n1. Credit card (+3% fee)");
					System.out.println("2. Debit card");
					System.out.println("3. Cash");
					payMethodAnswer = getStudentBillScanner.nextLine();
				}

				switch (payMethodAnswer.toLowerCase()){
					case "1":
						payMethodAnswer = "credit card";
						break;
					case "2":
						payMethodAnswer = "debit card";
						break;
					case "3":
						payMethodAnswer = "cash";
						break;
					default:
						payMethodAnswer = "cash";
						break;
				}

				//3. Ask the user whether s/he wants to pay at once or with installments
				String numberOfPaysAnswer = "";
				while(!numberOfPaysAnswer.toLowerCase().equals("1") &&  !numberOfPaysAnswer.toLowerCase().equals("2")){
					System.out.println("One payment or installments?: ");
					System.out.println("\n1. ONE payment.");
					System.out.println("2. ONE Installment.");
					numberOfPaysAnswer = getStudentBillScanner.nextLine();
				}
				if(numberOfPaysAnswer.toLowerCase().equals("1")){
					numberOfPaysAnswer = "one payment";
					System.out.println(student.getTuitionPay().getBasicTuitionBill(courses,payMethodAnswer,true));
				}else{
                    System.out.println(student.getTuitionPay().getBasicTuitionBill(courses,payMethodAnswer,false));
                    if(student.getTuitionPay().getNumberOfInstallments() ==0){
                        int numberOfInstallmentsAnswer = 0;
                        while(numberOfInstallmentsAnswer<=1 || numberOfInstallmentsAnswer>48){
                            System.out.println("How many installments you want to pay? [2-48]");
                            numberOfInstallmentsAnswer = getStudentBillScanner.nextInt();
                        }
                        student.getTuitionPay().setNumberOfInstallments(numberOfInstallmentsAnswer);
                    }
                    System.out.println(student.getTuitionPay().getCurrentInstallmentTuitionBill());




					numberOfPaysAnswer = "one installment";

				}
                //case 1: the student is going to pay using installments but this is the first installment. We have to
                //ask the user how many installments
                //case 2: the student is going to pay using installments but this is NOT the first installment. We have
                //to ckeck which installment this is

                String confirmationAnswer = "";
				getStudentBillScanner = new Scanner(System.in);
				while(!confirmationAnswer.toLowerCase().trim().equals("y") && !confirmationAnswer.toLowerCase().trim().equals("n")){
                    System.out.println("\n DO YOU CONFIRM YOU WANT TO PAY? [y/n]");
				    confirmationAnswer=getStudentBillScanner.nextLine();
                }

				if(confirmationAnswer.toLowerCase().trim().equals("y")){
                    System.out.println("Payment successful");
                    if(numberOfPaysAnswer.equals("one installment")){
                        System.out.println("Installments left: "+(student.getTuitionPay().getNumberOfInstallments()-student.getTuitionPay().getCurrentInstallment()));
                        student.getTuitionPay().updateCurrentInstallment();
                    }
                }else{
                    System.out.println("Payment cancelled");
                }


			}
		}else{
            System.out.println("Student not found");
        }
	}

	//Auxiliary methods
	private static boolean canAddCourseToStudent(int studentID, String courseID){
		if (studentsController.isStudentEnrolled(studentID) && coursesController.isCourseRegistered(courseID)){
			Student student = studentsController.searchStudentById(studentID);
			Course course = coursesController.searchCourseById(courseID);
			if(student.getCredits()+course.getCreditHrs()>student.MAX_CREDITS){
				return false;
			}else{
			    student.setCredits(student.getCredits()+course.getCreditHrs());
				return true;
			}
		}
		return false;
	}

	private static boolean canRemoveCourseFromStudent(int studentID, String courseID){
		if (studentCourseController.studentCourseRegister.containsKey(studentID)){
			if(studentCourseController.studentCourseRegister.get(studentID) == null){
				System.out.println("Student is registered in no courses");
				return false;
			}else{
				if(studentCourseController.studentCourseRegister.get(studentID).contains(courseID)){
					return true;
				}else{
					return false;
				}
			}

		}
		return false;
	}

	private static boolean canCreateBillForStudent(int studentID, int numberOfInstallments, String paymentMethod){
	    return studentsController.isStudentEnrolled(studentID) && (numberOfInstallments>0 && numberOfInstallments<=48) && (paymentMethod.toLowerCase().equals("credit card")
                || paymentMethod.toLowerCase().equals("debit card") || paymentMethod.toLowerCase().equals("cash"));
    }
	//Course methods

	//8
	public static void addCourseApp() {
		Scanner addCourseScanner = new Scanner(System.in);
		System.out.println("Input course ID: ");
		String courseID = addCourseScanner.nextLine();
		System.out.println("Input cursename: ");
		String courseName = addCourseScanner.nextLine();
		System.out.println("Input Department: ");
		String department = addCourseScanner.nextLine();
		System.out.println("Input course's credit hours: ");
		int creditHrs = addCourseScanner.nextInt();
		System.out.println("Input rate per credit: ");
		double ratePerCredit = addCourseScanner.nextDouble();
		Course course = new Course(courseID,courseName, department,creditHrs,ratePerCredit);
		coursesController.addCourse(course);
	}
	//9
	public static void showListOfCoursesApp() {
		HashMap<String, Course> courses=coursesController.getRegisteredCourses();
		 System.out.println("The currently registered courses are: ");
		 for(Course course : courses.values()) {
			 System.out.println("ID: "+course.COURSE_ID+" "+course.getCourseName()+" "+course.getCreditHrs()+" credits");
		 }
	}
	//10
	private static void addCourseToStudentApp() {
		Scanner addCourseToStudentScanner = new Scanner(System.in);
		System.out.println("Input student's ID: ");
		int studentID = addCourseToStudentScanner.nextInt();
		addCourseToStudentScanner.nextLine();
		System.out.println("Input Course ID of the course to add to student : ");
		String courseID = addCourseToStudentScanner.nextLine();

		if(canAddCourseToStudent(studentID,courseID)){
			studentCourseController.addCourseToStudent(studentID,courseID);
			System.out.println("Course added successfully to the student");
		}else{
			System.out.println("Failed to add course to student");
		}

	}
	//11
	public static void searchCourseByIDApp() {
		Scanner searchCourseScanner = new Scanner(System.in);
		System.out.println("Input course's ID: ");
		String id = searchCourseScanner.nextLine();
		Course course = coursesController.searchCourseById(id);
		if(course == null) {
			System.out.println("No course was found with the given ID");
		}else {
			System.out.println("Course found: ");
			System.out.println("ID: "+course.COURSE_ID+". Course name: "+course.getCourseName()+". Credits:"+course.getCreditHrs());
		}
	}
	//12
	public static void searchCourseByName() {
		Scanner searchCourseScanner = new Scanner(System.in);
		System.out.println("Input course name: ");
		String name = searchCourseScanner.nextLine();
		Course course = coursesController.searchCoursesByCourseName(name);
		if(course == null) {
			System.out.println("No course was found with the given name");
		}else {
			System.out.println("Course found: ");
			System.out.println("ID: "+course.COURSE_ID+". Course name: "+course.getCourseName()+". Credits:"+course.getCreditHrs());
		}
	}
	//13
	public static void deleteCourseByIDApp(){
		Scanner searchCourseScanner = new Scanner(System.in);
		System.out.println("Input course's ID: ");
		String id = searchCourseScanner.nextLine();
		Course course = coursesController.searchCourseById(id);
		if(course!=null){
			coursesController.deleteCourseById(id);
			System.out.println("Course "+course.COURSE_ID+": "+course.getCourseName()+" was successfully deleted");
		}
		else{
			System.out.println("No course exists with such ID");
		}

	}
	//14
	public static void deleteCourseFromStudent(){
		Scanner deleteCourseFromStudentScanner = new Scanner(System.in);
		System.out.println("Input student's ID: ");
		int studentID = deleteCourseFromStudentScanner.nextInt();
		deleteCourseFromStudentScanner.nextLine();
		System.out.println("Input Course ID of the course to remove from student : ");
		String courseID = deleteCourseFromStudentScanner.nextLine();
		if(canRemoveCourseFromStudent(studentID,courseID)){
			studentCourseController.removeCourseFromStudent(studentID,courseID);
		}
	}

	//Configuration Apps

	public static void loadData(){
		studentsController.readFromFile();
		coursesController.readFromFile();
		studentCourseController.readFromFile();
		TuitionPay.administrationFee = 50;
		TuitionPay.technologyUseFee =  100;
	}

	public static void quit() {
		studentsController.saveToFile();
		coursesController.saveToFile();
		studentCourseController.saveToFile();
	}
	
}
