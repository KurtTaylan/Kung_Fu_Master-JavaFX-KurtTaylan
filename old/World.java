/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameFrameworkJavaFX;

import Actors.Actor;
import java.util.ArrayList;

/**
 *
 * @author anc
 */
public class World {

    ArrayList<Actor> actors;

    public World() {
        actors = new ArrayList();
    }

    void addActor(Actor actor, int x, int y) {
        actors.add(actor);
        actor.setLocation(x, y);
        Gui.getInstance().addImageView(actor.getImageView());
    }

    void removeFromWorld(Actor actor) {
        actors.remove(actor);
        Gui.getInstance().removeImageView(actor.getImageView());
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void timeTick() {
        for (Actor actor : actors) {
            actor.timeTick();
        }
    }

}
