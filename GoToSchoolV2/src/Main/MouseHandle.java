package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseHandle implements MouseListener, MouseWheelListener, MouseMotionListener{

	boolean mouseLeftPress, mouseRightPress, mouseWhellUp, mouseWhellDown;
	boolean mouseLeftClick, mouseRightClick;

	int worldX, worldY;
	public int getWorldX() {
		return worldX;
	}
	public int getWorldY() {
		return worldY;
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	    int rotation = e.getWheelRotation();
	    if (rotation < 0) {
	        mouseWhellUp = true;
	        mouseWhellDown = false; // Reset to false to ensure only one direction is true at a time
	    } else if (rotation > 0) {
	        mouseWhellDown = true;
	        mouseWhellUp = false; // Reset to false to ensure only one direction is true at a time
	    }
	    else {
	        // If rotation is 0, neither up nor down
	        mouseWhellUp = false;
	        mouseWhellDown = false;
	    }
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int code  = e.getButton();
		if(MouseEvent.BUTTON1 == code) {
			mouseLeftPress = true;
		}
		if(MouseEvent.BUTTON3 == code) {
			mouseRightPress = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int code  = e.getButton();
		if(MouseEvent.BUTTON1 == code) {
			mouseLeftPress = false;
		}
		if(MouseEvent.BUTTON3 == code) {
			mouseRightPress = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub


	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int code  = e.getButton();
		if(MouseEvent.BUTTON1 == code) {
			mouseLeftClick = true;
		}
		if(MouseEvent.BUTTON3 == code) {
			mouseRightClick = true;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		worldX = e.getX();
		worldY = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public boolean isMouseRightPress() {
		return mouseRightPress;
	}
	public boolean isMouseLeftPress() {
		return mouseLeftPress;
	}
	public boolean isMouseWhellUp() {
		return mouseLeftPress;
	}
	public boolean isMouseWhellDown() {
		return mouseLeftPress;
	}
	public boolean isMouseLeftClick() {
		return mouseLeftClick;
	}
	public boolean isMouseRightClick() {
		return mouseRightClick;
	}

	public void setMouseLeftClick(boolean mouseLeftClick) {
		this.mouseLeftClick = mouseLeftClick;
	}

	public void setMouseRightClick(boolean mouseRightClick) {
		this.mouseRightClick = mouseRightClick;
	}
}
