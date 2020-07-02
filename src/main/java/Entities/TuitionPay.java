package Entities;

import java.util.List;

public class TuitionPay {

    static public double administrationFee;
    static public double technologyUseFee;

    private double totalAmount;
    private int discount;
    private String paymentMethod;

    private double installmentAmount;
    private int numberOfInstallments;
    private int currentInstallment;

    public TuitionPay() {
        totalAmount =0.0;
        discount =0;
        numberOfInstallments =0;
        currentInstallment=1;
        paymentMethod = "cash";
        administrationFee = 0;
        technologyUseFee =0;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public int getCurrentInstallment() {
        return currentInstallment;
    }

    public void updateCurrentInstallment(){
        this.currentInstallment++;
    }

    //Bill that contains standard prices for the courses + administration and tech fees + student status discount
    public String getBasicTuitionBill(List<Course> courses,String paymentMethod, boolean onePayment){
        String bill ="";
        bill+="Courses:\n";
        bill += String.format("%8s   %11s   %6s%n","ID","Rate/Credit","Total");
        double Total =0;

        for(Course course:courses){
            Double totalCourse = course.getRatePerCredit()*course.getCreditHrs();
            Total+=totalCourse;
            bill += String.format("%8s   %11s   %6.2f%n",course.COURSE_ID,String.format("%5.2f",course.getRatePerCredit())+" x "+course.getCreditHrs(),totalCourse);
        }
        bill+="Admin fee: "+administrationFee+"\nTech fee: "+technologyUseFee+"\n";
        bill+="Student status discount ("+discount+"%):     -"+Total*(double)(discount)/100+"\n";
        double onePaymentDiscount = 0;
        if(onePayment){
            onePaymentDiscount=0.05;
            bill+="One payment discount (-5%):       -"+Total*onePaymentDiscount+"\n";
        }
        double creditCardFee = 0;
        if(paymentMethod.toLowerCase().trim().equals("credit card")){
            creditCardFee = 0.03;
            bill+="Credit card fee (+3%):    +"+Total*creditCardFee;
        }
        Total = Total*(1-(double)(discount)/100)+administrationFee+technologyUseFee+Total*creditCardFee - Total*onePaymentDiscount;
        bill +="\nTotal: "+String.format("%10.2f",Total);
        if(this.numberOfInstallments>1){
            bill+="\nLeft to pay: "+String.format("%10.2f",Total-(Total*(this.currentInstallment-1)/this.numberOfInstallments))+"\n";
        }
        this.totalAmount = Total;
        return bill;
    }

    public String getCurrentInstallmentTuitionBill() {
        String bill="Installment "+this.currentInstallment+" of "+this.numberOfInstallments;
        this.installmentAmount = this.totalAmount/this.numberOfInstallments;
        bill+="\nTo Pay:    "+String.format("%10.2f",this.installmentAmount);
        return bill;
    }
}
