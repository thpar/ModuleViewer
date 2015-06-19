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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import be.ugent.psb.modulegraphics.display.CanvasLabel;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element.Alignment;
import be.ugent.psb.modulegraphics.elements.Rectangle;
import be.ugent.psb.modulegraphics.elements.Spacer;
import be.ugent.psb.moduleviewer.elements.TickBoxColumn;
import be.ugent.psb.moduleviewer.elements.TickBoxMatrix;
import be.ugent.psb.moduleviewer.elements.Title;
import be.ugent.psb.moduleviewer.elements.Title.Angle;
import be.ugent.psb.moduleviewer.model.Gene;

public class CanvasTest implements MouseListener {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CanvasTest test = new CanvasTest();
//		test.showWindow();
		test.clickTest();
	}




//	private GeneNames gn;

	
	
	
	private void clickTest() {

		Gene a = new Gene("something");
		Gene b = new Gene("blablabalb");
		Gene c = new Gene("ATH6564");
		Gene d = new Gene("sheep");
		Gene e = new Gene("onetwoone");
		Gene f = new Gene("tadtaag");


		List<Gene> genes = new ArrayList<Gene>();
		genes.add(a);
		genes.add(b);
		genes.add(c);
		genes.add(d);
		genes.add(e);
		genes.add(f);
		
		
		List<Gene> checked1 = new ArrayList<Gene>();
		checked1.add(b);
		checked1.add(e);
		
		List<Gene> checked2 = new ArrayList<Gene>();
		checked2.add(a);
		checked2.add(b);
		checked2.add(d);
		
		List<Gene> checked3 = new ArrayList<Gene>();
		checked3.add(f);
		checked3.add(e);
		
		
		TickBoxColumn tbc1 = new TickBoxColumn(genes, checked1, Color.RED);
		TickBoxColumn tbc2 = new TickBoxColumn(genes, checked2, Color.GREEN);
		TickBoxColumn tbc3 = new TickBoxColumn(genes, checked3, Color.BLUE);
		
		List<TickBoxColumn> tbcList = new ArrayList<TickBoxColumn>();
		tbcList.add(tbc1);
		tbcList.add(tbc2);
		tbcList.add(tbc3);
		
		TickBoxMatrix tbcs = new TickBoxMatrix(tbcList);
		
		
		
		Canvas main = new Canvas();
		
		Title title = new Title("Some nice title"); 
		title.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				System.out.println("Title clicked! "+((Title)e.getSource()).getTitle());
			}

		});
		
		
//		gn = new GeneNames(genes);
//		gn.addMouseListener(this);
		
//		gn.highlightGene(b, true);
//		gn.highlightGene(d, true);
//		
		main.add(title);

		
//		main.add(gn);
		main.add(tbcs);
		
		main.newRow();
		

		showIt(main);
		
	}




	/**
	 * @deprecated This test is currently not up to date with the existing API.
	 */
	@Deprecated
	private void showWindow() {
		//make a title
		Canvas canTitle = new Canvas();
		Title title = new Title("myModule_0");
		title.setMargin(0,10,0,0);
		title.setAngle(Angle.VERTICAL);
		canTitle.add(title);
		
		//some more stuff for the content
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("kjghkjghkjghkj");
		labels.add("lkjfmlsfjlmsdfjmdkk");
		labels.add("kjghkjghkjghkj");
		labels.add("kjghkjghk");
		labels.add("kjghkjghkjghkjslj");
		labels.add("mlkùjmljmlkjmljk");
		labels.add("mkfhkmzfhskljhlhjkh");
		
		//a matrix 
		Boolean[][] data = new Boolean[7][7];
		data[0][0] = false;data[0][1] = false;data[0][2] = true;data[0][3] = true;data[0][4] = false;data[0][5] = false;data[0][6] = false;
		data[1][0] = true;data[1][1] = true;data[1][2] = false;data[1][3] = false;data[1][4] = false;data[1][5] = false;data[1][6] = true;
		data[2][0] = true;data[2][1] = true;data[2][2] = false;data[2][3] = false;data[2][4] = false;data[2][5] = true;data[2][6] = false;
		data[3][0] = true;data[3][1] = false;data[3][2] = false;data[3][3] = true;data[3][4] = false;data[3][5] = true;data[3][6] = false;
		data[4][0] = false;data[4][1] = true;data[4][2] = false;data[4][3] = true;data[4][4] = true;data[4][5] = true;data[4][6] = false;
		data[5][0] = false;data[5][1] = true;data[5][2] = false;data[5][3] = true;data[5][4] = true;data[5][5] = true;data[5][6] = false;
		data[6][0] = false;data[6][1] = true;data[6][2] = false;data[6][3] = true;data[6][4] = true;data[6][5] = true;data[6][6] = false;
		
		//the actual content
		Canvas canContent = new Canvas();
		canContent.setHorizontalSpacing(5);
		canContent.setVerticalSpacing(5);

		canContent.add(new Rectangle(new Dimension(7, 4), Color.GREEN));
		canContent.add(new Rectangle(new Dimension(7, 3), Color.MAGENTA));
		canContent.getLastAddedElement().setAlignment(Alignment.TOP_CENTER);
//		canContent.add(new GOMatrix(data));
		canContent.getLastAddedElement().setAlignment(Alignment.BOTTOM_CENTER);
//		GeneNames geneNames = new GeneNames(labels);
//		canContent.add(geneNames);
//		geneNames.setAlignment(Alignment.BOTTOM_CENTER);
		
		
		canContent.newRow();
		canContent.add(new Rectangle(new Dimension(5, 3), Color.ORANGE));
		canContent.getLastAddedElement().setAlignment(Alignment.CENTER);
		canContent.add(new Rectangle(new Dimension(6, 3), Color.DARK_GRAY));
//		canContent.add(new Spacer());
		canContent.add(new Rectangle(new Dimension(5, 3), Color.ORANGE));
		canContent.newRow();
//		canContent.add(new GOMatrix(data));
		canContent.add(new Rectangle(new Dimension(7, 2), Color.YELLOW));
		canContent.getLastAddedElement().setAlignment(Alignment.BOTTOM_CENTER);
		canContent.add(new Rectangle(new Dimension(7, 5), Color.YELLOW));
		canContent.getLastAddedElement().setAlignment(Alignment.BOTTOM_LEFT);
		
		canContent.newRow();
//		ConditionLabels condLabels = new ConditionLabels(labels);
//		canContent.add(condLabels);
		
//		canContent.add(new Spacer());
		
//		GOLabels goLabels1 = new GOLabels(labels);
//		canContent.add(goLabels1);
		//don't let the skewed labels push the other Elements to the side
//		goLabels1.setPushBounds(false);
		
		//some more labels, just for fun
		//here we let them push the side of the image
//		GOLabels goLabels2 = new GOLabels(labels);
//		canContent.add(goLabels2);

		
		
		//a footer
		Canvas canFooter = new Canvas();
		canFooter.add(new Rectangle(new Dimension(15, 2), Color.GRAY));
		canFooter.getLastAddedElement().setMargin(20, 0);
		canFooter.newRow();
		
		//and wrap it all into the main canvas to be drawn.
		Canvas canMain = new Canvas();
		canMain.add(canTitle); //canMain.newRow();
		canMain.add(canContent);
		canMain.newRow();
		canMain.add(new Spacer());
		canMain.add(canFooter);
		canMain.setMargin(20);
		
		canMain.setUnit(new Dimension(20, 30));
		
//		canMain.setHorizontalSpacing(10);
//		canMain.setVerticalSpacing(10);
		
		
		showIt(canMain);
		
	}
	
	private void showIt(Canvas canvas){
		//put this Canvas on a JLabel and wrap it into a JFrame
		CanvasLabel painting = new CanvasLabel(canvas);
//		painting.setCanvas(canvas);
		
		JFrame window = new JFrame("Look at this new canvas");
		JPanel pane = new JPanel();
		pane.add(painting);
		window.setContentPane(pane);
		//the painting will only return a valid Graphics env after packing.
		window.pack();
		painting.setPreferredSize(canvas.getDimension(painting.getGraphics()));
		//repack to adjust to the correct painting size
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
	}




	@Override
	public void mouseClicked(MouseEvent e) {
//		Gene g = gn.getHitGene(e.getX(), e.getY());
//		gn.toggleHighlightGene(g);
		
	}




	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
