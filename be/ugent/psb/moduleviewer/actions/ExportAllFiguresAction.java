package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import be.ugent.psb.modulegraphics.display.CanvasFigure;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.moduleviewer.elements.DefaultCanvas;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.parsers.GeneTreeParser;



public class ExportAllFiguresAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	GUIModel guiModel;

	private Model model;
	
	
	public ExportAllFiguresAction(Model model, GUIModel guiModel){
		super("Export all figures");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	public ExportAllFiguresAction(Model model, GUIModel guiModel, boolean showLabel){
		super(new String());
		this.guiModel = guiModel;
		this.model = model;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		File outputDir = guiModel.getOutputDir();
		if (outputDir==null){
			JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setDialogTitle("Select output directory");
			int answer = fc.showOpenDialog(guiModel.getTopContainer());
			if (answer == JFileChooser.APPROVE_OPTION){
				guiModel.setOutputDir(fc.getSelectedFile());
			} else {
				guiModel.setOutputDir(guiModel.getCurrentDir());
			}
			
		}
		

		SaveTask worker = new SaveTask();
		worker.addPropertyChangeListener(guiModel);
		worker.execute();
		
		
		
	}
	
	
	
	public class SaveTask extends SwingWorker<Void, Void>{

		public SaveTask(){

		}
		
		@Override
		protected Void doInBackground() throws Exception {
			guiModel.showProgressBar(true);
			setProgress(0);
			
			//trick to pass on progListener to other class. Not needed now.
//			ProgressListener progListener = new ProgressListener(){
//				@Override
//				public void setMyProgress(int percent) {
//					setProgress(percent);
//				}
//			};
			
			
			ModuleNetwork modnet = model.getModnet();
			guiModel.showProgressBar(true);
			guiModel.setStateString("Exporting images...");
			
			int modCount = 0;
			int totModCount = modnet.getModules().size();
			
			for (Module mod : modnet.getModules()){
				String title = "Module "+mod.getId();
				String file_id = String.valueOf(mod.getId());
				if (mod.getName()!=null && !mod.getName().isEmpty()){
					title+=" - "+mod.getName();
					file_id+="_"+mod.getName();
				}
				String fileName = guiModel.getOutputDir() + System.getProperty("file.separator") + 
				guiModel.getFileNameTemplate(file_id)+"."+guiModel.getOutputFormat();
				
				Canvas canvas = new DefaultCanvas(mod, model, guiModel, title);
				CanvasFigure figCanvas = new CanvasFigure(canvas, fileName);
				figCanvas.writeToFigure(guiModel.getOutputFormat());
				
				modCount++;
				int prog = (int)Math.round(((double)modCount/(double)totModCount) * 100);
				setProgress(prog);
			}
			
			setProgress(100);
			guiModel.setStateString("Figures exported to "+guiModel.getOutputDir());
			return null;
		}
		
		
		
		@Override
		protected void done() {
			guiModel.showProgressBar(false);
			guiModel.refresh();
		}
	}


}
