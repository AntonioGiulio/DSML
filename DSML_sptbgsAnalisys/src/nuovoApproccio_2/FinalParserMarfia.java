package nuovoApproccio_2;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.opencsv.CSVWriter;

public class FinalParserMarfia {

	private SAXBuilder builder;
	private String fileName;
	private Document doc;
	private Element root;
	private Iterable<Element> children;
	private Iterator itr;
	private Hashtable<String, DataContainer> hashDataContainer;
	
	public FinalParserMarfia(String file) {
		builder = new SAXBuilder();
		fileName = file;
		try {
			doc = builder.build(fileName);
		}catch (JDOMException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		root = doc.getRootElement();
		children = root.getChildren();
		itr = children.iterator();
		hashDataContainer = new Hashtable<String, DataContainer>();
	}
	
	public ArrayList<Element> findBugInstance(){
		ArrayList<Element> list = new ArrayList<Element>();
		while (itr.hasNext()) {
			Element e = (Element) itr.next();
			if(e.getName() == "BugInstance" && e.getChild("Method") != null) {
				list.add(e);
			}
		}
		return list;
	}
	
	//proviamo con l'hash table
	public void pupulateList(ArrayList<Element> bugList) {
		for(Element e: bugList) {
			DataContainer box = new DataContainer();
			String className = "";
			try {
				box.setClassPath(e.getChild("Method").getChild("SourceLine").getAttributeValue("sourcepath"));
				className = e.getChild("Method").getChild("SourceLine").getAttributeValue("sourcefile");
				box.setClassName(className);
				box.setMethodName(e.getChild("Method").getAttributeValue("name"));
				box.setVulnerability(e.getAttributeValue("type"));
				box.setBuggedLine(Integer.parseInt(e.getChild("Method").getChild("SourceLine").getAttributeValue("start")));
			}catch (NullPointerException e1) {
				e1.printStackTrace();
			}
			hashDataContainer.put(className, box);						
		}
	}
	
	
	
	public String obtainBugSnippet(String classPath, String methodName, int bugLine) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(classPath));
		String retString = "";
		String line = "";
		for(int i = 0; i < bugLine - 5; i++) {
			line = reader.readLine();
		}
		retString += line + "\n";
		for(int j = bugLine -5; j < bugLine + 5; j++) {
			line = reader.readLine();
			retString += line + "\n";
		}
		
		reader.close();
		System.out.println("ho finito");
		return retString;		
	}
	
	public void listFiles(final File folder, CSVWriter writer) throws IOException {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFiles(fileEntry, writer);
	        } else {
	        	if(hashDataContainer.containsKey(fileEntry.getName())) {
	        		DataContainer handle = hashDataContainer.get(fileEntry.getName());
	        		System.out.println(hashDataContainer.get(fileEntry.getName()).toString());
	        		this.csvWriter(writer, handle.getVulnerability(), this.obtainBugSnippet(fileEntry.getAbsolutePath(), handle.getMethodName(), handle.getBuggedLine()));
	        		
	        	}
	        }
	    }
	}
	
	public void csvWriter(CSVWriter writer, String vul, String code) throws IOException {
		String[] data = {code, vul};
		writer.writeNext(data);
	}
	
	public Hashtable<String, DataContainer> getHashList() {
		return hashDataContainer;
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
	
}
