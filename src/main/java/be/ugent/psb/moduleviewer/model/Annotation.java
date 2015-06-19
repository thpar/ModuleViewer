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

import java.util.HashSet;
import java.util.Set;

/**
 * Annotates a list of genes or conditions.
 * 
 * T : class of objects to be annotated
 * 
 * @author thpar
 *
 * @param <T> Type of contents ({@link Gene} or {@link Condition})
 */
public class Annotation<T> {
	/**
	 * Name of the list.
	 */
	protected String name;
	/**
	 * The module network this list is referring to.
	 */
	protected ModuleNetwork modnet;

	/**
	 * Genes, conditions, ... the objects to be annotated.
	 */
	protected Set<T> contents = new HashSet<T>();
	
		
	/**
	 * Create a new annotation list, linked to the modnet
	 * @param name
	 * @param modnet
	 */
	public Annotation(String name, ModuleNetwork modnet){
		this.name = name;
		this.modnet = modnet;
	}
	
	/**
	 * 
	 * @return the name of this list
	 */
	public String getName(){
		return name;
	}
	
	public void addItem(T item){
		this.contents.add(item);
	}
	
	
	public Set<T> getItems(){
		return contents;
	}
	
	
	public boolean hasItem(T item){
		return this.contents.contains(item);
	}

	
	
	
	@Override
	public String toString(){
		String out = new String();
		out+=this.name;
		out+="\t";
		for (T item : contents){
			out+=":"+item+":" + " ";
		}
		return out;
	}

	public ModuleNetwork getModnet() {
		return modnet;
	}
	
	/**
	 * If the name of this annotation is supposed to be a {@link Gene}, find the object in the
	 * {@link ModuleNetwork}.
	 * @return
	 */
	public Gene getNameAsGene() throws UnknownItemException{
		return this.modnet.getGene(this.name);
	}
	
	
	
}
