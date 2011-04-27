package be.ugent.psb.moduleviewer.model;

public class Condition{

	/**
	 * Name of this Condition.
	 */
	private String name;
	
	/**
	 * Number of this condition in the list in ModuleNetwork
	 */
	private int number;

	
	/**
	 * Create a condition with a given name.
	 * @param name
	 */
	public Condition(String name) {
		this.name = name;
	}

	public Condition(String name, int nr){
		this(name);
		this.number = nr;
	}
	
	/**
	 * 
	 * @return condition name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name condition name
	 */
	public void setName(String name) {
		this.name = name;
	}


	public int getNumber() {
		return this.number;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
}
