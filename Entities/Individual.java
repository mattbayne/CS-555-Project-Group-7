package GEDCOM.Entities;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Date;

public class Individual {
	private String id;		// id of the individual
	private String name;
	private char gender;		// either 'M' or 'F'
	private Date birthday;	// date of birth
	private int age;
	private boolean isAlive;
	private Date death;	// date of death
	private ArrayList<String> child;	// ids of families
	private ArrayList<String> spouse;	// ids of families
	
	// Constructor
	public Individual(String id) {
		this.id = id;
		this.child = new ArrayList<String>();
		this.spouse = new ArrayList<String>();
	}

	// Getters and Setters
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = stringToDate(birthday);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Date getDeath() {
		return death;
	}

	public void setDeath(String death) {
		this.death = stringToDate(death);
	}

	public ArrayList<String> getChild() {
		return child;
	}

	public void addChild(String child) {
		this.child.add(child);
	}

	public  ArrayList<String> getSpouse() {
		return spouse;
	}

	public void addSpouse(String spouse) {
		this.spouse.add(spouse);
	}
	
	/**
	 * Takes in the GEDCOM String representation of the date and converts it to a java.util.Date object.
	 * @param dateString GEDCOM String representation of the date
	 * @return instance of the java.util.Date class
	 */
	private Date stringToDate(String dateString) {
		final String[] gedcomMonths = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
		int day, month, year;
		
		String[] dateSplit = dateString.split(" ");
		day = Integer.parseInt(dateSplit[0]);
		for(int i = 0; i < gedcomMonths.length; i++) {
			if ( dateSplit[1].equals(gedcomMonths[i])) {
				month = i;
			}
		}
		month = Arrays.binarySearch(gedcomMonths,dateSplit[1]);
		year = Integer.parseInt(dateSplit[2]) - 1900;
		
		return new Date(year, month, day);
	}
	
	public String toString() {
		return "Individual:\n"
				+ "\tId:\t\t" + this.id + "\n"
				+ "\tName:\t\t"+ this.name + "\n"
				+ "\tGender:\t\t"+ this.gender + "\n"
				+ "\tBirthday:\t"+ this.birthday.toString() + "\n"
				+ "\tAge:\t\t" + "Not implemented" + "\n"
				+ "\tAlive:\t\t" + (isAlive ? "Y" : "N") + "\n"
				+ "\tDeath:\t\t" + (death==null ? "NA" : this.death.toString()) + "\n"
				+ "\tChild:\t\t" + this.child.toString() + "\n"
				+ "\tSpouse:\t\t" + this.spouse.toString();
	}
	
}
