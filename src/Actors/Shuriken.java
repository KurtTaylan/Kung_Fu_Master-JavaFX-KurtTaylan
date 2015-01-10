/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import GameFrameworkJavaFX.*;
import java.util.ArrayList;

/**
 *
 * @author Taylan Kurt
 */


public class Shuriken extends Actor {
    
    private final int shurikenDmg = 2; //ammount of damage a knife does to others
    
    public Shuriken(String name, String filename, int sizeX, int sizeY) {
        super(name, filename, sizeX, sizeY);
    }
    
    @Override
    public void timeTick() {
        super.timeTick();
        
        if (this.atHorizontalEdge()) {                           //remove knife if it hits screen bounds
                Game.getInstance().removeWeaponFromWorld(this);
        }
        shurikenHitsEnemy(Game.getInstance().getEnemies());
    }
    
    public void shurikenHitsEnemy(ArrayList<Actor> Enemies){
        for (Actor boogie : Enemies){
            if(this.collidesWith(boogie)){
                Game.getInstance().removeWeaponFromWorld(this);
                Game.getInstance().master.damage(shurikenDmg);
            
                if(boogie instanceof Dwarf){
                    Dwarf dwarf = (Dwarf)boogie;
                    if(!dwarf.isAttack()){
                        dwarf.setX(dwarf.getX() + 30); //if the enemy is hit he steps back 3 pixels
                        dwarf.damage(shurikenDmg);
                        Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                        Gui.getInstance().stabFX();
                        Gui.getInstance().painFX();
                    }
                }else if(!(boogie instanceof Dwarf)){
                    boogie.setX(boogie.getX() + 30); //if the enemy is hit he steps back 3 pixels
                    boogie.damage(shurikenDmg);
                    Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                    Gui.getInstance().stabFX();
                    Gui.getInstance().painFX();
                            
                }else if(boogie instanceof Henchman){
                    boogie.setX(boogie.getX() + 30); //if the enemy is hit he steps back 3 pixels
                    boogie.damage(shurikenDmg);
                    Gui.getInstance().showEnemyHP(boogie.getHitpoints());
                    Gui.getInstance().stabFX();
                    Gui.getInstance().painFX();
                        
                }
                if (boogie.getHitpoints() <= 0) {
                    Game.getInstance().removeEnemyFromWorld(boogie);
                    Game.getInstance().calculateScore(boogie);          //Everytime an enemy is defeated score points are added
                    break;
                } 
            }
        }
    }
}    

