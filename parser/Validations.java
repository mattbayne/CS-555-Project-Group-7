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

public static String birth_before_marriage(HashMap<String, Family> families, HashMap<String, Individual> individuals){
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

public static String marriage_before_divorce(HashMap<String, Family> families, HashMap<String, Individual> individuals){
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
    
// User Story #07 - Less than 150 years old
// (Death should be less than 150 years after birth for dead people, and current date should be less than 150 years after birth for all living people)

public static String check_under_150(HashMap<String, Family> families, HashMap<String, Individual> individuals){
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

public static String parents_not_old(HashMap<String, Family> families, HashMap<String, Individual> individuals){
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
                        } else {output+="didnt hit";}
                }
        	}
        }
    }
    return output;
} //end parents_not_old


private static String generateError(Individual i){
    return "\nIndividual "+i.getId()+": Created on Line "+i.getLineNum();
}
private static String generateError(Family f){
    return "\nFamily "+f.getId()+": Created on Line "+f.getLineNum();
}
private static String generateError(Individual i, Family f){
    return "\nIndividual "+i.getId()+": Created on Line "+i.getLineNum()+
            "\nFamily "+f.getId()+": Created on Line "+f.getLineNum();
}




}// end class Validations
