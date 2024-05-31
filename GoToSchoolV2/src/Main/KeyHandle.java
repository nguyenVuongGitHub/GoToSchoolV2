/**
 * 
 * this class handle the key broad.
 * 
 */
package Main;

import baseAttributeSkills.BaseArrowLight;
import baseAttributeSkills.BaseCircleFire;
import baseAttributeSkills.BaseMoonLight;
import baseAttributeSkills.BaseMultiArrow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandle implements KeyListener{

	GameState gs;
	private boolean downPress, upPress, leftPress, rightPress, escPress, enterPress, spacePress, tabPress;

    private boolean skill1Press,skill2Press;
	private boolean supportSkill1;
	private boolean supportSkill2;

	private boolean exitMap = false;
	private boolean accessLoadMap = false;
	private boolean accessReturnLoopy = false;
    private boolean accessSaveGame = false;
    private boolean accessExitGame = false;

	private boolean isResetSkillSupport = false;
	private boolean isResetSkillAttack = false;
	private boolean isAddSkillSupport = false;
	private boolean isAddSkillAttack = false;
	private boolean isUpgradeSkill = false;

	private String yourAddSkillSupport = null;
	private String yourAddSkillAttack = null;

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
		if(!gs.changeScene.isAlive()) {
			if(gs.state == State.CAMPAIGN) {
				// dialog
				if(gs.campaign.isShowDialog()) {
					if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
						gs.playSE(4);
					}
				}
				if(gs.campaign.isShowDialog()) {
					gs.playSE(4);
					if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
						if(gs.campaign.getChoose() > 1) {
							gs.campaign.setChoose(gs.campaign.getChoose()-1);
						}
					}
					if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
						if(gs.campaign.getChoose() < (gs.user.getNumberLeversUnlocked() - gs.campaign.getStep())
						&& gs.campaign.getChoose() < gs.user.getMaxNumberLevers()) {
							gs.campaign.setChoose(gs.campaign.getChoose()+1);
						}
					}
					if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
						gs.campaign.setCurrentStateCampaign((short) (gs.campaign.getCurrentStateCampaign() - 1));
						gs.campaign.setChoose(1);
						if(gs.campaign.getCurrentStateCampaign() < 1) {
							gs.campaign.setCurrentStateCampaign((short)1);
						}
					}
					if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
						gs.campaign.setCurrentStateCampaign((short) (gs.campaign.getCurrentStateCampaign() + 1));
						gs.campaign.setChoose(1);
						if(gs.campaign.getCurrentStateCampaign() > gs.user.getMaxStateCampaign()) {
							gs.campaign.setCurrentStateCampaign(gs.user.getMaxStateCampaign());
						}
					}
					if(code == KeyEvent.VK_SPACE) {
						if(gs.campaign.getChoose() + gs.campaign.getStep() <= gs.user.getNumberLeversUnlocked()) {
							accessLoadMap = true;
							gs.changeScene.setAlive(true);
						}
						resetAllKeyMoving();
					}
					if(code == KeyEvent.VK_ESCAPE) {
						accessReturnLoopy = true;
						gs.changeScene.setAlive(true);
						resetAllKeyMoving();
						gs.stopMusic();
					}
				}else{ // battle

					// exit Game
					if(code == KeyEvent.VK_ESCAPE) {
						gs.stopMusic();
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
					if(code == KeyEvent.VK_TAB) {
						tabPress = true;
					}
					if(code == KeyEvent.VK_1) {
						skill1Press = true;
					}
					if(code == KeyEvent.VK_2) {
						skill2Press = true;
					}
					if(code == KeyEvent.VK_Q) {
						supportSkill1 = true;
					}
					if(code == KeyEvent.VK_E) {
						supportSkill2 = true;
					}
				}
			}
			else if (gs.state == State.SURVIVAL) {
				if (gs.survival.getEndOfDay()) {
					resetAllKeyMoving();
					if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
						if (gs.survival.getSelected() - 1 <= 0) {
							gs.survival.setSelected(gs.survival.getMaxBlessingSlot());
						} else {
							gs.survival.setSelected(gs.survival.getSelected() - 1);
						}
					}
					if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
						gs.survival.setSelected(gs.survival.getSelected() % gs.survival.getMaxBlessingSlot() + 1);
					}
					if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
						gs.survival.setEndOfDay(false);
						//Give effect of blessing
						gs.survival.applyBlessing(gs.survival.getListBlessing().get(gs.survival.getSelected() - 1));
					}
				} else if (gs.survival.getMeeting()) {
					resetAllKeyMoving();
					if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
						if (gs.survival.getSelected() - 1 <= 0) {
							gs.survival.setSelected(gs.survival.getMaxShopSlot() -1);
						} else {
							gs.survival.setSelected(gs.survival.getSelected() - 1);
						}
					}
					if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
						gs.survival.setSelected(gs.survival.getSelected() % gs.survival.getMaxShopSlot() + 1);
					}
					if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
						if(gs.survival.getSelected() - (gs.survival.getMaxShopSlot() / 2) > 0)
						{
							gs.survival.setSelected(gs.survival.getSelected() - (gs.survival.getMaxShopSlot() / 2));
						}
					}
					if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
						if(gs.survival.getSelected() <= 3)
						{
							gs.survival.setSelected(gs.survival.getSelected() + gs.survival.getMaxShopSlot() / 2);
						}
					}
					if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
						//Give item
						gs.survival.giveItem(gs.survival.getListItem().get(gs.survival.getSelected()-1));
					}
					if (code == KeyEvent.VK_ESCAPE) {
						gs.survival.setMeeting(false);
						resetAllKeyMoving();
					}
				} else { // In game
					if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
						upPress = true;
					}
					if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
						downPress = true;
					}
					if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
						leftPress = true;
					}
					if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
						rightPress = true;
					}
					if (code == KeyEvent.VK_SPACE) {
						spacePress = true;
					}
					if (code == KeyEvent.VK_ESCAPE) {
						escPress = true;
					}
					if (code == KeyEvent.VK_ENTER) {
						enterPress = true;
					}
					if (code == KeyEvent.VK_TAB) {
						tabPress = true;
					}
					if (code == KeyEvent.VK_Q) {
						supportSkill1 = true;
					}
					if (code == KeyEvent.VK_E) {
						supportSkill2 = true;
					}
					if (code == KeyEvent.VK_R) {
						skill1Press = true;
					}
					if (code == KeyEvent.VK_F) {
						skill2Press = true;
					}
					if (code == KeyEvent.VK_ESCAPE) {
						resetAllKeyMoving();
						accessReturnLoopy = true;
					}
				}
			}
			else if (gs.state == State.LOOPY) {
				if (gs.loopy.isShowDialogExit()) {
					if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
						gs.loopy.setChooseDialogExit(gs.loopy.getChooseDialogExit() - 1);
						if (gs.loopy.getChooseDialogExit() < 1) {
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
				}
				else if(gs.loopy.isShowDialogChooseSkillsSupport()) {
					if(code == KeyEvent.VK_ESCAPE) {
						gs.loopy.setShowDialogChooseSkillsSupport(false);
					}
				}
				else if(gs.loopy.isShowDialogChooseSkillsAttack()) {
					if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
						gs.loopy.setChooseSkill(gs.loopy.getChooseSkill() - 1);
						if (gs.loopy.getChooseSkill() < 1) {
							gs.loopy.setChooseSkill(gs.user.getMaxNumberSkillsAttackUnlocked());
						}
					}
					if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
						gs.loopy.setChooseSkill(gs.loopy.getChooseSkill() + 1);
						if (gs.loopy.getChooseSkill() > gs.user.getMaxNumberSkillsAttackUnlocked()) {
							gs.loopy.setChooseSkill(1);
						}
					}
					if (code == KeyEvent.VK_SPACE) {
						gs.loopy.setSkillAttackHave(gs.loopy.getSkillAttackHave() + 1);
						if (gs.loopy.getSkillAttackHave() == 1) {
							if (gs.loopy.getChooseSkill() == 1) {
								gs.Map_chooseSkillAttack.put("ArrowLight", gs.loopy.getSkillAttackHave());
							} else if (gs.loopy.getChooseSkill() == 2) {
								gs.Map_chooseSkillAttack.put("MultiArrowLight", gs.loopy.getSkillAttackHave());
							} else if (gs.loopy.getChooseSkill() == 3) {
								gs.Map_chooseSkillAttack.put("MoonLight", gs.loopy.getSkillAttackHave());
							} else if (gs.loopy.getChooseSkill() == 4) {
								gs.Map_chooseSkillAttack.put("CircleFire", gs.loopy.getSkillAttackHave());
							}
						}else if(gs.loopy.getSkillAttackHave() == 2) {
							if (gs.loopy.getChooseSkill() == 1) {
								if(!gs.Map_chooseSkillAttack.containsKey("ArrowLight")) {
									gs.Map_chooseSkillAttack.put("ArrowLight", gs.loopy.getSkillAttackHave());
								}else {
									gs.loopy.setSkillAttackHave(gs.loopy.getSkillAttackHave() - 1);
								}
							} else if (gs.loopy.getChooseSkill() == 2) {
								if(!gs.Map_chooseSkillAttack.containsKey("MultiArrowLight")) {
									gs.Map_chooseSkillAttack.put("MultiArrowLight", gs.loopy.getSkillAttackHave());
								}else {
									gs.loopy.setSkillAttackHave(gs.loopy.getSkillAttackHave() - 1);
								}
							} else if (gs.loopy.getChooseSkill() == 3) {
								if(!gs.Map_chooseSkillAttack.containsKey("MoonLight")) {
									gs.Map_chooseSkillAttack.put("MoonLight", gs.loopy.getSkillAttackHave());
								}else {
									gs.loopy.setSkillAttackHave(gs.loopy.getSkillAttackHave() - 1);
								}
							} else if (gs.loopy.getChooseSkill() == 4) {
								if(!gs.Map_chooseSkillAttack.containsKey("CircleFire")) {
									gs.Map_chooseSkillAttack.put("CircleFire", gs.loopy.getSkillAttackHave());
								}else {
									gs.loopy.setSkillAttackHave(gs.loopy.getSkillAttackHave() - 1);
								}
							}
						}else {
							gs.loopy.setSkillAttackHave(gs.loopy.getSkillAttackHave() - 1);
						}
					}
					if (code == KeyEvent.VK_Q) {
						isResetSkillAttack = true;
					}
					if (code == KeyEvent.VK_ESCAPE) {
						gs.loopy.setShowDialogChooseSkillsAttack(false);
					}
				}
				else if (gs.loopy.isShowDialogUpgradeSkill()) {
					if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
						gs.loopy.setChooseSkill(gs.loopy.getChooseSkill() - 1);
						if (gs.loopy.getChooseSkill() < 1) {
							gs.loopy.setChooseSkill(gs.user.getMaxNumberSkillsAttackUnlocked());
						}
					}
					if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
						gs.loopy.setChooseSkill(gs.loopy.getChooseSkill() + 1);
						if (gs.loopy.getChooseSkill() > gs.user.getMaxNumberSkillsAttackUnlocked()) {
							gs.loopy.setChooseSkill(1);
						}
					}
					if (code == KeyEvent.VK_SPACE) {
						if(gs.user.getCoin() >= gs.user.getCoinNeedUpgrade()) {
							if(gs.loopy.getChooseSkill() == 1) {
								if(BaseArrowLight.LEVER < 50)
									BaseArrowLight.LEVER++;
							}else if(gs.loopy.getChooseSkill() == 2) {
								if(BaseMultiArrow.LEVER < 50)
									BaseMultiArrow.LEVER++;
							}else if(gs.loopy.getChooseSkill() == 3) {
								if(BaseMoonLight.LEVER < 50)
									BaseMoonLight.LEVER++;
							}else if(gs.loopy.getChooseSkill() == 4) {
								if(BaseCircleFire.LEVER < 50)
									BaseCircleFire.LEVER++;
							}
							gs.user.setCoin(gs.user.getCoin() - gs.user.getCoinNeedUpgrade());
							gs.user.setCoinNeedUpgrade(gs.user.getCoinNeedUpgrade() + 10);
						}
					}
					if (code == KeyEvent.VK_ESCAPE) {
						gs.loopy.setShowDialogUpgradeSkill(false);
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
					if(code == KeyEvent.VK_ESCAPE) {
						gs.loopy.setShowDialogExit(true);
					}
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
		if(code == KeyEvent.VK_1 || code == KeyEvent.VK_R) {
			skill1Press = false;
		}
		if(code == KeyEvent.VK_2 || code == KeyEvent.VK_F) {
			skill2Press = false;
		}
		if(code == KeyEvent.VK_Q) {
			supportSkill1 = false;
		}
		if(code == KeyEvent.VK_E) {
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
	public void resetAllKeyMoving() {
		downPress = false;
		upPress = false;
		leftPress = false;
		rightPress = false;
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
	public boolean isResetSkillAttack() {
		return isResetSkillAttack;
	}

	public void setResetSkillAttack(boolean resetSkillAttack) {
		isResetSkillAttack = resetSkillAttack;
	}

	public boolean isAddSkillAttack() {
		return isAddSkillAttack;
	}

	public void setAddSkillAttack(boolean addSkillAttack) {
		isAddSkillAttack = addSkillAttack;
	}

	public String getYourAddSkillAttack() {
		return yourAddSkillAttack;
	}

	public void setYourAddSkillAttack(String yourAddSkillAttack) {
		this.yourAddSkillAttack = yourAddSkillAttack;
	}

	public boolean isUpgradeSkill() {
		return isUpgradeSkill;
	}

	public void setUpgradeSkill(boolean upgradeSkill) {
		isUpgradeSkill = upgradeSkill;
	}
}
