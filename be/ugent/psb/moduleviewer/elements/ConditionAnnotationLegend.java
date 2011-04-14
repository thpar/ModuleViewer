package be.ugent.psb.moduleviewer.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.ModuleNetwork.ConditionClassification;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Colorizer;
import be.ugent.psb.modulegraphics.elements.Label;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.LabelList.Direction;
import be.ugent.psb.modulegraphics.elements.Matrix;

public class ConditionAnnotationLegend extends Canvas implements Colorizer<String>{

	
	private ConditionClassification classification;
	private List<String> exclude;

	/**
	 * 
	 * @param classification the classification object which maps conditions to a class, a property and a color.
	 * @param exclude a list of properties that shouldn't show up in the legend
	 */
	public ConditionAnnotationLegend(ConditionClassification classification, List<String> exclude){
		this.classification = classification;
		this.exclude = exclude;
		compose();
	}
	
	/**
	 * 
	 * @param classification the classification object which maps conditions to a class, a property and a color.
	 */
	public ConditionAnnotationLegend(ConditionClassification classification) {
		this(classification, new ArrayList<String>());
	}

	private void compose() {
		Canvas innerCanvas = new Canvas();
		innerCanvas.setMargin(5);
		innerCanvas.setHorizontalSpacing(10);
		
		for (String clas : classification.getClasses()){
			Canvas classCanvas = new Canvas();
			classCanvas.setHorizontalSpacing(5);
			classCanvas.add(new Label(clas, new Font("SansSerif", Font.BOLD, 12)));
			classCanvas.getLastAddedElement().setBottomMargin(5);
			classCanvas.newRow();
			List<String> props = new ArrayList<String>();
			for (String prop : classification.getProperties(clas)){
				if (!exclude.contains(prop)){
					props.add(prop);
				}
			}
			String[][] data = new String[props.size()][1];
			int i =0;
			for (String prop : props){
				data[i++][0] = prop;
			}
			LabelList propLabels = new LabelList(props);
			propLabels.setDirection(Direction.TOP_TO_BOTTOM);
			propLabels.setAlignment(Alignment.CENTER_LEFT);
			
			Matrix<String> legend = new Matrix<String>(data, this);
			
			classCanvas.add(propLabels);
			classCanvas.add(legend);
			
			innerCanvas.add(classCanvas);
			
		}
		
		this.add(innerCanvas);
	}

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		//draw content of this canvas
		Dimension drawnDim = super.paintElement(g, xOffset, yOffset);
		
		//add a border
		g.setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);
		g.drawRect(xOffset, yOffset, drawnDim.width, drawnDim.height);
		
		return drawnDim;
	}

	@Override
	public Color getColor(String prop) {
		return classification.getPropertyColor(prop);
	}
	
	
	
}
