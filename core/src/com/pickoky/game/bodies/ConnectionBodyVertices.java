package com.pickoky.game.bodies;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by MickelPickel on 3/31/2017.
 */

public class ConnectionBodyVertices {
    private Vector2 connectionBodyVertices[][];
    private float piStart = 0;
    public ConnectionBodyVertices(float r){
        connectionBodyVertices = new Vector2[4][157];
        for(int i = 0; i < 4; i ++){
            for(int x = 0; x < 157; x ++){
                connectionBodyVertices[i][x] = new Vector2((float)Math.cos(piStart)*r,(float)Math.sin(piStart)*r);
                piStart+=0.01;
            }
        }
    }

    public Vector2[] getConnectionBodyVertices(int index){
        return connectionBodyVertices[index];
    }
}
