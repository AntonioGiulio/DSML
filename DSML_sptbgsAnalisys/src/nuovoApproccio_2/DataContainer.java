package nuovoApproccio_2;

public class DataContainer {

	private String classPath;
	private String className;
	private String methodName;
	private String vulnerability;
	private int buggedLine;
	
	public DataContainer() {
		
	}
	
	public DataContainer(String s1, String s2, String s3, String s4) {
		classPath = s1;
		className = s2;
		methodName = s3;
		vulnerability = s4;
	}
	
	public String getClassPath() {
		return this.classPath;
	}
	
	public void setClassPath(String s) {
		this.classPath = s;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public void setClassName(String s) {
		this.className = s;
	}
	
	public String getMethodName() {
		return this.methodName;
	}
	
	public void setMethodName(String s) {
		this.methodName = s;
	}
	
	public String getVulnerability() {
		return this.vulnerability;
	}
	
	public void setVulnerability(String s) {
		this.vulnerability = s;
	}
	
	public int getBuggedLine() {
		return this.buggedLine;
	}
	
	public void setBuggedLine(int n) {
		this.buggedLine = n;
	}
	
	public String toString() {
		String retString;
		retString = "classPath: " + this.classPath + "\n";
		retString += "classname: " + this.className + "\n";
		retString += "methodName: " + this.methodName + "\n";
		retString += "vulnerability: " + this.vulnerability +"\n";
		retString += "bugLine: " + this.buggedLine + "\n";
		return retString;
		
	}
}
