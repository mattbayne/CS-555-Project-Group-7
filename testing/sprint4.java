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

			testFile.createNewFile();
			FileWriter fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
					+"0 NOTE Test GEDCOM File for Sprint 4: US25 (Unique Names in Family)\n"
					+"0 I01 INDI\n"
					+"1 NAME John /Smith/\n"
					+"1 FAMC F01\n"
					+"0 I02 INDI\n"
					+"1 NAME John /Smith/\n"
					+"1 FAMC F01\n"
					+"0 F01 FAM\n"
					+"1 CHIL I01\n"
					+"1 CHIL I02\n"
					+"0 TAIL\n");
			fw.close();
			GEDCOM_Parser parser = new GEDCOM_Parser();
			parser.parse(testGed, "US25.txt");
			testFile.delete();
			
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
					+ "0 F01 FAM\n"
					+ "1 HUSB I02\n"
					+ "1 WIFE I03\n"
					+ "1 CHIL I01\n"
					+ "0 TAIL\n");
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed, "US33.txt");
			testFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
