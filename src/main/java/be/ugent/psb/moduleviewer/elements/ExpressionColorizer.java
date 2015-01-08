package be.ugent.psb.moduleviewer.elements;

import java.awt.Color;

import be.ugent.psb.modulegraphics.elements.Colorizer;

abstract public class ExpressionColorizer implements Colorizer<Double> {

	@Override
	abstract public Color getColor(Double element);


}
