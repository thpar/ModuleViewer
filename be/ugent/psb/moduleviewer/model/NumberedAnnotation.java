package be.ugent.psb.moduleviewer.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class NumberedAnnotation<T> extends Annotation<T> {
	
	
	protected Map<T, Integer> contentColor = new HashMap<T, Integer>();
	
	public NumberedAnnotation(String name, ModuleNetwork modnet) {
		super(name, modnet);
	}

	@Override
	public void addItem(T item) {
		contentColor.put(item, null);
	}
	
	public void addItem(T item, int c){
		contentColor.put(item, c);
	}

	@Override
	public Set<T> getItems() {
		return contentColor.keySet();
	}

	public Collection<Entry<T, Integer>> getEntrySet(){
		return contentColor.entrySet();
	}
	
	public int getNumber(T item){
		return contentColor.get(item);
	}
	
	@Override
	public boolean hasItem(T item) {
		return contentColor.containsKey(item);
	}

	
	
}
