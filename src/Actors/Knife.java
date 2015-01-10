/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import GameFrameworkJavaFX.*;

/**
 *
 * @author Taylan Kurt
 */


public class Knife extends Actor {
    
    private final int knifeDmg = 5; //ammount of damage a knife does to others
    
    public Knife(String name, String filename, int sizeX, int sizeY) {
        super(name, filename, sizeX, sizeY);
    }
    
    @Override
    public void timeTick() {
        super.timeTick();
//        getX() > 1300 || getX() < 5
        if (this.atHorizontalEdge()) {                           //remove knife if it hits screen bounds
                Game.getInstance().removeWeaponFromWorld(this);
        }
        knifeAttacksMaster();
    }
    
    public void knifeAttacksMaster(){
        if(this.collidesWith(Game.getInstance().master)){
            Gui.getInstance().stabFX();
            Gui.getInstance().painFX();
            Game.getInstance().removeWeaponFromWorld(this);
            Game.getInstance().master.damage(knifeDmg);
            
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
}
