package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.parsers.ModuleNetworkParser;

/**
 * 
 * @author thpar
 *
 */
public class LoadModulesAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private ModuleNetwork modnet;
	private GUIModel guiModel;

	
	public LoadModulesAction(ModuleNetwork modnet, GUIModel guiModel){
		super("Load Modules...");
		this.modnet = modnet;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load modules");
		fc.setFileFilter(new FileNameRegexFilter("zipped module files", ".*\\.xml\\.gz"));
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			final File file = fc.getSelectedFile();
			
			LoadTask worker = new LoadTask(file);
			worker.addPropertyChangeListener(guiModel);
			worker.execute();
			
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}
	
	public interface ProgressListener{
		void setMyProgress(int percent);
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
			
			ProgressListener progListener = new ProgressListener(){
				@Override
				public void setMyProgress(int percent) {
					setProgress(percent);
				}
			};
			
			ModuleNetworkParser parser = new ModuleNetworkParser();
			parser.setProgressListener(progListener);
			ModuleNetworkParser.loadWithSAXParser(modnet, file);
			
			setProgress(100);

			return null;
		}
		
		
		
		@Override
		protected void done() {
			guiModel.showProgressBar(false);
			guiModel.refresh();
		}
	}

}