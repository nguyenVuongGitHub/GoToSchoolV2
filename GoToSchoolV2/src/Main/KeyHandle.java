/**
 * 
 * this class handle the key broad.
 * 
 */
package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandle implements KeyListener{

	private boolean downPress, upPress, leftPress, rightPress, escPress, enterPress;
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPress = true;
		}
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPress = true;
		}
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPress = true;
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPress = true;
		}
		if(code == KeyEvent.VK_ESCAPE) {
			escPress = true;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPress = true;
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPress = false;
		}
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPress = false;
		}
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPress = false;
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPress = false;
		}
//		if(code == KeyEvent.VK_ESCAPE) {
//			escPress = false;
//		}
		if(code == KeyEvent.VK_ENTER) {
			enterPress = false;
		}
	}

	public boolean isDownPress() {
		return downPress;
	}

	public boolean isUpPress() {
		return upPress;
	}

	public boolean isLeftPress() {
		return leftPress;
	}

	public boolean isRightPress() {
		return rightPress;
	}

	public boolean isEscPress() {
		return escPress;
	}

	public boolean isEnterPress() {
		return enterPress;
	}

}
