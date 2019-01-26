package com.housecurling.com.Systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.housecurling.com.Constants;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Random;

public class BotHouse {
    public Array<BotHouse> getEnemyRNorms() {
        return enemyRNorms;
    }

    private Array<BotHouse> enemyRNorms;

    public BotHouse(Body body) {
        this.body = body;
    }

    Body body;

    HashMap<Integer, Integer> indexToIdMapping;
    Vector2 attackPrior;

    public Vector2 attackPrior() {
        Vector2 res = new Vector2(0, 0);
        for (int i = 0; i < enemyRNorms.size; i++)
            res.x += Math.exp(inSecurity(enemyRNorms.get(i).getRNorm()));

        double[] probas = new double[enemyRNorms.size];

        for (int i = 0; i < probas.length; i++)
            probas[i] = Math.exp(inSecurity(enemyRNorms.get(i).getRNorm())) / res.x;

        res.y = randomWeightedChoice(probas);
        res.x = (float) probas[(int) res.y];

        System.out.print("Attack Prior" + res + "\t");
        this.attackPrior = res;
        return res;
    }

    public BotHouse attackWho() {
        return enemyRNorms.get((int)this.attackPrior.y);
    }

    private float sigmoid(float x) {
        return 1f / (1 + (float)Math.exp(-x));
    }

    public float getRNorm() {
        return body.getPosition().len()/ Constants.CIRCLE_INITIAL_RADIUS;
    }

    public float protectPrior() {
        float res = sigmoid(inSecurity(getRNorm()));
        System.out.print("Protect Prior: " + res + "\n");
        return res;
    }

    public int randomWeightedChoice(double[] arr) {
        double acuml = 0;
        Random rnd = new Random();
        for (int i = 0; i < arr.length; ++i) {
            acuml += arr[i];
            if (acuml >= rnd.nextDouble())
                return i;
        }
        return -1;
    }

    private float inSecurity(float security) {
        return 1f / (float)Math.pow(4, security+1f);
    }

    public void setEnemyRNorms(Array<BotHouse> enemyRNorms) {
        this.enemyRNorms = enemyRNorms;
    }

}
