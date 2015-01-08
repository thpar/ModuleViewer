package be.ugent.psb.moduleviewer.elements;

import java.util.List;

import be.ugent.psb.modulegraphics.elements.Canvas;

/**
 * The tickbox matrix lays out a list of {@link TickBoxColumn}}
 * 
 * @author thpar
 *
 */
public class TickBoxMatrix extends Canvas{


	public TickBoxMatrix(List<TickBoxColumn> cols) {
		for (TickBoxColumn col : cols){
			this.add(col);
		}
	}
}
