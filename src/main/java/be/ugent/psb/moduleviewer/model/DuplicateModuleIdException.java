package be.ugent.psb.moduleviewer.model;

public class DuplicateModuleIdException extends Exception {

	private static final long serialVersionUID = 1L;

	String modId;

	public DuplicateModuleIdException(String modId) {
		super();
		this.modId = modId;
	}

	@Override
	public String getMessage() {
		return "Module ID already exists: "+modId;
	}
	
	
	
}
