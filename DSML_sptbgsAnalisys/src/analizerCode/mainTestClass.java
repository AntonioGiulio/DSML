package analizerCode;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import org.jdom2.Element;

import com.opencsv.CSVWriter;

public class mainTestClass {

	public static void main(String[] args) throws IOException {
		
		
		
		Final_Parser parser = new Final_Parser("gaopu.xml");
		ArrayList<Element> bugList = parser.findBugInstance();
		File file = new File("mycsv.csv");
		FileWriter outputFile = new FileWriter(file);
		CSVWriter writer = new CSVWriter(outputFile);
		int count = 0;		
		
		
		for (Element e: bugList) {
			String className = parser.getBugClass(e).getChild("SourceLine").getAttributeValue("sourcepath");
			String methodName = parser.getBugMethod(e).getAttributeValue("name");
			String vulnerability = e.getAttributeValue("type");
			String sourceFile=parser.getBugClass(e).getChild("SourceLine").getAttributeValue("sourcefile");
			System.out.println(parser.printBugMethod(className, methodName));
			System.out.println(className);
			System.out.println(methodName);
			System.out.println(vulnerability);
			parser.csvWriter(writer, vulnerability, parser.printBugMethod(className, methodName));
		}
		
		
		writer.close();
	}	
	
		
		
}

