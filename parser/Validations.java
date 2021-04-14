package parser;
import entities.*;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

public class Validations {

    private HashMap<String,Family> families;
    private HashMap<String,Individual> individuals;

    public Validations(HashMap<String,Family> families, HashMap<String,Individual> individuals){
        this.families = families;
        this.individuals = individuals;
    }

    //User Story #10 - Marriage after 14
    // (Both parents are at least 14 years old)

    public String checkMarriageAge(){
        String output = "";
        for(String key : families.keySet()){
            Family fam = families.get(key);
            if(fam.getMarried() != null){
                if(fam.getWifeId() != null){
                    Individual wife = individuals.get(fam.getWifeId());
                    if(wife.getBirthday().yearsSince(fam.getMarried().getJavaDate()) < 14){
                        output += "Error: Wife with ID: "+wife.getId()+" not old enough to be married on date:"+ fam.getMarried()+ ".\n";
                    }
                }
                if(fam.getHusbId() != null){
                    Individual husb = individuals.get(fam.getHusbId());
                    if(husb.getBirthday().yearsSince(fam.getMarried().getJavaDate()) < 14){
                        output += "Error: Husband with ID: "+husb.getId()+" not old enough to be married on date:"+ fam.getMarried()+ ".\n";
                    }
                }
            }
            
        }
        return output;
    }

    //User Story #9 birth of child has to be before death of mother and prior to 9 months post death of husband
    @SuppressWarnings("deprecation")
    public String birth_before_death_parents(){
        HashMap<Integer, Integer[]> months_map = new HashMap<Integer, Integer[]>();
                months_map.put(1,new Integer[]{1,2,3,4,5,6,7,8,9,10});
                months_map.put(2,new Integer[]{2,3,4,5,6,7,8,9,10,11});
                months_map.put(3,new Integer[]{3,4,5,6,7,8,9,10,11,12});
                months_map.put(4,new Integer[]{4,5,6,7,8,9,10,11,12,1});
                months_map.put(5,new Integer[]{5,6,7,8,9,10,11,12,1,2});
                months_map.put(6,new Integer[]{6,7,8,9,10,11,12,1,2,3});
                months_map.put(7,new Integer[]{7,8,9,10,11,12,1,2,3,4});
                months_map.put(8,new Integer[]{8,9,10,11,12,1,2,3,4,5});
                months_map.put(9,new Integer[]{9,10,11,12,1,2,3,4,5,6});
                months_map.put(10,new Integer[]{10,11,12,1,2,3,4,5,6,7});
                months_map.put(11,new Integer[]{11,12,1,2,3,4,5,6,7,8});
                months_map.put(12,new Integer[]{12,1,2,3,4,5,6,7,8,9});
        String output = "";
        for(String key : families.keySet()){
            Family fam = families.get(key);
            if(fam.getWifeId() != null){
                Individual wife = individuals.get(fam.getWifeId()); 
                if (wife.getDeath() != null){
                    if(!fam.getChildIds().isEmpty()){
                        for(String childId : fam.getChildIds()){
                            Individual child = individuals.get(childId);
                            if (child.getBirthday().getJavaDate().after(wife.getDeath().getJavaDate())){
                                output += ("ERROR: FAMILY: US09: Child " + childId + " was born " + child.getBirthday() + " after mothers death " + wife.getDeath() +
                                generateError(fam));
                            }        
                        }
                    }
                }  
            }
            if (fam.getHusbId() != null){
                Individual husb = individuals.get(fam.getHusbId());
                if (husb.getDeath() != null){
                    int husb_death_month = husb.getDeath().getJavaDate().getMonth() + 1;
                    if(!fam.getChildIds().isEmpty()){
                        for(String childId : fam.getChildIds()){
                            Individual child = individuals.get(childId);
                            int child_birth_month = child.getBirthday().getJavaDate().getMonth() + 1;
                            if (child.getBirthday().getJavaDate().after(husb.getDeath().getJavaDate())){
                                if (husb.getDeath().yearsSince(child.getBirthday().getJavaDate()) <= 1){
                                    if(months_map.get(husb_death_month)!= null){
                                        boolean in_range = false;
                                        for (int i = 0; i < months_map.get(husb_death_month).length; i++){
                                            if (months_map.get(husb_death_month)[i] != child_birth_month){
                                                in_range = false;
                                            }
                                            else{
                                                in_range = true;
                                                break;
                                            }
                                        }
                                       if (in_range == false){
                                            output += ("ERROR: FAMILY: US09: Child " + childId + " born on " + child.getBirthday() +
                                            " past 9 months after fathers death " + husb.getDeath() + generateError(fam));     
                                        }
                                    }  
                                }  
                                
                                else {
                                    output += ("ERROR: FAMILY: US09: Child " + childId + " born on " + child.getBirthday() +
                                    " past 9 months after fathers death " + husb.getDeath() + generateError(fam));     
    
                                }
                            }        
                        }
                    }
                }  
                }
            }
        
        return output;
    }

    //User Story #18 Siblings should not marry one another
    public String siblingsNoMarry(){
        String output = "";
        for(Family fam : families.values()){
            Individual wife = individuals.get(fam.getWifeId());
            Individual husb = individuals.get(fam.getHusbId());
            if (wife == null || husb == null) continue;
            if(!wife.getChild().isEmpty()){
                for(String famId : wife.getChild()){
                    if(husb.getChild().indexOf(famId) !=-1){
                        output += "ERROR: Two siblings are married.";
                        output += generateError(wife);
                        output += generateError(husb, fam);
                    }
                }
            }
        }
        return output;
    }

    //User Story #23 No more than one individual with the same name and birth date should appear
    public String uniqueNameAndDate(){
        String output = "";
        ArrayList<String> set = new ArrayList<String>();
        for(Individual person : individuals.values()){
            if(person.getName() != null){
                String entry = person.getName() + person.getBirthday().toString();
                boolean found = false;
                for (String x : set) {
                	if (x.equals(entry)) {
                		found = true;
                		break;
                	}
                }
                if(found){
                    output = "ERROR: Two or more individuals have the same name and birthday.";
                    output += generateError(person);
                }else{
                    set.add(entry);
                }
            }
        }
        return output;
    }


    //User Story #8 birth of child has to be after marriage and prior to 9 months post divorce
    @SuppressWarnings("deprecation")
    public String birth_before_marriage_parents(){
        HashMap<Integer, Integer[]> months_map = new HashMap<Integer, Integer[]>();
                months_map.put(1,new Integer[]{1,2,3,4,5,6,7,8,9,10});
                months_map.put(2,new Integer[]{2,3,4,5,6,7,8,9,10,11});
                months_map.put(3,new Integer[]{3,4,5,6,7,8,9,10,11,12});
                months_map.put(4,new Integer[]{4,5,6,7,8,9,10,11,12,1});
                months_map.put(5,new Integer[]{5,6,7,8,9,10,11,12,1,2});
                months_map.put(6,new Integer[]{6,7,8,9,10,11,12,1,2,3});
                months_map.put(7,new Integer[]{7,8,9,10,11,12,1,2,3,4});
                months_map.put(8,new Integer[]{8,9,10,11,12,1,2,3,4,5});
                months_map.put(9,new Integer[]{9,10,11,12,1,2,3,4,5,6});
                months_map.put(10,new Integer[]{10,11,12,1,2,3,4,5,6,7});
                months_map.put(11,new Integer[]{11,12,1,2,3,4,5,6,7,8});
                months_map.put(12,new Integer[]{12,1,2,3,4,5,6,7,8,9});
        String output = "";
        for(String key : families.keySet()){
            Family fam = families.get(key);
            if(fam.getMarried() != null){
                if(!fam.getChildIds().isEmpty()){
                    for(String childId : fam.getChildIds()){
                        Individual child = individuals.get(childId);
                        if (fam.getMarried().getJavaDate().after(child.getBirthday().getJavaDate())){
                            output += ("ERROR: FAMILY: US08: Child " + childId + " was born " + child.getBirthday() + " before parents married " + fam.getMarried() +
                                generateError(fam));
                        }        
                    }
                }  
            }
            if (fam.getDivorced() != null){
                if(!fam.getChildIds().isEmpty()){
                    for(String childId : fam.getChildIds()){
                        Individual child = individuals.get(childId);
                        int divorce_month = fam.getDivorced().getJavaDate().getMonth() + 1;
                        int child_birth_month = child.getBirthday().getJavaDate().getMonth() + 1;
                        if (child.getBirthday().getJavaDate().after(fam.getDivorced().getJavaDate())){
                            if (fam.getDivorced().yearsSince(child.getBirthday().getJavaDate()) <= 1){
                                if(months_map.get(divorce_month)!= null){
                                    boolean in_range = false;
                                    for (int i = 0; i < months_map.get(divorce_month).length; i++){
                                        if (months_map.get(divorce_month)[i] != child_birth_month){
                                            in_range = false;
                                        }
                                        else{
                                            in_range = true;
                                            break;
                                        }
                                    }
                                    if (in_range == false){
                                        output += ("ERROR: FAMILY: US08: Child " + childId + " born on " + child.getBirthday() +
                                        " past 9 months after divorce date " + fam.getDivorced() + generateError(fam));     
                                    }
                                }  
                            }
                            else {
                                output += ("ERROR: FAMILY: US08: Child " + childId + " born on " + child.getBirthday() +
                                " past 9 months after divorce date " + fam.getDivorced() + generateError(fam));     

                            }
                        }                               
                    }
                }
            }
        }
        return output;
    }

    /**
     * Checks that each individual record has a corresponding record in families when necessary
     * @return string of inconsistencies or empty string if none found
     */
    public String checkCorrespondingEntries(){
        String output = "";
        for(String key : families.keySet()){
            Family fam = families.get(key);
            if(fam.getWifeId() != null && individuals.get(fam.getWifeId())==null){
                boolean found = false;
                for(String famId: individuals.get(fam.getWifeId()).getSpouse()){
                    if(famId.equals(key)){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    output += "Error: Wife with ID: "+fam.getWifeId()+" in Family Record not found in Individuals Record."+
                        generateError(fam);
                }
            }
            if(fam.getHusbId() != null && individuals.get(fam.getHusbId())==null){
                boolean found = false;
                for(String famId: individuals.get(fam.getHusbId()).getSpouse()){
                    if(famId.equals(key)){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    output += "Error: Husband with ID: "+fam.getHusbId()+" in Family Record not found in Individuals Record."+
                        generateError(fam);
                }
            }
            if(!fam.getChildIds().isEmpty()){
                for(String childId : fam.getChildIds()){
                    if(individuals.get(childId)!=null){
                        boolean found = false;
                        for(String famId: individuals.get(childId).getChild()){
                            if(famId.equals(key)){
                                found = true;
                                break;
                            }
                        }
                        if(!found){
                            output += "Error: Child with ID: "+childId+" in Family Record not found in Individuals Record."+
                                generateError(fam);
                        }
                    }
                }    
            }
        }
        for(String key : individuals.keySet()){
            Individual person = individuals.get(key);
            
            if(!person.getChild().isEmpty()){
                for(String famId : person.getChild()){
                    if(families.get(famId)!=null){
                        boolean found = false;
                        for(String childId: families.get(famId).getChildIds()){
                            if(childId.equals(key)){
                                found = true;
                                break;
                            }
                        }
                        if(!found){
                            output += "Error: Family with ID: "+famId+" in Individuals Record not found in Family Record."+
                                generateError(person);
                        }
                    }
                }    
            }
            if(!person.getSpouse().isEmpty()){
                for(String famId : person.getSpouse()){
                    if(families.get(famId)!=null){
                        if(!families.get(famId).getHusbId().equals(key) && !families.get(famId).getWifeId().equals(key)){
                            output += "Error: Family with ID: "+famId+" in Individuals Record not found in Family Record."+
                                generateError(person);
                        }
                    }
                }    
            }
        
        }
        return output;
    }

    /**
     * Checks that marriage is before death of spouses
     * @return string of inconsistencies or empty string if none found
     */
    public String checkMarriedBeforeDeath(){
        String output = "";
        for(String key : families.keySet()){
            Family fam = families.get(key);
            if(fam.getWifeId() != null && fam.getHusbId() != null){
                Individual wife = individuals.get(fam.getWifeId());
                Individual husb = individuals.get(fam.getHusbId());
                if(fam.getMarried() != null){
                    Date marriageDate = fam.getMarried().getJavaDate();
                    if(!wife.isAlive() && wife.getDeath()!= null){
                        Date wifeDeath = wife.getDeath().getJavaDate();
                        if(marriageDate.after(wifeDeath)){
                            output += "Error: Wife with id: "+ wife.getId() +" has death that occurs before her marriage."+
                                generateError(wife,fam);
                        }
                    }
                    if(!husb.isAlive() && husb.getDeath()!= null){
                        Date husbDeath = husb.getDeath().getJavaDate();
                        if(marriageDate.after(husbDeath)){
                            output += "Error: Husband with id: "+ husb.getId() +" has death that occurs before his marriage."+
                                generateError(husb,fam);
                        }
                    }
                }
            }
        }
        return output;
    }
    /**
     * Checks that divorce is before death of spouses
     * @return string of inconsistencies or empty string if none found
     */
    public String checkDivorcedBeforeDeath(){
        String output = "";
        for(String key : families.keySet()){
            Family fam = families.get(key);
            if(fam.getWifeId() != null && fam.getHusbId() != null){
                Individual wife = individuals.get(fam.getWifeId());
                Individual husb = individuals.get(fam.getHusbId());
                if(fam.getDivorced() != null){
                    Date divorceDate = fam.getDivorced().getJavaDate();
                    if(!wife.isAlive() && wife.getDeath()!= null){
                        Date wifeDeath = wife.getDeath().getJavaDate();
                        if(divorceDate.after(wifeDeath)){
                            output += "Error: Wife with id: "+ wife.getId() +" has death that occurs before her divorce."+
                                generateError(wife,fam);
                        }
                    }
                    if(!husb.isAlive() && husb.getDeath()!= null){
                        Date husbDeath = husb.getDeath().getJavaDate();
                        if(divorceDate.after(husbDeath)){
                            output += "Error: Husband with id: "+ husb.getId() +" has death that occurs before his divorce."+
                                generateError(husb,fam);
                        }
                    }
                }
                
            }
        }
        return output;
    }

    public String birth_before_marriage(){
        String output = "";
        for(String key : families.keySet()){
            Family fam = families.get(key);
            if(fam.getMarried() != null){
                if(fam.getWifeId() != null){
                    Individual wife = individuals.get(fam.getWifeId());
                    if(wife.getBirthday().getJavaDate().after(fam.getMarried().getJavaDate())){
                        output += ("ERROR: INDIVIDUAL: US02: " + wife.getId() + " born on " + wife.getBirthday() +
                            " after marriage date " + fam.getMarried() + generateError(wife,fam));
                    }
                }
                if(fam.getHusbId() != null){
                    Individual husb = individuals.get(fam.getHusbId());
                    if(husb.getBirthday().getJavaDate().after(fam.getMarried().getJavaDate())){
                        output += ("ERROR: INDIVIDUAL: US02: " + husb.getId() + " born on " + husb.getBirthday() +
                            " after marriage date " + fam.getMarried() + generateError(husb,fam));
                    }
                }
            }
            
        }
        return output;
    }

    public String marriage_before_divorce(){
        String output = "";
        for(String key : families.keySet()){
            Family fam = families.get(key);
            if(fam.getDivorced() != null){
                if(fam.getWifeId() != null && fam.getHusbId() != null){
                    if(fam.getMarried().getJavaDate().after(fam.getDivorced().getJavaDate())){
                        output += ("ERROR: FAMILY: US04: divorced " + fam.getDivorced() + " before married " + fam.getMarried() +
                                    generateError(fam));
                    }
                }   
            }  
        }
        return output;
    }

    public String birth_before_death(){
        String output = "";
        for(String key : individuals.keySet()){
            Individual indi = individuals.get(key);
            if (indi.getDeath() != null){
                if (indi.getDeath().getJavaDate().after(indi.getBirthday().getJavaDate())){
                    continue;
                }
                else{
                    output += ("ERROR: INDIVIDUAL: US03: born " + indi.getBirthday() + " after death " + indi.getDeath() +
                    generateError(indi));
                }
            }

        }
        return output;
    }
        
    // User Story #07 - Less than 150 years old
    // (Death should be less than 150 years after birth for dead people, and current date should be less than 150 years after birth for all living people)

    public String check_under_150(){
        String output = "";
        for(String key : individuals.keySet()){
            Individual person = individuals.get(key);
            
            if(!person.isAlive()){
                if(person.getBirthday().yearsSince(person.getDeath().getJavaDate()) >= 150){
                            output += "Error: Individual with ID: "+person.getId()+" | Death (" +person.getDeath()+") is greater than 150 years after birth ("+ person.getBirthday()+ ").\n";
                } 
            }
            
            if(person.isAlive()){
                if(person.getBirthday().yearsSinceToday() >= 150){
                            output += "Error: Individual with ID: "+person.getId()+" | Current date is greater than 150 years after birth ("+ person.getBirthday()+ ").\n";
                } 
            }
        }
        return output;
    }

    // User Story #12 - Parents not too old
    // (Mother should be less than 60 years older than her children and father should be less than 80 years older than his children)

    public String parents_not_old(){
        String output = "";
        for(String key : families.keySet()){
            Family fam = families.get(key);
            
            if(!(individuals.get(fam.getWifeId()) == null)) {
                Individual mother = individuals.get(fam.getWifeId());
                if(!fam.getChildIds().isEmpty()){
                    for(String childId : fam.getChildIds()){
                            Individual child = individuals.get(childId);
                            //less than 60 
                            if(mother.getBirthday().yearsSince(child.getBirthday().getJavaDate()) >= 60){
                                output += "Error: Individual with ID: "+mother.getId()+" and Child with ID: "+mother.getId()+" Mother's birthday (" +mother.getBirthday()+") is greater than 60 years after childs birth ("+ child.getBirthday()+ ").\n";
                            } 
                    }
                }
                
            }
            
            
            if(!(individuals.get(fam.getHusbId()) == null)) {
                Individual father = individuals.get(fam.getHusbId());
                if(!fam.getChildIds().isEmpty()){
                    for(String childId : fam.getChildIds()){
                            Individual child = individuals.get(childId);
                            //less than 80
                            if(father.getBirthday().yearsSince(child.getBirthday().getJavaDate()) >= 80){
                                output += "Error: Individual with ID: "+father.getId()+" and Child with ID: "+child.getId()+" Father's birthday (" +father.getBirthday()+") is greater than 80 years after childs birth ("+ child.getBirthday()+ ").\n";
                            } 
                    }
                }
            }
        }
        return output;
    } //end parents_not_old

    /**
     * Will check that each role in a family is filled by a person with the correct gender
     *      - Wife -> Female
     *      - Husband -> Male
     * @return String of all errors discovered
     */
    public String checkGenderRoles(){
        String output = "";

        for (Family fam : families.values()){
            // Checks that the wife is female
            if(fam.getWifeId() != null && individuals.get(fam.getWifeId()).getGender() != 'F'){
                output += "Error: Wife must be female." + generateError(individuals.get(fam.getWifeId()), fam);
            }
            // Checks that the husband if male
            if(fam.getHusbId() != null && individuals.get(fam.getHusbId()).getGender() != 'M'){
                output += "Error: Husband must be male." + generateError(individuals.get(fam.getHusbId()), fam);
            }
        }

        return output;
    }
    
     // User Story #15 - Fewer than 15 siblings
    // (There should be fewer than 15 siblings in a family)
    public String checkSiblingCount() {
    	String output = "";
    
    	 for (Family fam : families.values()){
    		 int child_count = 0;
    		 if(!fam.getChildIds().isEmpty()){
                 for(String childId : fam.getChildIds()){
                         child_count++;
                 }
    		 }
    		 
    		 if (!(child_count < 15)) {
        		 output += "Error: There should be fewer than 15 siblings in a family: "+fam.getId()+ " has "+child_count+" children.\n" + generateError(fam);
        	 }
    	 }
    	 
    	 return output;
    } //end checkSiblingCount()
    
    // User Story #16 - Male last names
    // (All male members of a family should have the same last name)
    public String checkMaleLastNames() {
    	String output = "";
    	String last_name;
    	
    	for (Family fam : families.values()){
    		Individual husb = individuals.get(fam.getHusbId());
    		last_name = husb.getLastName();

    		if(!fam.getChildIds().isEmpty()){
                for(String childId : fam.getChildIds()){
                	Individual child = individuals.get(childId);
                
                	if(child.getGender()=='M') {
                		if(!(child.getLastName().equals(last_name))){
                			output += "Error: All male members of a family should have the same last name." + generateError(child,fam);
                		}	
                	}
                }
    		}
    	}
    	return output;
    }

    private String generateError(Individual i){
        return "\n\tIndividual "+i.getId()+": Created on Line "+i.getLineNum()+"\n";
    }
    private String generateError(Family f){
        return "\n\tFamily "+f.getId()+": Created on Line "+f.getLineNum()+"\n";
    }
    private String generateError(Individual i, Family f){
        return "\n\tIndividual "+i.getId()+": Created on Line "+i.getLineNum()+
                "\n\tFamily "+f.getId()+": Created on Line "+f.getLineNum()+"\n";
    }




}// end class Validations
