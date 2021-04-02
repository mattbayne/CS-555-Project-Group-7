package parser;

import java.util.HashMap;

import entities.Individual;

public class TableBuilder {
	public String buildIndiTable(HashMap<String,Individual> individuals) {
		String horizontalLine = "+";
		for (int i = 0; i < 128; i++) {
			horizontalLine += "-";
		}
		horizontalLine += "+\n";
		String table = horizontalLine; 
		// column labels
		table += String.format("|%-5s|%-30s|%-6s|%-13s|%-4s|%-13s|%-25s|%-25s|\n", "  Id","            Name",
				"Gender","  Birthday",
				"Age","    Death","    Children Families","    Spouse Families");
		table += horizontalLine;
		for(Individual indi : individuals.values()) {
			String[] values = indi.getAsString();
			table += String.format("|%-5s|%-30s|%-6s|%-13s|%-4s|%-13s|%-25s|%-25s|\n", values[0],values[1],
					values[2],values[3],values[4],values[5],values[6],values[7]);
		}
		table += horizontalLine;
		
		return table;
	}
}
