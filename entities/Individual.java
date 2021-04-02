package entities;

import java.util.ArrayList;
import java.util.Date;
public class Individual {
	private int lineNum;	// the line number the Individual was created on
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
	public Individual(String id, int lineNum) {
		this.id = id;
		this.lineNum = lineNum;
		this.isAlive = true; // will be true unless the death date is set
		this.child = new ArrayList<String>();
		this.spouse = new ArrayList<String>();
	}

	// Getters and Setters
	public int getLineNum(){
		return lineNum;
	}

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

	public void setAge() {
		this.age = calculateAge();
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

	// TODO implement US28: Order siblings by age
	public void addChild(String child) {
		this.child.add(child);
	}

	public  ArrayList<String> getSpouse() {
		return spouse;
	}

	public void addSpouse(String spouse) {
		this.spouse.add(spouse);
	}

	// returns all object's variables as strings for the table
	public String[] getAsString() {
		String[] values = new String[8];
		
		values[0] = this.id;
		values[1] = this.name;
		values[2] = this.gender+"";
		values[3] = (this.birthday != null) ? this.birthday.toString() : "NA";
		values[4] = this.age+"";
		values[5] = (this.death != null) ? this.death.toString() : "NA";
		values[6] = this.child.toString();
		values[7] = this.spouse.toString();
		
		return values;
	}
	
	@SuppressWarnings(value = { "deprecation" })
	private int calculateAge(){
		int age = 0;
		int birth_year = this.birthday.getJavaDate().getYear() + 1900;
		if (this.death != null){
			int death_year = this.death.getJavaDate().getYear() + 1900;
			if (this.death.getJavaDate().getMonth() > this.birthday.getJavaDate().getMonth()){
				age = death_year - birth_year;
			}
			else if (this.death.getJavaDate().getMonth() == this.birthday.getJavaDate().getMonth()){
				if (this.death.getJavaDate().getDay() >= this.birthday.getJavaDate().getDay()){
					age = death_year - birth_year;
				}
			}
			else{
				age = death_year - birth_year - 1;
			}
		}
		else{
			Date today = new Date();
			int curr_year = today.getYear() + 1900;
			if (today.getMonth() > this.birthday.getJavaDate().getMonth()){
				age = curr_year - birth_year;
			}
			else if (today.getMonth() == this.birthday.getJavaDate().getMonth()){
				if (today.getDay() >= this.birthday.getJavaDate().getDay()){
					age = curr_year - birth_year;
				} else {
					age = curr_year - birth_year - 1;
				}
			}
			else{
				age = curr_year - birth_year - 1;
			}
		}
		return age;
	}

	private boolean birth_before_death(){
		if (this.death != null){
			if (this.death.getJavaDate().after(this.birthday.getJavaDate())){
				return true;
			}
			return false;
		}
		return true;
		
	}

	public String toString() {

		try{
			if (birth_before_death() == false){
				throw new IllegalArgumentException("ERROR: INDIVIDUAL: US03: " + this.name + "[" +this.id + "]" + ": died " + this.death + "before born" + this.birthday + "\n");
			}
		}catch (Exception e) {
			throw new IllegalArgumentException("ERROR: INDIVIDUAL: US03: " + this.name + "[" +this.id + "]" + ": died " + this.death + " before born " + this.birthday + "\n");

		}

		return "Individual:\n"
				+ "\tId:\t\t" + this.id + "\n"
				+ "\tName:\t\t"+ this.name + "\n"
				+ "\tGender:\t\t"+ this.gender + "\n"
				+ "\tBirthday:\t"+ this.birthday + "\n"
				+ "\tAge:\t\t" + this.age + "\n"
				+ "\tAlive:\t\t" + (isAlive ? "Y" : "N") + "\n"
				+ "\tDeath:\t\t" + (death==null ? "NA" : this.death) + "\n"
				+ "\tChild:\t\t" + this.child.toString() + "\n"
				+ "\tSpouse:\t\t" + this.spouse.toString();
	}
	
}
