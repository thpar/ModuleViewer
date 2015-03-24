package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;

/**
 * 
 * Safes the 'session' to a file.
 * A session should consist of all loaded files and the GUIModel settings.
 * 
 * @author thpar
 *
 */
public class SaveSessionAsAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private Model model;

	
	public SaveSessionAsAction(Model model, GUIModel guiModel){
		super("Save session as...");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Save session as...");
		int answer = fc.showSaveDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			final File file = fc.getSelectedFile();	
			try {
				writeToFile(file);
				guiModel.addRecentSession(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

	private void writeToFile(File file) throws IOException{
		Properties props = new Properties();
		FileWriter fw = new FileWriter(file);
		
		props.setProperty("data", model.getDataFile());
		props.setProperty("genes", model.getGeneFile());
		
		if (model.getSymbolMappingFile() !=null){
			props.setProperty("symbols", model.getSymbolMappingFile());			
		}
		if (model.getRegulatorFile()!=null){
			props.setProperty("regulators", model.getRegulatorFile());			
		}
		if (model.getConditionFile()!=null){
			props.setProperty("conditions", model.getConditionFile());			
		}
		
		props.setProperty("show_tree", guiModel.isDrawTreeStructure()?"On":"Off");
		props.setProperty("show_cond_labels", guiModel.isDrawConditionLabels()?"On":"Off");
		
		
		String anFileNames = new String();
		for (Iterator<String> it = model.getAnnotationFiles().iterator(); it.hasNext();){
			anFileNames = anFileNames.concat(it.next());
			if (it.hasNext()){
				anFileNames = anFileNames.concat(";");
			}
		}
		props.setProperty("annotations", anFileNames);
		
		//TODO store GUIModel settings on later point
		
		props.store(fw, "ModuleViewer");
	}
	
	
	

}