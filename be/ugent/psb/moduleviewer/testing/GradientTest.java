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
		
		Gradient g = new Gradient(new EnigmaColorizer(0.2, -0.5));
		
		g.setWidth(20);
		g.addCheckPoint("min", -2);
		g.addCheckPoint("avg", 0);
		g.addCheckPoint("max", 2);
		
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
