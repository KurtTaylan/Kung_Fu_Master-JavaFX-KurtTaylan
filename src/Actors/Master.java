

package Actors;

import GameFrameworkJavaFX.Game;
import GameFrameworkJavaFX.Gui;
import java.util.ArrayList;
import javafx.scene.image.Image;
/**
 *
 * @author Taylan Kurt
 */

public class Master extends Actor {

    private boolean jumping;
    private boolean crouches = false;                                           //flag to check if the hero crouches
    private boolean jumps = false;
    private boolean punching = false;                                           //if master made a punch attack
    private boolean kicking = false; 
    
    private final int MAXIMUM_LIFE = 3;
    private final int MAXIMUM_HITPOINTS= 100;
    private int life = getMAXIMUM_LIFE();
    
    private final int masterPunchDmg = 1;                                       //ammount of damage master does to enemies with a punch
    private final  int masterKickDmg = 2;                                       //ammount of damage master does to enemies with a kick
    
    private int originalY;
    private final Image image[] = new Image[22];
    private int animationTimeCounter = 1;
    private int JumpCounter = 0;
    private boolean lastPosition = true;
    private int timeCounter = 0;
    private Shuriken shuriken;
    private int ammo = 10;

    public Master(String name, String filename, int sizeX, int sizeY) {
        super(name, filename, sizeX, sizeY);
        image[0] = new Image(getClass().getResourceAsStream("/resources/" + "walking_left.png"));
        image[1] = new Image(getClass().getResourceAsStream("/resources/" + "walking_left2.png"));
        image[2] = new Image(getClass().getResourceAsStream("/resources/" + "walking_right.png"));
        image[3] = new Image(getClass().getResourceAsStream("/resources/" + "walking_right2.png"));
        image[4] = new Image(getClass().getResourceAsStream("/resources/" + "jump_left.png"));
        image[5] = new Image(getClass().getResourceAsStream("/resources/" + "jump_right.png"));
        image[6] = new Image(getClass().getResourceAsStream("/resources/" + "standing_left.png"));
        image[7] = new Image(getClass().getResourceAsStream("/resources/" + "standing_right.png"));
        image[8] = new Image(getClass().getResourceAsStream("/resources/" + "duck_left.png"));
        image[9] = new Image(getClass().getResourceAsStream("/resources/" + "duck_right.png"));
        image[10] = new Image(getClass().getResourceAsStream("/resources/" + "duck_punch_left.png"));
        image[11] = new Image(getClass().getResourceAsStream("/resources/" + "duck_punch_right.png"));
        image[12] = new Image(getClass().getResourceAsStream("/resources/" + "jump_punch_left.png"));
        image[13] = new Image(getClass().getResourceAsStream("/resources/" + "jump_punch_right.png"));
        image[14] = new Image(getClass().getResourceAsStream("/resources/" + "punch_left.png"));
        image[15] = new Image(getClass().getResourceAsStream("/resources/" + "punch_right.png"));
        image[16] = new Image(getClass().getResourceAsStream("/resources/" + "duck_kick_left.png"));
        image[17] = new Image(getClass().getResourceAsStream("/resources/" + "duck_kick_right.png"));
        image[18] = new Image(getClass().getResourceAsStream("/resources/" + "jump_kick_left.png"));
        image[19] = new Image(getClass().getResourceAsStream("/resources/" + "jump_kick_right.png"));
        image[20] = new Image(getClass().getResourceAsStream("/resources/" + "kick_left.png"));
        image[21] = new Image(getClass().getResourceAsStream("/resources/" + "kick_right.png"));
        
        this.setHitpoints(MAXIMUM_HITPOINTS);
    }

    @Override
    public void timeTick() {
        super.timeTick();
        if (jumping) {
            int[] jumpingY = {-25, -45, -55, -55, -80, -80, -80, -80, -80, -80, 
                -80, -55, -55, -45, -25, 0};

            setY(originalY + jumpingY[JumpCounter]);
            JumpCounter++;
            if (JumpCounter == jumpingY.length) {
                jumping = false;
                JumpCounter = 0;
            }
        }
 
        masterAttack(Game.getInstance().getEnemies());
    }

    public void setRotationBasedOnSpeed(){
        if (getSpeedY()>0){
            getImageView().setRotate(90);
            getImageView().setScaleX(1);
        }
        else if (getSpeedY()<0){
            getImageView().setRotate(270);
            getImageView().setScaleX(1);
        }
        else if (getSpeedX()>0){
            getImageView().setRotate(0);
            getImageView().setScaleX(1);
        }
        else if (getSpeedX()<0){
            getImageView().setRotate(0);
            getImageView().setScaleX(-1);
        }
    }
    
    public void animateLeft() {
        animationTimeCounter++;
        if (animationTimeCounter == 1) {
            updateImage(image[0]);
        } else if (animationTimeCounter == 3) {
            updateImage(image[1]);
            animationTimeCounter = 0;                                           //animationCounter to 1
        }
    }

    public void animateRight() {                                                //method for animation when walking right
        animationTimeCounter++;
        if (animationTimeCounter == 1) {
            updateImage(image[2]);
        } else if (animationTimeCounter == 3) {
            updateImage(image[3]);
            animationTimeCounter = 0;                                           //animationCounter to 1
        }
    }

    public void startJumping() {
        if (jumping) {
            return;
        }
        jumping = true;
        originalY = getY();
    }
    
    public void heroJump(){
        if (isLastPosition()) {
            updateImage(image[4]);
            startJumping();
            updateImage(image[6]);
        } else {
            updateImage(image[5]);
            startJumping();
            updateImage(image[7]);
        }
    }
       
    public void heroCrouch(){
        if (isLastPosition()) {
            updateImage(image[8]);
            setSize(50,70 );
            setY(600);
        } else {
            updateImage(image[9]);
            setSize(50,70 );
            setY(600);
        }


    }
    
    public void heroLeft(){
        setY(Game.getInstance().getFLOOR_COORDINATE());
        animateLeft();
        setSpeedX(-3);
        lastPosition = true;
    }
    
    public void heroRight(){
        setY(Game.getInstance().getFLOOR_COORDINATE());
        animateRight();
        setSpeedX(3);
        lastPosition = false;
        
    }
  
    public boolean isLastPosition() {
        return lastPosition;
    }
    
    public boolean isCrouches() {
        return crouches;
    }

    public void setCrouches(boolean crouches) {
        this.crouches = crouches;
    }

    public boolean isJumps() {
        return jumps;
    }

    public void setJumps(boolean jumps) {
        this.jumps = jumps;
    }
    
    public void punchAttackImage(){
        if (isCrouches()) {
                if (isLastPosition()) {  //true for left false for right
                    updateImage(image[10]);                    
                    //setCrouches(false);
                } else if (!isLastPosition()) {
                    updateImage(image[11]);
                    //setCrouches(false);
                }
            } else if (isJumps()) {
                if (isLastPosition()) {
                    updateImage(image[12]);
                    setJumps(false);

                } else if (!isLastPosition()) {
                    updateImage(image[13]);
                    setJumps(false);
                }
            } else {
                if (isLastPosition()) {  //true for left false for right
                    updateImage(image[14]);

                } else if (!isLastPosition()) {
                    updateImage(image[15]);

                }
            }
    }
    
    public void kickAttackImage(){
        if (isCrouches()) {
                if (isLastPosition()) {  //true for left false for right
                    updateImage(image[16]);
                } else if (!isLastPosition()) {
                    updateImage(image[17]);

                }
            } else if (isJumps()) {
                if (isLastPosition()) {
                    updateImage(image[18]);
                    setJumps(false);

                } else if (!isLastPosition()) {
                    
                    updateImage(image[19]);
                    setJumps(false);
                }
            } else {
                if (isLastPosition()) {  //true for left false for right
                    updateImage(image[20]);

                } else if (!isLastPosition()) {
                    updateImage(image[21]);

                }
            }
    }
    
    public void standLeft(){
        updateImage(image[6]);
    }
    
    public void standRight(){
        updateImage(image[7]);
    }
   
    public void masterAttack(ArrayList<Actor> Enemies){
        for (Actor boogie : Enemies) { 
            //check if Hero has punched the enemy (if his arm has collided with the enemy body)
                if (isPunching()) {
                if (!isLastPosition()) {//check if the hero is facing right                    
                    if (punchesTheRight(boogie)) {
                        setPunching(false);
                        if(boogie instanceof Dwarf){
                            Dwarf dwarf = (Dwarf)boogie;
                            if(!dwarf.isAttack()){
                                dwarf.setX(dwarf.getX() + 30); //if the enemy is hit he steps back 3 pixels
                                dwarf.damage(masterPunchDmg);
                                Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                                Gui.getInstance().punchFX();
                            }
                        }else if(!(boogie instanceof Dwarf)){
                            boogie.setX(boogie.getX() + 30); //if the enemy is hit he steps back 3 pixels
                            boogie.damage(masterPunchDmg);
                            Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                            Gui.getInstance().punchFX();
                            
                        }else if(boogie instanceof Henchman){
                            boogie.setX(boogie.getX() + 30); //if the enemy is hit he steps back 3 pixels
                            boogie.damage(masterPunchDmg);
                            Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                            Gui.getInstance().punchFX();
                        
                        }
                        if (boogie.getHitpoints() <= 0) {
                            Game.getInstance().removeEnemyFromWorld(boogie);
                            Game.getInstance().calculateScore(boogie);          //Everytime an enemy is defeated score points are added
                            break;
                        } 
                    }
                }else if (isLastPosition()) {//check if the hero is facing left
                    if (punchesTheLeft(boogie)) {
                        setPunching(false);
                        if(boogie instanceof Dwarf){
                            Dwarf dwarf = (Dwarf)boogie;
                            if(!dwarf.isAttack()){
                                dwarf.setX(dwarf.getX() - 30); //if the enemy is hit he steps back 3 pixels
                                dwarf.damage(masterPunchDmg);
                                Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                                Gui.getInstance().punchFX();
                            }
                        }else if(!(boogie instanceof Dwarf)){
                            boogie.setX(boogie.getX() - 30); //if the enemy is hit he steps back 3 pixels
                            boogie.damage(masterPunchDmg);
                            Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                            Gui.getInstance().punchFX();
                        }else if(boogie instanceof Henchman){
                            boogie.setX(boogie.getX() + 30); //if the enemy is hit he steps back 3 pixels
                            boogie.damage(masterPunchDmg);
                            Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                            Gui.getInstance().punchFX();
                        }                       
                        if (boogie.getHitpoints() <= 0) {
                            Game.getInstance().removeEnemyFromWorld(boogie);
                            Game.getInstance().calculateScore(boogie);          //Everytime an enemy is defeated score points are added
                            break;
                        } 
                    }
                }
            } 
            
            if (isKicking()) {
                //kicking = false;
                if (!isLastPosition()) { //check if the hero is facing right
                    if (kicksTheRight(boogie)) {
                        setKicking(false);
                        if(boogie instanceof Dwarf){
                            Dwarf dwarf = (Dwarf)boogie;
                            if(!dwarf.isAttack()){
                                dwarf.setX(boogie.getX() + 30); //if the enemy is hit he steps back 3 pixels
                                dwarf.damage(masterKickDmg);
                                Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                                Gui.getInstance().kickFX();
                            }
                        }else if(!(boogie instanceof Dwarf)){
                            boogie.setX(boogie.getX() + 30); //if the enemy is hit he steps back 3 pixels
                            boogie.damage(masterKickDmg);
                            Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                            Gui.getInstance().kickFX();
                        }
                        else if(boogie instanceof Henchman){
                            boogie.setX(boogie.getX() + 30); //if the enemy is hit he steps back 3 pixels
                            boogie.damage(masterPunchDmg);
                            Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                            Gui.getInstance().kickFX();
                        
                        }
                        if (boogie.getHitpoints() <= 0) {
                            Game.getInstance().removeEnemyFromWorld(boogie);
                            Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                            Game.getInstance().calculateScore(boogie);
                            break;
                        } 

                    }
                } else if (isLastPosition()) {//check if the hero is facing left
                    if (kicksTheLeft(boogie)) {
                        setKicking(false);
                         if(boogie instanceof Dwarf){
                            Dwarf dwarf = (Dwarf)boogie;
                            if(!dwarf.isAttack()){
                                dwarf.setX(boogie.getX() - 30); //if the enemy is hit he steps back 3 pixels
                                dwarf.damage(masterKickDmg);
                                Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                                Gui.getInstance().kickFX();
                            }
                        }else if(!(boogie instanceof Dwarf)){
                            boogie.setX(boogie.getX() - 30); //if the enemy is hit he steps back 3 pixels
                            boogie.damage(masterKickDmg);
                            Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                            Gui.getInstance().kickFX();
                        }else if(boogie instanceof Henchman){
                            boogie.setX(boogie.getX() + 30); //if the enemy is hit he steps back 3 pixels
                            boogie.damage(masterPunchDmg);
                            Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                            Gui.getInstance().kickFX();
                        
                        }
                         
                        if (boogie.getHitpoints() <= 0) {
                            Game.getInstance().removeEnemyFromWorld(boogie);
                            Game.getInstance().calculateScore(boogie);
                            break;
                        } 
                    }
                }
            }               
        }
    }
    
    public boolean isPunching() {
        return punching;
    }

    public void setPunching(boolean punching) {
        this.punching = punching;
    }

    public boolean isKicking() {
        return kicking;
    }

    public void setKicking(boolean kicking) {
        this.kicking = kicking;
    }

    /**
     * @return the life
     */
    public int getLife() {
        return life;
    }

    /**
     * @param life the life to set
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * @return the MAXIMUM_LIFE
     */
    public int getMAXIMUM_LIFE() {
        return MAXIMUM_LIFE;
    }

    /**
     * @return the MAXIMUM_HITPOINTS
     */
    public int getMAXIMUM_HITPOINTS() {
        return MAXIMUM_HITPOINTS;
    }
     public void throwShuriken(){
         if (getAmmo()>0){
            if (isCrouches()) {
                if (isLastPosition()) {  //true for left false for right
                    updateImage(image[10]);
                    Gui.getInstance().knifeFX();
                    shuriken = new Shuriken("shuriken", "ShurikenSmall.png", 22, 22);
                    Game.getInstance().addToWorldWeapons(shuriken, getX(), Game.getInstance().getFLOOR_COORDINATE()+45,
                    -5, 0, 1.0);        //add only one object with a speed to the world because of a counter(to avoid duplicate children error)

                } else if (!isLastPosition()) {
                    updateImage(image[11]);
                    Gui.getInstance().knifeFX();
                    shuriken = new Shuriken("shuriken", "ShurikenSmall.png", 22, 22);
                    Game.getInstance().addToWorldWeapons(shuriken, getX(), Game.getInstance().getFLOOR_COORDINATE()+45,
                    5, 0, 1.0);
                }
            } else if (isJumps()) {
                if (isLastPosition()) {
                    updateImage(image[12]);
                    setJumps(false);
                    Gui.getInstance().knifeFX();
                    shuriken = new Shuriken("shuriken", "ShurikenSmall.png", 22, 22);
                    Game.getInstance().addToWorldWeapons(shuriken, getX(), Game.getInstance().getFLOOR_COORDINATE()-50,
                    -5, 0, 1.0);        //add only one object with a speed to the world because of a counter(to avoid duplicate children error)

                } else if (!isLastPosition()) {
                    updateImage(image[13]);
                    setJumps(false);
                    Gui.getInstance().knifeFX();
                    shuriken = new Shuriken("shuriken", "ShurikenSmall.png", 22, 22);
                    Game.getInstance().addToWorldWeapons(shuriken, getX(), Game.getInstance().getFLOOR_COORDINATE()-50,
                    5, 0, 1.0);
                }
            } else {
                if (isLastPosition()) {  //true for left false for right
                    updateImage(image[14]);
                    Gui.getInstance().knifeFX();
                    shuriken = new Shuriken("shuriken", "ShurikenSmall.png", 22, 22);
                    Game.getInstance().addToWorldWeapons(shuriken, getX(), Game.getInstance().getFLOOR_COORDINATE(),
                    -5, 0, 1.0);        //add only one object with a speed to the world because of a counter(to avoid duplicate children error)

                } else if (!isLastPosition()) {
                    updateImage(image[15]);
                    Gui.getInstance().knifeFX();
                    shuriken = new Shuriken("shuriken", "ShurikenSmall.png", 22, 22);
                    Game.getInstance().addToWorldWeapons(shuriken, getX(), Game.getInstance().getFLOOR_COORDINATE(),
                    5, 0, 1.0);
                }
            }
            setAmmo(getAmmo() - 1);
         }
         else;
        
    }

    /**
     * @param ammo the ammo to set
     */
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    /**
     * @return the ammo
     */
    public int getAmmo() {
        return ammo;
    }
}
