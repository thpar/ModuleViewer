package be.ugent.psb.moduleviewer;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.moduleviewer.elements.ConditionLabels;
import be.ugent.psb.moduleviewer.elements.ExpressionMatrix;
import be.ugent.psb.moduleviewer.elements.GeneNames;
import be.ugent.psb.moduleviewer.elements.Title;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;

/**
 * A Default layout for a Canvas that looks up which elements to 
 * draw in the graphical model of the module.
 * 
 * The Module itself is drawn anyway. If topregulators are given, they are drawn as well, together
 * with the tree structure.
 * 
 * @author thpar
 *
 */
public class OldDefaultCanvas extends Canvas {
	
	ModuleNetwork modnet;
	
	private ExpressionMatrix matrix;
	private GeneNames geneNames;
	private ExpressionMatrix topRegMatrix;
	private GeneNames topRegGeneNames;
//	private ConditionAnnotationMatrix conditionAnnotationMatrix;
	private Element conditionLabels;
	
	public OldDefaultCanvas(Module mod, GUIModel guiModel, String title){

		this.modnet = mod.getModuleNetwork();
		
		//general settings
		this.setHorizontalSpacing(5);
		this.setVerticalSpacing(5);
		this.setMargin(20);

		//check which mean and sigma to use
		//TODO move sigma and mean calculations to specific colorizers...
		double mean;
		double sigma;
		if (guiModel.isUseGlobalMeans()){
			mean = mod.getModuleNetwork().getMean();
			sigma = mod.getModuleNetwork().getSigma();
		} else {
//			mean = mod.getMean();
//			sigma = mod.getSigma();
		}

		
		//title
		if (guiModel.isDrawFileName()){
			this.add(new Title(title));
			this.getLastAddedElement().setBottomMargin(10);
			this.newRow();
		}
		
//		List<String> checkListNames = mod.getCheckListNames();
		
		boolean recursiveNodes = false;
		//tree structure and top regulators only needed if we do have topRegulators
		//tell the matrices to traverse the nodes recursively
//		if (mod.getTopRegulators().size()>0){
//			if (mod.getLinkLists()!=null && guiModel.isDrawGeneLinks()){
//				this.add(new Spacer());
//			}
//			recursiveNodes = true;
//			Canvas regCanvas = new Canvas();
//
//			//tree structure 
//			regCanvas.add(new TreeStructure(mod.getRootNode()));
//			regCanvas.newRow();
//
//			//the topregulators
//			topRegMatrix = new ExpressionMatrix(mod.getTopRegulators(), mod.getRootNode(), mean, sigma, recursiveNodes);
//			regCanvas.add(topRegMatrix);
//
//			//join these on one canvas to draw them without spacing in between
//			this.add(regCanvas);
//
//			topRegGeneNames = new GeneNames(mod.getTopRegulators());
//			this.add(topRegGeneNames);
//			topRegGeneNames.setAlignment(Alignment.BOTTOM_LEFT);
//			
//			if(guiModel.isDrawGOForTopRegulators()){
////				this.add(new GOMatrix(mod.getTopRegulators(), mod.GOsuper));
////				this.getLastAddedElement().setAlignment(Alignment.BOTTOM_LEFT);
//			}
////			if (guiModel.isDrawAracyc() && mod.getAracyc() != null){
////				TickBoxColumn araCol = new TickBoxColumn(mod.topRegulators, mod.getAracyc(), Color.RED);
////				this.add(araCol);
////				this.getLastAddedElement().setAlignment(Alignment.BOTTOM_LEFT);
////			}
//			
////			if (guiModel.isDrawGeneCheckLists() && mod.getTopregCheckLists()!=null){
////				Map<String, List<Gene>> checkLists = mod.getTopregCheckLists();
////				List<TickBoxColumn> cols = new ArrayList<TickBoxColumn>();
////				for (String listName : checkListNames){
////					List<Gene> list = checkLists.get(listName);
////					TickBoxColumn col = new TickBoxColumn(mod.topRegulators, list, guiModel.getGeneCheckListColorMap().get(listName));
////					cols.add(col);
////				}
////				TickBoxMatrix tbMatrix = new TickBoxMatrix(cols);
////				this.add(tbMatrix);
////				this.getLastAddedElement().setAlignment(Alignment.BOTTOM_LEFT);
////			}
//			
//			this.newRow();
//			this.add(new Spacer(new Dimension(0,10)));
//			this.newRow();
//		}
		
//		if (mod.getLinkLists()!=null && guiModel.isDrawGeneLinks()){
//			GeneLinks linksElement = new GeneLinks(mod.getLinkLists());
//			for (Entry<String, Color> ent : guiModel.getGeneLinkColorMap().entrySet()){
//				String id = ent.getKey();
//				Color c   = ent.getValue();
//				linksElement.setColor(id, c);
//			}
//			this.add(linksElement);
//		}
		//the genes
//		this.matrix = new ExpressionMatrix(mod.getGeneTree(), mod.getConditionTree(), mean, sigma, recursiveNodes);
//		this.add(matrix);
//		this.geneNames = new GeneNames(mod.getGeneTree());
		geneNames.setAlignment(Alignment.BOTTOM_LEFT);
		this.add(geneNames);
		
//		if (guiModel.isDrawGOForGenes()){
//			this.add(new GOMatrix(mod.genes, mod.GOsuper));
//		}

//		if (guiModel.isDrawAracyc() && mod.getAracyc() != null){
//			TickBoxColumn araCol = new TickBoxColumn(mod.genes, mod.getAracyc(), Color.RED);
//			this.add(araCol);
//		}
		
		//init the checkLists
//		if (guiModel.isDrawGeneCheckLists() && mod.getCheckLists()!=null){
//			List<GeneCheckList> checkLists = mod.getCheckLists();
//			List<TickBoxColumn> cols = new ArrayList<TickBoxColumn>();
//			for (String listName : checkListNames){
//				List<Gene> list = checkLists.get(listName);
//				TickBoxColumn col = new TickBoxColumn(mod.genes, list, guiModel.getGeneCheckListColorMap().get(listName));
//				cols.add(col);
//			}
//			TickBoxMatrix tbMatrix = new TickBoxMatrix(cols);
//			this.add(tbMatrix);
//		}
//		LabelList checkListLabels = new LabelList(checkListNames);
//		checkListLabels.setDirection(Direction.LEFT_TO_RIGHT);
//		checkListLabels.setAngle(Angle.SKEWED);
//		checkListLabels.setFont(new Font("SansSerif", Font.PLAIN, 10));
//		checkListLabels.setPushBounds(true);
		
		this.newRow();
		
//		if (mod.getLinkLists()!=null && guiModel.isDrawGeneLinks()){
//			this.add(new Spacer());
//		}
		//condition and GO labels
		conditionLabels = new ConditionLabels(mod.getConditionTree(), recursiveNodes); 
		this.add(conditionLabels);
		
		
//		this.add(new Spacer());
//		if (guiModel.isDrawGOForTopRegulators() || guiModel.isDrawGOForGenes()){
//			GOLabels goLabels = new GOLabels(mod.GOsuper);
//			goLabels.setPushBounds(false);
//			this.add(goLabels);
//		}	
//		if (guiModel.isDrawAracyc() && mod.getAracyc() != null){
//			this.add(new Label("Aracyc"));
//			((Label)this.getLastAddedElement()).setAngle(Math.PI/4);
//		}
//		if (guiModel.isDrawGeneCheckLists() && mod.getCheckLists()!=null){
//			this.add(checkListLabels);
//		}
//		
//		this.newRow();
		
		//condition annotations
//		if (guiModel.isDrawConditionAnnotations() && mod.moduleNetwork.getConditionClassification()!=null){
//			if (mod.getLinkLists()!=null && guiModel.isDrawGeneLinks()){
//				this.add(new Spacer());
//			}
//			conditionAnnotationMatrix = new ConditionAnnotationMatrix(mod.hierarchicalTree, 
//					mod.moduleNetwork.getConditionClassification(), mod.moduleNetwork.conditionSet, recursiveNodes);
//			this.add(conditionAnnotationMatrix);
//
//			LabelList classLabels = new LabelList(mod.moduleNetwork.getConditionClassification().getClasses());
//			classLabels.setDirection(Direction.TOP_TO_BOTTOM);
//			this.add(classLabels);
//			this.newRow();
//		}
		//the legend for condition annotations
		//we don't want the white CTRL blocks to show up here
//		if (guiModel.isDrawConditionAnnotationLegend() && mod.moduleNetwork.getConditionClassification()!=null){
//			if (mod.getLinkLists()!=null && guiModel.isDrawGeneLinks()){
//				this.add(new Spacer());
//			}
//			List<String> excludedProps = new ArrayList<String>();
//			excludedProps.add("CTRL");
//			excludedProps.add("ctrl");
//			this.add(new ConditionAnnotationLegend(mod.moduleNetwork.getConditionClassification(), excludedProps));
//		}
		
	}

	public ExpressionMatrix getMatrix() {
		return matrix;
	}

	public GeneNames getGeneNames() {
		return geneNames;
	}

	public ExpressionMatrix getTopRegMatrix() {
		return topRegMatrix;
	}

	public GeneNames getTopRegGeneNames() {
		return topRegGeneNames;
	}

//	public ConditionAnnotationMatrix getConditionAnnotationMatrix() {
//		return conditionAnnotationMatrix;
//	}

	public Element getConditionLabels() {
		return conditionLabels;
	}
	
	
	
}
