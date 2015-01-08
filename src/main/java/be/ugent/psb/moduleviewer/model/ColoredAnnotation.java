package be.ugent.psb.moduleviewer.model;

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
