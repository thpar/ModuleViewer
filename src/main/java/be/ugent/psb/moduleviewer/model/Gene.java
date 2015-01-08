package be.ugent.psb.moduleviewer.model;



public class Gene {

	/**
	 * GeneID
	 */
	private String name;
	
	/**
	 * Gene alternative name
	 */
	private String alias;
	
	private String description;
	
	/**
	 * The original data for this gene
	 * The corresponding line from the data matrix 
	 */
	private double data[];

	/**
	 * 
	 * @param name Gene ID
	 */
	public Gene(String name){
		this.name = name;
	}
	
	/**
	 * Create a gene with name and alias
	 * 
	 * @param name
	 * @param alias
	 */
	public Gene(String name, String alias){
		this.name = name;
		this.alias = alias;
	}
	
	/**
	 * Get the alternative gene name. If no alias is set, the {@link name} is returned.
	 * @return
	 */
	public String getAliasOrName() {
		if (alias!=null) return alias;
		else return name;
	}

	public String getAlias(){
		return alias;
	}
	
	
	/**
	 * Set the alternative gene name
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Get the gene name
	 * @return
	 */
	public String getName() {
		return name;
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

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		if (alias==null || alias.isEmpty()) return this.name;
		else return this.name + "("+this.alias+")";
	}

	
	

	
	
}
