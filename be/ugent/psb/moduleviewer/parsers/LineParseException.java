package be.ugent.psb.moduleviewer.parsers;

public class LineParseException extends Exception {

	private static final long serialVersionUID = 1L;

	public LineParseException(String message) {
		super("Couldn't parse MVF line: "+ message);
	}

	public LineParseException(Throwable cause) {
		super(cause);
	}

	
}
