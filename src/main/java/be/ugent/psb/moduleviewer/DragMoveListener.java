package be.ugent.psb.moduleviewer;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

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

