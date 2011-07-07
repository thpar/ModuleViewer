package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import be.ugent.psb.modulegraphics.display.CanvasFigure.OutputFormat;
import be.ugent.psb.moduleviewer.model.GUIModel;

/**
 * Loads an MVF file. These files represent several kinds of annotations for 
 * genes and conditions.
 * 
 * @author thpar
 *
 */
public class SetOutputFormatAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private OutputFormat format;

	
	public SetOutputFormatAction(GUIModel guiModel, OutputFormat format){
		super(format.toString());
		this.format = format;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.setOutputFormat(format);
	}
	
	
	
}
