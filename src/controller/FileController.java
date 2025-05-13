package controller;

import java.io.File;


public class FileController{
	
	private String loadErrorMessage;
	
	public FileController() {
		
	}
	
	public boolean verifyFile(String route) {
		if (!verifyFileExists(route)) {
			loadErrorMessage = "Archivo no existe";
			return false;
		}
		if (!verifyXmlFileType(route)) {
			loadErrorMessage = "Archivo no es .xml";
			return false;
		}
		return true;
	}

	public Object getMessage() {
		
		return loadErrorMessage;
	}
	
	private boolean verifyFileExists(String route) {
		File f = new File(route);
		return f.exists();
	}
	
	private boolean verifyXmlFileType(String route) {
		StringBuilder aux = new StringBuilder(route);
		aux.delete(0, (route.length()-4));
		String result = aux.toString();
		return result.compareTo(".xml")==0;
		
	}
}
