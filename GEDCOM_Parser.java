package parser;
// MATTHEW BAYNE
// CS-492 Project 2

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;

import entities.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class GEDCOM_Parser{
    
    public String [] lvl0_tags = {"HEAD", "TRLR", "NOTE"};
    public String [] lvl1_tags = {"NAME", "SEX", "BIRT", "DEAT", "FAMC", "FAMS", "HUSB", "WIFE", "CHIL", "DIV", "MARR"};
    public String [] lvl2_tags = {"DATE"};
    
    public String [] dateable_tags = {"BIRT", "DEAT", "DIV", "MARR"};
    public Individual [] dead_indis = new Individual [5000];
    public Individual [] married_indis = new Individual [5000];
    
    // HashMap that maps the individual's id to the corresponding object
    public HashMap<String,Individual> individuals = new HashMap<String,Individual>();
    public ArrayList<String> indi_ids = new ArrayList<String>();
    // HashMap that maps the family's id to the corresponding object
    public HashMap<String,Family> families = new HashMap<String,Family>();
    public ArrayList<String> fam_ids = new ArrayList<String>();
    // represents the most recent individual encountered
    public Individual current_indi = null;
    // represents the most recent family encountered
    public Family current_fam = null;
    public String last_tag = null;
    public int dead_count = 0;
    public int marr_count = 0;

    //prints out the array
    public static void printList (Individual[] arr,FileWriter fw){
	int size = arr.length;
    	for (int i = 0; i < size-1; i++){
			try {
				fw.write(arr[i].toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    } //end printList()
    
    //checks if input is valid
    public String checkValid(String [][] whole_line, int iter){
    	
    	if (whole_line[iter].length == 1) {
    		return "N";
    	}
    	
    	int level = Integer.parseInt(whole_line[iter][0]);
    	String tag = whole_line[iter][1];
    	
    	
    	//if there is no tag return N
        if (tag == null){
            return "N";
        }
    	
    	//level must be between 0 and 2, otherwise return N
    	if (level < 0 || level > 2) {
            return "N";
        }
    	
    	//if level is 0, tag must be member of lvl0_tags. if it's not, check if its the INDI or FAM case, otherwise return N
    	if(level == 0){
            if (Arrays.asList(lvl0_tags).contains(tag) != true){
            	if (whole_line[iter].length > 2 && (whole_line[iter][2].equals("INDI") || whole_line[iter][2].equals("FAM"))) {
            		return "Y";
            	}
            	return "N";
            }
        }
    	
    	//if level is 1, tag must be member of lvl1_tags, otherwise return N
    	if(level == 1){
            if (Arrays.asList(lvl1_tags).contains(tag) != true){
                return "N";
            }
        }
    	
    	//if level is 2, tag must be member of lvl2_tags, otherwise return N
    	if(level == 2){
            if (Arrays.asList(lvl2_tags).contains(tag) != true){
                return "N";
            }
            return "Y";
        }
        
        //if it passes all cases, return Y
		else{
			return "Y";
		}
    	
    } //end checkValid()
    
    public void parse(String filename){
    	
    	File file = new File(filename);
    	List<String> line_list = new ArrayList<String>();
    	try {
    		File output = new File("output.txt");
    		output.createNewFile();
    		FileWriter fw = new FileWriter(output);
	        try {
	        	Scanner scanner = new Scanner(file);
	        	while (scanner.hasNextLine()) {
	        		line_list.add(scanner.nextLine());
	            }
	        	scanner.close();
	        } catch (FileNotFoundException e) {
	        	e.printStackTrace();
	        }
	         
	        String[] line_arr = line_list.toArray(new String[line_list.size()]); 
	        String[][] whole_line = new String[line_list.size()][10];
	         
	        for (int i=0; i<line_list.size(); i++) {
	        	
	        	whole_line[i] = line_arr[i].split(" ");
	        	
	        	
	        	String valid = checkValid(whole_line, i);
	        	
	        	
	        	
	        	
	        	if (whole_line[i].length == 2) {
	        		//System.out.print("<-- " + whole_line[i][0] + "|" + whole_line[i][1] + "|" + valid + "|");
	        		last_tag = whole_line[i][1];
	        	}
	        	if (whole_line[i].length == 1) {
	        		//System.out.print("<-- " + whole_line[i][0] + "|" + "N" + "|");
	        		last_tag = whole_line[i][0];
	        	}
	        	
	        	if (whole_line[i].length > 2) {	
	        		// part 3
	    			if (whole_line[i][2].equals("INDI")) {
	        			String id = whole_line[i][1];
	        			// create the last individual record to pull data for
	        			if(individuals.get(id) == null) {
	        				// individual does not yet exist
	        				current_indi = new Individual(id); 
	        				individuals.put(id, current_indi);
	        				indi_ids.add(id);
	        			} else {
	        				fw.write("Error: Individual with this ID already exists. ("+ filename + " -> Line " + i + ")");
	        			}
	        			last_tag = "INDI";
	        		} else if (whole_line[i][2].equals("FAM")) {
	        			
	        			// TODO create new family
	        			String id = whole_line[i][1];
	        			if(families.get(id) == null) {
	        				current_fam = new Family(id);
	        				families.put(id, current_fam);
	        				fam_ids.add(id);
	        			} else {
	        				fw.write("Error: Family with this ID already exists. ("+ filename + " -> Line " + i + ")");
	        			}
	        		} else {
	        			//System.out.print("<-- " + whole_line[i][0] + "|" + whole_line[i][1] + "|" + valid + "|");
	        		    // Copy rest of the arguments into an array
	        			String[] arr = Arrays.copyOfRange(whole_line[i], 2, whole_line[i].length);
	        			// Combine contents of array into one argument string
	        			String arg = "";
	        			for ( String str : arr ) {
	        				arg += str + (str.equals(arr[arr.length-1]) ? "" : " ");
	        			}
	        			String tag = whole_line[i][1];
	        			
	        			if (valid.equals("Y")) {
	        				
	        				if(tag.equals(lvl1_tags[0])) { // NAME tag for individual
	        					current_indi.setName(arg);
	        				} else if (tag.equals(lvl1_tags[1])) { // SEX tag for individual
	        					current_indi.setGender(arg.charAt(0));
	        				} else if (tag.equals(lvl1_tags[4])) { // FAMC tag for individual
	        					current_indi.addChild(arg);
	        				} else if (tag.equals(lvl1_tags[5])) { // FAMS tag for individual
	        					current_indi.addSpouse(arg);
	        				} // BIRT and DEAT excluded because they will be set when DATE is encountered
	        				// TODO FAMILY tags
	        				else if (tag.equals(lvl1_tags[6])) { // HUSB tag for fam
	        					current_fam.setHusbId(arg);
	        					current_fam.setHusbName(individuals.get(arg).getName());
	        				}
	        				else if (tag.equals(lvl1_tags[7])) { // WIFE tag for fam
	        					current_fam.setWifeId(arg);
	        					current_fam.setWifeName(individuals.get(arg).getName());
	        				}
	        				else if (tag.equals(lvl1_tags[8])) { // CHIL tag for fam
	        					current_fam.addChildIds(arg);
	        				}
	        				else if (whole_line[i][1].equals(lvl2_tags[0])) { // handles updates for the DATE fields
	            				if(Arrays.asList(dateable_tags).contains(last_tag)) {
	            					try {
		            					// TODO: US01 make sure dates given are before the current date
		            					if(last_tag.equals(dateable_tags[0])) {
		            						// TODO: US27 Determine individuals' names and store in their object
		            						// update the birthday field of last individual
		            						current_indi.setBirthday(arg);
		            					} else if (last_tag.equals(dateable_tags[1])) {
		            						// TODO: US29 Add IDs of deceased individuals into a list
											dead_indis[dead_count] = current_indi;
											dead_count++;
		            						// update death field of last individual and set isAlive to false
		            						current_indi.setDeath(arg);
		            						current_indi.setIsAlive(false);
		            					} else if (last_tag.equals(dateable_tags[2])) {
		            						// update divorce field of last family
		            						current_fam.setDivorced(arg);
		            					} else if (last_tag.equals(dateable_tags[3])) {
		            						// TODO: US10 make sure people getting married are older than 14
		            						// TODO: US30 Add IDs of married individuals into a list
											married_indis[marr_count] = current_indi;
											marr_count++;
		            						// update marriage field of last family
		            						current_fam.setMarried(arg);
		            					}
	            					} catch (Exception e) {
	            						fw.write(e.getMessage() + " ("+filename+" -> Line "+i+")\n");
	            					}
	            				} else {
	            					fw.write("Error: Cannot use DATE tag on " + last_tag + " tag. ("+filename+" -> Line "+i+")\n");
	            				}
	                		}	
	        			}
	        			/*
	        			for (int x=0; x<arr.length; x++) {
	        				System.out.print(arr[x]);
	        				System.out.print(" ");
	        			}
	        			*/
	        			last_tag = whole_line[i][1];
	        		}
	        	}	
	        }
	        
	        for(String id : indi_ids) {
	        	fw.write(individuals.get(id).toString() + "\n");
	        }
	        for(String id : fam_ids) {
	        	fw.write(families.get(id).toString() + "\n");
	        }
	        fw.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    } //end parse()

} //end class
