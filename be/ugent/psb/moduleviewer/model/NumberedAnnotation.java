package be.ugent.psb.moduleviewer.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
