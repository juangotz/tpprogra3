package controller;

import FileReader.FileVerificator;

public class FileVerificationController {
	
	private FileVerificator fv;
	private String route;
	
	public FileVerificationController(String route) {
		fv = new FileVerificator();
		this.route=route;
	}
	
	public boolean verifyFileExists() {
		return fv.verifyFileExists(this.route);
	}
	
	public boolean verifyXmlFileType() {
		return fv.verifyXmlFileType(this.route);
	}
	
}
