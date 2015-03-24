package be.ugent.psb.moduleviewer.elements;

import java.awt.Color;

public class EnigmaColorizer extends ExpressionColorizer{

	
	private double sigma;
	private double mean;
	
	private Scale scale = Scale.BLUE_TO_YELLOW;
	
	/**
	 * For low expression to high, make the scale blue to yellow or the
	 * other way around?
	 * 
	 * @author Thomas Van Parys
	 *
	 */
	enum Scale{
		YELLOW_TO_BLUE, BLUE_TO_YELLOW;
	}
	
	/**
	 * Initialize an {@link ExpressionColorizer} using given sigma and mean. The scale determines 
	 * whether expression levels from low to high are drawn as blue to yellow or the other way around.
	 * 
	 * @param sigma
	 * @param mean
	 * @param scale
	 */
	public EnigmaColorizer(double sigma, double mean, Scale scale) {
		this.sigma = sigma;
		this.mean = mean;
		this.scale = scale;
	}
	
	/**
	 * Initialize an {@link ExpressionColorizer} using given sigma and mean.
	 * Expression levels are drawn by default as blue (low) to yellow (high)
	 * 
	 * @param sigma
	 * @param mean
	 */
	public EnigmaColorizer(double sigma, double mean) {
		this.sigma = sigma;
		this.mean = mean;
	}
	

	@Override
	public Color getColor(Double data) {
		if (Double.isNaN(data))	return Color.WHITE;
		
		Color col;
		float blueperc, yellowperc;
		float up, down;
		up = new Float(upProb(data,mean,sigma));
		down= new Float(downProb(data,mean,sigma));
		
		switch(scale){
		default:
		case BLUE_TO_YELLOW:
			blueperc = down;
			yellowperc = up;
			break;
		case YELLOW_TO_BLUE:
			blueperc = up;
			yellowperc = down;
			break;
		}
		
		col = new Color(yellowperc, yellowperc, blueperc);

		return col;
	}
	
	private double upProb (double x, double mean, double sigma){
	    double prob=0.0;
	    if (x >= mean )
	    	prob = 1-Math.exp(-Math.pow((x-mean)/sigma, 2));
	    return prob;
	}
	
	private double downProb (double x, double mean, double sigma){
            double prob=0.0;
	    if (x <= mean )
	    	prob = 1-Math.exp(-Math.pow((x-mean)/sigma, 2));
	    return prob;
	}
	

}