package be.ugent.psb.moduleviewer.elements;

import java.awt.Color;

public class EnigmaColorizer extends ExpressionColorizer{

	
	private double sigma;
	private double mean;
	
	
	public EnigmaColorizer(double sigma, double mean) {
		this.sigma = sigma;
		this.mean = mean;
	}


	@Override
	public Color getColor(Double data) {
		if (Double.isNaN(data))	return Color.WHITE;
		
		Color col;
		float blueperc, yellowperc;
		blueperc = new Float(upProb(data,mean,sigma));
		yellowperc = new Float(downProb(data,mean,sigma));

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
