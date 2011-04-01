package be.ugent.psb.graphicalmodule.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Date;
import java.util.HashSet;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import be.ugent.psb.ModuleNetwork.BiNGO;
import be.ugent.psb.ModuleNetwork.ModuleNetwork;
import be.ugent.psb.ModuleNetwork.parsers.ModuleNetworkParser;
import be.ugent.psb.graphicalmodule.model.GraphicalModuleModel;

public class LoadBiNGOAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	GraphicalModuleModel guiModel;

	private ModuleNetwork modnet;
	
	
	public LoadBiNGOAction( ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super("Load BiNGO...");
		this.guiModel = guiModel;
		this.modnet = modnet;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		//geneAssoc file
		File geneAssoc = null;
		JFileChooser fcAssoc = new JFileChooser(guiModel.getCurrentDir());
		fcAssoc.setDialogTitle("Load BiNGO gene associations");
		int assocAnswer = fcAssoc.showOpenDialog(guiModel.getTopContainer());
		if (assocAnswer == JFileChooser.APPROVE_OPTION){
			geneAssoc = fcAssoc.getSelectedFile();
		} else {
			return;
		}
		
		guiModel.setCurrentDir(fcAssoc.getCurrentDirectory());
		
		//geneOnt (obo) file
		File geneOnto = null;
		JFileChooser fcOnto = new JFileChooser(guiModel.getCurrentDir());
		fcOnto.setFileFilter(new FileNameRegexFilter("Ontology Files", ".*\\.obo"));
		fcOnto.setDialogTitle("Load gene ontology file");
		int ontoAnswer = fcOnto.showOpenDialog(guiModel.getTopContainer());
		if (ontoAnswer == JFileChooser.APPROVE_OPTION){
			geneOnto = fcOnto.getSelectedFile();
		} else {
			return;
		}
		
		guiModel.setCurrentDir(fcOnto.getCurrentDirectory());
		
		
		LoadTask worker = new LoadTask(geneAssoc, geneOnto);
		worker.addPropertyChangeListener(guiModel);
		worker.execute();
		
	
		
	}
	
	private static String timerCalc(Date before, Date after){
		long diff = (after.getTime() - before.getTime())/1000;

		int hours = (int)(diff / 60 / 60);
		diff-= hours*60*60;
		int mins  = (int)diff/60;
		diff-= mins*60;
		int secs   = (int)diff;
		return new String(hours +"h "+mins+"' "+secs+"''");
	}
	
	
	
	public interface ProgressListener{
		void setMyProgress(int percent);
	}
	
	public class LoadTask extends SwingWorker<Void, Void>{

		
		private File assocFile;
		private File ontoFile;

		public LoadTask(File assocFile, File ontoFile){
			this.assocFile = assocFile;
			this.ontoFile = ontoFile;
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
			
			//load and calculate BiNGO stuff
			BiNGO b = new BiNGO(modnet, assocFile, ontoFile.getAbsolutePath(), 
					"0.05", false, false, null, "biological_process"); 
//			//for now, we just use an empty allNodes set
			HashSet<String> allNodes = new HashSet<String>();
			b.GOmodules(allNodes, null);
			
			
			setProgress(30);

			return null;
		}
		
		
		
		@Override
		protected void done() {
			guiModel.showProgressBar(false);
			guiModel.refresh();
		}
	}


}
