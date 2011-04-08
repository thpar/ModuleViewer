package be.ugent.psb.graphicalmodule.model;

public class Experiment implements Comparable<Experiment> {

	/**
	 * Name of this experiment.
	 */
	private String name;

	
	/**
	 * Create an experiment with a given name.
	 * @param name
	 */
	public Experiment(String name) {
		this.name = name;
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
	
	
	
	//TODO condition annotations?
	
}
