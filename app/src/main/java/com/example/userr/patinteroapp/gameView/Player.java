package com.example.userr.patinteroapp.gameView;

/**
 * Created by P004 on 9/7/2017.
 */
//player class
public class Player {
    /*----------------------VARIABLES---------------*/
    /*Player variables*/
    private float x;                                //x position
    private float y;                                //y position
    public int maxX;                                //max X position, min is 0
    public int maxY;                                //max Y position, min is 0
    public float stamina = 20;                      //stamina - used for movement
    public float maxSpeed = 20;                     //maxspeed - used for movement. NOT ACTUAL SPEED
    public Opponent.Dir faceDir = Opponent.Dir.UP;

    /*powerup variables*/
    private boolean unliStamina = false;             //unlistamina variable. set to false after testing
    private float speedMultiplier = 1;              //speed multiplier for speedup powerup

    /*getters and setter*/
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

    /*----------------------METHODS-------------*/

    /*construct with the necessary variables*/
    public Player(float x, float y, int maxX, int maxY){
        this.x = x;
        this.y = y;
        this.maxX = maxX;
        this.maxY = maxY;
    }
    /*---------------movement functions------------------*/

    public void moveUp(){
        this.faceDir = Opponent.Dir.UP;
        float speed = (stamina/maxSpeed) * speedMultiplier;                 //speed is stamina/maxSpeed. At full stamina, movement is 1*speedmultiplier. halfstamina is 0.5*speedmultiplier. Etc
        if((this.y + speed) <= maxY -1){
            this.y += speed;
        }
        else{
            this.y = maxY-1;
        }
        if(!unliStamina) if(stamina>1) stamina--;                           //stamina reduced every movement
    }
    public void moveDown(){
        this.faceDir = Opponent.Dir.DOWN;
        float speed = (stamina/maxSpeed) * speedMultiplier;
        if((this.y - speed) >= 0){
            this.y -= speed;
        }
        else{
            this.y = 0;
        }
        if(!unliStamina) if(stamina>1) stamina--;
    }
    public void moveRight(){
        //this.faceDir = Opponent.Dir.RIGHT;
        float speed = (stamina/maxSpeed) * speedMultiplier;
        if((this.x + speed) <= maxX -1){
            this.x += speed;
        }
        else{
            this.x = maxX-1;
        }
        if(!unliStamina) if(stamina>1) stamina--;
    }
    public void moveLeft(){
        //this.faceDir = Opponent.Dir.LEFT;
        float speed = (stamina/maxSpeed) * speedMultiplier;
        if((this.x - speed) >= 0){
            this.x -= speed;
        }
        else{
            this.x = 0;
        }
        if(!unliStamina) if(stamina>1) stamina--;
    }

    //replenish is called in gameLogic.update()
    //adds 1 stamina. reduce if needed
    public void replenish(){
        if(stamina<20) stamina+=0.05;
    }

    //powerup fucntions here here
    public void speedUp(){
        speedMultiplier = 2;
    }
    public void speedDown(){
        speedMultiplier = 1;
    }
    public void staminaUp(){
        stamina = 20;
        unliStamina = true;
    }
    public void staminaDown(){
        unliStamina = false;
    }
}
