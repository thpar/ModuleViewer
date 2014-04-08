package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.MVFParser;

/**
 * Loads an MVF file. These files represent several kinds of annotations for 
 * genes and conditions.
 * 
 * @author thpar
 *
 */
public class LoadAnnotationAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private Model model;

	
	public LoadAnnotationAction(Model model, GUIModel guiModel){
		super("Load Annotations...");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load Annotations");
		fc.setFileFilter(new FileNameRegexFilter("module viewer format", ".*\\.mvf"));
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			final File file = fc.getSelectedFile();
			
			LoadTask worker = new LoadTask(file);
			worker.addPropertyChangeListener(guiModel);
			worker.execute();
			
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}
	
	
	public class LoadTask extends SwingWorker<Void, Void>{

		private File file;
		public LoadTask(File file){
			this.file = file;
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			guiModel.showProgressBar(true);
			setProgress(0);
			guiModel.setStateString("Loading annotation: "+file);
			
//			ProgressListener progListener = new ProgressListener(){
//				@Override
//				public void setMyProgress(int percent) {
//					setProgress(percent);
//				}
//			};
			
			MVFParser parser = new MVFParser();
			
			parser.parse(model, file);
			model.addAnnotationFile(file.getAbsolutePath());
			
			setProgress(100);

			return null;
		}
		
		
		
		@Override
		protected void done() {
			guiModel.showProgressBar(false);
			guiModel.clearStateString();
			guiModel.refresh();
		}
	}

}
