package be.ugent.psb.moduleviewer;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;

import be.ugent.psb.modulegraphics.display.CanvasLabel;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.moduleviewer.elements.DefaultCanvas;
import be.ugent.psb.moduleviewer.model.ConditionNode;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;



public class ModuleLabel extends CanvasLabel implements Observer{

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;

//	private int currentPaintedModule = -1;
	private ModuleNetwork modnet;
	
	private Dimension currentCanvasSize = new Dimension();
	//FIXME DONT DO THIS HARD CODED!
	private final String GENEFETCH = "http://bioinformatics.psb.ugent.be/webtools/genefetch/search.html";
	private Model model; 
		
		
	public ModuleLabel(Model model, GUIModel guiModel){
		this.model = model;
		this.modnet = model.getModnet();
		this.guiModel = guiModel;
		this.guiModel.addObserver(this);
		this.setSplash(new ImageIcon(this.getClass().getResource("/icons/lemone.png")));
	}
	
	
	public Element initCanvas() {
		//don't even start if we don't have the essential data loaded
		if (!model.isEssentialsLoaded()) return null;
		
		
		int displayedModule = guiModel.getDisplayedModule();
		Module mod = null;
		try {
			mod = modnet.getModule(displayedModule);
		} catch (UnknownItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ConditionNode n = mod.getConditionTree();
//		mod.hierarchicalTree = n;
		
//		for (TreeNode node : mod.hierarchicalTree.getInternalNodes()){
//			Collections.sort(node.testSplits);
//			node.regulationSplit = node.testSplits.get(0);
//		}
		
		DefaultCanvas canvas = new DefaultCanvas(mod, model, guiModel, "Module "+displayedModule);
		setCanvas(canvas);
		
//		addCanvasListeners(canvas);
		
				
		currentCanvasSize = canvas.getDimension(this.getGraphics());
		setPreferredSize(currentCanvasSize);
		revalidate();
		
//		this.currentPaintedModule = displayedModule;
		return canvas;
	}



//	private void addCanvasListeners(DefaultCanvas canvas) {
//		canvas.getGeneNames().addMouseListener(new MouseAdapter(){
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				GeneNames gn = (GeneNames)e.getSource();
//				String gnString = gn.getHitGeneName(e.getX(), e.getY());
//				if (Desktop.isDesktopSupported()){
//					guiModel.setStateString("GeneFetching: "+gnString);
//					Desktop desktop = Desktop.getDesktop();
//					 if (desktop.isSupported(Desktop.Action.BROWSE)) {
//						 try {
//							URI uri = new URI(GENEFETCH+"?gene="+gnString);
//							 desktop.browse(uri);
//						} catch (URISyntaxException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						} catch (IOException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//
//				     } else {
//				    	 System.err.println("Can't identify web browser");
//				     }
//				} else {
//					System.err.println("No Desktop operations supported!");
//				}
//			}
//		});
//		
//		canvas.getMatrix().addMouseListener(new MouseAdapter(){
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				ExpressionMatrix matrix = (ExpressionMatrix)e.getSource();
//				double data = matrix.getHitData(e.getX(), e.getY());
//				guiModel.setStateString(String.valueOf(data));
//			}
//		});
//		
//		ExpressionMatrix topRegMatrix = canvas.getTopRegMatrix();
//		if (topRegMatrix!=null){
//			topRegMatrix.addMouseListener(new MouseAdapter(){
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					ExpressionMatrix matrix = (ExpressionMatrix)e.getSource();
//					double data = matrix.getHitData(e.getX(), e.getY());
//					guiModel.setStateString(String.valueOf(data));
//				}
//			});
//		}
//		
//		GeneNames topRegGeneNames = canvas.getTopRegGeneNames();
//		if (topRegGeneNames!=null){
//			canvas.getTopRegGeneNames().addMouseListener(new MouseAdapter(){
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					GeneNames gn = (GeneNames)e.getSource();
//					String gnString = gn.getHitGeneName(e.getX(), e.getY());
//					guiModel.setStateString(gnString);
//				}
//			});
//		}
//		canvas.getConditionLabels().addMouseListener(new MouseAdapter(){
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				ConditionLabels cl = (ConditionLabels)e.getSource();
//				String cnString = cl.getHitConditionName(e.getX(), e.getY());
//				guiModel.setStateString(cnString);
//			}
//		});
////		ConditionAnnotationMatrix condAnnotMatrix = canvas.getConditionAnnotationMatrix();
////		if (condAnnotMatrix!=null){
////			canvas.getConditionAnnotationMatrix().addMouseListener(new MouseAdapter(){
////				@Override
////				public void mouseClicked(MouseEvent e) {
////					ConditionAnnotationMatrix cam = (ConditionAnnotationMatrix)e.getSource();
////					String cnString = cam.getHitAnnotation(e.getX(), e.getY());
////					guiModel.setStateString(cnString);
////				}
////			});
////		}
//	}


	@Override
	public void update(Observable o, Object arg) {
		initCanvas();
		repaint();
	}

	public Dimension getCurrentCanvasSize() {
		return currentCanvasSize;
	}
	
	
	
	
}
