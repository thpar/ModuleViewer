package be.ugent.psb.moduleviewer.model;

public class Gene {

	/**
	 * GeneID
	 */
	private String id;
	
	/**
	 * Gene alternative name
	 */
	private String name;
		
	/**
	 * The original data for this gene
	 * The corresponding line from the data matrix 
	 */
	private double data[];

	/**
	 * 
	 * @param id Gene ID
	 */
	public Gene(String id){
		this.id = id;
	}
	
	
	/**
	 * Get the alternative gene name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the alternative gene name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the gene id
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the raw expression data for this gene
	 * @return
	 */
	public double[] getData() {
		return data;
	}

	/**
	 * Link a row of expression data to this gene
	 * @param data
	 */
	public void setData(double[] data) {
		this.data = data;
	}

	public double getValue(Condition cond){
		return data[cond.getNumber()];
	}


	
	
}
