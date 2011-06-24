package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.ConditionTreeParser;
import be.ugent.psb.moduleviewer.parsers.DataMatrixParser;
import be.ugent.psb.moduleviewer.parsers.GeneTreeParser;
import be.ugent.psb.moduleviewer.parsers.MVFParser;
import be.ugent.psb.moduleviewer.parsers.RegulatorTreeParser;

/**
 * 
 * Safes the 'session' to a file.
 * A session should consist of all loaded files and the GUIModel settings.
 * 
 * @author thpar
 *
 */
public class LoadSessionAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private Model model;

	
	public LoadSessionAction(Model model, GUIModel guiModel){
		super("Load session...");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load session...");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			final File file = fc.getSelectedFile();	
			try {
				loadFromFile(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

	private void loadFromFile(File file) throws IOException{
		Properties props = new Properties();
		
		FileReader fr = new FileReader(file);
		props.load(fr);
		
		DataMatrixParser dmp = new DataMatrixParser();
		dmp.parse(model, new File(props.getProperty("data")));
		
		GeneTreeParser gtp   = new GeneTreeParser();
		gtp.parse(model, new File(props.getProperty("genes")));
		
		ConditionTreeParser ctp = new ConditionTreeParser();
		ctp.parse(model, new File(props.getProperty("conditions")));
		
		String regTreeFile = props.getProperty("regulators");
		if (regTreeFile != null){
			RegulatorTreeParser rtp = new RegulatorTreeParser();
			rtp.parse(model, new File(regTreeFile));
		}
		
		String mvfFileString = props.getProperty("annotations");
		if (mvfFileString != null){
			String[] mvfFiles = mvfFileString.split(";");
			for (String mvf : mvfFiles){
				MVFParser mvfp = new MVFParser();
				mvfp.parse(model, new File(mvf));
			}
		}
		
		guiModel.refresh();
		
		
	}
	
	
	

}
