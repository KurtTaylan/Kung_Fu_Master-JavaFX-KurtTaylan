/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import GameFrameworkJavaFX.Game;
import GameFrameworkJavaFX.Gui;
import javafx.scene.image.Image;

/**
 *
 * @author  Taylan Kurt
 * 
 */
public class Henchman extends Actor 
{    
    private boolean facingRight = false;
    private boolean movementStrategyFlag = true;
    private int timePauseCounter = 0;
    private final Image[] henchPics = new Image[8];
    private final int MAXIMUM_HITPOINTS = 3;
    private int animationTimeCounter;
    private int punchTimeCounter = 0 ;
    private int kickTimeCounter = 0;
    private boolean isKick ;
    private boolean isPunch;
    private int punchDamage = 5;
    private int kickDamage = 10;
       
    public Henchman (String name, String filename, int sizeX, int sizeY)
    {
        super(name, filename, sizeX, sizeY);
        henchPics[0] = new Image(getClass().getResourceAsStream("/resources/" + "henchman_walking_left.png"));
        henchPics[1] = new Image(getClass().getResourceAsStream("/resources/" + "henchman_walking_right.png"));
        henchPics[2] = new Image(getClass().getResourceAsStream("/resources/" + "henchman_standing_left.png"));
        henchPics[3] = new Image(getClass().getResourceAsStream("/resources/" + "henchman_standing_right.png"));
        henchPics[4] = new Image(getClass().getResourceAsStream("/resources/" + "henchman_punch_left.png"));
        henchPics[5] = new Image(getClass().getResourceAsStream("/resources/" + "henchman_punch_right.png"));
        henchPics[6] = new Image(getClass().getResourceAsStream("/resources/" + "henchman_kick_left.png"));
        henchPics[7] = new Image(getClass().getResourceAsStream("/resources/" + "henchman_kick_right.png"));
        
        
        this.setHitpoints(MAXIMUM_HITPOINTS);
    }
    
    
    @Override
    public void timeTick()
    {   super.timeTick();
    
        doEnemyMovementStrategy();
        //hechmenColidesWithMaster();
        
    }
   
    
    public void doEnemyMovementStrategy()
    {
        facingRight();
        
            if (!facingRight) 
            {
                if (movementStrategyFlag) 
                {
                    setSpeedX(-3);
                    animateWalkingLeft();
                    if ((getX() - Game.getInstance().master.getX()) < 50) 
                    {
                        setSpeedX(0);
                        updateImage(henchPics[2]);
                        movementStrategyFlag = false;
                    }
                }
                if (!movementStrategyFlag) 
                {
                    timePauseCounter++;
                    
                    if (timePauseCounter == 1) 
                    {
                        System.out.println("Punchingleft");
                        
                        
                        punch();                          // PUNCH ANİMATE
                        
                       
                    }
                    if(timePauseCounter == 40)
                    {
                        System.out.println("Kickingleft");
                        kick();
                        
                    }
                    if (timePauseCounter > 80) 
                    {  
                        
                        
                        setSpeedX(2);
                        animateWalkingRight();
                        
                        if (atHorizontalEdge()) 
                        {
                            timePauseCounter = 0;
                            movementStrategyFlag = true;
                        }
                    }
                    if ((getX() - Game.getInstance().master.getX()) > 300) 
                    {
                        timePauseCounter = 0;
                        movementStrategyFlag = true;
                    }
                }

            }
            if (facingRight) 
            {
                
                if (movementStrategyFlag) 
                {
                    setSpeedX(3);
                    animateWalkingRight();
                    
                    if ((Game.getInstance().master.getX() - getX()) < 50)
                    {
                        setSpeedX(0);
                        updateImage(henchPics[3]);
                        movementStrategyFlag = false;
                    }
                }
                if (!movementStrategyFlag) 
                {
                    timePauseCounter++;
                    
                    if (timePauseCounter == 1) 
                    {
                        System.out.println("Kickingright");
                        
                        
                        kick();                            // KİCK ANİMATE
                       
                    }
                    
                    if(timePauseCounter == 40)
                    {   System.out.println("punchingright");
                        punch();
                    }
                    if (timePauseCounter > 80) 
                    {   
                        
                        setSpeedX(-2);
                        animateWalkingLeft();
                        
                        if (atHorizontalEdge()) 
                        {
                            timePauseCounter = 0;
                            movementStrategyFlag = true;
                        }
                    }
                    
                    if ((Game.getInstance().master.getX() - getX()) > 300) 
                    {
                        timePauseCounter = 0;
                        movementStrategyFlag = true;
                    }
                }
            }
    }
    
    public void punch()
    {  setIsPunch(true);
       
        if (facingRight) 
        {
            punchTimeCounter++;
            if(punchTimeCounter == 2) 
            {
                updateImage( henchPics[5] );
            }
            else if( punchTimeCounter == 4 )
            {
                updateImage( henchPics[3] );
                punchTimeCounter = 0;
                
            }
        
        
        }
        else if(!facingRight)
        {
            punchTimeCounter++;
            if(punchTimeCounter == 2) 
            {
                updateImage( henchPics[4] );
            }
            else if( punchTimeCounter == 4 )
            {
                updateImage( henchPics[2] );
                punchTimeCounter = 0;
                
            }
        
        
        if( this.collidesWith(Game.getInstance().master) && this.isIsPunch() )
        {   
            
            Game.getInstance().master.damage(punchDamage);
            Gui.getInstance().punchFX();
                
            if (Game.getInstance().master.getHitpoints() <= 0) 
            {
                Game.getInstance().master.setLife(Game.getInstance().master.getLife() - 1); //remove one life
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS()); //reset hitpoints
                
                    
                if (Game.getInstance().master.getLife() <= 0) 
                {
                    Gui.getInstance().showLifeLabel(Game.getInstance().master.getLife());
                    Game.getInstance().removeHeroFromWorld(Game.getInstance().master);
                    
                }
            
            }
            
        }
        setIsPunch(false);
        
       }
    }
    
    public void kick()
    {   setIsKick(true);
        
        if (facingRight) 
        {
            kickTimeCounter++;
            
            if(kickTimeCounter == 2) 
            {
                updateImage( henchPics[7] );
            }
            else if( kickTimeCounter == 4 )
            {
                updateImage( henchPics[3] );
                kickTimeCounter = 0;
                
            }
        
        
        }else if(!facingRight)
        {
            kickTimeCounter++;
            
            if(kickTimeCounter == 2) 
            {
                updateImage( henchPics[6] );
            }
            else if( kickTimeCounter == 4 )
            {
                updateImage( henchPics[2] );
                kickTimeCounter = 0;
                
            }
        
        
        }
        
        if( this.collidesWith(Game.getInstance().master) && this.isIsKick() )
        {
            
            Game.getInstance().master.damage(kickDamage);
            Gui.getInstance().kickFX();
            
            if (Game.getInstance().master.getHitpoints() <= 0) 
            {
                Game.getInstance().master.setLife(Game.getInstance().master.getLife() - 1); //remove one life
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS()); //reset hitpoints
                    
                if (Game.getInstance().master.getLife() <= 0) 
                {
                    Gui.getInstance().showLifeLabel(Game.getInstance().master.getLife());
                    Game.getInstance().removeHeroFromWorld(Game.getInstance().master);
                    
                }
            
            }
        
         
        }
        
        setIsKick(false);
    
    }
    
    public void hechmenColidesWithMaster()
    {
        
        if( this.collidesWith(Game.getInstance().master) && this.isIsPunch() )
        {   
            
            Game.getInstance().master.damage(punchDamage);
                
            if (Game.getInstance().master.getHitpoints() <= 0) 
            {
                Game.getInstance().master.setLife(Game.getInstance().master.getLife() - 1); //remove one life
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS()); //reset hitpoints
                    
                if (Game.getInstance().master.getLife() <= 0) 
                {
                    Gui.getInstance().showLifeLabel(Game.getInstance().master.getLife());
                    Game.getInstance().removeHeroFromWorld(Game.getInstance().master);
                    
                }
            
            }
            
        }
        
        if( this.collidesWith(Game.getInstance().master) && this.isIsKick() )
        {
            
            Game.getInstance().master.damage(kickDamage);
            
            
            if (Game.getInstance().master.getHitpoints() <= 0) 
            {
                Game.getInstance().master.setLife(Game.getInstance().master.getLife() - 1); //remove one life
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS()); //reset hitpoints
                    
                if (Game.getInstance().master.getLife() <= 0) 
                {
                    Gui.getInstance().showLifeLabel(Game.getInstance().master.getLife());
                    Game.getInstance().removeHeroFromWorld(Game.getInstance().master);
                    
                }
            
            }
            
        }
    
    }
   
    public void animateWalkingLeft()
    {
        animationTimeCounter++;
        
        if (animationTimeCounter == 2) 
        {
            updateImage(henchPics[0]);
        } 
        else if (animationTimeCounter == 6) 
        {
            updateImage(henchPics[2]);
            
            animationTimeCounter = 0;                                           //animationCounter to 1
        }
                
    } 
    
    public void animateWalkingRight()
    {
        animationTimeCounter++;
        
        if (animationTimeCounter == 2) 
        {
            updateImage(henchPics[1]);
        } 
        else if (animationTimeCounter == 6) 
        {
            updateImage(henchPics[3]);
            
            animationTimeCounter = 0;                                           //animationCounter to 1
        }
    }
    
    /**
     * @return the isKick
     */
    public boolean isIsKick() 
    {
        return isKick;
    }

    /**
     * @param isKick the isKick to set
     */
    public void setIsKick(boolean isKick)
    {
        this.isKick = isKick;
    }

    /**
     * @return the isPunch
     */
    public boolean isIsPunch() 
    {
        return isPunch;
    }

    /**
     * @param isPunch the isPunch to set
     */
    public void setIsPunch(boolean isPunch)
    {
        this.isPunch = isPunch;
    }
    
    public boolean facingRight()
    {
        facingRight = Game.getInstance().master.getX() >= getX();

        return facingRight;
    }
    
    
}
