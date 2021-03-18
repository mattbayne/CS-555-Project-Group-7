package parser;
import entities.*;
import java.util.HashMap;
public class Validations {
    
//User Story #10 - Marriage after 14
// (Both parents are at least 14 years old)

public static String us10(HashMap<String, Family> families, HashMap<String, Individual> individuals){
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

public static String us26(HashMap<String, Family> families, HashMap<String, Individual> individuals){
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
}// end class Validations
