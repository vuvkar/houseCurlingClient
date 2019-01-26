package com.housecurling.com.Systems;

public class BotsManagerSystem {
    BotSystem[] bots;
    void decide(BotSystem bot){
        int choice = bot.randomWeightedChoice(new double[] {bot.attackPrior().x, bot.protectPrior()});
        if(choice==0){
            //attack to bot with index bot.attackPrior().y,
        }
        else{
            // slide to middle
        }
    }

}
