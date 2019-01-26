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

        int choice = bot.randomWeightedChoice(new double[] {ap.x, protect});
        if(choice == 0){
           BotHouse  botHouse = bot.getEnemyRNorms().get((int)ap.y);
           tempVector.set(botHouse.body.getPosition());
           tempVector.sub(bot.body.getPosition());
           tempVector.nor().scl(Constants.MAX_VECTOR_LENGTH * ap.x);
           physicSystem.applyImpulse(bot.body, tempVector);
        }
        else{
            tempVector.set(bot.body.getPosition());
            tempVector.scl(-1);
            tempVector.nor().scl(Constants.MAX_VECTOR_LENGTH * protect);
            physicSystem.applyImpulse(bot.body, tempVector);
        }
    }

    void botsAction() {
        for(BotHouse botHouse : bots) {
            temp.clear();
            temp.addAll(bots);
            temp.removeValue(botHouse, true);
            botHouse.setEnemyRNorms(temp);
        }

        for(BotHouse botHouse: bots) {
            decide(botHouse);
        }
    }


    public void addBot(Body bot) {
        BotHouse botHouse = new BotHouse(bot);
        bots.add(botHouse);
    }

    public void removeBot(Body bot) {

    }

}
