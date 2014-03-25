package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.parsers.SymbolicNameParser;

/**
 * Runs over all modules and sorts genes alphabetically within their tree structure.
 * 
 * @author Thomas Van Parys
 *
 */
public class SortGenesAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private Model model;

	
	public SortGenesAction(Model model, GUIModel guiModel){
		super("Sort genes alphabetically");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
			
		LoadTask worker = new LoadTask();
		worker.addPropertyChangeListener(guiModel);
		worker.execute();
			
	}
	
	public class LoadTask extends SwingWorker<Void, Void>{
		
		
		@Override
		protected Void doInBackground() throws Exception {			
			guiModel.showProgressBar(true);
			guiModel.setStateString("Sorting genes");
			
			setProgress(0);
			Collection<Module> modules = model.getModnet().getModules();
			int prog = 0;
			int step = 100/modules.size();
			
			for (Module module : modules){
				module.getGeneTree().sortLeavesAlphabetically();
				
				prog+=step;
				setProgress(prog);
			}
			
			setProgress(100);
			return null;
		}
		
		@Override
		protected void done() {
			guiModel.clearStateString();
			guiModel.showProgressBar(false);
			guiModel.refresh();
		}
	}
	

}
