package be.ugent.psb.moduleviewer.parsers;

public class ParseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Exception originalException;
	
	public ParseException(Exception e){
		originalException = e;
	}

	public Exception getOriginalException() {
		return originalException;
	}
	
	

}
