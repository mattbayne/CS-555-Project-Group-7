package GEDCOM.Entities;

import java.util.Date;

public class GEDDate {
	private Date date;
	private final int[] dayCount = { 31,28,31,30,31,30,31,31,30,31,30,31};
	private final String[] months = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
	private int day, month = -1, year;
	
	@SuppressWarnings("deprecation")
	public GEDDate(String date) {		
		try {
			String[] dateSplit = date.split(" ");
			this.day = Integer.parseInt(dateSplit[0]);
			for(int i = 0; i < months.length; i++) {
				if ( dateSplit[1].equals(months[i])) {
					this.month = i;
				}
			}
			if (this.month == -1) {
				throw new IllegalArgumentException("month does not exist");
			}
			this.year = Integer.parseInt(dateSplit[2]);
			
			if(!isProperDate()) {
				throw new IllegalArgumentException("improper date");
			}
		} catch(Exception e) {
			throw new IllegalArgumentException("GEDCOM Date: " + date + " is not a proper date.");
		}
		
		this.date = new Date(this.year - 1900, this.month, this.day);
	}
	
	/**
	 * @return the date represented as an instance of a java.util.Date object
	 */
	public Date getJavaDate() {
		return this.date;
	}
	
	/**
	 * Verifies that the date passed in is a valid date
	 * @return true if date is valid, false otherwise
	 */
	private boolean isProperDate() {
		if ( this.day <= 0 || // Checks that the day is a positive number
			this.day > this.dayCount[this.month] ) { // tests all other days against their respective month
			// handles the leap year case for February
			if ( (this.year % 4) == 0 && this.month == 1 && this.day <= 29 ) {
				return true;
			}
			return false;
		}
		return true;
	}
	
	public String toString() {
		return this.day + " " + months[this.month] + " " + this.year;
	}
	
	
}