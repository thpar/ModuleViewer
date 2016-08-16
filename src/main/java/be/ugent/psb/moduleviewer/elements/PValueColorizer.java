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

/**
 * Calculate the colors for p-values: high p-values are lightly colored (blue for underrepresentation and yellow for overrepresentation)
 * and get brighter towards 0.
 * 
 * @author Thomas Van Parys
 *
 */
public class PValueColorizer extends ExpressionColorizer{

	
	private double sigma;
	private double mean;
	
		
	/**
	 * Initialize an {@link ExpressionColorizer} using given sigma and mean.
	 * 
	 * @param sigma
	 * @param mean
	 */
	public PValueColorizer(double sigma, double mean) {
		this.sigma = sigma;
		this.mean = mean;
	}
	

	@Override
	public Color getColor(Double data) {
		if (Double.isNaN(data))	return Color.WHITE;
		
		Color col;
		float redperc, blueperc, greenperc;
		
		if (data<0){
			blueperc = 1;
			redperc = new Float(downProb(data, mean, sigma));
			greenperc = redperc;
		} else {
			redperc = 1;
			greenperc = 1;
			blueperc = new Float(upProb(data, mean, sigma));
		}
		
		col = new Color(redperc, greenperc, blueperc);

		return col;
	}
	
	private double upProb (double x, double mean, double sigma){
	    double prob=1;
	    if (x <= 2*sigma){
	    	prob = 1-Math.exp(-Math.pow((x-mean)/sigma, 2));
	    }
	    return prob;
	}
	
	private double downProb (double x, double mean, double sigma){
        double prob=1;
	    if (x >= -2*sigma){	
	    	prob = 1 - Math.exp(-Math.pow((x-mean)/sigma, 2));
	    }
	    return prob;
	}
	

}
