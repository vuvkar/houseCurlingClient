package com.housecurling.com.Systems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.housecurling.com.Constants;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Random;

public class BotHouse {
    public Array<BotHouse> getrNorms() {
        return rNorms;
    }

    private Array<BotHouse> rNorms;

    public BotHouse(Body body) {
        this.body = body;
    }

    Body body;

    HashMap<Integer, Integer> indexToIdMapping;
    Vector2 attackPrior;

    public Vector2 attackPrior() {
        Vector2 res = new Vector2(0, 0);
        for (int i = 0; i < rNorms.size; i++)
            res.x += Math.exp(inSecurity(rNorms.get(i).getRNorm()));

        Array<Float> probas = new Array<Float>(rNorms.size);

        for (int i = 0; i < rNorms.size; i++)
            probas.add((float)Math.exp(inSecurity(rNorms.get(i).getRNorm())) / res.x);

        res.y = randomWeightedChoice(probas);
        res.x = probas.get((int)res.y);

        System.out.print("Attack Prior" + res + "\t");
        try {
            res.x =  probas.get((int)res.y);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.attackPrior = res;
        return res;
    }



    public BotHouse attackWho() {
        return rNorms.get((int)this.attackPrior.y);
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

    public int randomWeightedChoice(Array<Float> arr) {
        double acuml = 0;
        float sum = 0;
        for(Float f: arr) {
            sum += f;
        }
        float rnd = MathUtils.random(0, sum - 0.0001f);
        for (int i = 0; i < arr.size; ++i) {
            acuml += arr.get(i);
            if (acuml >= rnd)
                return i;
        }
        return -1;
    }

    private float inSecurity(float security) {
        return 1f / ((float)Math.pow(1, security) + 0.05f);
    }

    public void botAction() {
        Array<Float> vuln = getInsecurity();
        for(Float value: vuln) {
            vuln.set(vuln.indexOf(value, true), (float) Math.exp(value));
        }

        float denom = 0;
        for(Float value: vuln) {
            denom += value;
        }

        Array<Float> probs = new Array<Float>(vuln.size);
        for(Float value: vuln) {
            probs.add((float)Math.exp(value) / denom);
        }

        for(int id_el = 0; id_el < vuln.size; id_el++) {
            int id_of = randomWeightedChoice(probs);
            try {
                if (id_of < probs.size && id_of >= 0) {
                    if (probs.get(id_of) > Constants.STOCHASTICITY) {
                        if (id_el == id_of) {
                            body.applyLinearImpulse(body.getPosition().nor().scl(-Constants.DIVERGENCE), body.getWorldCenter(), true);
                        } else {
                            Vector2 distance = rNorms.get(id_of).body.getPosition().sub(rNorms.get(id_el).body.getPosition());
                            body.applyLinearImpulse(distance.nor().scl(Constants.DIVERGENCE), body.getWorldCenter(), true);
                            ;
                        }
                    } else {
                        Vector2 stoch = new Vector2(MathUtils.random(0f, 1f), MathUtils.random(0f, 1f));
                        body.applyLinearImpulse(stoch.nor().scl(Constants.DIVERGENCE), body.getWorldCenter(), true);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private Array<Float> getInsecurity() {
        Array<Float> insecurities = new Array<Float>();
        for(BotHouse botHouse: this.getrNorms()) {
            insecurities.add(inSecurity(botHouse.getRNorm()));
        }
        return insecurities;
    }

    public void setrNorms(Array<BotHouse> rNorms) {
        this.rNorms = rNorms;
    }

}
