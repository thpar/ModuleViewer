package be.ugent.psb.moduleviewer.elements;

/*
 * #%L
 * ModuleViewer
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.ColorMatrix;
import be.ugent.psb.modulegraphics.elements.Label;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.LabelList.Direction;
import be.ugent.psb.modulegraphics.elements.PassThroughColorizer;
import be.ugent.psb.modulegraphics.elements.Spacer;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.ModuleNetwork.LegendKeyColorPair;

/**
 * Legend drawn underneath the figure
 * 
 * @author Thomas Van Parys
 *
 */
public class Legend extends Canvas {
	
	
	public Legend(ModuleNetwork modnet, int blockID){		
		List<String> labels = modnet.getLegendLabels(blockID);
				
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
