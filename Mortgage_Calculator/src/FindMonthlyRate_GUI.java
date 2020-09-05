/**
 * The FindMonthlyRate_GUI class Creates a GUI to calculate monthly rate for a mortgage 
 * and implements the calculation
 * @Author - Jon Mantooth
 * @Version - 1.0
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindMonthlyRate_GUI implements ActionListener{
	//Declare Variables
	
	//frames
	JFrame frame;

	//panels
	JPanel agePanel;
	JPanel datePanel;
	
	//labels
	JLabel totalLabel;
	JLabel aprLabel;
	JLabel maturityLabel;
	JLabel maturityLabel2;
	JLabel orLabel;
	JLabel birthdayLabel;
	JLabel birthMonthLabel;
	JLabel birthYearLabel;
	JLabel ageMaturityLabel;
	JLabel dateMaturityLabel;
	JLabel maturityMonthLabel;
	JLabel maturityYearLabel;
	JLabel outputLabel;
	
	//textfields
	JTextField totalField;
	JTextField aprField;
	JTextField ageField;
	JTextField birthMonthField;
	JTextField birthYearField;
	JTextField maturityMonthField;
	JTextField maturityYearField;

	//buttons
	JButton calculateButton;
	
	/**
	 * The constructor builds the GUI with all necessary fields to calculate the monthly rate
	 * of the mortgage
	 */
	public FindMonthlyRate_GUI() {
		
		//build frame
		frame=new JFrame();
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Mortgage Calculator");
		frame.setSize(920, 1000);
		
		//build labels
		totalLabel=new JLabel("Enter the Outstanding Principal Balance.");
		totalLabel.setBounds(20, 70, 300, 20);
		aprLabel=new JLabel("Enter the annual percentage rate");
		aprLabel.setBounds(20, 130, 300, 20);
		maturityLabel = new JLabel("Enter either the future date of payoff or the age at");
		maturityLabel.setBounds(20, 190, 350, 20);
		maturityLabel2=new JLabel("which birthday you would like to payoff the mortgage");
		maturityLabel2.setBounds(20, 210, 350, 20);
		dateMaturityLabel=new JLabel("Future Date");
		dateMaturityLabel.setBounds(30, 250, 100, 20);
		maturityMonthLabel=new JLabel("Month");
		maturityMonthLabel.setBounds(40, 270, 40, 20);
		maturityYearLabel=new JLabel("Year");
		maturityYearLabel.setBounds(110, 270, 40, 20);
		orLabel= new JLabel("OR");
		orLabel.setBounds(20,330,20,20);
		ageMaturityLabel=new JLabel("Age");
		ageMaturityLabel.setBounds(30, 370, 40, 20);
		birthdayLabel=new JLabel("Birthday");
		birthdayLabel.setBounds(30,430,100,20);
		birthMonthLabel = new JLabel("Month");
		birthMonthLabel.setBounds(40, 450, 40, 20);
		birthYearLabel = new JLabel("Year");
		birthYearLabel.setBounds(110, 450, 40, 20);
		outputLabel=new JLabel();
		outputLabel.setBounds(350, 70, 600, 20);
		
		//build textfields
		totalField=new JTextField();
		totalField.setBounds(20, 90, 200, 20);
		aprField=new JTextField();
		aprField.setBounds(20, 150, 200, 20);
		maturityMonthField=new JTextField();
		maturityMonthField.setBounds(40, 290, 40, 20);
		maturityYearField=new JTextField();
		maturityYearField.setBounds(110, 290, 60, 20);
		ageField=new JTextField();
		ageField.setBounds(30,390,40,20);
		birthMonthField=new JTextField();
		birthMonthField.setBounds(40, 470, 40, 20);
		birthYearField=new JTextField();
		birthYearField.setBounds(110, 470, 60, 20);
		
		//build buttons
		calculateButton = new JButton("Calculate");
		calculateButton.setBounds(320, 550, 150, 60);
		calculateButton.addActionListener(this);
		
		/*build panels - age panel and date panel are used to outline age and
		date to specify what needs to be populated by the user
		*/
		agePanel=new JPanel();
		agePanel.setLayout(null);
		agePanel.setBounds(25, 365, 180, 130);
		agePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		datePanel=new JPanel();
		datePanel.setLayout(null);
		datePanel.setBounds(25, 245, 150, 70);
		datePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
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
		frame.add(maturityLabel);
		frame.add(maturityLabel2);
		frame.add(dateMaturityLabel);
		frame.add(maturityMonthLabel);
		frame.add(maturityYearLabel);
		frame.add(maturityMonthField);
		frame.add(maturityYearField);
		frame.add(orLabel);
		frame.add(ageField);
		frame.add(ageMaturityLabel);
		frame.add(agePanel);
		frame.add(datePanel);
		frame.add(calculateButton);
		frame.add(outputLabel);
		frame.setVisible(true);

	}
	
	/**
	 * The actionPerformed method calculates and outputs the monthly rate for the mortgage
	 * when the calculate button is clicked. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//declare variables for data provided by user
		String totalText;
		double total=0.0;
		double apr=0.0;
		int maturityMonth=0;
		int maturityYear=0;
		int birthMonth =0;
		int birthYear = 0;
		int age=0;
		
		//output variable
		Double monthlyPayment;
		String output;
		
		//ordinal suffix for age
		String ordinal;
		
		//instance of object Mortgage_Life_Calculator to calculate monthly rate
		MortgageCalculator newMortgage = new MortgageCalculator();
		
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
		
		//if future date is used to calculate monthly payment
		if (ageField.getText().isEmpty()) {
			
			//error message for invalid future month
			try {
				maturityMonth=Integer.parseInt(maturityMonthField.getText());
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(null, "The month of maturity must be a number between 1 and 12");
				return;
			}
			
			if (maturityMonth<1 || maturityMonth>12){
				JOptionPane.showMessageDialog(null, "The month of maturity must be a number between 1 and 12");
				return;
			}
			
			//error message for invalid future year
			try {
				maturityYear=Integer.parseInt(maturityYearField.getText());
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(null, "The year of maturity must be a number");
				return;
			}
			
			//error message if 2 digits used for year field 
			if (maturityYear<100) {
				JOptionPane.showMessageDialog(null, "Please enter 4 digits for the year.");
				return;
			}
			
			//error message if future date is not actually in the future
			if (maturityYear<currentYear || (maturityYear==currentYear && maturityMonth<=currentMonth)){
				JOptionPane.showMessageDialog(null, "The maturity date must be in the future.");
				return;
			}
			
			//calculate monthly payment
			monthlyPayment=newMortgage.monthlyPayment(total, apr, maturityMonth, maturityYear);
			
			//round monthly payment to 2 decimal places
			monthlyPayment=Math.ceil(monthlyPayment*100)/100.00;
			
			//output string for monthly payment
			output="Monthly payment required to pay off loan by " + Month.of(maturityMonth) + " of " + maturityYear + " is $" + monthlyPayment;
		}else {
			
			//error message for invalid age
			try {
				age=Integer.parseInt(ageField.getText());
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(null, "Please enter a number for age.");
				return;
			}
			
			//add ordinal suffix for age for output (ex. 65th birthday as opposed to 65 birthday)
			switch(age%10) {
			case 1:
				ordinal = "st";
				break;
			case 2:
				ordinal = "nd";
				break;
			case 3:
				ordinal = "rd";
				break;
			default:
				ordinal="th";
			}
			
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
			
			//error message if future birthday is actually in the past
			if (birthYear+age<currentYear || (birthYear+age==currentYear && birthMonth<=currentMonth)){
				JOptionPane.showMessageDialog(null, "Who are you trying to fool? You already passed your " + age + ordinal + " birthday!");
				return;
			}
			
			//using age and birthday calculate future maturity date
			newMortgage.findMaturityDate(birthMonth, birthYear, age);
			maturityMonth=newMortgage.getMaturityMonth();
			maturityYear=newMortgage.getMaturityYear();

			//calculate monthly payment
			monthlyPayment=newMortgage.monthlyPayment(total, apr, maturityMonth, maturityYear);
			
			//round payment to 2 decimal places
			monthlyPayment=Math.ceil(monthlyPayment*100)/100.00;
			
			//output string for monthly payment
			output="Monthly payment required to pay off loan by your " + age + ordinal + " birthday is $" + monthlyPayment;
		}
		
		//output monthly payment
		outputLabel.setText(output);
	}
}