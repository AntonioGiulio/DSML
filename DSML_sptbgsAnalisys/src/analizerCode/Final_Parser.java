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
	
	public Element getBugSourceLine(Element e) {
		return e.getChild("SourceLine");
	}
	
	public String printBugMethod(String className, String methodName, String sourceFile) throws IOException {
		BufferedReader reader;
		reader = new BufferedReader(new FileReader("C:\\Users\\andre\\git\\DSML\\DSML_sptbgsAnalisys\\src\\prova\\" + sourceFile));
		
		
		String retString;
		String line = reader.readLine();
		int count = 0;
		while (!(line.contains(methodName) && line.contains("{") && line.contains("(") && line.contains(")"))) {
			line = reader.readLine();
		} 
		System.out.println(line);
		retString = line +  "\n";
		if(line.contains("{")) {
			count++;
		}
		
		while (count != 0) {
			line = reader.readLine();
			if(line.contains("{")) {
				count++;
			}else if(line.contains("}")) {
				count--;
			}
			
			retString += line + "\n";
		}
		retString += line;
		reader.close();
		return retString;
	}
	
	public void csvWriter(CSVWriter writer, String vul, String code) throws IOException {
		String[] data = {code, vul};
		writer.writeNext(data);
	}
	
	public void copyFiles(String folder) throws IOException {
	    File folderFile=new File(folder);
	    if (folderFile.isDirectory()) {
	    File[] files=folderFile.listFiles();
	    for (File file2 : files) {
			if (file2.isDirectory()) {
				copyFiles(file2.getAbsolutePath());
			}
			else {
				if (file2.getAbsolutePath().contains(".java")) {
					InputStream inStream = null;
			        OutputStream outStream = null;

			        File inputFile=new File(file2.getAbsolutePath());
			        File outputFile =new File("C:\\Users\\andre\\OneDrive\\Desktop\\prova\\"+file2.getName());

			        inStream = new FileInputStream(inputFile);
			        outStream = new FileOutputStream(outputFile);

			        byte[] buffer = new byte[1024];


			        int fileLength;
			        while ((fileLength = inStream.read(buffer)) > 0){

			              outStream.write(buffer, 0, fileLength );

			              }

			        inStream.close();
			        outStream.close();
				}
			}
		}
	    }
	}
	
}
