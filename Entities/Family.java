package GEDCOM.Entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Family {

	private String id;
	private boolean isMarried;
	private Date marriageDate;
	private Date divorced;
	private String husbId;
	private String husbName;
	private String wifeId;
	private String wifeName;
	private ArrayList<String> childIds;
	
	public Family(String id) {
		this.id = id;
		this.childIds = new ArrayList<String>();
	}
	
	// Getters and Setters
	public String getId() {
		return id;
	}

	public boolean getIsMarried() {
		return isMarried;
	}

	public void setIsMarried() {
		this.isMarried = true;
	}


	public Date getMarriageDate() {
		return marriageDate;
	}

	public void setMarriageDate(String date) {
		this.marriageDate = stringToDate(date);
	}

	public Date getDivorced() {
		return divorced;
	}

	public void setDivorced(String divorced) {
		this.divorced = stringToDate(divorced);
	}

	public String getHusbId() {
		return husbId;
	}

	public void setHusbId(String husbId) {
		this.husbId = husbId;
	}

	public String getHusbName() {
		return husbName;
	}

	public void setHusbName(String husbName) {
		this.husbName = husbName;
	}

	public String getWifeId() {
		return wifeId;
	}

	public void setWifeId(String wifeId) {
		this.wifeId = wifeId;
	}

	public String getWifeName() {
		return wifeName;
	}

	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}

	public ArrayList<String> getChildIds() {
		return childIds;
	}

	public void addChildIds(String childIds) {
		this.childIds.add(childIds);
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
		return "Family:\n"
				+ "\tId:\t" + this.id + "\n"
				+ "\tMarried:\t" + this.isMarried.toString() + "\n"
				+ "\tMarriage Date:\t" + this.marriedDate.toString() + "\n"
				+ "\tDivorced:\t" + this.divorced.toString() + "\n"
				+ "\tHusb Id:\t" + this.husbId + "\n"
				+ "\tHusb Name:\t" + this.husbName + "\n"
				+ "\tWife Id:\t" + this.wifeId + "\n"
				+ "\tWife Name:\t" + this.wifeName + "\n"
				+ "\tChildren:\t" + "Not implemented";
	}
	
}
