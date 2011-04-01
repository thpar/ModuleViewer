package be.ugent.psb.graphicalmodule.elements;

import java.awt.Color;

import be.ugent.psb.modulegraphics.elements.Colorizer;


public class ExpressionColorizer implements Colorizer<Double> {
	
	private double mean;
	private double sigma;
	
	/**
	 * 
	 * @param mean
	 * @param sigma
	 */
	public ExpressionColorizer(double mean, double sigma){
		this.mean = mean;
		this.sigma = sigma;
	}
	
	@Override
	public Color getColor(Double value) {
		if (value==null || Double.isNaN(value)) {
			System.err.println("No data?");
			return Color.WHITE;
		}
		
		float yellowPerc=0.5F, bluePerc=0.5F;
		double x;

		if (value <= mean)
			x = Math.max((value - mean)/(1.5 * sigma), -0.5);
		else
			x = Math.min((value - mean)/(1.5 * sigma), 0.5);
		
		yellowPerc = new Float(0.5 + x);
		bluePerc = 1.0F - yellowPerc;	
	
		return new Color(yellowPerc, yellowPerc, bluePerc);
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}
	
	
}
