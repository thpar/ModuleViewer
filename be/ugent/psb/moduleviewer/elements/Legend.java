package be.ugent.psb.moduleviewer.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.ColorMatrix;
import be.ugent.psb.modulegraphics.elements.Label;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.LabelList.Direction;
import be.ugent.psb.modulegraphics.elements.PassThroughColorizer;
import be.ugent.psb.modulegraphics.elements.Spacer;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.ModuleNetwork.LegendKeyColorPair;

public class Legend extends Canvas {
	
	
	public Legend(ModuleNetwork modnet, int blockID){		
		Set<String> labels = modnet.getLegendLabels(blockID);
				
		for (String label : labels){
			Canvas labelCanvas = new Canvas();
			Label labelElement = new Label(label);
			labelElement.setFont(new Font("SansSerif", Font.BOLD, 14));
			labelElement.setMargin(10, 0);
			labelElement.setAlignment(Alignment.TOP_CENTER);
			labelCanvas.add(labelElement);
			labelCanvas.newRow();
			

			Canvas matrixCanvas = new Canvas();
			List<LegendKeyColorPair> entries = modnet.getLegendEntries(blockID, label);
			List<String> keyList = new ArrayList<String>();
			Color[][] colorList = new Color[entries.size()][1];
			int count = 0;
			for (LegendKeyColorPair entry : entries){
				keyList.add(entry.key);
				colorList[count++][0] = entry.color;
			}
			LabelList labelList = new LabelList(keyList);
			labelList.setDirection(Direction.TOP_TO_BOTTOM);
			labelList.setMargin(0, 5, 0, 0);
			matrixCanvas.add(labelList);
			
			ColorMatrix<Color> colorMatrix = new ColorMatrix<>(colorList, new PassThroughColorizer());
			matrixCanvas.add(colorMatrix);
			
			
			labelCanvas.add(matrixCanvas);
			this.add(labelCanvas);
			this.add(new Spacer(new Dimension(20,20)));
			
			
		}

	}
	
	
}
