package com.housecurling.com.Systems;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class BotSystem {
    private double rNorm;
    private double[] enemyRNorms;

    public Vector2 attackPrior(){
     Vector2 res = new Vector2(0,0);
     for (int i=0;i<enemyRNorms.length;i++)
        res.x+=Math.exp(inSecurity(enemyRNorms[i]));
     double[] probas = new double[enemyRNorms.length];
     for(int i=0;i<probas.length;i++)
         probas[i]=Math.exp(inSecurity(enemyRNorms[i]))/res.x;
     res.y = randomWeightedChoice(probas);
     res.x = (float)probas[(int) res.y];
     return res;
    }

    private double sigmoid(double x){
        return 1f/(1+Math.exp(-x));
    }

    public double protectPrior(){
        return sigmoid(inSecurity(rNorm));
    }

    public int randomWeightedChoice(double[] arr){
        double acuml = 0;
        Random rnd = new Random();
        for (int i=0;i<arr.length;++i){
            acuml+=arr[i];
            if(acuml>=rnd.nextDouble())
                return i;
        }
        return -1;
    }

    private double inSecurity(double security){
        return 1f/security;
    }
}
