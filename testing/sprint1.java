package testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import parser.GEDCOM_Parser;

public class sprint1 {

	public static void main(String[] args) {
		
		try {
			File testFile = new File("testGed.ged");
			testFile.createNewFile();
			
			FileWriter fw1 = new FileWriter(testFile);
			fw1.write("0 HEAD\n"+
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
			fw1.close();
			GEDCOM_Parser parser1 = new GEDCOM_Parser();
			parser1.parse("testGed.ged");
			testFile.delete();
			// test birth before death
			File testFile2 = new File("testbirthbeforedeath.ged");
			testFile.createNewFile();
			
			FileWriter fw2 = new FileWriter(testFile);
			fw2.write("0 HEAD\n"+
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
			fw2.close();
			GEDCOM_Parser parser2 = new GEDCOM_Parser();
			parser2.parse("testbirthbeforedeath.ged");
			testFile2.delete();

		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}

}
