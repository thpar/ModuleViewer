package be.ugent.psb.moduleviewer.model;

public class UnknownItemException extends Exception {

	private static final long serialVersionUID = 1L;

	enum ItemType{
		GENE, CONDITION, MODULE;
	}
	
	/**
	 * 
	 * @param type specify if the item is a gene, a condition or a module...
	 * @param itemId id of unknown gene, condition, module, ...
	 */
	public UnknownItemException(ItemType type, String itemId) {
		super("Item of type \""+type +"\" not defined: "+itemId);
	}
	
	/**
	 * 
	 * @param itemId id of unknown gene, condition, module, ...
	 */
	public UnknownItemException(ItemType type, int itemId) {
		super("Item of type \""+type +"\" not defined: "+itemId);
	}
}
