package com.example.userr.patinteroapp.gameView;

import com.example.userr.patinteroapp.A_PlayArea;

import java.util.ArrayList;
import java.util.Random;

/**
 * Game logic is entirely independent from renderer
 * Logic can be edited as much


 TODO
 *
 * 1. Alter Opponent class to move from minX - maxX instead of 0 to limitX. (applies to Y)
 * 2. Implement powerups
 *      2.1 powerUP class (for differentiation and rendering)
 *      2.2 triggers in game logic (could also be just plain booleans)
 *      2.3 powerUP duration checker (could be new thread or just plain counter)
 *      2.4 powerUP effects
 * FINISHED - 3. Implement loading of stage from an external file (JSON or XML)
 * 4. Implement different speeds. (Opponent or gameLogic thread rate)
 * 5. Implement player profile (for saving current stage, current number of powerups, achievements, etc)
 * 6. Implement win condition
 * */


public class gameLogic extends Thread {
    //game variables
    //game length is a representation of a grid
    public int width = 15;                          //width of the game
    public int height = 0;                         //height of the game
    public int rate = 300;                          //refresh rate of opponent movements in milliseconds
    public boolean isPlaying = true;                //

    public Player player;                           //plyaer instantiation
    public ArrayList<Opponent> opponents;           //arraylist of opponents
    public ArrayList<A_PlayArea.UserJson> input_opponents;
    public volatile boolean running = true;         //boolean used for the update thread

    public gameLogic(ArrayList<A_PlayArea.UserJson> input){
        //instantiate here
        opponents = new ArrayList<Opponent>();
        player = new Player(width/2, 0, width, height);
        float inputHeight = 0;

        //use the json list input as the basis for opponent instantiation
        for(A_PlayArea.UserJson i : input){
            switch (i.dir){
                case "U":{
                    opponents.add(new Opponent(player, i.x, i.y, i.maxX, i.maxY, Opponent.Dir.UP, i.type));
                    break;
                }
                case "D":{
                    opponents.add(new Opponent(player, i.x, i.y, i.maxX, i.maxY, Opponent.Dir.DOWN, i.type));
                    break;
                }
                case "L":{
                    opponents.add(new Opponent(player, i.x, i.y, i.maxX, i.maxY, Opponent.Dir.LEFT, i.type));
                    break;
                }
                case "R":{
                    opponents.add(new Opponent(player, i.x, i.y, i.maxX, i.maxY, Opponent.Dir.RIGHT, i.type));
                    break;
                }
            }
            // height of the game is relative to the position of the farthest opponent
            if(i.y > inputHeight) inputHeight = i.y;
            if(i.maxY > inputHeight) inputHeight = i.maxY;
        }
        //margin from the last opponent
        inputHeight+=2;
        height = (int) inputHeight;
        player.maxY = height;
    }

    /*----------------------------------------------------------------------------*/
    /*powerup related booleans*/

    public boolean isPower_speed() {
        return power_speed;
    }

    public boolean isPower_slow() {
        return power_slow;
    }

    public boolean isPower_stamina() {
        return power_stamina;
    }

    public boolean isPower_invis() {
        return power_invis;
    }

    //powerup booleans
    private boolean power_speed = false;
    private boolean power_slow = false;
    private boolean power_stamina = false;
    private boolean power_invis = false;



    /*speed power up
      player movement speed goes up*/
    public void activateSpeed(){
        if(power_speed){
            player.speedDown();
            power_speed = false;
        }
        else{
            player.speedUp();
            power_speed = true;
        }
    }
    /*slow powerup
      opponent movement speed goes down*/
    public void activateSlow(){
        if(power_slow){
            for(Opponent op : opponents){
                op.speedUp();
            }
            power_slow = false;
        }
        else{
            for(Opponent op : opponents){
                op.speedDown();
            }
            power_slow = true;
        }
    }
    /*stamina powerup
      stamina gets unlimited*/
    public void activateStamina(){
        if(power_stamina){
            player.staminaDown();
            power_stamina = false;
        }
        else{
            player.staminaUp();
            power_stamina = true;
        }
    }
    /*invis powerup
      player becomes invisible. unable to be targetted by chasing opponents*/
    public void activateInvis(){
        if(power_invis){
            for(Opponent op : opponents){
                op.visTarget();
            }
            power_invis = false;
        }
        else{
            for(Opponent op : opponents){
                op.invisTarget();
            }
            power_invis = true;
        }
    }

    /*----------------------------------------------------------------------------*/

    //update opponent movements and check collision
    public void update(){
        for(Opponent op : opponents){
            //checks collision
            //uncomment after test

            /*if(Math.abs(op.getX()-player.getX())<0.99 &&
                    Math.abs(op.getY()-player.getY())<0.99){
                isPlaying = false;
                shutdown();
                return;
            }*/
            op.move();
        }
        player.replenish();
    }

    /*update thread
      currently not used. updates are happening in GL thread*/
    public void run(){
        while (running) {
            try{
                Thread.sleep(rate);
                update();

            }
            catch (Exception e){

            }
        }
    }

    //stop update thread/game
    public void shutdown(){
        running = false;
    }
    //movement functions
    public void moveUp(){
        player.moveUp();
    }
    public void moveDown(){
        player.moveDown();
    }
    public void moveRight(){
        player.moveRight();
    }
    public void moveLeft(){
        player.moveLeft();
    }
}
