package analizerCode;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Element;

import com.opencsv.CSVWriter;

public class mainTestClass {

	public static void main(String[] args) throws IOException {
		Final_Parser parser = new Final_Parser("src/sptbgs.xml");
		ArrayList<Element> bugList = parser.findBugInstance();
		File file = new File("myCsv.csv");
		FileWriter outputFile = new FileWriter(file);
		CSVWriter writer = new CSVWriter(outputFile);
		
		
		for (Element e: bugList) {
			String className = parser.getBugClass(e).getChild("SourceLine").getAttributeValue("sourcepath");
			String methodName = parser.getBugMethod(e).getAttributeValue("name");
			String vulnerability = e.getAttributeValue("type");
			System.out.println(vulnerability);
			System.out.println(className);
			System.out.println(parser.printBugMethod(className, methodName));
			parser.csvWriter(writer, vulnerability, parser.printBugMethod(className, methodName));
		}
		writer.close();

	}
	
}
