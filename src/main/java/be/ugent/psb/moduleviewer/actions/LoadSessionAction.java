package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
	protected GUIModel guiModel;
	protected Model model;

	
	public LoadSessionAction(Model model, GUIModel guiModel){
		this("Load session...", model, guiModel);
	}
	public LoadSessionAction(String label, Model model, GUIModel guiModel){
		super(label);
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
				guiModel.addRecentSession(file);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(guiModel.getTopContainer(), 
						"Could not load session file: "+file,
						"Parsing error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

	protected void loadFromFile(File file) throws IOException{
		Properties props = new Properties();
		
		FileReader fr = new FileReader(file);
		props.load(fr);
		
		DataMatrixParser dmp = new DataMatrixParser();
		File dmpFile = new File(props.getProperty("data"));
		dmp.parse(model, dmpFile);
		model.setDataFile(dmpFile.getAbsolutePath());
		
		GeneTreeParser gtp   = new GeneTreeParser();
		File gtpFile = new File(props.getProperty("genes"));
		gtp.parse(model, gtpFile);
		model.setGeneFile(gtpFile.getAbsolutePath());
		
		ConditionTreeParser ctp = new ConditionTreeParser();
		File ctpFile = new File(props.getProperty("conditions"));
		ctp.parse(model, ctpFile);
		model.setConditionFile(ctpFile.getAbsolutePath());
		
		String regTreeFile = props.getProperty("regulators");
		if (regTreeFile != null){
			RegulatorTreeParser rtp = new RegulatorTreeParser();
			rtp.parse(model, new File(regTreeFile));
			model.setRegulatorFile(regTreeFile);
		}
		
		String mvfFileString = props.getProperty("annotations");
		if (mvfFileString != null && !mvfFileString.isEmpty()){
			String[] mvfFiles = mvfFileString.split(";");
			for (String mvf : mvfFiles){
				MVFParser mvfp = new MVFParser();
				mvfp.parse(model, new File(mvf));
				model.addAnnotationFile(mvf);
			}
		}
		
		guiModel.refresh();
		
		
	}
	
	
	

}
