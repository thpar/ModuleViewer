package be.ugent.psb.moduleviewer.model;

public class UnknownItemException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param itemId id of unknown gene or condition
	 */
	public UnknownItemException(String itemId) {
		super("Item not defined: "+itemId);
	}
	
	
}
