package GEDCOM.Entities;

import java.util.Arrays;
import java.util.Date;

public class Family {

	private String id;
	private Date married;
	private Date divorced;
	private String husbId;
	private String husbName;
	private String wifeId;
	private String wifeName;
	private String[] childIds;
	
	public Family(String id) {
		this.id = id;
	}
	
	// Getters and Setters
	public String getId() {
		return id;
	}

	public Date getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = stringToDate(married);
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

	public String[] getChildIds() {
		return childIds;
	}

	public void setChildIds(String[] childIds) {
		this.childIds = childIds;
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
	
}
