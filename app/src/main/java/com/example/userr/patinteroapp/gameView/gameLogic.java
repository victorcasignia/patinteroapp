package com.example.userr.patinteroapp.gameView;

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
 *      2.4 powerUP effectsxd
 * 3. Implement loading of stage from an external file (JSON or XML)
 * 4. Implement different speeds. (Opponent or gameLogic thread rate)
 * 5. Implement player profile (for saving current stage, current number of powerups, achievements, etc)
 * */


public class gameLogic extends Thread {
    //game variables
    //game length is a representation of a grid
    public int width = 15;                          //width of the game
    public int height = 30;                         //height of the game
    public int rate = 300;                          //refresh rate of opponent movements in milliseconds
    public boolean isPlaying = true;                //

    public Player player;                           //plyaer instantiation
    public ArrayList<Opponent> opponents;           //arraylist of opponents

    public volatile boolean running = true;         //boolean used for the update thread

    public gameLogic(){
        //instantiate here
        opponents = new ArrayList<Opponent>();
        player = new Player(width/2, 0, width-1, height);

        Random rand = new Random();
        for(int i=0;i<3;i++){
            switch (rand.nextInt(3)+1) {
                case 1: {
                    opponents.add(new Opponent(player, rand.nextInt(width-1) + 1, rand.nextInt(height) + 1, width, height, Opponent.Dir.DOWN, 2));          //Opponent(Player target, int posX, int posY, int limitX, int limitY, Opponent.Dir direction, int movementType)
                    break;                                                                                                                                  //Currently opponents move from 0-limitX/limitY
                }
                case 2: {
                    opponents.add(new Opponent(player, rand.nextInt(width-1) + 1, rand.nextInt(height) + 1, width, height, Opponent.Dir.UP, 2));
                    break;
                }
                case 3: {
                    opponents.add(new Opponent(player, rand.nextInt(width-1) + 1, rand.nextInt(height) + 1, width, height, Opponent.Dir.LEFT, 2));
                    break;
                }
                case 4: {
                    opponents.add(new Opponent(player, rand.nextInt(width-1) + 1, rand.nextInt(height) + 1, width, height, Opponent.Dir.RIGHT, 2));
                    break;
                }
            }
        }
    }


    /*----------------------------------------------------------------------------*/
    /*powerup related booleans*/

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

        }
        else{

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
