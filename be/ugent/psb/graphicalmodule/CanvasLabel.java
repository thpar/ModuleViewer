package be.ugent.psb.graphicalmodule;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import be.ugent.psb.modulegraphics.clickable.ElementEventPassThrough;
import be.ugent.psb.modulegraphics.elements.Canvas;

public class CanvasLabel extends JLabel{

	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private Color background = Color.WHITE;
	private ImageIcon splash;
	
	public CanvasLabel(){
	}
	public CanvasLabel(Canvas canvas){
		setCanvas(canvas);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
		canvas.setContainer(this);
		for (MouseListener ml : getMouseListeners()){
			if (ml instanceof ElementEventPassThrough){
				this.removeMouseListener(ml);
			}
		}
		this.addMouseListener(new ElementEventPassThrough(canvas));
		this.repaint();
	}

	public void setBackground(Color background) {
		this.background = background;
	}


	@Override
	protected void paintComponent(Graphics g) {
		if (canvas!=null){
			g.setColor(background );
			g.fillRect(0, 0, canvas.getDimension(g).width, canvas.getDimension(g).height);
			canvas.paint(g, 0, 0);
		} else {
			splash.paintIcon(this, g, 0, 0);
		}
	}

	public ImageIcon getSplash() {
		return splash;
	}

	public void setSplash(ImageIcon splash) {
		this.splash = splash;
	}
	
	
}
