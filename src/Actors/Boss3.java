/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import GameFrameworkJavaFX.Game;
import GameFrameworkJavaFX.Gui;
import static java.lang.Math.abs;
import javafx.scene.image.Image;

/**
 *
 * @author Taylan Kurt
 */
public class Boss3 extends Actor {

    private boolean facingRight = false;
    boolean movementStrategyFlag = true;
    private int timePauseCounter = 0;
    private final int MAXIMUM_HITPOINTS = 50;
    private int animationTimeCounter;
    private int punchTimeCounter = 0;
    private int kickTimeCounter = 0;
    private boolean isPunching;
    private boolean isKicking;
    private boolean energyBlastFlag = false;
    private final int punchDamage = 2;
    private final int kickDamage = 4;
    private final Image[] boss3Pics = new Image[10];

    private EnergyBlast energyBall;

    public Boss3(String name, String filename, int sizeX, int sizeY) {
        super(name, filename, sizeX, sizeY);
        boss3Pics[0] = new Image(getClass().getResourceAsStream("/resources/" + "ryu_standing_right.png"));
        boss3Pics[1] = new Image(getClass().getResourceAsStream("/resources/" + "ryu_standing_left.png"));
        boss3Pics[2] = new Image(getClass().getResourceAsStream("/resources/" + "ryu_walking_right.png"));
        boss3Pics[3] = new Image(getClass().getResourceAsStream("/resources/" + "ryu_walking_left.png"));
        boss3Pics[4] = new Image(getClass().getResourceAsStream("/resources/" + "ryu_punch_right.png"));
        boss3Pics[5] = new Image(getClass().getResourceAsStream("/resources/" + "ryu_punch_left.png"));
        boss3Pics[6] = new Image(getClass().getResourceAsStream("/resources/" + "ryu_kick_right.png"));
        boss3Pics[7] = new Image(getClass().getResourceAsStream("/resources/" + "ryu_kick_left.png"));
        boss3Pics[8] = new Image(getClass().getResourceAsStream("/resources/" + "ryu_energy_right.png"));
        boss3Pics[9] = new Image(getClass().getResourceAsStream("/resources/" + "ryu_energy_left.png"));

        this.setHitpoints(MAXIMUM_HITPOINTS);
    }

    @Override
    public void timeTick() {
        super.timeTick();
        if (!energyBlastFlag) {
            doBossMovementStrategy();
        } else {
            energyBallBlast();
        }
//        else if (Game.getInstance().master.getHitpoints() <= 50){
//            energyBallBlast();
//        }

    }

    public void animateWalkingLeft() {
        animationTimeCounter++;
        if (animationTimeCounter == 2) {
            updateImage(boss3Pics[3]);
        } else if (animationTimeCounter == 6) {
            updateImage(boss3Pics[1]);
            animationTimeCounter = 0;                                           //animationCounter to 1
        }
    }

    public void animateWalkingRight() {
        animationTimeCounter++;
        if (animationTimeCounter == 2) {
            updateImage(boss3Pics[2]);
        } else if (animationTimeCounter == 6) {
            updateImage(boss3Pics[0]);
            animationTimeCounter = 0;
        }
    }

    public boolean facingRight() {
        facingRight = Game.getInstance().master.getX() >= getX();
        return facingRight;
    }

    public void doBossMovementStrategy() {
        facingRight();
        if (!facingRight) {
            if (movementStrategyFlag) {
                setSpeedX(-6);
                animateWalkingLeft();
                if ((getX() - Game.getInstance().master.getX()) < 20) {
                    setSpeedX(0);
                    updateImage(boss3Pics[1]);
                    movementStrategyFlag = false;
                }
            }
            if (!movementStrategyFlag) {
                timePauseCounter++;
                if (timePauseCounter == 1) {
                    System.out.println("Punchingleft");
                    updateImage(boss3Pics[5]);
                    punch();                          // PUNCH ANİMATE
                }
                if (timePauseCounter == 30) {
                    System.out.println("Kickingleft");
                    updateImage(boss3Pics[7]);
                    kick();
                }
                if (timePauseCounter > 60) {
                    setSpeedX(4);
                    animateWalkingRight();
                    if (atHorizontalEdge()) {
                        timePauseCounter = 0;
                        movementStrategyFlag = true;
                    }
                }
                if ((getX() - Game.getInstance().master.getX()) > 300) {
                    timePauseCounter = 0;
                    setSpeedX(0);
                    updateImage(boss3Pics[1]);
                    movementStrategyFlag = true;
                    energyBlastFlag = true;
                }
            }
        }
        if (facingRight) {
            if (movementStrategyFlag) {
                setSpeedX(6);
                animateWalkingRight();
                if ((Game.getInstance().master.getX() - getX()) < 50) {
                    setSpeedX(0);
                    updateImage(boss3Pics[0]);
                    movementStrategyFlag = false;
                }
            }
            if (!movementStrategyFlag) {
                timePauseCounter++;
                if (timePauseCounter == 1) {
                    System.out.println("Kickingright");
                    updateImage(boss3Pics[6]);
                    kick();                            // KİCK ANİMATE
                }
                if (timePauseCounter == 30) {
                    System.out.println("punchingright");
                    updateImage(boss3Pics[4]);
                    punch();
                }
                if (timePauseCounter > 60) {
                    setSpeedX(-4);
                    animateWalkingLeft();
                    if (atHorizontalEdge()) {
                        timePauseCounter = 0;
                        movementStrategyFlag = true;
                    }
                }
                if ((Game.getInstance().master.getX() - getX()) > 300) {
                    timePauseCounter = 0;
                    setSpeedX(0);
                    updateImage(boss3Pics[0]);
                    movementStrategyFlag = true;
                    energyBlastFlag = true;

                }
            }
        }
    }

    public void boss3CollidesWith() {
        if (this.collidesWith(Game.getInstance().master) && this.isPunching()) {

            Game.getInstance().master.damage(punchDamage);

            if (Game.getInstance().master.getHitpoints() <= 0) {
                Game.getInstance().master.setLife(Game.getInstance().master.getLife() - 1); //remove one life
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS()); //reset hitpoints

                if (Game.getInstance().master.getLife() <= 0) {
                    Gui.getInstance().showLifeLabel(Game.getInstance().master.getLife());
                    Game.getInstance().removeHeroFromWorld(Game.getInstance().master);

                }

            }

        }

        if (this.collidesWith(Game.getInstance().master) && this.isKicking()) {

            Game.getInstance().master.damage(kickDamage);

            if (Game.getInstance().master.getHitpoints() <= 0) {
                Game.getInstance().master.setLife(Game.getInstance().master.getLife() - 1); //remove one life
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS()); //reset hitpoints

                if (Game.getInstance().master.getLife() <= 0) {
                    Gui.getInstance().showLifeLabel(Game.getInstance().master.getLife());
                    Game.getInstance().removeHeroFromWorld(Game.getInstance().master);

                }

            }

        }

    }

    public void kick() {
        setKicking(true);
        if (facingRight) {
            kickTimeCounter++;
            if (kickTimeCounter == 2) {
//                updateImage(boss3Pics[6]);
            } else if (kickTimeCounter == 4) {
//                updateImage(boss3Pics[0]);
                kickTimeCounter = 0;
            }
        } else if (!facingRight) {
            kickTimeCounter++;
            if (kickTimeCounter == 2) {
//                updateImage(boss3Pics[7]);
            } else if (kickTimeCounter == 4) {
//                updateImage(boss3Pics[1]);
                kickTimeCounter = 0;
            }
        }

        if (this.collidesWith(Game.getInstance().master) && this.isKicking()) {
            Game.getInstance().master.damage(kickDamage);
            Gui.getInstance().kickFX();

            if (Game.getInstance().master.getHitpoints() <= 0) {
                Game.getInstance().master.setLife(Game.getInstance().master.getLife() - 1); //remove one life
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS()); //reset hitpoints

                if (Game.getInstance().master.getLife() <= 0) {
                    Gui.getInstance().showLifeLabel(Game.getInstance().master.getLife());
                    Game.getInstance().removeHeroFromWorld(Game.getInstance().master);
                }
            }
        }
        setKicking(false);
    }

    public void punch() {
        setPunching(true);
        if (facingRight) {
            punchTimeCounter++;
            if (punchTimeCounter == 2) {
//                updateImage(boss3Pics[4]);
            } else if (punchTimeCounter == 4) {
//                updateImage(boss3Pics[0]);
                punchTimeCounter = 0;
            }
        } else if (!facingRight) {
            punchTimeCounter++;
            if (punchTimeCounter == 2) {
//                updateImage(boss3Pics[5]);
            } else if (punchTimeCounter == 4) {
//                updateImage(boss3Pics[1]);
                punchTimeCounter = 0;
            }

        }
        if (this.collidesWith(Game.getInstance().master) && this.isPunching()) {
            Game.getInstance().master.damage(punchDamage);
            Gui.getInstance().punchFX();

            if (Game.getInstance().master.getHitpoints() <= 0) {
                Game.getInstance().master.setLife(Game.getInstance().master.getLife() - 1); //remove one life
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS()); //reset hitpoints

                if (Game.getInstance().master.getLife() <= 0) {
                    Gui.getInstance().showLifeLabel(Game.getInstance().master.getLife());
                    Game.getInstance().removeHeroFromWorld(Game.getInstance().master);
                }
            }
        }
        setPunching(false);

    }

    public void energyBallBlast() {
        timePauseCounter++;
        if (abs(getX() - Game.getInstance().master.getX()) >= 50) {
            if (timePauseCounter == 33) {
                Gui.getInstance().fireballFX();
                if (!facingRight()) {                                         //decide which way is enemy faced
                    updateImage(boss3Pics[9]);
                    energyBall = new EnergyBlast("energyball", "energy_ball_left.png", 22, 11);
                    Game.getInstance().addToWorldWeapons(energyBall, getX(), Game.getInstance().getFLOOR_COORDINATE(),
                            -8, 0, 1.0);        //add only one object with a speed to the world because of a counter(to avoid duplicate children error)

                }
                if (facingRight()) {
                    updateImage(boss3Pics[8]);
                    energyBall = new EnergyBlast("energyball", "energy_ball_right.png", 22, 11);
                    Game.getInstance().addToWorldWeapons(energyBall, getX(), Game.getInstance().getFLOOR_COORDINATE(),
                            8, 0, 1.0);
                }
            }
            if (timePauseCounter == 66) {
                Gui.getInstance().fireballFX();
                if (!facingRight()) {                                         //decide which way is enemy faced
                    updateImage(boss3Pics[9]);
                    energyBall = new EnergyBlast("energyball", "energy_ball_left.png", 22, 11);
                    Game.getInstance().addToWorldWeapons(energyBall, getX(), Game.getInstance().getFLOOR_COORDINATE() + 40,
                            -9, 0, 1.0);        //add only one object with a speed to the world because of a counter(to avoid duplicate children error)

                }
                if (facingRight()) {
                    updateImage(boss3Pics[8]);
                    energyBall = new EnergyBlast("energyball", "energy_ball_right.png", 22, 11);
                    Game.getInstance().addToWorldWeapons(energyBall, getX(), Game.getInstance().getFLOOR_COORDINATE() + 40,
                            9, 0, 1.0);
                }
                timePauseCounter = 0;
                energyBlastFlag = false;
            }
        }
    }

    public boolean isPunching() {
        return isPunching;
    }

    public boolean isKicking() {
        return isKicking;
    }

    public void setKicking(boolean kicking) {
        this.isKicking = kicking;
    }

    public void setPunching(boolean punching) {
        this.isPunching = punching;
    }
}
