package analizerCode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.opencsv.CSVWriter;

public class Final_Parser {
	
	private SAXBuilder builder;
	private String fileName;
	private Document doc;
	private Element root;
	private Iterable<Element> children;
	private Iterator itr;
	
	/*costruttore*/
	public Final_Parser(String file) {
		builder = new SAXBuilder();	//inizializzo il builder
		fileName = file;
		try {
			doc = builder.build(fileName);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		root = doc.getRootElement();	//ottengo la root del doc xml
		children = root.getChildren(); //carico in un iteratore tutti i nodi
									   //del doc xml
		itr = children.iterator();
	}
	
	public ArrayList<Element> findBugInstance() {
		ArrayList<Element> list = new ArrayList<Element>();
		while (itr.hasNext()) {
			Element e = (Element) itr.next();
			if (e.getName() == "BugInstance") {
				if(e.getChild("Method") != null)
					list.add(e);
			}			
		}
		return list;
	}
	
	public Element getBugClass(Element e) {
		return e.getChild("Class");
	}
	
	public Element getBugMethod(Element e) {
		return e.getChild("Method");
	}	
	
	public Element getBugReturnField(Element e) {
		return e.getChild("Field");
	}
	
	public Element getBugSourceLine(Element e) {
		return e.getChild("SourceLine");
	}
	
	public String printBugMethod(String classPath, String methodName) throws IOException {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\Antonio\\eclipse-workspace\\gaopu.zip_expanded\\Java-master\\"+classPath));
		}catch(FileNotFoundException e) {
			try {
				reader = new BufferedReader(new FileReader("C:\\Users\\Antonio\\eclipse-workspace\\gaopu.zip_expanded\\Java-master\\CrawlDouyuDanmu\\src\\main\\java\\"+classPath));
			}catch (FileNotFoundException e2){
				try {
					reader = new BufferedReader(new FileReader("C:\\Users\\Antonio\\eclipse-workspace\\gaopu.zip_expanded\\Java-master\\CrawlPandaDanmu\\src\\main\\java\\"+classPath));
				}catch (FileNotFoundException e3){
					try {
						reader = new BufferedReader(new FileReader("C:\\Users\\Antonio\\eclipse-workspace\\gaopu.zip_expanded\\Java-master\\InsulinPump\\"+classPath));
					}catch (FileNotFoundException e4){
						try {
							reader = new BufferedReader(new FileReader("C:\\Users\\Antonio\\eclipse-workspace\\gaopu.zip_expanded\\Java-master\\medical_question\\"+classPath));
						}catch (FileNotFoundException e5){
							try {
								reader = new BufferedReader(new FileReader("C:\\Users\\Antonio\\eclipse-workspace\\gaopu.zip_expanded\\Java-master\\MicroWave\\"+classPath));
							}catch (FileNotFoundException e6){
								try {
									reader = new BufferedReader(new FileReader("C:\\Users\\Antonio\\eclipse-workspace\\gaopu.zip_expanded\\Java-master\\PullBookinfo\\"+classPath));
								}catch (FileNotFoundException e7){
									try {
										reader = new BufferedReader(new FileReader("C:\\Users\\Antonio\\eclipse-workspace\\gaopu.zip_expanded\\Java-master\\Struts2FileUpAndDown\\src\\"+classPath));
									}catch (FileNotFoundException e8){
										reader = new BufferedReader(new FileReader("C:\\Users\\Antonio\\eclipse-workspace\\gaopu.zip_expanded\\Java-master\\西邮导游系统源码\\源码\\"+classPath));
									}
								}
							}
						}
					}
				}
			}
		}		
		
		String retString = "";
		String line = reader.readLine();
		int count = 0;
		try {
			/*
			while (!line.matches("(public|protected|private).+" + methodName + ".+")){
				line = reader.readLine();
			}*/
			while (!(line.contains(methodName) && line.contains("{") && line.contains("(") && line.contains(")") && !line.contains("*") && line.contains("public"))) {
				line = reader.readLine();
			}
			System.out.println(line);
			retString = line +  "\n";
			count++;
			
			while (count != 0) {
				line = reader.readLine();
				if(line.contains("{")) {
					if(!line.contains("}")) {
						count++;
					}
				}else if(line.contains("}")){
					count--;
				}
				
				retString += line + "\n";
			}
		}catch(NullPointerException e) {
			retString += null;
		}
		System.out.println(line);
		retString += line;
		reader.close();
		System.out.println("ho finito");
		return retString;
	}
	
	public void csvWriter(CSVWriter writer, String vul, String code) throws IOException {
		String[] data = {code, vul};
		writer.writeNext(data);
	} 	
	
}
