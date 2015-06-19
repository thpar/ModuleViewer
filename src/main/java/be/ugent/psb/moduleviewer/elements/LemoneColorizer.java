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


public class LemoneColorizer extends ExpressionColorizer {
	
	private double mean;
	private double sigma;
	
	/**
	 * 
	 * @param mean
	 * @param sigma
	 */
	public LemoneColorizer(double mean, double sigma){
		this.mean = mean;
		this.sigma = sigma;
	}
	
	@Override
	public Color getColor(Double value) {
		if (value==null || Double.isNaN(value)) {
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
