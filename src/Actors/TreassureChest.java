/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Actors;

import GameFrameworkJavaFX.Game;
import GameFrameworkJavaFX.GameState;
import javafx.scene.image.Image;

/**
 *
 * @author Taylan Kurt
 */
public class TreassureChest extends Actor{
    
    private String chestContent;
    private final Image[] bonusPics = new Image[2];
    public TreassureChest(String name, String filename, int sizeX, int sizeY, String bonusType){
        super(name,filename,sizeX,sizeY);
        bonusPics[0] = new Image(getClass().getResourceAsStream("/resources/" + "life.png"));
        bonusPics[1] = new Image(getClass().getResourceAsStream("/resources/" + "ShurikenBig.png"));
                
        this.chestContent = bonusType;
    }
    
    @Override
    public void timeTick() {
        super.timeTick();
     
        if(this.atVerticalEdge() && this.chestContent.equalsIgnoreCase("life")){
            Game.getInstance().master.setLife(Game.getInstance().master.getLife() +1);
            Game.getInstance().removeTreasureChestsFromWorld(this);
            Game.getInstance().gameInformation();
            Game.getInstance().setGameState(GameState.LevelComplete);
        }else if (this.atVerticalEdge() && this.chestContent.equalsIgnoreCase("shuriken")){
            Game.getInstance().removeTreasureChestsFromWorld(this);
            Game.getInstance().master.setAmmo(Game.getInstance().master.getAmmo()+20);
        }
            giveBonus();
    }
    
    /*
    *More binuses might be added in the future
    */
    public void giveBonus(){
        switch(chestContent){
            case "life":
                if(this.collidesWith(Game.getInstance().master)){  
                this.updateImage(bonusPics[0]);
                this.setSpeedY(-3);
                break;
            }               
            case "shuriken":
                if(this.collidesWith(Game.getInstance().master)){   
                this.updateImage(bonusPics[1]);
                this.setSpeedY(-3);
                break;
            }
        }
    }
    
}
