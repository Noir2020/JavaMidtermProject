package Entities;

public class Course {
    public String COURSE_ID;    
    private String courseName;
    private int creditHrs;
    private double ratePerCredit;
    private String department;

    public Course(){
        //Left intentionally blank
    }
    public Course(String COURSE_ID, String courseName, String department,int creditHrs, double ratePerCredit) {
        this.COURSE_ID=COURSE_ID;
        this.courseName = courseName;
        this.department = department;
        this.creditHrs = creditHrs;
        this.ratePerCredit = ratePerCredit;
    }

    public  String getCOURSE_ID() {
        return COURSE_ID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCreditHrs() {
        return creditHrs;
    }

    public void setCreditHrs(int creditHrs) {
        this.creditHrs = creditHrs;
    }

    public double getRatePerCredit() {
        return ratePerCredit;
    }

    public void setRatePerCredit(double ratePerCredit) {
        this.ratePerCredit = ratePerCredit;
    }

    
}
