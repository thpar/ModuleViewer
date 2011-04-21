package be.ugent.psb.moduleviewer.parsers;

import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;

public class ConditionAnnotation extends Annotation<Condition> {

	public ConditionAnnotation(String name, ModuleNetwork modnet) {
		super(name, modnet);
	}

	@Override
	public void addItem(String itemId) {
		Condition cond = modnet.getCondition(itemId);
		this.contents.add(cond);
	}

	

}
