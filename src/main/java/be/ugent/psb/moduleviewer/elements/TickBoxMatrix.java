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

import java.util.List;

import be.ugent.psb.modulegraphics.elements.Canvas;

/**
 * The tickbox matrix lays out a list of {@link TickBoxColumn}}
 * 
 * @author Thomas Van Parys
 *
 */
public class TickBoxMatrix extends Canvas{


	public TickBoxMatrix(List<TickBoxColumn> cols) {
		for (TickBoxColumn col : cols){
			this.add(col);
		}
	}
}
