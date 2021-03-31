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
				"0 NOTE Group 7 Test File for Sprint 2: US28\n"+
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
            //Test US02
            testFile.createNewFile();
			fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
                    + "0 I01 INDI\n"
                    + "1 FAMS F99\n"
                    + "1 FAMC F01\n"
                    + "1 BIRT\n"
                    + "2 DATE 12 MAR 1976\n"
                    + "0 I02 INDI\n"
                    + "1 FAMS F01\n"
                    + "1 FAMC F03\n"
                    + "1 BIRT\n"
                    + "2 DATE 15 FEB 1990\n"
                    + "0 I03 INDI\n"
                    + "1 FAMS F01\n"
                    + "1 BIRT\n"
                    + "2 DATE 28 JUL 2020\n"
                    + "0 F01 FAM\n"
                    + "1 HUSB I03\n"
                    + "1 WIFE I02\n"
                    + "1 CHIL I01\n"
                    + "1 MARR\n"
                    + "2 DATE 1 AUG 2016\n" // error because marriage is before birth
                    + "0 F02 FAM\n"
                    + "1 HUSB I01\n"
                    + "1 WIFE I01\n"
                    + "1 CHIL I02\n"
                    + "1 MARR\n"
                    + "2 DATE 30 DEC 2020\n");
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed,"US02Test.txt");
            testFile.delete();//end test US02
            
             //Test US04
             testFile.createNewFile();
             fw = new FileWriter(testFile);
             fw.write ("0 HEAD\n" +
			"1 SOUR Family Echo\n" +
			"2 WWW http://www.familyecho.com/\n" +
			"1 FILE Family 2\n" +
			"1 DATE 30 MAR 2021\n" +
			"1 DEST ANSTFILE\n" +
			"1 GEDC\n" +
			"2 VERS 5.5.1" +
			"2 FORM LINEAGE-LINKED" +
			"1 SUBM @I1@\n" +
			"2 NAME Bob Smith\n" +
			"1 SUBN\n"+
			"1 CHAR UTF-8\n"+
			"0 @I1@ INDI\n"+
			"1 NAME Bob\n"+
			"2 GIVN Bob\n"+
			"1 SEX M\n"+
			"1 BIRT\n"+
			"2 DATE 4 MAY 1990\n"+
			"1 FAMS @F1@\n"+
			"0 @I2@ INDI\n"+
			"1 NAME Mary\n"+
			"2 GIVN Mary\n"+
			"1 SEX F\n"+
			"1 BIRT\n"+
			"2 DATE 9 AUG 1995\n"+
			"1 FAMS @F1@\n"+
			"0 @F1@ FAM\n"+
			"1 HUSB @I1@\n"+
			"1 WIFE @I2@\n"+
			"1 MARR\n"+
			"2 DATE 7 MAY 2018\n"+
			"1 DIV\n"+
			"2 DATE 5 APR 2005\n"+ //error because divorce is before marriage
			"1 _CURRENT Y\n"+
			"0 TRLR\n");
             fw.close();
             parser = new GEDCOM_Parser();
             parser.parse(testGed,"US04Test.txt");
             testFile.delete();//end test US04

        }catch(IOException e){
            e.printStackTrace();
        }

        //TEST FOR US 05 and US 06
        try{
            String testGed = "testGed.ged";
			File testFile = new File(testGed);
			testFile.createNewFile();
			
			FileWriter fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"+
				"0 NOTE Group 7 Test File for Sprint 2: US05 & US06\n"+
				"0 I01 INDI\n"+            // i1 and i2 died before married.
                    "1 FAMS F01\n"+
                    "1 BIRT\n"+
                    	"2 DATE 15 JUN 1918\n"+
                    "1 DEAT\n"+
                        "2 DATE 11 APR 1971\n"+
                "0 I02 INDI\n"+
                    "1 FAMS F01\n"+
                    "1 BIRT\n"+
                    	"2 DATE 20 NOV 1950\n"+
                    "1 DEAT\n"+
                        "2 DATE 19 MAR 1964\n"+
                "0 I03 INDI\n"+         // i3 and i4 no death dates
                    "1 FAMS F02\n"+
                    "1 BIRT\n"+
                    	"2 DATE 4 JAN 1980\n"+
                "0 I04 INDI\n"+
                    "1 FAMS F02\n"+
                    "1 BIRT\n"+
                    	"2 DATE 8 FEB 1979\n"+
                "0 I05 INDI\n"+         //i5 and i6 die after
                    "1 FAMS F03\n"+
                    "1 DEAT\n"+
                        "2 DATE 25 DEC 2020\n"+
                    "1 BIRT\n"+
                        "2 DATE 10 DEC 1940\n"+
                "0 I06 INDI\n"+
                    "1 FAMS F03\n"+
                    "1 DEAT\n"+
                        "2 DATE 28 JAN 2019\n"+
                    "1 BIRT\n"+
                        "2 DATE 9 AUG 1950\n"+
                "0 F01 FAM\n"+
                    "1 HUSB I01\n"+
                    "1 WIFE I02\n"+
                    "1 MARR\n"+
                    "2 DATE 11 DEC 2006\n"+      //married after death
                    "1 DIV\n"+
                    "2 DATE 30 DEC 2007\n"+      //divorced after death
                "0 F02 FAM\n"+
                    "1 HUSB I03\n"+
                    "1 WIFE I04\n"+
                    "1 MARR\n"+
                    "2 DATE 11 DEC 2016\n"+
                    "1 DIV\n"+
                    "2 DATE 30 DEC 2017\n"+
                "0 F03 FAM\n"+
                    "1 HUSB I05\n"+
                    "1 WIFE I06\n"+
                    "1 MARR\n"+
                    "2 DATE 11 DEC 1988\n"+      //married before death
                    "1 DIV\n"+
                    "2 DATE 30 DEC 1989\n"     //divorced before death
                ); 
			fw.close();
			GEDCOM_Parser parser = new GEDCOM_Parser();
			parser.parse(testGed,"US05-06test.txt");
			testFile.delete();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
