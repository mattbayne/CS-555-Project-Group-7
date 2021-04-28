package testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import parser.GEDCOM_Parser;

public class sprint4 {

	public static void main(String[] args) {
		
		try {
			String testGed = "testGed.ged";
			File testFile = new File(testGed);
			// Test for US 25
			testFile.createNewFile();
			FileWriter fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
					+"0 NOTE Test GEDCOM File for Sprint 4: US25 (Unique Names in Family)\n"
					+"0 I01 INDI\n"
					+"1 NAME John /Smith/\n"
					+"1 FAMC F01\n"
					+"0 I02 INDI\n"
					+"1 NAME John /Jacobs/\n"
					+"1 FAMC F01\n"
					+"0 I03 INDI\n"
					+"1 NAME John /Smith/\n"
					+"1 FAMC F02\n"
					+"0 I04 INDI\n"
					+"1 NAME Allen /Smith/\n"
					+"1 FAMC F01\n"
					+"0 F01 FAM\n"
					+"1 CHIL I01\n"
					+"1 CHIL I02\n"
					+"1 CHIL I04\n"
					+"0 F02 FAM\n"
					+"1 CHIL I03\n"
					+"0 TAIL\n");
			fw.close();
			GEDCOM_Parser parser = new GEDCOM_Parser();
			parser.parse(testGed, "US25.txt");
			testFile.delete();

			// Test for US 33
			testFile.createNewFile();
			fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
					+ "0 NOTE Test GEDCOM File for Sprint 4: US33 (List Orphans)\n"
					+ "0 I01 INDI\n"
					+ "1 BIRT\n"
					+ "2 DATE 2 MAR 2018\n"
					+ "1 FAMC F01\n"
					+ "0 I02 INDI\n"
					+ "1 FAMS F01\n"
					+ "1 DEAT\n"
					+ "2 DATE 8 JAN 2020\n"
					+ "0 I03 INDI\n"
					+ "1 FAMS F01\n"
					+ "1 DEAT\n"
					+ "2 DATE 8 JAN 2020\n"
					+ "0 I04 INDI\n"
					+ "1 BIRT\n"
					+ "2 DATE 14 JUN 2015\n"
					+ "1 FAMC F02\n"
					+ "0 I05 INDI\n"
					+ "1 DEAT\n"
					+ "2 DATE 5 AUG 2020\n"
					+ "1 FAMS F02\n"
					+ "0 I06 INDI\n"
					+ "1 FAMS F02\n"
					+ "0 F01 FAM\n"
					+ "1 HUSB I02\n"
					+ "1 WIFE I03\n"
					+ "1 CHIL I01\n"
					+ "0 F02 FAM\n"
					+ "1 HUSB I05\n"
					+ "1 WIFE I06\n"
					+ "1 CHIL I04\n"
					+ "0 TAIL\n");
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed, "US33.txt");
			testFile.delete();

		//Test for US 38
			testFile.createNewFile();
			fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
					+ "0 NOTE Test GEDCOM File for Sprint 4: US38 (List Upcoming Birthdays)\n"
					+ "0 I01 INDI\n"
					+ "1 BIRT\n"
					+ "2 DATE 2 MAR 2018\n"
					+ "0 I02 INDI\n"
					+ "1 BIRT\n"
					+ "2 DATE 2 MAR 2018\n"
					+ "1 DEAT\n"
					+ "2 DATE 8 JAN 2020\n"
					+ "0 I03 INDI\n"
					+ "1 BIRT\n"
					+ "2 DATE 2 MAR 1994\n"
					+ "1 DEAT\n"
					+ "2 DATE 8 NOV 2020\n"
					+ "0 I04 INDI\n"
					+ "1 BIRT\n"
					+ "2 DATE 22 FEB 2001\n"
					+ "0 I04 INDI\n"
					+ "1 BIRT\n"
					+ "2 DATE 6 AUG 1977\n"
					+ "0 TAIL\n");
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed, "US38.txt");
			testFile.delete();
			
			// Test for US 39
			testFile.createNewFile();
			fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
					+ "0 NOTE Test GEDCOM File for Sprint 4: US39 (List Upcoming Anniversaries)\n"
					+ "0 I01 INDI\n"
					+ "1 BIRT\n"
					+ "2 DATE 2 MAR 2018\n"
					+ "1 FAMS F01\n"
					+ "0 I02 INDI\n"
					+ "1 FAMS F01\n"
					+ "1 DEAT\n"
					+ "2 DATE 8 JAN 2020\n"
					+ "0 I03 INDI\n"
					+ "1 FAMS F02\n"
					+ "0 I04 INDI\n"
					+ "1 FAMS F02\n"
					+ "0 F01 FAM\n"
					+ "1 HUSB I02\n"
					+ "1 WIFE I01\n"
					+ "1 MARR\n"
					+ "2 DATE 4 NOV 2018"
					+ "0 F02 FAM\n"
					+ "1 HUSB I03\n"
					+ "1 WIFE I04\n"
					+ "1 MARR\n"
					+ "2 DATE 18 APR 2016"
					+ "0 TAIL\n");
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed, "US39.txt");
			testFile.delete();
			
			testFile.createNewFile();
			fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
					+ "0 NOTE Test GEDCOM File for Sprint 4: US34 (List Large Age Differences)\n"
					+ "0 I01 INDI\n"  	//husband for F01 (error)
					+ "1 BIRT\n"
					+ "2 DATE 2 MAR 1950\n" //50yrs old at marriage
					+ "1 FAMS F01\n"
					+ "0 I02 INDI\n"	//wife for F01 (error)
					+ "1 BIRT\n"
					+ "2 DATE 2 MAR 1980\n" //20yrs old at marriage
					+ "1 FAMS F01\n"
					+ "0 I03 INDI\n"	//husband for F02 (correct)
					+ "1 BIRT\n"
					+ "2 DATE 2 MAR 1978\n"
					+ "1 FAMS F02\n"
					+ "0 I04 INDI\n"	//wife for F02 (correct)
					+ "1 BIRT\n"
					+ "2 DATE 2 DEC 1979\n"
					+ "1 FAMS F02\n"
					+ "0 F01 FAM\n"		//fam F01 (error)
					+ "1 HUSB I01\n"
					+ "1 WIFE I02\n"
					+ "1 MARR\n"
					+ "2 DATE 10 JAN 2000\n"
					+ "0 F02 FAM\n"		//fam F02 (correct)
					+ "1 HUSB I03\n"
					+ "1 WIFE I04\n"
					+ "1 MARR\n"
					+ "2 DATE 10 JAN 2000\n"
					+ "0 TAIL\n");
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed, "US34.txt");
			testFile.delete();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
