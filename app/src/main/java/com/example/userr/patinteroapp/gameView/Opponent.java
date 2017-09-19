package com.example.userr.patinteroapp.gameView;

import java.util.Random;

/**
 * Created by P004 on 9/11/2017.
 */

public class Opponent {
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    private float x;
    private float y;
    public float acc = 0.5f;
    public float maxX;
    public float maxY;
    private float speed = 0.1f;
    public Player target;
    public int chance = 400;
    public int pattern = 1;
    public enum Dir{
        UP, DOWN, LEFT, RIGHT
    }
    //current direction. used for direction change
    public Dir curDir;

    private boolean invisTarget = false;

    public Opponent(Player target, float x, float y, float maxX, float maxY, Dir dir, int pattern){
        this.target = target;
        this.x = x;
        this.y = y;
        this.maxX = maxX;
        this.maxY = maxY;
        curDir = dir;
        this.pattern = pattern;
    }

    public void move(){
        Random rand = new Random();
        if(rand.nextInt(1000) + 1 < chance) return;
        if(invisTarget){
            if (curDir == Dir.UP) {
                if (getY() >= maxY) {
                    curDir = Dir.DOWN;
                    setY(getY() - speed);
                } else {
                    setY(getY() + speed);
                }
            } else if (curDir == Dir.DOWN) {
                if (getY() <= 1) {
                    curDir = Dir.UP;
                    setY(getY() + speed);
                } else {
                    setY(getY() - speed);
                }
            } else if (curDir == Dir.LEFT) {
                if (getX() <= 0) {
                    curDir = Dir.RIGHT;
                    setX(getX() + speed);
                } else {
                    setX(getX() - speed);
                }
            } else if (curDir == Dir.RIGHT) {
                if (getX() >= maxX - 1) {
                    curDir = Dir.LEFT;
                    setX(getX() - speed);
                } else {
                    setX(getX() + speed);
                }
            }
            return;
        }
        switch(pattern) {
            case 1: {
                if (curDir == Dir.UP) {
                    if (getY() >= maxY) {
                        curDir = Dir.DOWN;
                        setY(getY() - speed);
                    } else {
                        setY(getY() + speed);
                    }
                } else if (curDir == Dir.DOWN) {
                    if (getY() <= 1) {
                        curDir = Dir.UP;
                        setY(getY() + speed);
                    } else {
                        setY(getY() - speed);
                    }
                } else if (curDir == Dir.LEFT) {
                    if (getX() <= 0) {
                        curDir = Dir.RIGHT;
                        setX(getX() + speed);
                    } else {
                        setX(getX() - speed);
                    }
                } else if (curDir == Dir.RIGHT) {
                    if (getX() >= maxX - 1) {
                        curDir = Dir.LEFT;
                        setX(getX() - speed);
                    } else {
                        setX(getX() + speed);
                    }
                }
                break;
            }
            case 2:{
                if (curDir == Dir.UP || curDir == Dir.DOWN) {
                    if(target.getY()<getY()){
                        if(!(getY()<=1))
                            setY(getY()-speed*acc);
                        curDir = Dir.DOWN;
                        return;
                    }
                    if(target.getY()>getY()){
                        if(!(getY()>=maxY - 1))
                            setY(getY()+speed*acc);
                        curDir = Dir.UP;
                        return;
                    }
                }
                if (curDir == Dir.LEFT || curDir == Dir.RIGHT) {
                    if(target.getX()<getX()){
                        if(!(getX()<0))
                            setX(getX()-speed*acc);
                        return;
                    }
                    if(target.getX()>getX()){
                        if(!(getX()>maxX - 1))
                            setX(getX()+speed*acc);
                        return;
                    }
                }
                break;
            }
            /*case 3: {
                break;
            }/*
            case 4:{

            }*/
        }
    }

    public void speedDown(){
        speed = 0.05f;
    }

    public void speedUp(){
        speed = 0.1f;
    }

    public void invisTarget(){
        invisTarget = true;
    }
    public void visTarget(){
        invisTarget = false;
    }
}
