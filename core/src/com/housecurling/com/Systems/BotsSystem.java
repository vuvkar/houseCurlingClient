package com.housecurling.com.Systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.housecurling.com.Constants;

public class BotsSystem {
    Array<BotHouse> bots;
    Array<BotHouse> temp;

    PhysicSystem physicSystem;

    Vector2 tempVector;


    public BotsSystem(PhysicSystem physicSystem) {
        temp = new Array<BotHouse>();
        tempVector = new Vector2();
        bots = new Array<BotHouse>();
        this.physicSystem = physicSystem;
    }

    void decide(BotHouse bot){
        Vector2 ap = bot.attackPrior();
        float protect = bot.protectPrior();

        Array<Float> zibil = new Array<Float>();
        zibil.add(ap.x, protect);
        int choice = bot.randomWeightedChoice(zibil);
        if(choice == 0){
           BotHouse  botHouse = bot.getrNorms().get((int)ap.y);
           tempVector.set(botHouse.body.getPosition());
           tempVector.sub(bot.body.getPosition());
           tempVector.nor().scl(Constants.MAX_VECTOR_LENGTH * ap.x);
           physicSystem.applyImpulse(bot.body, tempVector.scl(0.01f));
        }
        else{
            tempVector.set(bot.body.getPosition());
            tempVector.scl(-1);
            tempVector.nor().scl(Constants.MAX_VECTOR_LENGTH * protect);
            physicSystem.applyImpulse(bot.body, tempVector.scl(0.001f));
        }
    }

    void botsAction() {
        for(BotHouse botHouse : bots) {
            temp.clear();
            temp.addAll(bots);
//            temp.removeValue(botHouse, true);
            botHouse.setrNorms(temp);
        }

        for(BotHouse botHouse: bots) {
//            decide(botHouse);
            botHouse.botAction();
        }
    }

    public void addBot(Body bot) {
        BotHouse botHouse = new BotHouse(bot);
        bots.add(botHouse);
    }

    public void removeBot(Body bot) {
        for(BotHouse house:bots) {
            if (house.body.equals(bot)) {
                bots.removeValue(house, true);
                break;
            }
        }
    }

}
