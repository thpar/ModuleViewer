package be.ugent.psb.graphicalmodule.model;

public class Experiment implements Comparable<Experiment> {

	/**
	 * Name of this experiment.
	 */
	private String name;
	
	/**
	 * Number of this experiment in the list in ModuleNetwork
	 */
	private int number;

	
	/**
	 * Create an experiment with a given name.
	 * @param name
	 */
	public Experiment(String name) {
		this.name = name;
	}

	public Experiment(String name, int nr){
		this(name);
		this.number = nr;
	}
	
	/**
	 * 
	 * @return experiment name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name experiment name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Experiment o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	public int getNumber() {
		return this.number;
	}
	
	
	
	//TODO condition annotations?
	
}
