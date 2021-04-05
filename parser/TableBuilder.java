package parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import entities.Family;
import entities.Individual;

public class TableBuilder {
	
	private String indiLine;
	private String famLine;
	
	public TableBuilder() {
		// creates horizontal line that will separate the data for individuals
		indiLine = "+";
		for (int i = 0; i < 128; i++) {
			indiLine += "-";
		}
		indiLine += "+\n";
		
		// creates horizontal line that will separate the data for families
		famLine = "+";
		for(int i = 0; i < 144; i++) {
			famLine += "-";
		}
		famLine += "+\n";
	}
	
	/**
	 * Builds a table to represent all of the individuals data
	 * @param individuals Hashmap of all individuals defined in the GEDCOM file
	 * @return Table of individuals
	 */
	public String buildIndiTable(HashMap<String,Individual> individuals) {
		String table = indiLine; 
		// adds column labels
		table += String.format("|%-5s|%-30s|%-6s|%-13s|%-4s|%-13s|%-25s|%-25s|\n", " Id","            Name",
				"Gender","  Birthday",
				"Age","    Death","    Children Families","    Spouse Families");
		table += indiLine;
		// add all the data for each individual
		if(individuals.isEmpty()) {
			table += String.format("|%129s\n", "|");
			table += String.format("%-54sNo individuals created%54s\n", "|", "|");
			table += String.format("|%129s\n", "|");
		}
		else {
			// Sorting the individuals by their ids
			Set<String> ids = individuals.keySet();
			String[] ids_array = new String[ids.size()];
			int ind = 0;
			// iterates through all ids in the keySet and stores them in an array to be sorted
			for(Iterator<String> it = ids.iterator(); it.hasNext(); ) {
				String id = it.next();
				ids_array[ind] = id;
				ind++;
			}
			// Finally sort the String array
			Arrays.sort(ids_array);
			
			// iterates through the sorted array of individual ids
			for(String indi_id : ids_array) {
				Individual indi = individuals.get(indi_id);
				// gets the values from the individual as strings
				// also converts null values into values that make more sense in the table
				String[] values = indi.getAsString();
				table += String.format("|%-5s|%-30s|%-6s|%-13s|%-4s|%-13s|%-25s|%-25s|\n", values[0],values[1],
						values[2],values[3],values[4],values[5],values[6],values[7]);
			}
		}
		table += indiLine;
		// end of the table
		
		return table;
	} // end buildIndiTable
	
	/**
	 * Builds a table to represent all of the families data
	 * @param families Hashmap of all families defined in the GEDCOM file
	 * @return Table of families
	 */
	public String buildFamTable(HashMap<String,Family> families) {
		String table = famLine;
		
		table += String.format("|%-5s|%-13s|%-13s|%-8s|%-30s|%-8s|%-30s|%-30s|\n", " Id","   Married","  Divorced",
				" HusbId","           HusbName"," WifeId","           WifeName","           Child Ids");
		table += famLine;
		if(families.isEmpty()) {
			table += String.format("|%145s\n", "|");
			table += String.format("%-63sNo families created%64s\n", "|", "|");
			table += String.format("|%145s\n", "|");
		}
		else {
			
			// Sorting the families by their ids
			Set<String> ids = families.keySet();
			String[] ids_array = new String[ids.size()];
			int ind = 0;
			// iterates through all ids in the keySet and stores them in an array to be sorted
			for(Iterator<String> it = ids.iterator(); it.hasNext(); ) {
				String id = it.next();
				ids_array[ind] = id;
				ind++;
			}
			// Finally sort the String array
			Arrays.sort(ids_array);
			
			for(String fam_id : ids_array) {
				Family fam = families.get(fam_id);
				// gets the values from the family as strings
				// also converts null values that make more sense in the table
				String[] values = fam.getAsString();
				table += String.format("|%-5s|%-13s|%-13s|%-8s|%-30s|%-8s|%-30s|%-30s|\n", values[0],values[1],
						values[2],values[3],values[4],values[5],values[6],values[7]);
			}
		}
		table += famLine;
		return table;
	}
	
}
