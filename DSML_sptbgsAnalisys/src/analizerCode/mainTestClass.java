package analizerCode;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Element;

import com.opencsv.CSVWriter;

public class mainTestClass {

	public static void main(String[] args) throws IOException {
		Final_Parser parser = new Final_Parser("src/sptbgs.xml");
		ArrayList<Element> bugList = parser.findBugInstance();	
		for (Element e: bugList) {
			String className = parser.getBugClass(e).getAttributeValue("classname");
			String methodName = parser.getBugMethod(e).getAttributeValue("name");
			parser.printBugMethod(className, methodName);
		}

	}
	
}
