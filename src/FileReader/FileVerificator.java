package FileReader;

import java.io.File;

public class FileVerificator {
	
	public boolean verifyFileExists(String route) {
		File f = new File(route);
		return f.exists();
	}
	
	public boolean verifyXmlFileType(String route) {
		StringBuilder aux = new StringBuilder(route);
		aux.delete(0, (route.length()-4));
		String result = aux.toString();
		return result.compareTo(".xml")==0;
		
	}
}
