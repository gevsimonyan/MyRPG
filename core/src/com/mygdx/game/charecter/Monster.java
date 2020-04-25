package com.mygdx.game.charecter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;

public class Monster extends GameCharacter {
    private float moveTimer;
    private float activityRadius;

    public Monster(GameScreen gameScreen) {
        this.texture = new Texture("MonsterPutin.png");
        this.textureHP = new Texture("bar.png");
        this.position = new Vector2(MathUtils.random(0, 1280), MathUtils.random(0, 720));
        while(!gameScreen.getMap().isCellPassable(position)) {
            this.position.set((MathUtils.random(0, 1280)), MathUtils.random(0, 720));
        }
        this.direction = new Vector2(0, 0);
        this.temp = new Vector2(0, 0);
        this.speed = 40.0f;
        this.gameScreen = gameScreen;
        this.activityRadius = 75.0f;
        this.hpMax = 20;
        this.hp = this.hpMax;
        this.weapon = new Weapon("Rusty sword", 50.0f, 0.8f, 5);
    }

    @Override
    public void update(float dt) {
        damageEffectTimer -= dt;
        if (damageEffectTimer < 0.0f) {
            damageEffectTimer = 0.0f;
        }

        float dst = gameScreen.getHero().getPosition().dst(this.position);
        if (dst < activityRadius) {
            direction.set(gameScreen.getHero().getPosition()).sub(this.position).nor();
        } else {
            moveTimer -= dt;
            if (moveTimer < 0) {
                moveTimer = MathUtils.random(1.0f, 4.0f);
                direction.set(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f));
                direction.nor();
            }
        }
        temp.set(position).mulAdd(direction, speed * dt);
        if (gameScreen.getMap().isCellPassable(temp)) {
            position.set(temp);
        }
        if (dst < weapon.getAttackRadius()) {
            attackTimer += dt;
            if (attackTimer >= weapon.getAttackPeriod()) {
                attackTimer = 0.0f;
                gameScreen.getHero().takeDamage(weapon.getDamage());
            }
        }

        checkScreenBounds();
    }
}
