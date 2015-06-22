package be.ugent.psb.moduleviewer;

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

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 * Implementation of mouse dragging events
 * 
 * @author Thomas Van Parys
 *
 */
public class DragMoveListener implements MouseListener, MouseMotionListener{
	private final Rectangle rect = new Rectangle();
	private final JViewport vport;
	
	private Point startPt = new Point();
	private Point move  = new Point();
	private JComponent comp;

	public DragMoveListener(JViewport vport, JComponent comp) {
		this.vport = vport;
		this.comp = comp;
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		Point pt = e.getPoint();
		move.setLocation(pt.x-startPt.x, pt.y-startPt.y);
		startPt.setLocation(pt);
		Rectangle vr = vport.getViewRect();
		int w = vr.width;
		int h = vr.height;
        Point ptZero = SwingUtilities.convertPoint(vport,0,0,comp);
		rect.setRect(ptZero.x-move.x, ptZero.y-move.y, w, h);
		comp.scrollRectToVisible(rect);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startPt.setLocation(e.getPoint());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
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

