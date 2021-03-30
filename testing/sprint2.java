package testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import parser.GEDCOM_Parser;

public class sprint2 {
    public static void main(String[] args){
        try{
            String testGed = "testGed.ged";
			File testFile = new File(testGed);
			testFile.createNewFile();
			
			FileWriter fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"+
				"0 NOTE Group 7 Test File for Sprint 2\n"+
				"0 I01 INDI\n"+
                "1 BIRT\n"+
                "2 DATE 17 MAR 2010\n"+ // I01: youngest child
				"1 FAMC F01\n"+
				"0 I02 INDI\n"+ // I02: middle child
                "1 BIRT\n"+
                "2 DATE 14 FEB 2008\n"+
                "1 FAMC F01\n"+
                "0 I03 INDI\n"+ // I03: oldest child
                "1 BIRT\n"+
                "2 DATE 26 JUL 2005\n"+
                "1 FAMC F01\n"+
                "0 I04 INDI\n"+
                "1 BIRT\n"+
                "2 DATE 19 JAN 1990\n"+
                "1 FAMC F03\n"+
                "0 I05 INDI\n"+
                "1 BIRT\n"+
                "2 DATE 1 DEC 2000\n"+
                "1 FAMC F04\n"+
                "0 I06 INDI\n"+
                "1 BIRT\n"+
                "2 DATE 29 AUG 2002\n"+
                "1 FAMC F04\n"+
                "0 F01 FAM\n"+   // family with reverse sorted children
                "1 CHIL I01\n"+
                "1 CHIL I02\n"+
                "1 CHIL I03\n"+
                "0 F02 FAM\n"+ // family with no children to make sure empty child list does not break the program
                "0 F03 FAM\n"+ // family with single child to test that method can handle size 1 arraylist
                "1 CHIL I04\n"+
                "0 F04 FAM\n"+ // family with already sorted children
                "1 CHIL I05\n"+
                "1 CHIL I06\n"
                ); 
			fw.close();
			GEDCOM_Parser parser = new GEDCOM_Parser();
			parser.parse(testGed,"US28test.txt");
			testFile.delete();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
