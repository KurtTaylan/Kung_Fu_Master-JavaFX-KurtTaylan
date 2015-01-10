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
public class KnifeThrower extends Actor {

    //Master master;
    private Knife knife;
    private boolean facingRight;
    private boolean movementStrategyFlag = true;
    private int timePauseCounter = 0;
    private final Image[] enemyPics = new Image[6];
    private final int MAXIMUM_HITPOINTS = 3;
    private int animationTimeCounter;

    public KnifeThrower(String name, String filename, int sizeX, int sizeY) {
        super(name, filename, sizeX, sizeY);
        enemyPics[0] = new Image(getClass().getResourceAsStream("/resources/" + "knifethrower_left.png"));
        enemyPics[1] = new Image(getClass().getResourceAsStream("/resources/" + "knifethrower_right.png"));
        enemyPics[2] = new Image(getClass().getResourceAsStream("/resources/" + "knifeThrower_throw_knife_left.png"));
        enemyPics[3] = new Image(getClass().getResourceAsStream("/resources/" + "knifeThrower_throw_knife_right.png"));
        enemyPics[4] = new Image(getClass().getResourceAsStream("/resources/" + "knifethrower_walking_left.png"));
        enemyPics[5] = new Image(getClass().getResourceAsStream("/resources/" + "knifethrower_walking_right.png"));

        this.setHitpoints(MAXIMUM_HITPOINTS);
    }

    @Override
    public void timeTick() {
        super.timeTick();
        doEnemyMovementStrategy();

    }

    public boolean facingRight() {                                               //method to know which side an enemy is face regards master
        if (Game.getInstance().master.getX() < getX()) {
            facingRight = false;
//            updateImage(enemyPics[0]);

        } else {
            facingRight = true;
//            updateImage(enemyPics[1]);
        }

        return facingRight;
    }

    public void castKnife() {
        Gui.getInstance().knifeFX();
        if (!facingRight()) {                                         //decide which way is enemy faced
            updateImage(enemyPics[2]);
            knife = new Knife("knife", "knife_left.png", 22, 11);
            Game.getInstance().addToWorldWeapons(knife, getX(), Game.getInstance().getFLOOR_COORDINATE(),
                    -5, 0, 1.0);        //add only one object with a speed to the world because of a counter(to avoid duplicate children error)

        }
        if (facingRight()) {
            updateImage(enemyPics[3]);
            knife = new Knife("knife", "knife_right.png", 22, 11);
            Game.getInstance().addToWorldWeapons(knife, getX(), Game.getInstance().getFLOOR_COORDINATE(),
                    5, 0, 1.0);
        }
    }

    public void doEnemyMovementStrategy() {
        facingRight();
        if (!facingRight) {
            if (movementStrategyFlag) {
                setSpeedX(-3);
                animateWalkingLeft();
                if ((getX() - Game.getInstance().master.getX()) < 200) {
                    setSpeedX(0);
                    updateImage(enemyPics[2]);
                    movementStrategyFlag = false;
                }
            }
            if (!movementStrategyFlag) {
                timePauseCounter++;
                if (timePauseCounter == 1) {
                    castKnife();
                }
                if (timePauseCounter > 80) {
                    setSpeedX(2);
                    animateWalkingRight();
                    if (atHorizontalEdge()) {
                        timePauseCounter = 0;
                        movementStrategyFlag = true;
                    }
                }
                if ((getX() - Game.getInstance().master.getX()) > 500) {
                    timePauseCounter = 0;
                    movementStrategyFlag = true;
                }
            }

        }
        if (facingRight) {
            if (movementStrategyFlag) {
                setSpeedX(3);
                animateWalkingRight();
                if ((Game.getInstance().master.getX() - getX()) < 200) {
                    setSpeedX(0);
                    updateImage(enemyPics[3]);
                    movementStrategyFlag = false;
                }
            }
            if (!movementStrategyFlag) {
                timePauseCounter++;
                if (timePauseCounter == 1) {
                    castKnife();
                }
                if (timePauseCounter > 80) {
                    setSpeedX(-2);
                    animateWalkingLeft();
                    if (atHorizontalEdge()) {
                        timePauseCounter = 0;
                        movementStrategyFlag = true;
                    }
                }
                if ((Game.getInstance().master.getX() - getX()) > 500) {
                    timePauseCounter = 0;
                    movementStrategyFlag = true;
                }
            }
        }
    }

    public void animateWalkingLeft() {
        animationTimeCounter++;
        if (animationTimeCounter == 2) {
            updateImage(enemyPics[0]);
        } else if (animationTimeCounter == 4) {
            updateImage(enemyPics[4]);
            animationTimeCounter = 0;                                           //animationCounter to 1
        }

    }

    public void animateWalkingRight() {
        animationTimeCounter++;
        if (animationTimeCounter == 2) {
            updateImage(enemyPics[1]);
        } else if (animationTimeCounter == 4) {
            updateImage(enemyPics[5]);
            animationTimeCounter = 0;                                           //animationCounter to 1
        }
    }
    
   

}
