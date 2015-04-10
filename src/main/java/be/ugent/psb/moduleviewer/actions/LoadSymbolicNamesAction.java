package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.SymbolicNameParser;


public class LoadSymbolicNamesAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private Model model;

	
	public LoadSymbolicNamesAction(Model model, GUIModel guiModel){
		super("Load symbolic name mapping...");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load symbolic name mapping");
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
			
			SymbolicNameParser parser = new SymbolicNameParser();
			parser.parse(model, file);
			
			model.setSymbolMappingFile(file.getAbsolutePath());
			
			setProgress(100);
			return null;
		}
		
		@Override
		protected void done() {
			guiModel.clearStateString();
			model.getLogger().addEntry("Symbolic name mapping loaded: "+file);
			guiModel.showProgressBar(false);
			guiModel.refresh();
		}
	}
	

}
