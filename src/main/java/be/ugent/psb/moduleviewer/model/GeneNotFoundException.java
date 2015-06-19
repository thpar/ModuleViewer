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
