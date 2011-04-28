package be.ugent.psb.moduleviewer.model;

public interface ValuedAnnotation<T> {
	
	public void addItem(String itemId, T value) throws UnknownItemException;

}
