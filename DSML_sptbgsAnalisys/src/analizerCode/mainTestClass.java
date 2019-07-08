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
		
		
		
		Final_Parser parser = new Final_Parser("C:\\Users\\andre\\git\\DSML\\DSML_sptbgsAnalisys\\RxJava_Spotbugs.xml");
		ArrayList<Element> bugList = parser.findBugInstance();
		File file = new File("myCsvRx2.csv");
		FileWriter outputFile = new FileWriter(file);
		CSVWriter writer = new CSVWriter(outputFile);
		int count = 0;
		
		parser.copyFiles("C:\\Users\\andre\\eclipse-workspace\\RxJava-3.x.zip_expanded");
		
		
		
		
		for (Element e: bugList) {
			count++;
			//System.out.println(e.toString());
		}
		System.out.println(count);
		
		
		for (Element e: bugList) {
			String className = parser.getBugClass(e).getChild("SourceLine").getAttributeValue("sourcepath");
			String methodName = parser.getBugMethod(e).getAttributeValue("name");
			String vulnerability = e.getAttributeValue("type");
			String sourceFile=parser.getBugClass(e).getChild("SourceLine").getAttributeValue("sourcefile");
			if(!vulnerability.equals("EI_EXPOSE_REP2")) {
			System.out.println("vulnerabilità:\t"+vulnerability);
			System.out.println("className:\t"+className);
			System.out.println("methodName:\t"+methodName);
			System.out.println("sourceFile:\t"+sourceFile);
			System.out.println(parser.printBugMethod(className, methodName,sourceFile));
			parser.csvWriter(writer, vulnerability, parser.printBugMethod(className, methodName, sourceFile));
			}
		}	
		writer.close();
		
}
}
