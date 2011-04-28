package be.ugent.psb.moduleviewer.model;


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
