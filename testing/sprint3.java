package testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import parser.GEDCOM_Parser;

public class sprint3 {
	public static void main(String[] args){
		try {
			String testGed = "testGed.ged";
			File testFile = new File(testGed);

			testFile.createNewFile();
			FileWriter fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
					+"0 NOTE Test GEDCOM File for Sprint 3: US31 (List Living Singles)\n"
					+"0 I01 INDI\n" // living person never married over 30 (SHOULD APPEAR)
					+"1 BIRT\n"
					+"2 DATE 10 FEB 1950\n"
					+"0 I02 INDI\n" // living person never married under 30 (SHOULD NOT APPEAR)
					+"1 BIRT\n"
					+"2 DATE 18 MAR 2000\n"
					+"0 I03 INDI\n" // living person married over 30 (SHOULD NOT APPEAR)
					+"1 BIRT\n"
					+"2 DATE 12 AUG 1962\n"
					+"1 FAMS F01\n"
					+"0 I04 INDI\n" // living person married under 30 (SHOULD NOT APPEAR)
					+"1 BIRT\n"
					+"2 DATE 1 JAN 1998\n"
					+"1 FAMS F01\n"
					+"0 I05 INDI\n" // deceased person never married over 30 (SHOULD NOT APPEAR)
					+"1 BIRT\n"
					+"2 DATE 9 DEC 1960\n"
					+"1 DEAT\n"
					+"2 DATE 11 AUG 2000\n"
					+"0 I06 INDI\n" // deceased person never married under 30 (SHOULD NOT APPEAR)
					+"1 BIRT\n"
					+"2 DATE 9 DEC 1980\n"
					+"1 DEAT\n"
					+"2 DATE 11 FEB 2000\n"
					+"0 I07 INDI\n" // deceased person married over 30 (SHOULD NOT APPEAR)
					+"1 BIRT\n"
					+"2 DATE 20 NOV 1960\n"
					+"1 DEAT\n"
					+"2 DATE 12 JAN 2000\n"
					+"1 FAMS F02\n"
					+"0 I08 INDI\n" // deceased person married under 30 (SHOULD NOT APPEAR)
					+"1 BIRT\n"
					+"2 DATE 20 AUG 1974\n"
					+"1 DEAT\n"
					+"2 DATE 12 MAR 2000\n"
					+"1 FAMS F02\n"
					+"0 TAIL\n");
			fw.close();
			GEDCOM_Parser parser = new GEDCOM_Parser();
			parser.parse(testGed, "US31.txt");
			testFile.delete();
			
			testFile.createNewFile();
			fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
					+"0 NOTE Test GEDCOM File for Sprint 3: US21 (Correct gender roles)\n"
					+"0 I01 INDI\n"
					+"1 BIRT\n"
					+"2 DATE 18 MAR 2000\n"
					+"1 SEX male\n"
					+"1 FAMS F01\n"
					+"0 I02 INDI\n"
					+"1 BIRT\n"
					+"2 DATE 20 AUG 1989\n"
					+"1 SEX female\n"
					+"1 FAMS F01\n"
					+"0 I03 INDI\n"
					+"1 BIRT\n"
					+"2 DATE 1 JAN 2001\n"
					+"1 SEX female\n"
					+"1 FAMS F02\n"
					+"0 I04 INDI\n"
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1995\n"
					+"1 SEX male\n"
					+"1 FAMS F02\n"
					+"0 F01 FAM\n"
					+"1 HUSB I01\n"
					+"1 WIFE I02\n"
					+"0 F02 FAM\n"
					+"1 HUSB I03\n"
					+"1 WIFE I04\n"
					+"0 TAIL\n"
					);
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed, "US21.txt");
			testFile.delete();
			
			//US15 
			testFile.createNewFile();
			fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
					+"0 NOTE Test GEDCOM File for Sprint 3: US15 (Less than 15 siblings)\n"
					
					+"0 I01 INDI\n"		//husb for F01 (error fam)
					+"1 BIRT\n"
					+"2 DATE 18 MAR 1970\n"
					+"1 SEX male\n"
					+"1 FAMS F01\n"		
					
					+"0 I02 INDI\n"		//wife for F01 (error fam)
					+"1 BIRT\n"
					+"2 DATE 20 AUG 1969\n"
					+"1 SEX female\n"
					+"1 FAMS F01\n"
					
					+"0 I03 INDI\n"		//husb for F02 (correct fam)
					+"1 BIRT\n"
					+"2 DATE 1 JAN 1980\n"
					+"1 SEX male\n"
					+"1 FAMS F02\n"
					
					+"0 I04 INDI\n"		//wife for F02 (correct fam)
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1981\n"
					+"1 SEX female\n"
					+"1 FAMS F02\n"
					
					+"0 I05 INDI\n"		//children for F01 (error fam)
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1995\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I06 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1996\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I07 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1997\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I08 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1998\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I09 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1999\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I10 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2000\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I11 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2001\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I12 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2002\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I13 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2003\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I14 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2004\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I15 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2005\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I16 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2006\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I17 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2007\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I18 INDI\n"		
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2008\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					+"0 I19 INDI\n"		//15th child for F01 (error fam)
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2009\n"
					+"1 SEX female\n"
					+"1 FAMC F01\n"
					
					+"0 I20 INDI\n"		//child for F02 (correct fam)
					+"1 BIRT\n"
					+"2 DATE 30 NOV 2009\n"
					+"1 SEX female\n"
					+"1 FAMC F02\n"
					
					
					+"0 F01 FAM\n"		//F01 (error fam)
					+"1 HUSB I01\n"
					+"1 WIFE I02\n"
					+"1 CHIL I05\n"
					+"1 CHIL I06\n"
					+"1 CHIL I07\n"
					+"1 CHIL I08\n"
					+"1 CHIL I09\n"
					+"1 CHIL I10\n"
					+"1 CHIL I11\n"
					+"1 CHIL I12\n"
					+"1 CHIL I13\n"
					+"1 CHIL I14\n"
					+"1 CHIL I15\n"
					+"1 CHIL I16\n"
					+"1 CHIL I17\n"
					+"1 CHIL I18\n"
					+"1 CHIL I19\n"
					
					+"0 F02 FAM\n"		//F02 (correct fam)
					+"1 HUSB I03\n"
					+"1 WIFE I04\n"
					+"1 CHIL I20\n"
					
					+"0 TAIL\n"
					);
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed, "US15.txt");
			testFile.delete();
			
			
			//US16
			testFile.createNewFile();
			fw = new FileWriter(testFile);
			fw.write("0 HEAD\n"
					+"0 NOTE Test GEDCOM File for Sprint 3: US16 (Matching male last names)\n"
					+"0 I01 INDI\n"		//husb for F01 (error fam)
					+"1 NAME James Woods\n"
					+"1 BIRT\n"
					+"2 DATE 18 MAR 1970\n"
					+"1 SEX male\n"
					+"1 FAMS F01\n"		
					
					+"0 I02 INDI\n"		//wife for F01 (error fam)
					+"1 BIRT\n"
					+"2 DATE 20 AUG 1969\n"
					+"1 SEX female\n"
					+"1 FAMS F01\n"
					
					+"0 I03 INDI\n"		//husb for F02 (correct fam)
					+"1 NAME Matt Bayne\n"
					+"1 BIRT\n"
					+"2 DATE 1 JAN 1980\n"
					+"1 SEX male\n"
					+"1 FAMS F02\n"
					
					+"0 I04 INDI\n"		//wife for F02 (correct fam)
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1981\n"
					+"1 SEX female\n"
					+"1 FAMS F02\n"
					
					+"0 I05 INDI\n"		//children for F01 (error fam)
					+"1 NAME Matt Woods\n"
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1995\n"
					+"1 SEX male\n"
					+"1 FAMC F01\n"
					+"0 I06 INDI\n"
					+"1 NAME Joe Smith\n"		//wrong last name
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1996\n"
					+"1 SEX male\n"
					+"1 FAMC F01\n"
					
					+"0 I07 INDI\n"		//child for F02 (correct fam)
					+"1 NAME Tony Bayne\n"
					+"1 BIRT\n"
					+"2 DATE 30 NOV 1997\n"
					+"1 SEX male\n"
					+"1 FAMC F02\n"
					
					+"0 F01 FAM\n"		//F01 (error fam)
					+"1 HUSB I01\n"
					+"1 WIFE I02\n"
					+"1 CHIL I05\n"
					+"1 CHIL I06\n"
					
					+"0 F02 FAM\n"		//F02 (correct fam)
					+"1 HUSB I03\n"
					+"1 WIFE I04\n"
					+"1 CHIL I07\n"
					
					+"0 TAIL\n"
					
					);
			fw.close();
			parser = new GEDCOM_Parser();
			parser.parse(testGed, "US16.txt");
			testFile.delete();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
