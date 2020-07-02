package Entities;

public class Student {
    public static int STUDENT_ID_COUNT;
    public int STUDENT_ID;
    public final int MAX_CREDITS=21;
    
    private  String firstName;
    private  String lastName;
    private String documentNumber;
    private String address;
    private String studentStatus;
    private int credits;
    private TuitionPay tuitionPay;

    public Student(){
        STUDENT_ID_COUNT++;
        STUDENT_ID = STUDENT_ID_COUNT;
        tuitionPay = new TuitionPay();
    }

    public Student(String firstName,String lastName, String documentNumber, String address, String studentStatus, int credits) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.address = address;
        this.studentStatus = studentStatus;
        this.credits = credits;
        STUDENT_ID_COUNT++;
        STUDENT_ID =STUDENT_ID_COUNT;
        tuitionPay = new TuitionPay();

        switch (studentStatus.toLowerCase()){
            case "international student":
                tuitionPay.setDiscount(0);
                break;
            case "out of state student":
                tuitionPay.setDiscount(15);
                break;
            case "in state student":
                tuitionPay.setDiscount(50);
                break;
            default:
                tuitionPay.setDiscount(0);
                break;
        }
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        switch (studentStatus.toLowerCase()){
            case "international student":
                tuitionPay.setDiscount(0);
                break;
            case "out of state student":
                tuitionPay.setDiscount(15);
                break;
            case "in state student":
                tuitionPay.setDiscount(50);
                break;
            default:
                tuitionPay.setDiscount(0);
                break;
        }
        this.studentStatus = studentStatus;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public  int getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TuitionPay getTuitionPay() {
        return tuitionPay;
    }

}


