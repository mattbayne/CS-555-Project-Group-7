package entities;

import java.util.ArrayList;

public class Individual {
	private String id;		// id of the individual
	private String name;
	private char gender;		// either 'M' or 'F'
	private GEDDate birthday;	// GEDDate of birth
	private int age;
	private boolean isAlive;
	private GEDDate death;	// GEDDate of death
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

	public GEDDate getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = new GEDDate(birthday);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = calculateAge(age);
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public GEDDate getDeath() {
		return death;
	}

	public void setDeath(String death) {
		this.death = new GEDDate(death);
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

	private int calculateAge(int age){
		if (this.death != null){
			age = this.death.year - this.birthday.year;
		}
		else{
			age = 2021 - getBirthday().year;
		}
		return age;
	}

	private boolean birth_before_death(){
		if (this.death != null){
			if (this.death.date.after(this.birthday.date)){
				return true;
			}
			return false;
		}
		return true;
		
	}

	public String toString() {

		try{
			if (birth_before_death() == false){
				throw new IllegalArgumentException("ERROR: INDIVIDUAL: US03:" + calculateAge(this.age) + ": died " + this.death + "before born" + this.birthday + "\n");
			}
		}catch (Exception e) {
			throw new IllegalArgumentException("ERROR: INDIVIDUAL: US03: " + calculateAge(this.age) + ": died " + this.death + " before born " + this.birthday + "\n");

		}

		return "Individual:\n"
				+ "\tId:\t\t" + this.id + "\n"
				+ "\tName:\t\t"+ this.name + "\n"
				+ "\tGender:\t\t"+ this.gender + "\n"
				+ "\tBirthday:\t"+ this.birthday + "\n"
				+ "\tAge:\t\t" + calculateAge(this.age) + "\n"
				+ "\tAlive:\t\t" + (isAlive ? "Y" : "N") + "\n"
				+ "\tDeath:\t\t" + (death==null ? "NA" : this.death) + "\n"
				+ "\tChild:\t\t" + this.child.toString() + "\n"
				+ "\tSpouse:\t\t" + this.spouse.toString();
	}
	
}
