package parser;
import entities.*;
import java.util.Date;
import java.util.HashMap;
public class Validations {
    
//User Story #10 - Marriage after 14
// (Both parents are at least 14 years old)

public static String checkMarriageAge(HashMap<String, Family> families, HashMap<String, Individual> individuals){
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

/**
 * Checks that each individual record has a corresponding record in families when necessary
 * @param families hashmap of all families
 * @param individuals hashmap of all individuals
 * @return string of inconsistencies or empty string if none found
 */
public static String checkCorrespondingEntries(HashMap<String, Family> families, HashMap<String, Individual> individuals){
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
                output += "Error: Wife with ID: "+fam.getWifeId()+" in Family Record not found in Individuals Record.\n";
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
                output += "Error: Husband with ID: "+fam.getHusbId()+" in Family Record not found in Individuals Record.\n";
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
                        output += "Error: Child with ID: "+childId+" in Family Record not found in Individuals Record.\n";
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
                        output += "Error: Family with ID: "+famId+" in Individuals Record not found in Family Record.\n";
                    }
                }
            }    
        }
        if(!person.getSpouse().isEmpty()){
            for(String famId : person.getSpouse()){
                if(families.get(famId)!=null){
                    if(!families.get(famId).getHusbId().equals(key) && !families.get(famId).getWifeId().equals(key)){
                        output += "Error: Family with ID: "+famId+" in Individuals Record not found in Family Record.\n";
                    }
                }
            }    
        }
      
    }
    return output;
}

/**
 * Checks that marriage is before death of spouses
 * @param families hashmap of all families
 * @param individuals hashmap of all individuals
 * @return string of inconsistencies or empty string if none found
 */
public static String checkMarriedBeforeDeath(HashMap<String, Family> families, HashMap<String, Individual> individuals){
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
                        output += "Error: Wife death occurs before her marriage.\n";
                    }
                }
                if(!husb.isAlive() && husb.getDeath()!= null){
                    Date husbDeath = husb.getDeath().getJavaDate();
                    if(marriageDate.after(husbDeath)){
                        output += "Error: Husband death occurs before his marriage.\n";
                    }
                }
            }
        }
    }
    return output;
}
/**
 * Checks that divorce is before death of spouses
 * @param families hashmap of all families
 * @param individuals hashmap of all individuals
 * @return string of inconsistencies or empty string if none found
 */
public static String checkDivorcedBeforeDeath(HashMap<String, Family> families, HashMap<String, Individual> individuals){
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
                        output += "Error: Wife death occurs before her marriage.\n";
                    }
                }
                if(!husb.isAlive() && husb.getDeath()!= null){
                    Date husbDeath = husb.getDeath().getJavaDate();
                    if(divorceDate.after(husbDeath)){
                        output += "Error: Husband death occurs before his marriage.\n";
                    }
                }
            }
            
        }
    }
    return output;
}

public static String birth_before_marriage(HashMap<String, Family> families, HashMap<String, Individual> individuals){
    String output = "";
    for(String key : families.keySet()){
        Family fam = families.get(key);
        if(fam.getMarried() != null){
            if(fam.getWifeId() != null){
                Individual wife = individuals.get(fam.getWifeId());
                if(wife.getBirthday().getJavaDate().after(fam.getMarried().getJavaDate())){
                    output += ("ERROR: INDIVIDUAL: US02: " + wife.getId() + " born on " + wife.getBirthday() + " after marriage date " + fam.getMarried() + "\n");
                }
            }
            if(fam.getHusbId() != null){
                Individual husb = individuals.get(fam.getHusbId());
                if(husb.getBirthday().getJavaDate().after(fam.getMarried().getJavaDate())){
                    output += ("ERROR: INDIVIDUAL: US02: " + husb.getId() + " born on " + husb.getBirthday() + " after marriage date " + fam.getMarried() + "\n");
                }
            }
        }
        
    }
    return output;
}

public static String marriage_before_divorce(HashMap<String, Family> families, HashMap<String, Individual> individuals){
    String output = "";
    for(String key : families.keySet()){
        Family fam = families.get(key);
        if(fam.getDivorced() != null){
            if(fam.getWifeId() != null && fam.getHusbId() != null){
                if(fam.getMarried().getJavaDate().after(fam.getDivorced().getJavaDate())){
                    output += ("ERROR: FAMILY: US04: divorced " + fam.getDivorced() + " before married " + fam.getMarried() + "\n");
                }
            }   
        }  
    }
    return output;
}






}// end class Validations
