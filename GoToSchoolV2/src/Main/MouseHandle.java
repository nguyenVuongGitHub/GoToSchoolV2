package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseHandle implements MouseListener, MouseWheelListener, MouseMotionListener{
	
	boolean mouseLeftPress, mouseRightPress, mouseWhellUp, mouseWhellDown;
	
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
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
