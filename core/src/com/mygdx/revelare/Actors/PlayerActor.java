package com.mygdx.revelare.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.revelare.Utils.Assets;

public class PlayerActor extends Actor {

    public enum InputRotationState {Left, Right, None};

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

            if(canControl) {
                setBounds(this.position.x - this.radius, this.position.y - this.radius, this.radius * 2, this.radius * 2);

                addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("touchDown at:" + x + ", " + y + " | Pointer: " + pointer + " Button: " + button);
                        rotateStop();
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("touchUp at:" + x + ", " + y + " | Pointer: " + pointer + " Button: " + button);
                    }
                });
            }
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

            switch(inputRotationState){
                case Left: angle += Math.toRadians(angularVel * delta);
                    break;
                case Right: angle -= Math.toRadians(angularVel * delta);
                    break;
            }

            if(!canControl){
                angle -= Math.toRadians(angularVel * delta);
            }

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

    private Actor leftButtonActor, rightButtonActor;
    private OrbiterActor orbitor;
    private ColorSwitcherActor switcher;
    private TextureRegion circleOutline;
    private Vector2 playerPosition;
    private InputRotationState inputRotationState = InputRotationState.None;
    private float outerRadius;
    private boolean canControl, resetting;

    public Circle getCollisionCircle(){ return orbitor.circle; }

    public PlayerActor(Vector2 position, float outerRadius, float velocity, Stage stage, boolean canControl){
        this.outerRadius = outerRadius;
        this.playerPosition = position;
        this.canControl = canControl;

        orbitor = new OrbiterActor(playerPosition, 20, outerRadius, velocity);
        switcher = new ColorSwitcherActor(playerPosition, 30);
        leftButtonActor = new Actor();
        rightButtonActor = new Actor();

        if(canControl) {
            leftButtonActor.setBounds(0, 0, stage.getWidth() / 2 - 50, stage.getHeight());
            rightButtonActor.setBounds(stage.getWidth() / 2 + 50, 0, stage.getWidth() / 2 - 50, stage.getHeight());

            leftButtonActor.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    rotateLeft();
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    rotateStop();
                }
            });

            rightButtonActor.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    rotateRight();
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    rotateStop();
                }
            });
        }

        circleOutline = new TextureRegion(Assets.get(Assets.circleBorder, Texture.class));

        stage.addActor(orbitor);
        stage.addActor(switcher);
        stage.addActor(leftButtonActor);
        stage.addActor(rightButtonActor);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(circleOutline, playerPosition.x - outerRadius, playerPosition.y - outerRadius, outerRadius, outerRadius, outerRadius * 2, outerRadius * 2, 1, 1, 0);
    }

    public void rotateLeft(){
        inputRotationState = InputRotationState.Left;
    }

    public void rotateRight(){
        inputRotationState = InputRotationState.Right;
    }

    public void rotateStop(){
        inputRotationState = InputRotationState.None;
    }

}
