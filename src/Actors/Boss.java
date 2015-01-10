
package Actors;

import GameFrameworkJavaFX.Game;
import GameFrameworkJavaFX.Gui;
import javafx.scene.image.Image;

/**
 *
 * @author Taylan Kurt
 */
public class Boss extends Actor {

    private Knife knife2;
    private Knife knife3;
    private final int MAXIMUM_HITPOINTS = 15;
    private boolean facingRight;
    private boolean bossStrategy = true;
    private int timePauseCounter = 0;
    private final Image[] bossPics;

    public Boss(String name, String filename, int sizeX, int sizeY) {
        super(name, filename, sizeX, sizeY);
        this.bossPics = new Image[2];
        this.setHitpoints(MAXIMUM_HITPOINTS);
        bossPics[0] = new Image(getClass().getResourceAsStream("/resources/" + "Gilgamesh.png"));
        bossPics[1] = new Image(getClass().getResourceAsStream("/resources/" + "GilgameshLeft.png"));
    }

    @Override
    public void timeTick() {
        super.timeTick();
        doEnemyMovementStrategy();
    }

    public void doEnemyMovementStrategy() {
        facingRight();
        if (!facingRight) {
            if (bossStrategy) {
                setSpeedX(-3);
                if ((getX() - Game.getInstance().master.getX()) < 250) {
                    setSpeedX(0);
                    bossStrategy = false;
                }
            }
            if (!bossStrategy) {
                timePauseCounter++;
                if (timePauseCounter == 1) {
                    castKnife();
                }
                if (timePauseCounter == 50) {
                    castSecondKnife();
                }
                if (timePauseCounter > 80) {
                    setSpeedX(3);
                    if (atHorizontalEdge()) {
                        timePauseCounter = 0;
                        bossStrategy = true;
                    }
                }
                if ((getX() - Game.getInstance().master.getX()) > 400) {
                    timePauseCounter = 0;
                    bossStrategy = true;
                }
            }
        }
        if (facingRight) {
            if (bossStrategy) {
                setSpeedX(3);
                if ((Game.getInstance().master.getX() - getX()) < 250) {
                    setSpeedX(0);
                    bossStrategy = false;
                }
            }
            if (!bossStrategy) {
                timePauseCounter++;
                if (timePauseCounter == 1) {
                    castKnife();
                }
                if (timePauseCounter == 50) {
                    castSecondKnife();
                }
                if (timePauseCounter > 80) {
                    setSpeedX(-3);
                    if (atHorizontalEdge()) {
                        timePauseCounter = 0;
                        bossStrategy = true;
                    }
                }
                if ((Game.getInstance().master.getX() - getX()) > 400) {
                    timePauseCounter = 0;
                    bossStrategy = true;
                }
            }

        }
    }

    public boolean facingRight() {                                               //method to know which side an enemy is face regards master
        if (Game.getInstance().master.getX() < getX()) {
            facingRight = false;
            updateImage(bossPics[1]);
        } else {
            facingRight = true;
            updateImage(bossPics[0]);
        }

        return facingRight;
    }

    public void castKnife() {
        Gui.getInstance().knifeFX();
        if (!facingRight()) {                                         //decide which way is enemy faced

            knife2 = new Knife("knife2", "knife_left.png", 22, 11);
            Game.getInstance().addToWorldWeapons(knife2, getX(), Game.getInstance().getFLOOR_COORDINATE(),
                    -5, 0, 1.0);        //add only one object with a speed to the world because of a counter(to avoid duplicate children error)

        }
        if (facingRight()) {

            knife2 = new Knife("knife2", "knife_right.png", 22, 11);
            Game.getInstance().addToWorldWeapons(knife2, getX(), Game.getInstance().getFLOOR_COORDINATE(),
                    5, 0, 1.0);
        }
    }

    public void castSecondKnife() {
        Gui.getInstance().knifeFX();
        if (!facingRight()) {
            knife3 = new Knife("knife3", "knife_left.png", 22, 11);
            Game.getInstance().addToWorldWeapons(knife3, getX(), Game.getInstance().getFLOOR_COORDINATE() + 50, -5, 0, 1.0);
        }
        if (facingRight())  {
            knife3 = new Knife("knife3", "knife_right.png", 22, 11);
            Game.getInstance().addToWorldWeapons(knife3, getX(), Game.getInstance().getFLOOR_COORDINATE() + 50, 5, 0, 1.0);
        }
        }
    }