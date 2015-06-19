package be.ugent.psb.moduleviewer.testing;

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

import java.awt.Dimension;

import javax.swing.JFrame;

import be.ugent.psb.modulegraphics.display.CanvasLabel;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.LegendGradient;
import be.ugent.psb.moduleviewer.elements.EnigmaColorizer;

public class GradientTest {

	public static void main(String[] args) {
		GradientTest test = new GradientTest();
		test.run();
	}
	
	
	public void run(){
		Canvas c = new Canvas();
		
		double sigma = 0.3;
		double mean = 0.5;
		double min = -2;
		double max = 2;
		
		LegendGradient g = new LegendGradient(min, max, new EnigmaColorizer(sigma, mean));
		g.setWidth(20);
		g.addLabel(mean);

		
		c.add(g);
		
		JFrame frame = new JFrame("Test Gradient");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CanvasLabel label = new CanvasLabel(c);
		frame.add(label);
		
		frame.pack();
		frame.setVisible(true);
		frame.setSize(new Dimension(250,200));
		
	}

}
