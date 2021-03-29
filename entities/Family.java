package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Family {

	private String id;
	private GEDDate married;
	private GEDDate divorced;
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

	public GEDDate getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = new GEDDate(married);
	}

	public GEDDate getDivorced() {
		return divorced;
	}

	public void setDivorced(String divorced) {
		this.divorced = new GEDDate(divorced);
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
	 * Will sort the arraylist of childIds by their age
	 * with the oldest in the beginning and the youngest at the end.
	 * @param individuals the hashmap of individuals used to get the details about each individual
	 */
	public void sortChildIdsByAge(HashMap<String,Individual> individuals){
		for(int i = 0; i < this.childIds.size(); i++){
			int max = i;
			for(int j = i; j < this.childIds.size(); j++){
				// if individuals age is greater than the current max we swap
				if(individuals.get(this.childIds.get(j)).getAge() > individuals.get(this.childIds.get(max)).getAge()){
					max = j;
				}
			}
			Collections.swap(this.childIds, max, i);
		}
	}
	
	public String toString() {
		return "Family:\n"
				+ "\tId:\t" + this.id + "\n"
				+ "\tMarried:\t" + (married==null ? "NA" : this.married) + "\n"
				+ "\tDivorced:\t" + (divorced==null ? "NA" : this.divorced) + "\n"
				+ "\tHusb Id:\t" + this.husbId + "\n"
				+ "\tHusb Name:\t" + this.husbName + "\n"
				+ "\tWife Id:\t" + this.wifeId + "\n"
				+ "\tWife Name:\t" + this.wifeName + "\n"
				+ "\tChildren:\t" + childIds.toString();
	}
	
}
