package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

public class DoubleConditionAnnotation extends ConditionAnnotation implements ValuedAnnotation<Double>{

	public DoubleConditionAnnotation(String name, ModuleNetwork modnet) {
		super(name, modnet);
	}

	List<Double> values = new ArrayList<Double>();
	
	@Override
	public void addItem(String itemId, Double value) throws UnknownItemException{
		this.addItem(itemId);
		this.values.add(value);
	}

	@Override
	public void addItem(String itemId) throws UnknownItemException{
		super.addItem(itemId);
		this.values.add(null);
	}
	
}
