/**
 * The FindMaturityDate_GUI class Creates a GUI to calculate maturity date for a mortgage 
 * and implements the calculation
 * @Author - Jon Mantooth
 * @Version - 1.0
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FindMaturityDate_GUI implements ActionListener{
	
	//declare variables
	
	//frames
	JFrame frame;
	
	//labels
	JLabel totalLabel;
	JLabel aprLabel;
	JLabel birthdayLabel;
	JLabel birthMonthLabel;
	JLabel birthYearLabel;
	JLabel monthlyPaymentLabel;
	JLabel monthlyInterestLabel;
	JLabel monthlyPrincipalLabel;
	JLabel paymentDeltaLabel;
	JLabel paymentDeltaLabelLine2;
	JLabel outputLabel1;
	JLabel outputLabel2;
	JLabel outputLabel3;
	
	//textfields
	JTextField totalField;
	JTextField aprField;
	JTextField birthMonthField;
	JTextField birthYearField;
	JTextField monthlyInterestField;
	JTextField monthlyPrincipalField;
	JTextField paymentDeltaField;
	
	//buttons
	JButton calculateButton;
	
	/**
	 * The constructor builds the GUI with all necessary fields to calculate the maturity date
	 * of the mortgage
	 */
	public FindMaturityDate_GUI() {
		
		//build frame
		frame=new JFrame();
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Mortgage Calculator");
		frame.setSize(920, 1000);
		
		//build labels
		totalLabel=new JLabel("Enter the Outstanding Principal Balance.");
		totalLabel.setBounds(20, 70, 300, 20);
		aprLabel=new JLabel("Enter the annual percentage rate.");
		aprLabel.setBounds(20, 130, 250, 20);
		birthdayLabel=new JLabel("Enter your birthday below:");
		birthdayLabel.setBounds(20, 190, 250, 20);
		birthMonthLabel=new JLabel("Month");
		birthMonthLabel.setBounds(30, 210, 40, 20);
		birthYearLabel=new JLabel("Year");
		birthYearLabel.setBounds(100, 210, 40, 20);
		monthlyPaymentLabel=new JLabel("Enter the amount you currently pay monthly");
		monthlyPaymentLabel.setBounds(20, 270, 300, 20);
		monthlyPrincipalLabel=new JLabel("Principal");
		monthlyPrincipalLabel.setBounds(30, 290, 100, 20);
		monthlyInterestLabel=new JLabel("Interest");
		monthlyInterestLabel.setBounds(30, 310, 100, 20);
		paymentDeltaLabel=new JLabel("Enter the additional monthly amount you would like");
		paymentDeltaLabel.setBounds(20, 350, 350, 20);
		paymentDeltaLabelLine2=new JLabel("to pay (new rate will persist through life of loan).");
		paymentDeltaLabelLine2.setBounds(20, 370, 350, 20);
		outputLabel1=new JLabel();
		outputLabel1.setBounds(350, 70, 600, 20);
		outputLabel2=new JLabel();
		outputLabel2.setBounds(350, 110, 600, 20);
		outputLabel3=new JLabel();
		outputLabel3.setBounds(350, 150, 600, 20);
		
		//build textfields
		totalField=new JTextField();
		totalField.setBounds(20, 90, 200, 20);
		aprField=new JTextField();
		aprField.setBounds(20, 150, 200, 20);
		birthMonthField=new JTextField();
		birthMonthField.setBounds(30, 230, 40, 20);
		birthYearField=new JTextField();
		birthYearField.setBounds(100, 230, 60, 20);
		monthlyPrincipalField=new JTextField();
		monthlyPrincipalField.setBounds(130, 290, 100, 20);
		monthlyInterestField=new JTextField();
		monthlyInterestField.setBounds(130, 310, 100, 20);
		paymentDeltaField=new JTextField();
		paymentDeltaField.setBounds(20,390,200,20);
		
		//build buttons
		calculateButton = new JButton("Calculate");
		calculateButton.setBounds(320, 470, 150, 60);
		calculateButton.addActionListener(this);

		
		//add components to Frame
		frame.add(totalLabel);
		frame.add(totalField);
		frame.add(aprLabel);
		frame.add(aprField);
		frame.add(birthdayLabel);
		frame.add(birthMonthField);
		frame.add(birthMonthLabel);
		frame.add(birthYearField);
		frame.add(birthYearLabel);
		frame.add(monthlyInterestField);
		frame.add(monthlyInterestLabel);
		frame.add(monthlyPaymentLabel);
		frame.add(monthlyPrincipalField);
		frame.add(monthlyPrincipalLabel);
		frame.add(paymentDeltaField);
		frame.add(paymentDeltaLabel);
		frame.add(paymentDeltaLabelLine2);
		frame.add(calculateButton);
		frame.add(outputLabel1);
		frame.add(outputLabel2);
		frame.add(outputLabel3);
		frame.setVisible(true);

	}

	/**
	 * The actionPerformed method calculates and outputs the maturity date for the mortgage
	 * when the calculate button is clicked. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Declare Variables
		
		//user inputs
		String totalText;
		double total;
		double apr;
		int birthMonth;
		int birthYear;
		double interest;
		double principal;
		double delta;
		String deltaString;
		
		//false if payment amount is too small to make up for interest
		boolean cantPay;
		
		//variables to calculate maturity dates at current payment and new payment
		double currentPayment;
		double newPayment;
		
		//instances of Mortgage_Life_Calculator to calculate maturity date at current rate and new rate
		Mortgage_Life_Calculator currentMortgage = new Mortgage_Life_Calculator();
		Mortgage_Life_Calculator newMortgage = new Mortgage_Life_Calculator();
		
		//variables to compare to current date
		LocalDate currentDate;
		int currentMonth;
		int currentYear;
		
		//find current month and year
		currentDate=LocalDate.now();
		currentMonth=currentDate.getMonth().getValue();
		currentYear=currentDate.getYear();
		
		//read in total value as a string to remove $ if necessary
		totalText=totalField.getText();
		if (totalText.charAt(0)=='$')
			totalText=totalText.substring(1);
				
		//error message if invalid balance is given
		try {
			total=Double.parseDouble(totalText);
		}catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Outstanding principal balance must be a dollar amount.");
			return;
		}
				
		//error message for negative balance
		if (total<=0) {
			JOptionPane.showMessageDialog(null, "Outstanding principal balance must be greater than $0.");
			return;
		}
				
		//error message for invalid apr 
		try {
			apr=Double.parseDouble(aprField.getText());
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "APR must be a numerical value.");
			return;
		}
				
		//error message for negative apr
		if (apr<0) {
			JOptionPane.showMessageDialog(null, "APR cannot be negative.");
			return;
		}
				
		//find apr as a percentage
		apr=apr/100.00;
		
		//error message for invalid birth month
		try {
			birthMonth=Integer.parseInt(birthMonthField.getText());
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "The birth month must be a number between 1 and 12");
			return;
		}
		
		if (birthMonth<1 || birthMonth>12){
			JOptionPane.showMessageDialog(null, "The birth month must be a number between 1 and 12");
			return;
		}
		
		//error message for invalid birth year
		try {
			birthYear=Integer.parseInt(birthYearField.getText());
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "The birth year must be a number");
			return;
		}
		
		//error message for 2 digit birth year
		if (birthYear<100) {
			JOptionPane.showMessageDialog(null, "Please enter 4 digits for the year.");
			return;
		}
		
		//error message for future birth year
		if (birthYear>currentYear || (birthYear==currentYear && birthMonth>=currentMonth)){
			JOptionPane.showMessageDialog(null, "Records show that you have not yet been born. We recommend that you wait until after your birth to take out a mortgage.");
			return;
		}
		
		//error message for invalid interest payment
		try {
			interest=Double.parseDouble(monthlyInterestField.getText());
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "Please enter a number for monthly interest payment.");
			return;
		}
		
		//error message for negative interest payment
		if (interest<0){
			JOptionPane.showMessageDialog(null, "Interest payment cannot be negative.");
			return;
		}
		
		//error message for invalid principal payment
		try {
			principal=Double.parseDouble(monthlyPrincipalField.getText());
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "Please enter a number for monthly principal payment.");
			return;
		}
		
		//error message for negative interest payment
		if (principal<0){
			JOptionPane.showMessageDialog(null, "Principal payment cannot be negative.");
			return;
		}
		
		//error message for invalid additional payment
		try {
			delta=Double.parseDouble(paymentDeltaField.getText());
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "Please enter a number for additional payment.");
			return;
		}
		
		//error message for negative interest payment
		if (delta<0){
			JOptionPane.showMessageDialog(null, "Our records show you want to pay less than your current payment. If you do this you will default on your loan and go to jail.");
			return;
		}
		
		//current payment is sum of interest and principal
		currentPayment = interest+principal;
		
		//new payment is sum of current payment and delta
		newPayment=currentPayment + delta;
		
		//calculate life span for both current mortgage and new mortgage
		cantPay=currentMortgage.mortgageLifeSpan(currentPayment, apr, total);
		
		//error message if interest is higher than payment
		if (!cantPay) {
			JOptionPane.showMessageDialog(null, "It looks like at your current rate your mortgage is increasing faster than you are paying it off. You will never pay it off at this rate and you are pretty much screwed.");
			return;
		}
		newMortgage.mortgageLifeSpan(newPayment, apr, total);
		
		//compare life span with current mortgage and new mortgage
		if (Double.parseDouble(paymentDeltaField.getText())>0) 
			deltaString = currentMortgage.getYearDelta(newMortgage.getMaturityMonth(), newMortgage.getMaturityYear()) + " sooner than";
		else 
			deltaString = "at the same time as";
		
		//output life span of mortgage
		outputLabel1.setText("With the updated payment you will pay off the mortgage in " + newMortgage.getMaturityDate());;
		outputLabel2.setText("You will be " + newMortgage.getYearDelta(birthMonth, birthYear) + " old when you pay off the loan."); 
		outputLabel3.setText("You will pay off the mortgage " + deltaString + " with your current rate." );
	}
}