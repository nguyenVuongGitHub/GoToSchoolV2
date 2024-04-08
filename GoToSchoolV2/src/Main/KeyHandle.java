/**
 * 
 * this class handle the key broad.
 * 
 */
package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class KeyHandle implements KeyListener{

	GameState gs;
	private boolean downPress, upPress, leftPress, rightPress, escPress, enterPress, spacePress, tabPress;
	private boolean skill1Press,skill2Press,skill3Press;
	public KeyHandle(GameState gs) {
		this.gs = gs;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if(gs.state == State.CAMPAIGN) {
			if(gs.campaign.isShowDialog()) { // dialog
				if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					gs.campaign.setChoose(gs.campaign.getChoose()-1);
					if(gs.campaign.getChoose() < 1) {
						gs.campaign.setChoose(gs.user.getNumberSkillsUnlocked());
					}
				}
				if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gs.campaign.setChoose(gs.campaign.getChoose()+1);
					if(gs.campaign.getChoose() > gs.user.getNumberLeversUnlocked()) {
						gs.campaign.setChoose(1);
					}
				}
				if(code == KeyEvent.VK_ENTER) {
					gs.campaign.loadMap(gs.campaign.getChoose());
					gs.campaign.setShowDialog(false);
					if(gs.campaign.getChoose() == 1) {
						gs.player.setWorldX(5*gs.getTile());
						gs.player.setWorldY(59*gs.getTile());
					}else if(gs.campaign.getChoose() == 2) {
						gs.player.setWorldX(24*gs.getTile());
						gs.player.setWorldY(30*gs.getTile());
					}else if(gs.campaign.getChoose() == 3) {
//                		gs.player.setWorldX(5*gs.getTile());
//                		gs.player.setWorldY(59*gs.getTile());
					}
				}
				if(code == KeyEvent.VK_ESCAPE) {
					gs.state = State.LOOPY;
					gs.player.setWorldX(56* gs.getTile());
					gs.player.setWorldY(24* gs.getTile());
					gs.changeState = true;
				}
			}else{ // battle
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
				if(code == KeyEvent.VK_SPACE) {
					spacePress = true;
				}
				if(code == KeyEvent.VK_ESCAPE) {
					escPress = true;
				}
				if(code == KeyEvent.VK_ENTER) {
					enterPress = true;
				}
				if(code == KeyEvent.VK_TAB) {
					tabPress = true;
				}
				if(code == KeyEvent.VK_J) {
					skill1Press = true;
				}
				if(code == KeyEvent.VK_K) {
					skill2Press = true;
				}
				if(code == KeyEvent.VK_L) {
					skill3Press = true;
				}
			}
		}
		else if(gs.state == State.SURVIVAL) {
			gs.survival.loadMap();
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
			if(code == KeyEvent.VK_SPACE) {
				spacePress = true;
			}
			if(code == KeyEvent.VK_ESCAPE) {
				escPress = true;
			}
			if(code == KeyEvent.VK_ENTER) {
				enterPress = true;
			}
			if(code == KeyEvent.VK_TAB) {
				tabPress = true;
			}
			if(code == KeyEvent.VK_J) {
				skill1Press = true;
			}
			if(code == KeyEvent.VK_K) {
				skill2Press = true;
			}
			if(code == KeyEvent.VK_L) {
				skill3Press = true;
			}
		}else if(gs.state == State.LOOPY) {

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
			if(code == KeyEvent.VK_SPACE) {
				spacePress = true;
			}
			if(code == KeyEvent.VK_ESCAPE) {
				escPress = true;
			}
			if(code == KeyEvent.VK_ENTER) {
				enterPress = true;
			}
			if(code == KeyEvent.VK_TAB) {
				tabPress = true;
			}

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
		if(code == KeyEvent.VK_ESCAPE) {
			escPress = false;
		}
		if(code == KeyEvent.VK_SPACE) {
			spacePress = false;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPress = false;
		}
		if(code == KeyEvent.VK_TAB) {
			tabPress = false;
		}
		if(code == KeyEvent.VK_J) {
			skill1Press = false;
		}
		if(code == KeyEvent.VK_K) {
			skill2Press = false;
		}
		if(code == KeyEvent.VK_L) {
			skill3Press = false;
		}
	}
	public boolean isSpacePress() {
		return spacePress;
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

	public boolean isSkill1Press() {
		return skill1Press;
	}

	public boolean isSkill2Press() {
		return skill2Press;
	}

	public boolean isSkill3Press() {
		return skill3Press;
	}

	public boolean isTabPress() {
		return tabPress;
	}
}
