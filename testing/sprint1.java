package testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import parser.GEDCOM_Parser;

public class sprint1 {

	public static void main(String[] args) {
		
		try {
			String testGed = "testGed.ged";
			File testFile = new File(testGed);
			testFile.createNewFile();
			
			FileWriter fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"+
				"0 NOTE Group 7 Test File for Sprint 1\n"+
				"0 I01 INDI\n"+
				"1 BIRT\n"+
				"2 DATE 29 FEB 2001\n"+ // invalid because not leap year
				"1 BIRT\n"+
				"2 DATE -1 JAN 2020\n"+ // invalid because negative day
				"1 BIRT\n"+
				"2 DATE 10 MXS 2011\n"+ // invalid because invalid month
				"1 BIRT\n"+
				"2 DATE 20 OCT 2026\n"+ // invalid because after today's date
				"1 BIRT\n"+
				"2 DATE 20 OCT asdf\n" + //invalid because invalid year
				"1 BIRT\n"+
				"2 DATE 16 MAR 2021\n"+ // good date
				"0 I01 INDI\n"); // attempt to add already existing id
			fw.close();
			GEDCOM_Parser parser = new GEDCOM_Parser();
			parser.parse(testGed,"US01Test.txt");
			testFile.delete();
			// test birth before death
			testFile.createNewFile();
			
			fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"+
				"0 NOTE Group 7 Test File for Sprint 1\n"+
				"0 @I9@ INDI\n"+
				"1 NAME Steve /Jobs/\n"+
				"2 GIVN Steve\n"+ 
				"2 SURN Jobs\n"+
				"2 _MARNM Jobs\n"+ 
				"1 SEX M\n"+
				"1 BIRT\n"+ 
				"2 DATE 9 JUL 2000\n"+ 
				"1 DEAT Y\n"+ 
				"2 DATE 9 AUG 1933\n"); // invalid because birth is not before death
			
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed,"saSprint1Test.txt");
			testFile.delete();


			// test US10
			testFile.createNewFile();
            fw = new FileWriter(testFile);
            fw.write("0 HEAD\n"
                    + "0 I01 INDI\n"
                    + "1 BIRT\n"
                    + "2 DATE 13 JAN 2020\n"
                    + "1 FAMS F01\n"
                    + "0 F01 FAM\n"
                    + "1 MARR\n"
                    + "2 DATE 10 JAN 2021\n"
                    + "1 HUSB I01\n"); // will have an error for marrying too young
			fw.close();
            parser = new GEDCOM_Parser();
            parser.parse(testGed, "US10Test.txt");
            testFile.delete();//end test US10

			// test US26
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
                    + "2 DATE 28 JUL 1986\n"
                    + "0 F01 FAM\n"
                    + "1 HUSB I03\n"
                    + "1 WIFE I02\n"
                    + "1 CHIL I01\n"
                    + "1 MARR\n"
                    + "2 DATE 1 AUG 2016\n"
                    + "0 F02 FAM\n"
                    + "1 HUSB I01\n"
                    + "1 WIFE I01\n"
                    + "1 CHIL I02\n"
                    + "1 MARR\n"
                    + "2 DATE 30 DEC 2020\n");
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed,"US26Test.txt");
			testFile.delete();//end test US26








		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}

}
