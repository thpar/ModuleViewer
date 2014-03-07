package be.ugent.psb.moduleviewer;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import be.ugent.psb.modulegraphics.display.CanvasLabel;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.moduleviewer.elements.DefaultCanvas;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.GUIModel.PointMode;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;


/**
 * The body of the GUI, the label that draws the Canvas with the module figures.
 * 
 * @author thpar
 *
 */
public class ModuleLabel extends CanvasLabel implements Observer{

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;

	private ModuleNetwork modnet;
	
	private Dimension currentCanvasSize = new Dimension();
	//FIXME DONT DO THIS HARD CODED!
	private final String GENEFETCH = "http://bioinformatics.psb.ugent.be/webtools/genefetch/search.html";
	private Model model;
	private boolean firstload = true;
	private Cursor cursor; 
		
		
	public ModuleLabel(Model model, GUIModel guiModel){
		this.model = model;
		this.model.addObserver(this);
		this.modnet = model.getModnet();
		this.guiModel = guiModel;
		this.guiModel.addObserver(this);
		//set splash here if needed
	}
	
	/**
	 * Create the Canvas and add it to this label.
	 * @return
	 */
	public Element initCanvas() {
		//don't even start if we don't have the essential data loaded
		if (!model.isEssentialsLoaded()){
			setCanvas(null);
			firstload = true;
			return null;
		} else {
			if (firstload ){
				Frame window = guiModel.getTopContainer();
				window.setExtendedState(window.getExtendedState()|JFrame.MAXIMIZED_BOTH);
				firstload = false;
			}
		}
	
		PointMode pm = guiModel.getPointMode();
		this.setPointMode(pm);
		
		int displayedModule = guiModel.getDisplayedModule();
		Module mod = null;
		try {
			mod = modnet.getModule(displayedModule);
		} catch (UnknownItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String title = "Module "+displayedModule;
		if (mod.getName()!=null && !mod.getName().isEmpty()){
			title+=" - "+mod.getName();
		}
		
		DefaultCanvas canvas = new DefaultCanvas(mod, model, guiModel, title);
		setCanvas(canvas);
		
//		addCanvasListeners(canvas);
		
				
		currentCanvasSize = canvas.getDimension(this.getGraphics());
		setPreferredSize(currentCanvasSize);
		revalidate();
		
//		this.currentPaintedModule = displayedModule;
		
		
		return canvas;
	}

	private void setPointMode(PointMode pm){
		switch(pm){
		case POINT:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			break;
		case PAN:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			break;
		}
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
		if (arg!=null && arg.toString().equals("nonredraw")){
			this.setPointMode(guiModel.getPointMode());
			return;
		}
		initCanvas();
		repaint();
	}

	public Dimension getCurrentCanvasSize() {
		return currentCanvasSize;
	}
	
	
	
	
}
