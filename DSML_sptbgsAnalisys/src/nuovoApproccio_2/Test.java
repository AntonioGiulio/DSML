package nuovoApproccio_2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class Test {

	public static void main(String[] args) throws IOException {
		FinalParserMarfia parser = new FinalParserMarfia("elasticSearch.xml");
		final File folder = new File("C:\\Users\\Antonio\\eclipse-workspace\\elasticsearch-master.zip_expanded");
		File file = new File("mycsv.csv");
		FileWriter outputFile = new FileWriter(file);
		CSVWriter writer = new CSVWriter(outputFile);
		parser.pupulateList(parser.findBugInstance());
		parser.listFiles(folder, writer);
		writer.close();
	}

}
