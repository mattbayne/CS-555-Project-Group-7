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
                    output += "Error: Wife with ID: "+husb.getId()+" not old enough to be married on date:"+ fam.getMarried()+ ".\n";
                }
            }
        }
        
    }
    return output;
}
    

}// end class Validations
