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
import com.mygdx.revelare.Utils.Assets;

import java.util.ArrayList;
import java.util.List;

public class PlayerActor extends Actor {

    public enum InputRotationState {Left, Right, None}

    ;

    public class ColorSwitcherActor extends Actor {

        private TextureRegion textureRegion;
        private Vector2 position;
        private Color circleColor;
        private float radius;

        public void setColor(Color c) {
            circleColor = c;
        }

        public ColorSwitcherActor(Vector2 position, float radius) {
            this.position = position;
            this.radius = radius;

            textureRegion = new TextureRegion(Assets.get(Assets.circle, Texture.class));
            circleColor = Color.WHITE;

            if (canControl) {
                setBounds(this.position.x - this.radius, this.position.y - this.radius, this.radius * 2, this.radius * 2);

                addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("touchDown at:" + x + ", " + y + " | Pointer: " + pointer + " Button: " + button);
                        rotateStop();
                        colorSwitch();
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
        public void act(float delta) {

        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            Color c = new Color(circleColor.r, circleColor.g, circleColor.b, 0.5f);
            batch.setColor(c);
            batch.draw(textureRegion, position.x - radius, position.y - radius, radius, radius, radius * 2, radius * 2, 1, 1, 0);
        }
    }

    public class OrbiterActor extends Actor {

        private Circle circle;
        private TextureRegion textureRegion;
        private Vector2 position, offset;
        private Color circleColor;
        private float orbitRadius, radius;
        private double angle, angularVel;

        //Getters & Setters
        public Color getCircleColor() {
            return this.circleColor;
        }

        public void setCircleColor(Color circleColor) {
            this.circleColor = circleColor;
        }

        public void setVelocity(double velocity) {
            this.angularVel = velocity;
        }

        public OrbiterActor(Vector2 offset, float radius, float orbitRadius, double angularVel) {
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


        @Override
        public void act(float delta) {

            switch (inputRotationState) {
                case Left:
                    angle += Math.toRadians(angularVel * delta);
                    break;
                case Right:
                    angle -= Math.toRadians(angularVel * delta);
                    break;
            }

            if (!canControl) {
                angle -= Math.toRadians(angularVel * delta);
            }

            position.set(getPosition(angle));
            circle.setPosition(position);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.setColor(circleColor);
            batch.draw(textureRegion, position.x - radius, position.y - radius, position.x, position.x, radius * 2, radius * 2, 1, 1, 0);
        }

        @Override
        public void drawDebug(ShapeRenderer shapeRenderer) {
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.circle(circle.x, circle.y, circle.radius);
        }

        private Vector2 getPosition(double angle) {
            Vector2 temp = new Vector2();
            temp.x = orbitRadius * (float) Math.cos(angle) + offset.x;
            temp.y = orbitRadius * (float) Math.sin(angle) + offset.y;
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

    private List<Color> possibleColors;
    private int selectedColorSwitcherColor = 1;
    private int selectedOrbitorColor = 0;

    //Getters & Setters
    public Circle getCollisionCircle() {
        return orbitor.circle;
    }

    public void setPlayerVelocity(double velocity) {
        orbitor.setVelocity(velocity);
    }

    public void setPossibleColors(List<Color> possibleColors) {
        this.possibleColors = possibleColors;
        colorSwitch();
    }

    public Color getColor() {
        return orbitor.circleColor;
    }

    public PlayerActor(Vector2 position, float outerRadius, float velocity, Stage stage, boolean canControl) {
        this.outerRadius = outerRadius;
        this.playerPosition = position;
        this.canControl = canControl;

        possibleColors = new ArrayList<Color>();
        possibleColors.add(Color.WHITE);
        possibleColors.add(Color.WHITE);

        orbitor = new OrbiterActor(playerPosition, 20, outerRadius, velocity);
        switcher = new ColorSwitcherActor(playerPosition, 30);
        leftButtonActor = new Actor();
        rightButtonActor = new Actor();

        if (canControl) {
            selectedOrbitorColor = 0;
            selectedColorSwitcherColor = 1;
            switcher.setColor(possibleColors.get(selectedColorSwitcherColor));
            orbitor.setCircleColor(possibleColors.get(selectedOrbitorColor));

            leftButtonActor.setBounds(0, 0, stage.getWidth() / 2, stage.getHeight());
            rightButtonActor.setBounds(stage.getWidth() / 2, 0, stage.getWidth() / 2, stage.getHeight());

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
        stage.addActor(leftButtonActor);
        stage.addActor(rightButtonActor);
        stage.addActor(switcher);
    }

    public PlayerActor(Vector2 position, float outerRadius, float velocity, Stage stage, boolean canControl, List<Color> possibleColors) {
        this.outerRadius = outerRadius;
        this.playerPosition = position;
        this.canControl = canControl;
        this.possibleColors = possibleColors;

        orbitor = new OrbiterActor(playerPosition, 20, outerRadius, velocity);
        switcher = new ColorSwitcherActor(playerPosition, 30);
        leftButtonActor = new Actor();
        rightButtonActor = new Actor();

        if (canControl) {
            selectedOrbitorColor = 0;
            selectedColorSwitcherColor = 1;
            switcher.setColor(possibleColors.get(selectedColorSwitcherColor));
            orbitor.setCircleColor(possibleColors.get(selectedOrbitorColor));

            leftButtonActor.setBounds(0, 0, stage.getWidth() / 2, stage.getHeight());
            rightButtonActor.setBounds(stage.getWidth() / 2, 0, stage.getWidth() / 2, stage.getHeight());

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
        stage.addActor(leftButtonActor);
        stage.addActor(rightButtonActor);
        stage.addActor(switcher);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(circleOutline, playerPosition.x - outerRadius, playerPosition.y - outerRadius, outerRadius, outerRadius, outerRadius * 2, outerRadius * 2, 1, 1, 0);
    }

    public void rotateLeft() {
        inputRotationState = InputRotationState.Left;
    }

    public void rotateRight() {
        inputRotationState = InputRotationState.Right;
    }

    public void rotateStop() {
        inputRotationState = InputRotationState.None;
    }

    public void colorSwitch() {
        if (!possibleColors.isEmpty() && possibleColors.size() >= 2) {
            if (selectedColorSwitcherColor + 1 >= possibleColors.size()) {
                selectedColorSwitcherColor = 0;
            } else {
                selectedColorSwitcherColor++;
            }

            if (selectedOrbitorColor + 1 >= possibleColors.size()) {
                selectedOrbitorColor = 0;
            } else {
                selectedOrbitorColor++;
            }

            switcher.setColor(possibleColors.get(selectedColorSwitcherColor));
            orbitor.setCircleColor(possibleColors.get(selectedOrbitorColor));
        }
    }

}
