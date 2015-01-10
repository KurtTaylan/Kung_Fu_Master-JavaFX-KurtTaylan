/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import GameFrameworkJavaFX.Game;
import GameFrameworkJavaFX.Gui;
import static java.lang.Math.abs;
import java.security.SecureRandom;
import javafx.scene.image.Image;

/**
 *
 * @author Taylan Kurt
 */
public class Boss2 extends Actor {

    private boolean facingRight;
    private boolean moveMode = true;
    private boolean attackMode = false;
    private boolean waitMode = false;
    private boolean superMode = false;
    
    private Fireball fireBall;
    
    private int timePauseCounter = 0;
    private final Image[] enemyPics = new Image[9];
    private SecureRandom rand = new SecureRandom();
    private final int MAXIMUM_HITPOINTS = 50;
    private final int attackDmg = 1;
    private final int superDmg= 7;
    private int animationTimeCounter = 0;
    private int animationAttackCounter = 0;

    public Boss2(String name, String filename, int sizeX, int sizeY) {
        super(name, filename, sizeX, sizeY);
        enemyPics[0] = new Image(getClass().getResourceAsStream("/resources/" + "Boss2.png"));
        enemyPics[1] = new Image(getClass().getResourceAsStream("/resources/" + "Boss2.png"));
        enemyPics[2] = new Image(getClass().getResourceAsStream("/resources/" + "Boss2.png"));
        enemyPics[3] = new Image(getClass().getResourceAsStream("/resources/" + "Boss2.png"));
        enemyPics[4] = new Image(getClass().getResourceAsStream("/resources/" + "Boss2.png"));
        enemyPics[5] = new Image(getClass().getResourceAsStream("/resources/" + "Boss2.png"));
        enemyPics[6] = new Image(getClass().getResourceAsStream("/resources/" + "Boss2.png"));
        enemyPics[7] = new Image(getClass().getResourceAsStream("/resources/" + "Boss2.png"));
        enemyPics[8] = new Image(getClass().getResourceAsStream("/resources/" + "SuperAttack.png"));
        
        this.setHitpoints(MAXIMUM_HITPOINTS);
    }

    @Override
    public void timeTick() {
        super.timeTick();
        
        if(this.getHitpoints()>40){
            
            dwarfAttack();
            bossColidesWith();
        }else if(this.getHitpoints()>4){
            this.setMove(false);
            this.setAttack(false);
            this.setWaitMode(false);
            this.setSuperMode(false);
            projectileAttack();
        }else{
            this.setMove(false);
            this.setAttack(false);
            this.setWaitMode(false);
            this.setSuperMode(true);
            superAttack();
            bossColidesWith();
        }
            
        
       
    }

    public boolean facingRight() {                                               //method to know which side an enemy is face regards master
        if (Game.getInstance().master.getX() < getX()) {
            facingRight = false;
            this.getImageView().setScaleX(-1);
        } else {
            facingRight = true;
            this.getImageView().setScaleX(1);
        }
        return facingRight;
    }

    public void dwarfAttack() {
        if(!moveMode && !attackMode && !waitMode && this.atHorizontalEdge()){
            facingRight();
            if(facingRight){
                this.updateImage(enemyPics[1]);
            }else if(!facingRight){
                this.updateImage(enemyPics[0]);
            }
            this.setWaitMode(false);
            setSpeedX(0);
            facingRight();
            timePauseCounter++;
            if(timePauseCounter >= 600+rand.nextInt(700)){
 
                this.setAttack(false);
                this.setMove(true);
                
                timePauseCounter=0;
            }
            
        }
        if(moveMode){
            facingRight();
            if(facingRight){
                setSpeedX(3);
                animateWalking();
                if(abs(getX()-Game.getInstance().master.getX()) <= 300){
                    this.setMove(false);
                    this.setAttack(true);
                }    
            } 
            if(!facingRight){
                setSpeedX(-3);
                animateWalking();
                if(abs(getX()-Game.getInstance().master.getX()) <= 300){
                    this.setMove(false);
                    this.setAttack(true);
                }
            }
        }
        if(attackMode){
            animateAttack();
            if(facingRight){
                setSpeedX(10);
                if(this.atHorizontalEdge()){                
                    updateImage(enemyPics[0]);
                    this.setWaitMode(true);
                    this.setAttack(false);
                }
            }
            if (!facingRight){
                setSpeedX(-10);
                if(this.atHorizontalEdge()){
                    updateImage(enemyPics[1]);
                    this.setWaitMode(true);
                    this.setAttack(false);
                }
            }
        }
        if (waitMode){
            timePauseCounter++;
            if(timePauseCounter> 150+rand.nextInt(400)){                       
                this.setAttack(false);
                this.setMove(true);
                facingRight = false;
                timePauseCounter=0;
            }
        }
    }
    
    public void projectileAttack(){
        
        timePauseCounter++;
        if(abs(getX()-Game.getInstance().master.getX()) >= 50){
                if(timePauseCounter==200){
                    Gui.getInstance().fireballFX();
                    if (!facingRight()) {                                         //decide which way is enemy faced
                        updateImage(enemyPics[2]);
                        fireBall = new Fireball("fireball", "Fireball.png", 22, 11);
                        Game.getInstance().addToWorldWeapons(fireBall, getX(), Game.getInstance().getFLOOR_COORDINATE()+45,
                            -8, 0, 1.0);        //add only one object with a speed to the world because of a counter(to avoid duplicate children error)

                    }
                    if (facingRight()) {
                        updateImage(enemyPics[3]);
                        fireBall = new Fireball("fireball", "Fireball.png", 22, 11);
                        Game.getInstance().addToWorldWeapons(fireBall, getX(), Game.getInstance().getFLOOR_COORDINATE()+45,
                        8, 0, 1.0);
                    }
                }
                if(timePauseCounter==240){
                    Gui.getInstance().fireballFX();
           
                    if (!facingRight()) {                                         //decide which way is enemy faced
                        updateImage(enemyPics[2]);
                        fireBall = new Fireball("knife", "Fireball.png", 22, 11);
                        Game.getInstance().addToWorldWeapons(fireBall, getX(), Game.getInstance().getFLOOR_COORDINATE()+20,
                            -8, 0, 1.0);        //add only one object with a speed to the world because of a counter(to avoid duplicate children error)

                    }       
                    if (facingRight()) {
                        updateImage(enemyPics[3]);
                        fireBall = new Fireball("knife", "Fireball.png", 22, 11);
                        Game.getInstance().addToWorldWeapons(fireBall, getX(), Game.getInstance().getFLOOR_COORDINATE()+20,
                            8, 0, 1.0);
                    }   
                timePauseCounter=0;
                }       
        }   
    }
    
    public void superAttack(){
        facingRight();
            timePauseCounter++;
            if(timePauseCounter<500){
                if(facingRight && this.atHorizontalEdge()){
                    this.updateImage(enemyPics[8]);
                    this.setSize(66, 64);
                    this.setY(Game.getInstance().getFLOOR_COORDINATE()-30);
                    setSpeedX(10);
                    
                }
                if (!facingRight && this.atHorizontalEdge()){
                    this.updateImage(enemyPics[8]);
                    this.setSize(100, 100);
                    this.setY(Game.getInstance().getFLOOR_COORDINATE()+20);
                    setSpeedX(-10);
                    
                }
                timePauseCounter=0;
            }else
                this.setSpeedX(0);     
    }
    
    public void bossColidesWith(){
        if(this.collidesWith(Game.getInstance().master)){  
            Gui.getInstance().painFX();
            
            if(this.isAttack()){
                Game.getInstance().master.damage(attackDmg);
                Gui.getInstance().kickFX();
                this.setWaitMode(true);
            }else if(this.isSuperMode()){
                
                this.setSuperMode(false);
                Game.getInstance().master.damage(superDmg);
                Gui.getInstance().explosionFX();
                
                
            }
            
            //this.setAttack(false);
            
            if (Game.getInstance().master.getHitpoints() <= 0) {
                Game.getInstance().master.setLife(Game.getInstance().master.getLife() - 1); //remove one life
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS()); //reset hitpoints
                
                    
                if (Game.getInstance().master.getLife() <= 0) {
                    Game.getInstance().gameInformation();
                    Gui.getInstance().showLifeLabel(Game.getInstance().master.getLife());
                    Game.getInstance().removeHeroFromWorld(Game.getInstance().master);
                }
            }
        }
    }
    
    /**
     * @return the move
     */
    public boolean isMove() {
        return moveMode;
    }
    /**
     * @param move the move to set
     */
    public void setMove(boolean move) {
        this.moveMode = move;
    }
    /**
     * @return the attack
     */
    public boolean isAttack() {
        return attackMode;
    }
    /**
     * @param attack the attack to set
     */
    public void setAttack(boolean attack) {
        this.attackMode = attack;
    }

    /**
     * @return the waitMode
     */
    public boolean isWaitMode() {
        return waitMode;
    }

    /**
     * @param waitMode the waitMode to set
     */
    public void setWaitMode(boolean waitMode) {
        this.waitMode = waitMode;
    }
    
    public void animateWalking() {
        if (!facingRight) {
            animationTimeCounter++;
            if (animationTimeCounter == 2) {
                updateImage(enemyPics[0]);
            } else if (animationTimeCounter == 4) {
                updateImage(enemyPics[2]);
                animationTimeCounter = 0;                                           //animationCounter to 1
            }
        } else{
            animationTimeCounter++;
            if (animationTimeCounter == 2) {
                updateImage(enemyPics[1]);
            } else if (animationTimeCounter == 4) {
                updateImage(enemyPics[3]);
                animationTimeCounter = 0;                                           //animationCounter to 1
            }
        }
    }
    public void animateAttack(){
        animationAttackCounter++;
        if (animationAttackCounter == 1){
            updateImage(enemyPics[4]);
        }else if (animationAttackCounter == 2){
            updateImage(enemyPics[5]);
        } else if (animationAttackCounter == 3){
            updateImage(enemyPics[6]);
        } else if (animationAttackCounter == 4){
            updateImage(enemyPics[7]);
            animationAttackCounter = 0;
        }
    }

    /**
     * @return the superMode
     */
    public boolean isSuperMode() {
        return superMode;
    }
    /**
     * @param superMode the superMode to set
     */
    public void setSuperMode(boolean superMode) {
        this.superMode = superMode;
    }
}
