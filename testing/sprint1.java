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
			parser.parse("testGed.ged");
			testFile.delete();
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}

}