package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

public class IntegerGeneAnnotation extends GeneAnnotation implements ValuedAnnotation<Integer>{

	public IntegerGeneAnnotation(String name, ModuleNetwork modnet) {
		super(name, modnet);
	}

	List<Integer> values = new ArrayList<Integer>();
	
	@Override
	public void addItem(String itemId, Integer value) throws UnknownItemException{
		this.addItem(itemId);
		this.values.add(value);
	}

	@Override
	public void addItem(String itemId) throws UnknownItemException{
		super.addItem(itemId);
		this.values.add(null);
	}
	
	@Override
	public void addItem(Gene item){
		super.addItem(item);
		this.values.add(null);
	}
}
