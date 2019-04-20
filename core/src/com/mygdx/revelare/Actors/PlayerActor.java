package com.mygdx.revelare.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.revelare.Utils.Assets;

public class PlayerActor extends Actor {

    public class ColorSwitcherActor extends Actor{

        private TextureRegion textureRegion;
        private Vector2 position;
        private Color circleColor;
        private float radius;

        public ColorSwitcherActor(Vector2 position, float radius){
            this.position = position;
            this.radius = radius;

            textureRegion = new TextureRegion(Assets.get(Assets.circle, Texture.class));
            circleColor = Color.WHITE;
        }

        @Override
        public void act(float delta){

        }

        @Override
        public void draw(Batch batch, float parentAlpha){
            batch.setColor(circleColor);
            batch.draw(textureRegion, position.x - radius, position.y - radius , radius, radius, radius * 2, radius * 2, 1, 1,0);
        }
    }

    public class OrbiterActor extends Actor{

        private Circle circle;
        private TextureRegion textureRegion;
        private Vector2 position, offset;
        private Color circleColor;
        private float orbitRadius, radius;
        private double angle, angularVel;

        public OrbiterActor(Vector2 offset, float radius, float orbitRadius, double angularVel){
            this.offset = offset;
            this.orbitRadius = orbitRadius;
            this.radius = radius;
            this.angularVel = angularVel;

            textureRegion = new TextureRegion(Assets.get(Assets.circle, Texture.class));

            position = new Vector2();

            circleColor = Color.WHITE;

            circle = new Circle();
            circle.setRadius(radius);
            circle.setPosition(getPosition(angle));
        }

        public void setCircleColor(Color circleColor) {
            this.circleColor = circleColor;
        }

        @Override
        public void act(float delta){
            angle += Math.toRadians(angularVel * delta);
            position.set(getPosition(angle));
            circle.setPosition(position);
        }

        @Override
        public void draw(Batch batch, float parentAlpha){
            batch.setColor(circleColor);
            batch.draw(textureRegion, position.x - radius, position.y - radius, position.x, position.x, radius * 2, radius * 2, 1, 1, 0);
        }

        @Override
        public void drawDebug(ShapeRenderer shapeRenderer){
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.circle(circle.x, circle.y, circle.radius);
        }

        private Vector2 getPosition(double angle){
            Vector2 temp = new Vector2();
            temp.x = orbitRadius * (float)Math.cos(angle) + offset.x;
            temp.y = orbitRadius * (float)Math.sin(angle) + offset.y;
            return temp;
        }
    }

    private OrbiterActor orbitor;
    private ColorSwitcherActor switcher;
    private TextureRegion circleOutline;
    private Vector2 playerPosition;
    private float outerRadius;

    public PlayerActor(Vector2 position, float outerRadius, Stage stage){
        this.outerRadius = outerRadius;
        this.playerPosition = position;

        orbitor = new OrbiterActor(playerPosition, 20, outerRadius, 100);
        switcher = new ColorSwitcherActor(playerPosition, 30);

        circleOutline = new TextureRegion(Assets.get(Assets.circleBorder, Texture.class));

        stage.addActor(orbitor);
        stage.addActor(switcher);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(circleOutline, playerPosition.x - outerRadius, playerPosition.y - outerRadius, outerRadius, outerRadius, outerRadius * 2, outerRadius * 2, 1, 1, 0);
    }

}
