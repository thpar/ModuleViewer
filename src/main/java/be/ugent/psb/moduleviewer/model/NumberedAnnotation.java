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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Annotation data needed to draw a numbered matrix. 
 * 
 * @author Thomas Van Parys
 *
 * @param <T>
 */
public class NumberedAnnotation<T> extends Annotation<T> {
	
	
	protected Map<T, Integer> contentNumber = new HashMap<T, Integer>();
	
	public NumberedAnnotation(String name, ModuleNetwork modnet) {
		super(name, modnet);
	}

	@Override
	public void addItem(T item) {
		contentNumber.put(item, null);
	}
	
	public void addItem(T item, int c){
		contentNumber.put(item, c);
	}

	@Override
	public Set<T> getItems() {
		return contentNumber.keySet();
	}

	public Collection<Entry<T, Integer>> getEntrySet(){
		return contentNumber.entrySet();
	}
	
	public int getNumber(T item){
		return contentNumber.get(item);
	}
	
	@Override
	public boolean hasItem(T item) {
		return contentNumber.containsKey(item);
	}

	
	
}
