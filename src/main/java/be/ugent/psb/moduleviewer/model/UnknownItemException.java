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
 * Thrown when a search for a certain item (gene, condition, module) on its identifier
 * yields nothing.
 * 
 * 
 * @author Thomas Van Parys
 *
 */
public class UnknownItemException extends Exception {

	private static final long serialVersionUID = 1L;

	enum ItemType{
		GENE, CONDITION, MODULE;
	}
	
	/**
	 * 
	 * @param type specify if the item is a gene, a condition or a module...
	 * @param itemId id of unknown gene, condition, module, ...
	 */
	public UnknownItemException(ItemType type, String itemId) {
		super("Item of type \""+type +"\" not defined: "+itemId);
	}
	
	/**
	 * 
	 * @param itemId id of unknown gene, condition, module, ...
	 */
	public UnknownItemException(ItemType type, int itemId) {
		super("Item of type \""+type +"\" not defined: "+itemId);
	}
}
