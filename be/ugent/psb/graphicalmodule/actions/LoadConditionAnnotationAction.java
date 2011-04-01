package be.ugent.psb.graphicalmodule.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import be.ugent.psb.ModuleNetwork.ConditionClassification;
import be.ugent.psb.ModuleNetwork.ModuleNetwork;
import be.ugent.psb.ModuleNetwork.parsers.ConditionMapParser;
import be.ugent.psb.graphicalmodule.GraphicalModuleModel;

/**
 * 
 * @author thpar
 *
 */
public class LoadConditionAnnotationAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private ModuleNetwork modnet;
	private GraphicalModuleModel guiModel;

	
	public LoadConditionAnnotationAction(ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super("Load condition annotation...");
		this.modnet = modnet;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load condition annotation");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			try {
				ConditionClassification condMap = ConditionMapParser.load(modnet, file);
				condMap.addFixedColorMapping("CTRL", Color.WHITE);
				condMap.addFixedColorMapping("ctrl", Color.WHITE);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			guiModel.refresh();
			
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

}
