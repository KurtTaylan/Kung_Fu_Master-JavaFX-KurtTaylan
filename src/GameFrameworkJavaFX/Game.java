/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameFrameworkJavaFX;

import Actors.Actor;
import Actors.Boss;
import Actors.Boss2;
import Actors.Boss3;
import Actors.Dwarf;
import Actors.Henchman;
import Actors.KnifeThrower;
import Actors.Master;
import Actors.TreassureChest;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Taylan Kurt
 */
public class Game {

    private static Game game; //A reference to the singleton game instance.
    private final Gui gui;
    private GameState gameState = GameState.TitleScreen;

    public Master master;
    private Boss boss;
    private Boss2 boss2;
    private Boss3 boss3;
    private KnifeThrower enemy,enemy2,enemy3;
    private Dwarf dwarf1, dwarf2, dwarf3;
    private Henchman henchman1, henchman2, henchman3 ;
    private TreassureChest chest;
    private final ArrayList<Actor> enemies;                                     // array of all enemies
    private final ArrayList<Actor> heros;                                      //array list to store Hero
    private final ArrayList<Actor> weapons;
    private final ArrayList<Actor> treasureChests;
    
    private int screenCounter = 1;                                              //keeps track the sceern of a level
    private int lvlCounter = 1;                                                 //keeps track of the level
    private boolean defeatedBoss = false;
    private final int MAXIMUM_TIME = 180000;
    private int time = getMAXIMUM_TIME(); 
    private int score;
    private final int ENEMY_POINTS = 100;
    private final int BOSS_POINTS = 500;                                        //time limit to complete the level
    private int enemieCounter;                                                  //counter used to add knifeCasters 1s after the last one is dead
    private final int FLOOR_COORDINATE = 560;
    private boolean addEnemies = true;
 
    private Game(Gui gui) {
        this.gui = gui;
        heros = new ArrayList();
        weapons = new ArrayList();
        //fireBall = new ArrayList();
        treasureChests = new ArrayList<>();
        enemies = new ArrayList();
        createBosses();
    }

    public static Game getInstance() {
        return game;
    }

    public static void createGameAndSetGui(Gui gui) {
        
        game = new Game(gui);
    }

    public ArrayList<Actor> getMaster() {
        return getHeros();
    }                          
    public ArrayList<Actor> getWeapons() {
        return weapons;
    }
    public ArrayList<Actor> getHeros() {
        return heros;
    }
    public ArrayList<Actor> getEnemies() {
        return enemies;
    }
    public ArrayList<Actor> getTreasureChests() {
        return treasureChests;
    }
    /*
    *Adds enemies to the world at a specific location, with a specific speed.
    */
    public void addToWorldEnemies(Actor enemy, int x, int y, int xSpeed, int ySpeed, double collisionRadius) {
        enemy.setLocation(x, y);
        enemy.setSpeedX(xSpeed);
        enemy.setSpeedY(ySpeed);
        enemy.setCollisionRadius(collisionRadius);
        getEnemies().add(enemy);
        gui.addImageView(enemy.getImageView());

    }

    /*
    *Method to add the main character to the world
    */
    public void addToWorldMaster(Actor actor, int x, int y, double collisionRadius) {
        actor.setLocation(x, y);
        actor.setCollisionRadius(collisionRadius);                              //new method to add master
        getHeros().add(actor);                                                      //without speed    
        gui.addImageView(actor.getImageView());
    }

    /*
    *Method to add weapons to the world. 
    */
    public void addToWorldWeapons(Actor weapon, int x, int y, int xSpeed, int ySpeed, double collisionRadius) {
        weapon.setLocation(x, y);
        weapon.setSpeedX(xSpeed);
        weapon.setSpeedY(ySpeed);
        weapon.setCollisionRadius(collisionRadius);
        weapons.add(weapon);
        gui.addImageView(weapon.getImageView());
    }
    
    public void addToWorldTreasureChests(TreassureChest chest, int x, int y, int xSpeed, int ySpeed, double collisionRadius) {
        chest.setLocation(x, y);
        chest.setSpeedX(xSpeed);
        chest.setSpeedY(ySpeed);
        chest.setCollisionRadius(collisionRadius);
        treasureChests.add(chest);
        gui.addImageView(chest.getImageView());
    }

    /*
    *Method for removing the main character from the world
    *i.e in case he is defeated
    */
    public void removeHeroFromWorld(Actor actor) {
        getHeros().remove(actor);
        gui.removeImageView(actor.getImageView());
        gameOver();
    }
    
    /*
    *Method for removing weapons from the world
    *Used when a weapon reaches the edge of the screen or when it collides with an enemy or main character
    */
 
    public void removeWeaponFromWorld(Actor weapon) {
        weapons.remove(weapon);
        gui.removeImageView(weapon.getImageView());
    }
    public void removeTreasureChestsFromWorld(TreassureChest chest) {
        treasureChests.remove(chest);
        gui.removeImageView(chest.getImageView());
    }
    /*
    *Method for removing enemies from the world when they are defeated
    */
    public void removeEnemyFromWorld(Actor enemy) {
        getEnemies().remove(enemy);
        gui.removeImageView(enemy.getImageView());
    }
    
    public void removeAllEnemies(){
        for(Actor enemy : getEnemies()){
            gui.removeImageView(enemy.getImageView());  
        }
        for(Actor weapon : getWeapons()){
            gui.removeImageView(weapon.getImageView());
        }
        weapons.clear();
    }

    public final void createMaster() {
        System.out.println("Creating Kung Fu Master...");
        master = new Master("Master", "standing_left.png", 35, 98);             // Master creation should allways be first
        addToWorldMaster(master, 900, getFLOOR_COORDINATE(), 1.0);
    }

    public final void createEnemies() {
        System.out.println("Creating Enemies...");
        enemy = new KnifeThrower("Enemy", "knifethrower_right.png", 40, 88);
        enemy2 = new KnifeThrower("Enemy2", "knifethrower_left.png", 40, 88);
        enemy3 = new KnifeThrower("Enemy3", "knifethrower_left.png", 40, 88);

        dwarf1 = new Dwarf("Dwarf1", "dwarf_standing_left.png", 40, 37);
        dwarf2 = new Dwarf("Dwarf2", "dwarf_standing_left.png", 40, 37);
        dwarf3 = new Dwarf("Dwarf3", "dwarf_standing_left.png", 40, 37);
        
        henchman1 = new Henchman ("Henchman1", "henchman_standing_left.png", 40, 88 );          
        henchman2 = new Henchman ("Henchman2", "henchman_standing_left.png", 40, 88 );        
        henchman3 = new Henchman ("Henchman3", "henchman_standing_left.png", 40, 88 );        

    }

    public final void createBosses() {
        boss = new Boss("Boss", "Gilgamesh.png", 100, 100);
        boss2 = new Boss2("Boss2", "Boss2.png", 50,64);
        boss3 = new Boss3("Boss3", "ryu_standing_right.png", 70, 77 );
    }
    
    public final void createTreasureChests(String chestContent){
        chest = new TreassureChest("Chest", "chest.png", 60, 44, chestContent);
    }

    public void timeTick() {
        gameInformation();                                                      //Display all the labels with information
        //First all heros do their default actions
        for (Actor actor : getHeros()) {
            actor.timeTick();
        }
        /*
        *Checks if the specific object of the array
        *is not removed from the world
        */
        int arrayofWeaponssIndex = 0;
        while (arrayofWeaponssIndex < weapons.size()) {
            weapons.get(arrayofWeaponssIndex).timeTick();
            arrayofWeaponssIndex++;
        }       
        
        int arrayOfTreasureChest=0;
        while (arrayOfTreasureChest<treasureChests.size()){
            treasureChests.get(arrayOfTreasureChest).timeTick();
            arrayOfTreasureChest++;
        }
        
        for (Actor enemy :getEnemies()){
            enemy.timeTick();
        }        
        /*
        *checks level and screen and adds  enemies or bosses
        */
        if (getLvlCounter() == 1){   
            System.out.println("Curently on Level "+getLvlCounter()); 
            System.out.println(screenCounter+"st screen");
            if(screenCounter==1 && getEnemies().isEmpty() && master.getX()< 3){
                createEnemies();
                gui.setBackground("pane_lvlOneScreenTwo");
                getHeros().get(0).setX(1300);
                setAddEnemies(true);
                screenCounter++;
            }else if(screenCounter==2 && getEnemies().isEmpty() && master.getX()< 3){
                gui.setBackground("pane_lvlOneScreenThree");
                createEnemies();
                getHeros().get(0).setX(1300);
                setAddEnemies(true);
                screenCounter++;
            }else if(screenCounter==3){
                if(getEnemies().isEmpty() && enemieCounter==1 ){
                   addToWorldEnemies(boss, 100, getFLOOR_COORDINATE()-60,0,0, 1.0);
                   enemieCounter = 0;
                   defeatedBoss = true;
                }
                if(defeatedBoss && getEnemies().isEmpty()){
                createTreasureChests("life");
                addToWorldTreasureChests(chest, 600, getFLOOR_COORDINATE()+50, 0, 0, 1.0);   
                lvlCounter++;
                defeatedBoss = false;                 
                }   
            }
        }else if (getLvlCounter() == 2){     
            System.out.println("Curently on Level "+getLvlCounter()); 
            System.out.println(screenCounter+"st screen");
            if(screenCounter==1 && getEnemies().isEmpty() && master.getX()> 1200){
                gui.setBackground("pane_lvlTwoScreenTwo");
                createEnemies();
                getHeros().get(0).setX(5);
                setAddEnemies(true);
                screenCounter++;
            }else if(screenCounter==2 && getEnemies().isEmpty() && master.getX()> 1200){
                gui.setBackground("pane_lvlTwoScreenThree");
                createTreasureChests("shuriken");
                addToWorldTreasureChests(chest, 600, getFLOOR_COORDINATE()+50, 0, 0, 1.0);
                createEnemies();
                getHeros().get(0).setX(5);
                setAddEnemies(true);
                screenCounter++;
            }else if(screenCounter==3){
                if(getEnemies().isEmpty() && enemieCounter==2 ){
                   addToWorldEnemies(boss2, 1100, getFLOOR_COORDINATE()+40,0,0, 1.0);
                   enemieCounter = 0;
                   defeatedBoss = true;
                }
                if(defeatedBoss && getEnemies().isEmpty() && master.getX()> 1200){
                    createTreasureChests("life");
                    addToWorldTreasureChests(chest, 600, getFLOOR_COORDINATE()+50, 0, 0, 1.0);
                    lvlCounter++;
                    defeatedBoss = false;
                }      
            }
        }else if (getLvlCounter() == 3){     
            System.out.println("Curently on Level "+getLvlCounter()); 
            System.out.println(screenCounter+"st screen");
            if(screenCounter==1 && getEnemies().isEmpty() && master.getX()< 3){
                gui.setBackground("pane_lvlThreeScreenTwo");
                createEnemies();
                getHeros().get(0).setX(1250);
                setAddEnemies(true);
                screenCounter++;
            }else if(screenCounter==2 && getEnemies().isEmpty() && master.getX()<3){
                gui.setBackground("pane_lvlThreeScreenThree");
                createEnemies();
                getHeros().get(0).setX(1250);
                setAddEnemies(true);
                screenCounter++;
            }else if(screenCounter==3){
                if(getEnemies().isEmpty() && enemieCounter==3 ){
                   addToWorldEnemies(boss3, 10, getFLOOR_COORDINATE(),0,0, 1.0);
                   enemieCounter = 0;
                   defeatedBoss = true;
                }
                if(defeatedBoss && getEnemies().isEmpty()){
                    removeAllEnemies();
                    removeHeroFromWorld(master);
                    System.out.println("You saved the girl!!!");
                    setGameState(GameState.FinalScreen);
                }      
            }
        }
        
        if (addEnemies) {                                                       //adding enemies
            if (getLvlCounter() == 1) {
                addLevelOneEnemies();
            }
            if (getLvlCounter() == 2) {
                addLevelTwoEnemies();
            }
            if (getLvlCounter() == 3) {
                addLevelThreeEnemies();
            }
        }
    }

    public void addLevelOneEnemies() {
        System.out.println("Adding lvl 1 enemies...");
        enemieCounter++;
        if (enemieCounter == 33) {
            addToWorldEnemies(enemy, 1, getFLOOR_COORDINATE(), 1, 0, 1.0);    
        }
        if (enemieCounter == 66) {
            addToWorldEnemies(enemy2, 1200, getFLOOR_COORDINATE(), 0, 0, 1.0);
        }
        if (enemieCounter == 99) {
            addToWorldEnemies(enemy3, 1, getFLOOR_COORDINATE(), 0, 0, 1.0);
            setAddEnemies(false);
            enemieCounter = 1;
        }

    }

    public void addLevelTwoEnemies() {
        enemieCounter++;
        if (enemieCounter == 33) {
            addToWorldEnemies(enemy, 1200, getFLOOR_COORDINATE(), 1, 0, 1.0);
            addToWorldEnemies(dwarf1, 1200, getFLOOR_COORDINATE() + 45, 0, 0, 1.0);
        }
        if (enemieCounter == 66) {
            addToWorldEnemies(enemy2, 1200, getFLOOR_COORDINATE(), 0, 0, 1.0);
            addToWorldEnemies(dwarf2, 1, getFLOOR_COORDINATE() + 45, 0, 0, 1.0);
        }
        if (enemieCounter == 99) {
            addToWorldEnemies(enemy3, 1, getFLOOR_COORDINATE(), 0, 0, 1.0);
            addToWorldEnemies(dwarf3, 1, getFLOOR_COORDINATE() + 45, 0, 0, 1.0);
            setAddEnemies(false);
            enemieCounter = 2;
        }
    }

    public void addLevelThreeEnemies() {
        enemieCounter++;
        if (enemieCounter == 33) {
            addToWorldEnemies(enemy, 1200, getFLOOR_COORDINATE(), 1, 0, 1.0);
            //addToWorldEnemies(dwarf1, 1200, getFLOOR_COORDINATE() + 45, 0, 0, 1.0);
            addToWorldEnemies(henchman1, 200, getFLOOR_COORDINATE(), 1, 0, 1.8);
        }
        if (enemieCounter == 66) {
            //addToWorldEnemies(enemy2, 1200, getFLOOR_COORDINATE(), 0, 0, 1.0);
            addToWorldEnemies(dwarf2, 1, getFLOOR_COORDINATE() + 45, 0, 0, 1.0);
            addToWorldEnemies(henchman2, 100, getFLOOR_COORDINATE(), 1, 0, 1.8);
        }
        if (enemieCounter == 99) {
            addToWorldEnemies(enemy3, 1, getFLOOR_COORDINATE(), 0, 0, 1.0);
            addToWorldEnemies(dwarf3, 1, getFLOOR_COORDINATE() + 45, 0, 0, 1.0);
            addToWorldEnemies(henchman3, 300, getFLOOR_COORDINATE(), 1, 0, 1.8);
            setAddEnemies(false);
            enemieCounter = 3;
        }
    }

    public void gameInformation() {
        /**
         * Displays heros Life, Hitpoints, and Time Remaining to clear stage in
         * labels The timer counts downwards until the stage is cleared from
         * enemies in the third screen an the hero reaches at the Horizontal
         * left of the screen.
         */
            gui.showHitPointsLabel(master.getHitpoints());
            gui.showLifeLabel(master.getLife());
            gui.showScoreLabel();
            gui.showShurikenLabel();
            
            if (getTime() > 0) {
                setTime(getTime() - 20);
                gui.showEnergyLabelWithSeconds(getTime());
            } else if (getTime() <= 0) {
                master.setLife(master.getLife() - 1); //remove one life
                master.setHitpoints(master.getMAXIMUM_HITPOINTS()); //reset hitpoints
                setTime(master.getLife() * 60000);  //reborn with one minute less available
                master.setX(900); //reborn at this position   

                if (master.getLife() <= 0) {
                    gui.showLifeLabel(master.getLife());
                    gameOver();
                    gui.stopWorldTime();
                    removeHeroFromWorld(master);
                    gameOver();
                }
                gui.showEnergyLabelWithSeconds(getTime());
        }
    }

    public void keyPressed(KeyCode id) {
        System.out.println("You pressed key: " + id.toString());

        if (id == KeyCode.UP) {
            master.heroJump();
            master.setJumps(true);

        }
        if (id == KeyCode.DOWN) {
            master.heroCrouch();
            master.setSpeedX(0);
            master.setCrouches(true);

        }
        if (id == KeyCode.LEFT) {
            master.heroLeft();

        }
        if (id == KeyCode.RIGHT) {
            master.heroRight();

        }
        if (id == KeyCode.X) {
            master.setPunching(true);
            master.punchAttackImage();
        }
        if (id == KeyCode.Z) {
            master.setKicking(true);
            master.kickAttackImage();

        }
        if(id == KeyCode.SPACE){

            master.throwShuriken();
        }

    }

    public void keyReleased(KeyCode id) {
        System.out.println("You released key: " + id.toString());
        if (id == KeyCode.DOWN) {
            master.setCrouches(false);

            if (master.isLastPosition()) {
                master.standLeft();
                master.setY(getFLOOR_COORDINATE());

            } else {
                master.standRight();
                master.setY(getFLOOR_COORDINATE());
            }
        } else if (id == KeyCode.LEFT) {
            master.setSpeedX(0);

        } else if (id == KeyCode.RIGHT) {
            master.setSpeedX(0);

        } else if (id == KeyCode.X) {
            if (master.isLastPosition()) {
                master.setPunching(false);
                if (master.isCrouches()) {
                    master.heroCrouch();
                } else if (master.isJumps()) {
                    master.heroJump();
                } else {
                    master.standLeft();
                }
            } else {
                master.setPunching(false);
                if (master.isCrouches()) {
                    master.heroCrouch();
                } else if (master.isJumps()) {
                    master.heroJump();
                } else {
                    master.standRight();
                }
            }
        } else if (id == KeyCode.Z) {
            if (master.isLastPosition()) {
                master.setKicking(false);
                if (master.isCrouches()) {
                    master.heroCrouch();
                } else if (master.isJumps()) {
                    master.heroJump();
                } else {
                    master.standLeft();
                }
            } else {
                master.setKicking(false);
                if (master.isCrouches()) {
                    master.heroCrouch();
                } else if (master.isJumps()) {
                    master.heroJump();
                } else {
                    master.standRight();
                }
            }
        }else if (id == KeyCode.SPACE){
            if (master.isLastPosition()) {
                if (master.isCrouches()) {
                    master.heroCrouch();
                } else if (master.isJumps()) {
                    master.heroJump();
                } else {
                    master.standLeft();
                }
            } else {
                if (master.isCrouches()) {
                    master.heroCrouch();
                } else if (master.isJumps()) {
                    master.heroJump();
                } else {
                    master.standRight();
                }
            }
        }
    }

    public void mousePressed(boolean leftMouse, int x, int y) {
        System.out.printf("Mouse clicked %s, x = %d, y = %d\n", leftMouse ? "LEFT BUTTON" : "OTHER BUTTON", x, y);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        System.out.printf("Game State changed: %s -> %s\n", this.gameState.toString(), gameState.toString());
        this.gameState = gameState;
        
        //When updating game state, make sure the Gui does what it's supposed to...
        gui.updateFromState(gameState); 
    }
    
     public void gameOver(){
        removeAllEnemies(); 
        setGameState(GameState.GameOver);
        gui.stopWorldTime();
        setTime(getMAXIMUM_TIME());
       
    }
    /**
     * @return the screenCounter
     */
    public int getScreenCounter() {
        return screenCounter;
    }
    /**
     * @return the MAXIMUM_TIME
     */
    public int getMAXIMUM_TIME() {
        return MAXIMUM_TIME;
    }
    /**
     * @return the time
     */
    public int getTime() {
        return time;
    }
    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }
    /**
     * @return the FLOOR_COORDINATE
     */
    public int getFLOOR_COORDINATE() {
        return FLOOR_COORDINATE;
    }   
    public void calculateScore(Actor enemy){
        if((enemy instanceof Boss) || (enemy instanceof Boss2)){
            setScore(score+BOSS_POINTS);
        }else{     
            setScore(score+ENEMY_POINTS);
        }
        System.out.println(score);
    }    
    /**
    *Overloading Method calculate Score
    *takes an integer as parameter
    *to add to score the remaining time
    */
    public void calculateScore(int timeLeft){
        setScore(score + (timeLeft/1000)*2);
    }
    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }
    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * @return the ENEMY_POINTS
     */
    public int getENEMY_POINTS() {
        return ENEMY_POINTS;
    }
    /**
     * @return the BOSS_POINTS
     */
    public int getBOSS_POINTS() {
        return BOSS_POINTS;
    }
    /**
     * @return the lvlCounter
     */
    public int getLvlCounter() {
        return lvlCounter;
    }
    /**
     * @param screenCounter the screenCounter to set
     */
    public void setScreenCounter(int screenCounter) {
        this.screenCounter = screenCounter;
    }

    public void setAddEnemies(boolean addEnemies) {
        this.addEnemies = addEnemies;
    }
       

}//end of Game class
