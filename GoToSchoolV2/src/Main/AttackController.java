package Main;

import AttackSkill.ArrowLight;
import AttackSkill.CircleFire;
import AttackSkill.MoonLight;
import AttackSkill.MultiArrow;
import Entity.Entity;
import SPSkill.Flicker;
import SPSkill.Restore;
import SPSkill.SUPPORT_SKILL;
import SPSkill.Sprint;
import Weapon.NormalAttack;

import java.util.Map;

public class AttackController {
    GameState gs;
    State state;
    long elapsedTime = 0;

    public AttackController(GameState gs) {
        this.gs = gs;
    }
    public void update() {
        state = gs.state;
        coolDownTimeAttack();

        if(state == State.CAMPAIGN) {
            // ATTACK
            if(gs.keyHandle.isSpacePress() && NormalAttack.TIME_COUNT_DOWN_ATTACK <= 0) {
                Entity normalAttack = new NormalAttack(gs);
                gs.skillAttacks.add(normalAttack);
                NormalAttack.TIME_COUNT_DOWN_ATTACK = NormalAttack.TIME_REDUCE;
                gs.player.setStateEntity("attack");
                gs.player.setSpriteNum(1);
            }
            if(gs.keyHandle.isSkill1Press() && gs.loopy.getSkillAttackHave() >= 1) {
                for(Map.Entry<String,Integer> entry : gs.Map_chooseSkillAttack.entrySet()) {
                    if(entry.getValue() == 1) {
                        Entity e;
                        switch (entry.getKey()) {
                            case "ArrowLight" -> {
                                if (ArrowLight.TIME_COUNT_DOWN_ATTACK <= 0) {
                                    e = new ArrowLight(gs);
                                    ArrowLight.TIME_COUNT_DOWN_ATTACK = ArrowLight.TIME_REDUCE;
                                    gs.skillAttacks.add(e);
                                    gs.player.setStateEntity("attack");
                                    gs.player.setSpriteNum(1);
                                }
                            }
                            case "MultiArrowLight" -> {
                                if (MultiArrow.TIME_COUNT_DOWN_ATTACK <= 0) {
                                    Entity e2 = new MultiArrow(gs);
                                    e = new MultiArrow(gs);
                                    e.setAngleTarget(e2.getAngleTarget() - Math.toRadians(15));
                                    Entity e3 = new MultiArrow(gs);
                                    e3.setAngleTarget(e2.getAngleTarget() + Math.toRadians(15));
                                    MultiArrow.TIME_COUNT_DOWN_ATTACK = MultiArrow.TIME_REDUCE;
                                    gs.skillAttacks.add(e);
                                    gs.skillAttacks.add(e2);
                                    gs.skillAttacks.add(e3);
                                    gs.player.setStateEntity("attack");
                                    gs.player.setSpriteNum(1);
                                }
                            }
                            case "MoonLight" -> {
                                if (MoonLight.TIME_COUNT_DOWN_ATTACK <= 0) {
                                    e = new MoonLight(gs);
                                    MoonLight.TIME_COUNT_DOWN_ATTACK = MoonLight.TIME_REDUCE;
                                    gs.skillAttacks.add(e);
                                    gs.player.setStateEntity("attack");
                                    gs.player.setSpriteNum(1);
                                }
                            }
                            case "CircleFire" -> {
                                if (CircleFire.TIME_COUNT_DOWN_ATTACK <= 0) {
                                    e = new CircleFire(gs);
                                    CircleFire.TIME_COUNT_DOWN_ATTACK = CircleFire.TIME_REDUCE;
                                    gs.skillAttacks.add(e);
                                    gs.player.setStateEntity("attack");
                                    gs.player.setSpriteNum(1);
                                }
                            }
                        }
                    }
                }
            }
            if(gs.keyHandle.isSkill2Press() && gs.loopy.getSkillAttackHave() == 2) {
                for(Map.Entry<String,Integer> entry : gs.Map_chooseSkillAttack.entrySet()) {
                    if(entry.getValue() == 2) {
                        Entity e;
                        switch (entry.getKey()) {
                            case "ArrowLight" -> {
                                if (ArrowLight.TIME_COUNT_DOWN_ATTACK <= 0) {
                                    e = new ArrowLight(gs);
                                    ArrowLight.TIME_COUNT_DOWN_ATTACK = ArrowLight.TIME_REDUCE;
                                    gs.skillAttacks.add(e);
                                    gs.player.setStateEntity("attack");
                                    gs.player.setSpriteNum(1);
                                }
                            }
                            case "MultiArrowLight" -> {
                                if (MultiArrow.TIME_COUNT_DOWN_ATTACK <= 0) {
                                    Entity e2 = new MultiArrow(gs);
                                    e = new MultiArrow(gs);
                                    e.setAngleTarget(e2.getAngleTarget() - Math.toRadians(15));
                                    Entity e3 = new MultiArrow(gs);
                                    e3.setAngleTarget(e2.getAngleTarget() + Math.toRadians(15));
                                    MultiArrow.TIME_COUNT_DOWN_ATTACK = MultiArrow.TIME_REDUCE;
                                    gs.skillAttacks.add(e);
                                    gs.skillAttacks.add(e2);
                                    gs.skillAttacks.add(e3);
                                    gs.player.setStateEntity("attack");
                                    gs.player.setSpriteNum(1);
                                }
                            }
                            case "MoonLight" -> {
                                if (MoonLight.TIME_COUNT_DOWN_ATTACK <= 0) {
                                    e = new MoonLight(gs);
                                    MoonLight.TIME_COUNT_DOWN_ATTACK = MoonLight.TIME_REDUCE;
                                    gs.skillAttacks.add(e);
                                    gs.player.setStateEntity("attack");
                                    gs.player.setSpriteNum(1);
                                }
                            }
                            case "CircleFire" -> {
                                if (CircleFire.TIME_COUNT_DOWN_ATTACK <= 0) {
                                    e = new CircleFire(gs);
                                    CircleFire.TIME_COUNT_DOWN_ATTACK = CircleFire.TIME_REDUCE;
                                    gs.skillAttacks.add(e);
                                    gs.player.setStateEntity("attack");
                                    gs.player.setSpriteNum(1);
                                }
                            }
                        }
                    }
                }
            }

            if(gs.keyHandle.isSupportSkill1() && gs.loopy.getSkillSuportHave() >= 1) {
                Entity e = gs.skillSupports.get(gs.indexSkillSupport1);
                if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Flicker) {
                    if(Flicker.TIME_COUNT_DOWN <= 0) {
                        e.setAlive(true);
                        Flicker.TIME_COUNT_DOWN = Flicker.TIME_REDUCE;
                        gs.player.setStateEntity("attack");
                        gs.player.setSpriteNum(1);
                    }
                }else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Sprint) {
                    if(Sprint.TIME_COUNT_DOWN <= 0) {
                        e.setAlive(true);
                        Sprint.TIME_COUNT_DOWN = Sprint.TIME_REDUCE;
                        gs.player.setStateEntity("attack");
                        gs.player.setSpriteNum(1);
                    }
                }else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Restore) {
                    if(Restore.TIME_COUNT_DOWN <= 0) {
                        e.setAlive(true);
                        Restore.TIME_COUNT_DOWN = Restore.TIME_REDUCE;
                        gs.player.setStateEntity("attack");
                        gs.player.setSpriteNum(1);
                    }
                }
                gs.skillSupports.set(gs.indexSkillSupport1,e);
            }
            if(gs.keyHandle.isSupportSkill2() && gs.loopy.getSkillSuportHave() == 2) {
                Entity e = gs.skillSupports.get(gs.indexSkillSupport2);
                if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Flicker) {
                    if(Flicker.TIME_COUNT_DOWN <= 0) {
                        e.setAlive(true);
                        Flicker.TIME_COUNT_DOWN = Flicker.TIME_REDUCE;
                        gs.player.setStateEntity("attack");
                        gs.player.setSpriteNum(1);
                    }
                }else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Sprint) {
                    if(Sprint.TIME_COUNT_DOWN <= 0) {
                        e.setAlive(true);
                        Sprint.TIME_COUNT_DOWN = Sprint.TIME_REDUCE;
                        gs.player.setStateEntity("attack");
                        gs.player.setSpriteNum(1);
                    }
                }else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Restore) {
                    if(Restore.TIME_COUNT_DOWN <= 0) {
                        e.setAlive(true);
                        Restore.TIME_COUNT_DOWN = Restore.TIME_REDUCE;
                        gs.player.setStateEntity("attack");
                        gs.player.setSpriteNum(1);
                    }
                }
                gs.skillSupports.set(gs.indexSkillSupport2,e);
            }
        }else if(state == State.SURVIVAL) {

        }
    }
    private void coolDownTimeAttack() {
        elapsedTime += 1_000_000_000L / GameState.FPS; // 1s/30fps

        if(elapsedTime >= 1_000_000_000L) {
            elapsedTime -=  1_000_000_000L;
            NormalAttack.TIME_COUNT_DOWN_ATTACK--;
            if(NormalAttack.TIME_COUNT_DOWN_ATTACK <= 0) {
                NormalAttack.TIME_COUNT_DOWN_ATTACK = -1;
            }
            MoonLight.TIME_COUNT_DOWN_ATTACK--;
            if(MoonLight.TIME_COUNT_DOWN_ATTACK <= 0) {
                MoonLight.TIME_COUNT_DOWN_ATTACK = -1;
            }
//				System.out.println(ArrowLight.TIME_REDUCE);
            ArrowLight.TIME_COUNT_DOWN_ATTACK--;
            if(ArrowLight.TIME_COUNT_DOWN_ATTACK <= 0) {
                ArrowLight.TIME_COUNT_DOWN_ATTACK = -1;
            }
            MultiArrow.TIME_COUNT_DOWN_ATTACK--;
            if(MultiArrow.TIME_COUNT_DOWN_ATTACK <= 0) {
                MultiArrow.TIME_COUNT_DOWN_ATTACK = -1;
            }
            CircleFire.TIME_COUNT_DOWN_ATTACK--;
            if(CircleFire.TIME_COUNT_DOWN_ATTACK <= 0) {
                CircleFire.TIME_COUNT_DOWN_ATTACK = -1;
            }
            Flicker.TIME_COUNT_DOWN--;
            if(Flicker.TIME_COUNT_DOWN <= 0) {
                Flicker.TIME_COUNT_DOWN = -1;
            }
            Restore.TIME_COUNT_DOWN--;
            if(Restore.TIME_COUNT_DOWN <= 0) {
                Restore.TIME_COUNT_DOWN = -1;
            }
            Sprint.TIME_COUNT_DOWN--;
            if(Sprint.TIME_COUNT_DOWN <= 0) {
                Sprint.TIME_COUNT_DOWN = -1;
            }
        }
    }
}
