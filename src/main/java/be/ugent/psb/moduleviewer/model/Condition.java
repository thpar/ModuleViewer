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
 * A single condition, identified by its name and column number in the input expression matrix 
 * 
 * @author Thomas Van Parys
 *
 */
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
		return this.number+"("+this.name+")";
	}
	
	
}
