package be.ugent.psb.moduleviewer.model;

/*
 * #%L
 * ModuleViewer
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */


/**
 * A single gene, identified by its gene id
 * @author Thomas Van Parys
 *
 */
public class Gene {

	/**
	 * GeneID
	 */
	private String name;
	
	/**
	 * Gene alternative name
	 */
	private String alias;
	
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

	@Override
	public String toString() {
		if (alias==null || alias.isEmpty()) return this.name;
		else return this.name + "("+this.alias+")";
	}

	
	

	
	
}
