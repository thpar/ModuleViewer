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

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ColoredAnnotation<T> extends Annotation<T> {
	
	
	protected Map<T, Color> contentColor = new HashMap<T, Color>();
	
	public ColoredAnnotation(String name, ModuleNetwork modnet) {
		super(name, modnet);
	}

	@Override
	public void addItem(T item) {
		contentColor.put(item, null);
	}
	
	public void addItem(T item, Color c){
		contentColor.put(item, c);
	}

	@Override
	public Set<T> getItems() {
		return contentColor.keySet();
	}

	public Collection<Entry<T, Color>> getEntrySet(){
		return contentColor.entrySet();
	}
	
	public Color getColor(T item){
		return contentColor.get(item);
	}
	
	@Override
	public boolean hasItem(T item) {
		return contentColor.containsKey(item);
	}

	
	
}
