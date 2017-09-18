package com.example.userr.patinteroapp.gameView;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.userr.patinteroapp.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.Matrix;


/**
 * Created by P004 on 9/6/2017.
 */
/*
*
Renderer. dependent on game logic data
TODO
1. eliminate stutter/ghosting
2. remove update from GL thread
3. if possible, eliminate the stretching of background_asset
4. optimize gl rendering both here and in the assets class (remove redundant functions)
*
* */
public class gameRenderer implements GLSurfaceView.Renderer {
    private Context context;

    public gameLogic gamelogic;                 //game logic used for rendering

    //mvp = Model-View-Projection
    //used for GL rendering
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    //frame size
    private float frameHeight = 10;
    private float frameWidth = 15;

    //assets
    private asset background_asset;
    private asset player_asset;
    private asset opponent_asset;

    public gameRenderer(Context context, gameLogic gamelogic){
        this.gamelogic = gamelogic;
        this.context = context;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);                                //clear GL first
        //gamelogic.start();                                                        //gameLogic thread. disabled for now. updates happening in GL thread
        background_asset = new asset(this.context, R.drawable.bg);                  //load assets here
        opponent_asset = new asset(this.context, R.drawable.npc_front_png);
        player_asset = new asset(this.context, R.drawable.player_back_png);
        //GLES20.glEnable(GLES20.GL_DEPTH_TEST);                                    //used for allowing depth rendering. currently disabled because it doesnt work with alpha blending
        //GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_BLEND);                                           //enable alpha blending for transparency
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);     //blend transparent with background
        //GLES20.glDepthFunc(GLES20.GL_LEQUAL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);                                     //set viewport back to default. not sure what viewport is. probably the drawing area
        float ratio = (float) width / height;                                       //no idea why but this works
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);          //no idea why but this works. frustum is used for projecting the view from a point away.
    }
    /*---------------------------Frame rendering here------------------------------------*/
    /*------------------------------this is what is hapening in the GL thread-------------------------------------------*/
    @Override
    public void onDrawFrame(GL10 gl) {
        /*
        * still experimenting here.
        * problem is ghost stuttering when player position changes as drawing happens
        * fix might be making a queue for user input and applying movement before rendering
        *
        * */



        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);                                                //clear the GL view

        //set camera location and where it looks at
        Matrix.setLookAtM(mViewMatrix, 0, 0, getCam(gamelogic.height - gamelogic.player.getY(), 1), 4f, 0f, getCam(gamelogic.height - gamelogic.player.getY(), 1), 0f, 0f, 1.0f, 0.0f);

        //idk what this is. probably for zooming
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //draw assets here in order for good overlap. background->player->opponent. Opponent is drawn last because of their number and might cause a bottle neck.
        background_asset.draw(mMVPMatrix, toFloatBuffer_fromPolyCodes(0,0,gamelogic.width, gamelogic.height, -1f));
        player_asset.draw(mMVPMatrix, toFloatBuffer_fromPolyCodes(gamelogic.player.getX(), gamelogic.height - gamelogic.player.getY(), 1, 1, 0.1f));
        for(Opponent op : gamelogic.opponents) {
            if(Math.abs(op.getY()-gamelogic.player.getY())<10) {                //only draw opponents within 10 units
                opponent_asset.draw(mMVPMatrix,toFloatBuffer_fromPolyCodes(op.getX(), gamelogic.height - op.getY(), 1f, 1f, 0.1f));
            }
        }
        gamelogic.update();     //update logic here. probably should be separate thread but stutters lower here. problem is thread is too fast. opponents move too fast. so I lowered the speed
    }

    public FloatBuffer toFloatBuffer_fromPolyCodes(float x, float y, float width, float height, float depth)        //converting location relative with the game size to the GL coordinates.
    {
        final float [] Poly_Coords = new float[4*3];
        float hW = frameWidth/2;
        float hH = frameHeight/2;


        Poly_Coords[0*3+0] = -width/frameWidth  + (x-hW+width/2)/hW;
        Poly_Coords[0*3+1] =  height/frameHeight - (y-hH+height/2)/hH;
        Poly_Coords[0*3+2] =  depth;

        Poly_Coords[1*3+0] = -width/frameWidth  + (x-hW+width/2)/hW;
        Poly_Coords[1*3+1] = -height/frameHeight - (y-hH+height/2)/hH;
        Poly_Coords[1*3+2] =  depth;

        Poly_Coords[2*3+0] =  width/frameWidth  + (x-hW+width/2)/hW;
        Poly_Coords[2*3+1] = -height/frameHeight- (y-hH+height/2)/hH;
        Poly_Coords[2*3+2] =  depth;

        Poly_Coords[3*3+0] =  width/frameWidth + (x-hW+width/2)/hW;
        Poly_Coords[3*3+1] =  height/frameHeight - (y-hH+height/2)/hH;
        Poly_Coords[3*3+2] =  depth;
        return toFloatBuffer(Poly_Coords);

    }
    public Float getCam(float y, float height)                      //get cam position. also the y position of the player
    {
        float hH = frameHeight/2;

        return -height/frameHeight - (y-hH+height/2)/hH;
    }
    public FloatBuffer toFloatBuffer(float[] arr) {                 //float array to float buffer

        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);    //allocate a byte buffer for a float array
        bb.order(ByteOrder.nativeOrder());                          //ordering of data

        FloatBuffer fb = bb.asFloatBuffer();                        //byte converted to float array
        fb.put(arr);                                                //insert data from argument
        fb.position(0);                                             //point buffer to first element

        return fb;
    }

    public void onPause(){

    }

    public void onResume(){

    }
    public void printLoc(float x, float y, float width, float height)                               //utility function to print GL locations from gameLogic locations
    {
        final float [] Poly_Coords = new float[4*3];
        float hW = frameWidth/2;
        float hH = frameHeight/2;
        Poly_Coords[0*3+0] = -width/frameWidth  + (x-hW+width/2)/hW;
        Poly_Coords[0*3+1] = -height/frameHeight - (y-hH+height/2)/hH;
        System.out.println(Poly_Coords[0*3+1]+"="+(-height/frameHeight)+"-"+((y-hH+height/2)/hH));
        Poly_Coords[0*3+2] =  0.1f;

        Poly_Coords[1*3+0] =  width/frameWidth  + (x-hW+width/2)/hW;
        Poly_Coords[1*3+1] = -height/frameHeight- (y-hH+height/2)/hH;
        Poly_Coords[1*3+2] =  0.1f;

        Poly_Coords[2*3+0] = -width/frameWidth  + (x-hW+width/2)/hW;
        Poly_Coords[2*3+1] =  height/frameHeight - (y-hH+height/2)/hH;
        Poly_Coords[2*3+2] =  0.1f;

        Poly_Coords[3*3+0] =  width/frameWidth + (x-hW+width/2)/hW;
        Poly_Coords[3*3+1] =  height/frameHeight - (y-hH+height/2)/hH;
        Poly_Coords[3*3+2] =  0.1f;

        for(int i=0;i<12;i++)
            System.out.print(Poly_Coords[i] +" ");

    }
}
