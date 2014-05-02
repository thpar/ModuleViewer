package be.ugent.psb.moduleviewer.testing;

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
