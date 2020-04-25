package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Map {
    public static final int CELLS_Y = 9;
    public static final int CELLS_X = 16;
    public static final int CELL_SIZE = 80;

    private Texture textureGrass;
    private Texture textureWall;
    private byte[][] data;


    public Map() {
        data = new byte[CELLS_X][CELLS_Y];
        for (int i = 0; i < 15; i++) {
            data[MathUtils.random(0 , CELLS_X - 1)][MathUtils.random(0,  CELLS_Y - 1)] = 1;
        }
        textureGrass = new Texture("grass.png");
        textureWall = new Texture("wall.png");

    }

    public boolean isCellPassable(Vector2 position) {
        if (position.x < 0.0f || position.x > 1280.0f || position.y < 0.0f || position.y > 720.0f) {
            return false;
        }
        return data[(int) (position.x / CELL_SIZE)][(int) (position.y) / CELL_SIZE] == 0;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                batch.draw(textureGrass, i * 80, j * 80);
                if(data[i][j] == 1) {
                    batch.draw(textureWall, i * 80, j * 80);
                }
            }
        }
    }
}
