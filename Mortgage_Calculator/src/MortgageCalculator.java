/**
 * The MortgageCalculator class runs all the calculations for the program. This includes 
 * calculating maturity date and monthly payments and any secondary calculations
 * @Author - Jon Mantooth
 * @Version - 1.0
 */

import java.time.LocalDate;
import java.time.Month;

public class MortgageCalculator {
	
	//Declare Variables
	private int months=0;
	private int monthsToMaturity;
	private int yearsToMaturity;
	private Month maturityMonth;
	private int maturityYear;
	private Month currentMonth;
	private LocalDate currentDate;
	private int currentYear;
	private int deltaMonth;
	private int deltaYear;
	
	/**
	 * The constructor finds the current month and year to be used in comparisons
	 */
	public MortgageCalculator() {
		currentDate=LocalDate.now();
		currentMonth=currentDate.getMonth().plus(1);
		currentYear=currentDate.getYear();
	}
	
	/**
	 * This method calculates the months required to pay off the mortgage
	 * @param payment - the monthly payment on the mortgage
	 * @param apr - the annual percentage rate
	 * @param lumpSum - the total balance of the mortgage
	 * @return boolean true if the life of mortgage is found, false if the mortgage
	 *    increases due to interest faster than it is paid of and therefore will never be paid off
	 */
	public boolean mortgageLifeSpan(double payment, double apr, double lumpSum) {
		
		double monthlyRate = apr/12.00; //monthly interest
		double newLumpSum; //create new variable for new total after every month to ensure it is not increasing
		
		//continue calculating new total balance until balance=0 one time through loop equalts one month
		while (lumpSum > 0){
			//calculate new value after interest added and monthly payment subtracted
			newLumpSum=lumpSum*(1+monthlyRate);
			newLumpSum=newLumpSum-payment;
			
			//if the new value is higher return false that mortgage will never be paid off
			if (newLumpSum>lumpSum)
				return false;
			
			lumpSum=newLumpSum;
			
			//increment months every time through loop to track total months of payment
			months++;
		}
		
		//represent months to maturity in years and months
		yearsToMaturity=months/12;
		monthsToMaturity=months%12;
		
		//find future pay off date
		maturityMonth = currentMonth.plus(monthsToMaturity);
		maturityYear = currentYear + yearsToMaturity;
		
		//if months goes past January add 1 to years
		if (currentMonth.getValue() + monthsToMaturity > 12)
			maturityYear++;
		
		return true;
	}
	
	/**
	 * This method returns a String representation of the maturity date in months and years
	 * @return String representation of the maturity date in months and years
	 */
	public String getMaturityDate() {
		return maturityMonth + " of " + maturityYear;
	}
	
	/**
	 * This method finds the difference in months and years between 2 dates
	 * @param compareMonth - month of date to compare
	 * @param compareYear - year of date to compare
	 * @return String representation of the delta in months and years
	 */
	public String getYearDelta(int compareMonth, int compareYear) {
		
		//number of months and years different
		deltaMonth = maturityMonth.getValue() - compareMonth;
		deltaYear = maturityYear-compareYear;
		
		//if month difference passes January subtract 1 from years
		if (deltaMonth < 0) {
			deltaMonth+=12;
			deltaYear--;
		}
		
		//if year difference is 0 then express difference in just months
		if (deltaYear==0)
			return deltaMonth + " months";
		else
			return deltaYear + " years and " + deltaMonth + " months";
	}
	
	/**
	 * This method returns the index of the month of maturity
	 * @return int 
	 */
	public int getMaturityMonth() {
		return maturityMonth.getValue();
	}
	
	/**
	 * This method returns the year of maturity
	 * @return int
	 */
	public int getMaturityYear() {
		return maturityYear;
	}
	
	/**
	 * This method calculates the monthly payment needed to pay off the loan by a given date
	 * @param lumpSum - total balance to pay off
	 * @param apr - annual percentage rate
	 * @param _maturityMonth - index of month of maturity
	 * @param _maturityYear -year of maturity
	 * @return double - monthly payment needed
	 */
	public double monthlyPayment(double lumpSum, double apr, int _maturityMonth, int _maturityYear) {
		
		int months;  //calculate months to maturity
		double monthlyRate = apr/12.00; //find monthly interest
		double payment; //monthly payment
		
		//find month and year difference
		deltaMonth = _maturityMonth - (currentMonth.getValue()-1);
		deltaYear = _maturityYear-currentYear;
		
		//if month difference passes January subtract 1 from years
		if (deltaMonth < 0) {
			deltaMonth+=12;
			deltaYear--;
		}
		
		//find total months
		months=deltaYear*12+deltaMonth;
		
		//calcualte monthly payment
		double compound = Math.pow(1+monthlyRate,months);
		payment=lumpSum/((compound-1)/(compound*monthlyRate));
		
		//return monthly payment
		return payment;
	}
	
	/**
	 * This method will find maturity date given birthday and age at which mortgage will be paid off
	 * @param birthMonth - month of birth
	 * @param birthYear - year of birth
	 */
	public void findMaturityDate(int birthMonth, int birthYear, int age) {
		//maturity month is same as birth month
		maturityMonth=Month.of(birthMonth);
		
		//add age to birth year to get maturity year
		maturityYear=birthYear+age;
	}
}
