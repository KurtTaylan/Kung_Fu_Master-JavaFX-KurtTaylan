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


public class Fireball extends Actor {
    
    private int firBallDmg = 20; //ammount of damage a knife does to others
    
    public Fireball(String name, String filename, int sizeX, int sizeY) {
        super(name, filename, sizeX, sizeY);
    }
    
    @Override
    public void timeTick() {
        super.timeTick();
//        getX() > 1300 || getX() < 5
        if (this.atHorizontalEdge()) {                           //remove knife if it hits screen bounds
                Game.getInstance().removeWeaponFromWorld(this);
        }
        fireBallHitsEnemy();
    }
    
    public void fireBallHitsEnemy(){
        if(this.collidesWith(Game.getInstance().master)){

            Game.getInstance().removeWeaponFromWorld(this);
            Game.getInstance().master.damage(firBallDmg);
            Gui.getInstance().explosionFX();
            Gui.getInstance().painFX();
            
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
}
