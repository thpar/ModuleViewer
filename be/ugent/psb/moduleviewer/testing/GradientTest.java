package be.ugent.psb.moduleviewer.testing;

import java.awt.Dimension;

import javax.swing.JFrame;

import be.ugent.psb.modulegraphics.display.CanvasLabel;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Gradient;
import be.ugent.psb.moduleviewer.elements.EnigmaColorizer;

public class GradientTest {

	public static void main(String[] args) {
		GradientTest test = new GradientTest();
		test.run();
	}
	
	
	public void run(){
		Canvas c = new Canvas();
		
		double sigma = 0.7;
		double mean = 0.0;
		double min = -2;
		double max = 2;
		
		Gradient g = new Gradient(new EnigmaColorizer(sigma, mean));
		
		g.setWidth(20);
		g.addCheckPoint(String.valueOf(min), min);
		g.addCheckPoint(String.valueOf(mean), mean);
		g.addCheckPoint(String.valueOf(max), max);
		
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
