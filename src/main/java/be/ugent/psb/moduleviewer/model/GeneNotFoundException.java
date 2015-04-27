package be.ugent.psb.moduleviewer.model;

/**
 * Thrown when a Gene object can not be found in a list where it's expected.
 * 
 * @author thpar
 *
 */
public class GeneNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Gene gene;
	
	public GeneNotFoundException(Gene gene){
		this.gene = gene;
	}

	@Override
	public String getMessage() {
		return new String("Couldn't find gene: "+gene);
	}
	
	
}
