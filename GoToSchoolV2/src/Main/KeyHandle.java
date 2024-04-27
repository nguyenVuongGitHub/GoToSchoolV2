/**
 * 
 * this class handle the key broad.
 * 
 */
package Main;

import com.sun.source.tree.IfTree;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class KeyHandle implements KeyListener{

	GameState gs;
	private boolean downPress, upPress, leftPress, rightPress, escPress, enterPress, spacePress, tabPress;

    private boolean skill1Press,skill2Press,skill3Press;
	private boolean supportSkill1;
	private boolean supportSkill2;

	private boolean exitMap = false;
	private boolean accessLoadMap = false;
	private boolean accessReturnLoopy = false;
    private boolean accessSaveGame = false;
    private boolean accessExitGame = false;

	private boolean isResetSkillSupport = false;
	private boolean isAddSkillSupport = false;
	private String yourAddSkillSupport = null;


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
						gs.campaign.setChoose(gs.user.getNumberLeversUnlocked());
					}
				}
				if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gs.campaign.setChoose(gs.campaign.getChoose()+1);
					if(gs.campaign.getChoose() > gs.user.getNumberLeversUnlocked()) {
						gs.campaign.setChoose(1);
					}
				}
				if(code == KeyEvent.VK_SPACE) {
					accessLoadMap = true;
				}
				if(code == KeyEvent.VK_ESCAPE) {
					accessReturnLoopy = true;
				}
			}else{ // battle

				// exit Game
				if(code == KeyEvent.VK_ESCAPE) {
					exitMap = true;
				}else {
					exitMap = false;
				}
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
				if(code == KeyEvent.VK_E) {
					enterPress = true;
				}
				if(code == KeyEvent.VK_TAB) {
					tabPress = true;
				}
				if(code == KeyEvent.VK_1) {
					skill1Press = true;
				}
				if(code == KeyEvent.VK_2) {
					skill2Press = true;
				}
				if(code == KeyEvent.VK_3) {
					skill3Press = true;
				}
				if(code == KeyEvent.VK_F) {
					supportSkill1 = true;
				}
				if(code == KeyEvent.VK_R) {
					supportSkill2 = true;
				}
			}
		}
		else if(gs.state == State.SURVIVAL) {
			if(!gs.survival.isMapExist())
			{
				gs.survival.loadMap();
				gs.survival.setMapExist(true);
			}
			if(gs.survival.getEndOfDay())
			{
				if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                    if (gs.survival.getSelected() - 1 <= 0) {
                        gs.survival.setSelected(3);
                    } else {
                        gs.survival.setSelected(gs.survival.getSelected() - 1);
                    }
                }
				if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
					gs.survival.setSelected(gs.survival.getSelected() % 3 + 1);
				}
				if(code == KeyEvent.VK_ENTER) {
					gs.survival.setEndOfDay(false);
				}
			}
			else
			{
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
		}else if(gs.state == State.LOOPY) {

            if(gs.loopy.isShowDialogExit()) {
                if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    gs.loopy.setChooseDialogExit(gs.loopy.getChooseDialogExit()-1);
                    if(gs.loopy.getChooseDialogExit() < 1) {
                        gs.loopy.setChooseDialogExit(2);
                    }
                }
                if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    gs.loopy.setChooseDialogExit(gs.loopy.getChooseDialogExit()+1);
                    if(gs.loopy.getChooseDialogExit() > 2) {
                        gs.loopy.setChooseDialogExit(1);
                    }
                }
                if(code == KeyEvent.VK_SPACE && gs.loopy.getChooseDialogExit() == 1) {
                    accessSaveGame = true;
					gs.loopy.setShowDialogExit(false);
                }
                if(code == KeyEvent.VK_SPACE && gs.loopy.getChooseDialogExit() == 2) {
                    accessExitGame = true;
					gs.loopy.setShowDialogExit(false);
                }
                if(code == KeyEvent.VK_ESCAPE) {
                    gs.loopy.setShowDialogExit(false);
                }
            }else if(gs.loopy.isShowDialogChooseSkillsSupport()) {
				if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					gs.loopy.setChooseSkill(gs.loopy.getChooseSkill()-1);
					if(gs.loopy.getChooseSkill() < 1) {
						gs.loopy.setChooseSkill(gs.user.getNumberSkillsUnlocked());
					}
				}
				if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gs.loopy.setChooseSkill(gs.loopy.getChooseSkill()+1);
					if(gs.loopy.getChooseSkill() > gs.user.getNumberSkillsUnlocked()) {
						gs.loopy.setChooseSkill(1);
					}
				}
				if(code == KeyEvent.VK_SPACE) {
					gs.loopy.setSkillHave(gs.loopy.getSkillHave()+1);
					if(gs.loopy.getSkillHave() <= 2) {
						if(gs.loopy.getChooseSkill() == 1) {
							gs.Map_chooseSkill.put("flash",gs.loopy.getSkillHave());
							yourAddSkillSupport = "flash";
						}else if(gs.loopy.getChooseSkill() == 2) {
							gs.Map_chooseSkill.put("speed",gs.loopy.getSkillHave());
							yourAddSkillSupport = "speed";
						}else if(gs.loopy.getChooseSkill() == 3) {
							gs.Map_chooseSkill.put("healing",gs.loopy.getSkillHave());
							yourAddSkillSupport = "healing";
						}
						isAddSkillSupport = true;
					}
				}
				if(code == KeyEvent.VK_Q) {
					isResetSkillSupport = true;
				}
				if(code == KeyEvent.VK_ESCAPE) {
					gs.loopy.setShowDialogChooseSkillsSupport(false);
				}
			}
			else {
				accessSaveGame = false;
				accessExitGame = false;
				gs.ui.setPlayerSay(false);
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
                if(code == KeyEvent.VK_E) {
                    enterPress = true;
                }
                if(code == KeyEvent.VK_TAB) {
                    tabPress = true;
                }
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
		if(code == KeyEvent.VK_E) {
			enterPress = false;
		}
		if(code == KeyEvent.VK_TAB) {
			tabPress = false;
		}
		if(code == KeyEvent.VK_1) {
			skill1Press = false;
		}
		if(code == KeyEvent.VK_2) {
			skill2Press = false;
		}
		if(code == KeyEvent.VK_3) {
			skill3Press = false;
		}
		if(code == KeyEvent.VK_F) {
			supportSkill1 = false;
		}
		if(code == KeyEvent.VK_R) {
			supportSkill2 = false;
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
	public boolean isAccessLoadMap() {
		return accessLoadMap;
	}

	public void setAccessLoadMap(boolean accessLoadMap) {
		this.accessLoadMap = accessLoadMap;
	}
	public boolean isAccessReturnLoopy() {
		return accessReturnLoopy;
	}

	public void setAccessReturnLoopy(boolean accessReturnLoopy) {
		this.accessReturnLoopy = accessReturnLoopy;
	}
	public boolean isExitMap() {
		return exitMap;
	}

	public void setExitMap(boolean exitMap) {
		this.exitMap = exitMap;
	}

	public void resetAllData() {
		exitMap = false;
		accessReturnLoopy = false;
		accessLoadMap = false;
	}
	public boolean isSupportSkill1() {
		return supportSkill1;
	}

	public void setSupportSkill1(boolean supportSkill1) {
		this.supportSkill1 = supportSkill1;
	}
	public boolean isSupportSkill2() {
		return supportSkill2;
	}

	public void setSupportSkill2(boolean supportSkill2) {
		this.supportSkill2 = supportSkill2;
	}
    public boolean isAccessExitGame() {
        return accessExitGame;
    }

    public void setAccessExitGame(boolean exitGame) {
        this.accessExitGame = exitGame;
    }

    public boolean isAccessSaveGame() {
        return accessSaveGame;
    }

    public void setAccessSaveGame(boolean saveGame) {
        this.accessSaveGame = saveGame;
    }
	public boolean isResetSkillSupport() {
		return isResetSkillSupport;
	}

	public void setResetSkillSupport(boolean resetSkillSupport) {
		isResetSkillSupport = resetSkillSupport;
	}
	public boolean isAddSkillSupport() {
		return isAddSkillSupport;
	}

	public void setAddSkillSupport(boolean addSkill) {
		isAddSkillSupport = addSkill;
	}
	public String getYourAddSkillSupport() {
		return yourAddSkillSupport;
	}

	public void setYourAddSkillSupport(String yourAddSkillSupport) {
		this.yourAddSkillSupport = yourAddSkillSupport;
	}
}
