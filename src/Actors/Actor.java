/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Actors;

import GameFrameworkJavaFX.*;
import java.awt.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Taylan Kurt
 */
public class Actor {
    
    private int speedX = 0;
    private int speedY = 0;
    private int x;
    private int y;
    private final String name;
    private final ImageView imageView;
    private int width;
    private int height;
    private double collisionRadius = 1.0; //shrink the collision radius by setting e.g. 0.5   
    private int hitpoints;
    
    
    public Actor(String name, String filename, int sizeX, int sizeY){
        this.name = name;
        imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResourceAsStream("/resources/"+filename)));
      //imageView.setFitWidth(size);                                          //dont need to change that, because cant use animate properly
        imageView.setPreserveRatio(true);
        setSize(sizeX, sizeY);
    }
       
    public void updateImage(Image image ){
       
        imageView.setImage(image);
        
    }
    
    public void setLocation(int x, int y){
        this.setX(x); 
        this.setY(y);
    }
    
    public final void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        imageView.setLayoutX(x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        imageView.setLayoutY(y);
    }

    public String getName() {
        return name;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void setCollisionRadius(double value){
        collisionRadius = value;
    }
    
    public double getCollisionRadius(){
        return collisionRadius;
    }
    
    public boolean collidesWith(Actor otherActor){
        int shrinkX = (int)(width*(1-collisionRadius))/2;
        int shrinkY = (int)(height*(1-collisionRadius))/2;
        Rectangle r1 = new Rectangle(x+shrinkX, y+shrinkY, width-(2*shrinkX), height-(2*shrinkY));
        int otherShrinkX = (int)(otherActor.getWidth()*(1-otherActor.getCollisionRadius()))/2;
        int otherShrinkY = (int)(otherActor.getHeight()*(1-otherActor.getCollisionRadius()))/2;
        Rectangle r2 = new Rectangle(otherActor.getX()+otherShrinkX, otherActor.getY()+otherShrinkY, otherActor.getWidth()-(2*otherShrinkX), otherActor.getHeight()-(2*otherShrinkY));
        return r1.intersects(r2);
    }
    public boolean punchesTheRight(Actor otherActor){
        int shrinkX = (int)(width*(1-collisionRadius))/2;
        int shrinkY = (int)(height*(1-collisionRadius))/2;
        Rectangle punch = new Rectangle(x+shrinkX, y+shrinkY, 61, 15);
        int otherShrinkX = (int)(otherActor.getWidth()*(1-otherActor.getCollisionRadius()))/2;
        int otherShrinkY = (int)(otherActor.getHeight()*(1-otherActor.getCollisionRadius()))/2;
        Rectangle body = new Rectangle(otherActor.getX()+otherShrinkX, otherActor.getY()+otherShrinkY, otherActor.getWidth()-(2*otherShrinkX), otherActor.getHeight()-(2*otherShrinkY));
        return punch.intersects(body);
    }
    public boolean kicksTheRight(Actor otherActor){
        int shrinkX = (int)(width*(1-collisionRadius))/2;
        int shrinkY = (int)((height)*(1-collisionRadius))/2;
        Rectangle kick = new Rectangle(x+shrinkX, y+shrinkY, 81, 15);
        int otherShrinkX = (int)(otherActor.getWidth()*(1-otherActor.getCollisionRadius()))/2;
        int otherShrinkY = (int)(otherActor.getHeight()*(1-otherActor.getCollisionRadius()))/2;
        Rectangle body = new Rectangle(otherActor.getX()+otherShrinkX, otherActor.getY()+otherShrinkY, otherActor.getWidth()-(2*otherShrinkX), otherActor.getHeight()-(2*otherShrinkY));
        return kick.intersects(body);
    }   
    public boolean punchesTheLeft(Actor otherActor){
        int shrinkX = (int)(width*(1-collisionRadius))/2;
        int shrinkY = (int)(height*(1-collisionRadius))/2;
        Rectangle punch = new Rectangle(((x+shrinkX)-6), y+shrinkY, 61, 15);
        int otherShrinkX = (int)(otherActor.getWidth()*(1-otherActor.getCollisionRadius()))/2;
        int otherShrinkY = (int)(otherActor.getHeight()*(1-otherActor.getCollisionRadius()))/2;
        Rectangle body = new Rectangle(otherActor.getX()+otherShrinkX, otherActor.getY()+otherShrinkY, otherActor.getWidth()-(2*otherShrinkX), otherActor.getHeight()-(2*otherShrinkY));
        return punch.intersects(body);
    }    
    public boolean kicksTheLeft(Actor otherActor){
        int shrinkX = (int)(width*(1-collisionRadius))/2;
        int shrinkY = (int)((height)*(1-collisionRadius))/2;
        Rectangle kick = new Rectangle(((x+shrinkX)-3), y+shrinkY, 81, 15);
        int otherShrinkX = (int)(otherActor.getWidth()*(1-otherActor.getCollisionRadius()))/2;
        int otherShrinkY = (int)(otherActor.getHeight()*(1-otherActor.getCollisionRadius()))/2;
        Rectangle body = new Rectangle(otherActor.getX()+otherShrinkX, otherActor.getY()+otherShrinkY, otherActor.getWidth()-(2*otherShrinkX), otherActor.getHeight()-(2*otherShrinkY));
        return kick.intersects(body);
    }
    
    private void updatePositionFromSpeed(){
        if (getSpeedX()==0 && getSpeedY()==0)
            return;
        setX(getX()+getSpeedX());
        setY(getY()+getSpeedY());
        if (getX()>Gui.getInstance().getWidth()-this.width)
            setX(Gui.getInstance().getWidth()-this.width);
        if (getX()<0)
            setX(0);
        if (getY()>Gui.getInstance().getHeight()-this.height)
            setY(Gui.getInstance().getHeight()-this.height);
        if (getY()<0)
            setY(0);
    }
    
    public boolean atHorizontalEdge(){
        return (x>=Gui.getInstance().getWidth()-this.width ||  x<=0);   
    }
    
    public boolean atVerticalEdge(){
        return (y>=Gui.getInstance().getHeight()-this.height ||  y<=0);   
    }
    
    public void timeTick(){
        updatePositionFromSpeed();
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int xSpeed) {
        this.speedX = xSpeed;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int ySpeed) {
        this.speedY = ySpeed;
    }

    /**
     * @return the hitpoints
     */
    public int getHitpoints() {
        return hitpoints;
    }

    /**
     * @param hitpoints the hitpoints to set
     */
    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }
    
    public void damage(int damage){
        this.setHitpoints(hitpoints - damage);
    }
    
}
