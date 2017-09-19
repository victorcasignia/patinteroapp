package com.example.userr.patinteroapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.userr.patinteroapp.gameView.Opponent;
import com.example.userr.patinteroapp.gameView.gameLogic;
import com.example.userr.patinteroapp.gameView.gameView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/*TODO
*
*  powerup animation
*  powerup pickup
* */
public class A_PlayArea extends AppCompatActivity {

    private boolean LayoutDone = false;
    private boolean EventDone = false;

    private ImageButton btnSpeed, btnSlow, btnStamina, btnInvisble, btnUp, btnDown, btnRight, btnLeft;

    private gameLogic x;
    private gameView gameView;

    private Activity activity= this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__play_area);

        x = new gameLogic(fromJSON());
        gameView = new gameView(this, x);

        setLayouts();
        setEvents();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Play_Stage);
        RelativeLayout.LayoutParams glParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        glParams.setMargins(0,0,0,400);
        layout.addView(gameView, glParams);
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(16);
                        gameView.requestRender();
                    }
                    catch (Exception e){

                    }
                }
            }
        }).start();*/
    }

    /*------------------------------Load opponents--------------------------------*/
    /**---------------------------------------------------------------------------**/
    /**---------------------------------------------------------------------------**/
    private int curLevel = 1;                                               //current level. default is 1. change this in the prior activity

    public void setLevel(int i){                                            //level setter
        curLevel = i;
    }

    private ArrayList<UserJson> fromJSON(){                                 //uses the GSON library to convert the JSON into an arraylist of a custom class for stage loading
        BufferedReader reader = null;
        String json="";
        //read file here. load from the assets folder
        try {
            reader = new BufferedReader(
                    new InputStreamReader(this.getAssets().open("level"+String.valueOf(curLevel)+".json"), "UTF-8"));
            String mLine;
            //concat the read line to the json string
            while ((mLine = reader.readLine()) != null) {
                json+=mLine;
            }
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        //initialize gson class
        Gson gson = new Gson();
        //use the gson function fromJson() to convert JSON file to list. Used the string json as the input.
        ArrayList<UserJson> list = gson.fromJson(json, new TypeToken<ArrayList<UserJson>>() {}.getType());
        return list;
    }
    //adapter class used for the JSON parser
    public class UserJson {
        public float x;
        public float y;
        public float minX;
        public float minY;
        public float maxX;
        public float maxY;
        public float speed;
        public String dir;
        public int type;
        public int chance;
    }

    /**---------------------------------------------------------------------------**/
    @Override
    protected void onResume()
    {
        super.onResume();

        G.actPatintero = this;
        G.appPatintero = getApplicationContext();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    private void setEvents()
    {
        if (EventDone) return;
        EventDone = true;

        //************************
        //     OnTouch Events    *
        //************************
        btnSpeed.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnSpeed.setImageAlpha(128);
                    x.activateSpeed();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            android.os.SystemClock.sleep(5000);
                            x.activateSpeed();
                        }
                    }).start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnSpeed.setImageAlpha(255);
                }
                return false;
            }
        });

        btnSlow.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnSlow.setImageAlpha(128);
                    x.activateSlow();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            android.os.SystemClock.sleep(5000);
                            x.activateSlow();
                        }
                    }).start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnSlow.setImageAlpha(255);
                }
                return false;
            }
        });

        btnStamina.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnStamina.setImageAlpha(128);
                    x.activateStamina();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            android.os.SystemClock.sleep(5000);
                            x.activateStamina();
                        }
                    }).start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnStamina.setImageAlpha(255);
                }
                return false;
            }
        });

        btnInvisble.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnInvisble.setImageAlpha(128);
                    x.activateInvis();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            android.os.SystemClock.sleep(5000);
                            x.activateInvis();
                        }
                    }).start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnInvisble.setImageAlpha(255);
                }
                return false;
            }
        });

        btnUp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnUp.setImageResource(R.drawable.arrow_up_active);
                    gameView.renderer.lastMove(Opponent.Dir.UP);
                    gameView.requestRender();
//                    btnUp.setImageAlpha(128);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnUp.setImageResource(R.drawable.arrow_up_inactive);
//                    btnUp.setImageAlpha(255);
                }
                return false;
            }
        });

        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnLeft.setImageResource(R.drawable.arrow_left_active);
                    gameView.renderer.lastMove(Opponent.Dir.LEFT);
                    gameView.requestRender();
//                    btnLeft.setImageAlpha(128);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnLeft.setImageResource(R.drawable.arrow_left_inactive);
//                    btnLeft.setImageAlpha(255);
                }
                return false;
            }
        });

        btnRight.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnRight.setImageResource(R.drawable.arrow_right_active);
                    gameView.renderer.lastMove(Opponent.Dir.RIGHT);
                    gameView.requestRender();
//                    btnRight.setImageAlpha(128);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnRight.setImageResource(R.drawable.arrow_right_inactive);
//                    btnRight.setImageAlpha(255);
                }
                return false;
            }
        });

        btnDown.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnDown.setImageResource(R.drawable.arrow_down_active);
                    gameView.renderer.lastMove(Opponent.Dir.DOWN);
                    gameView.requestRender();
//                    btnDown.setImageAlpha(128);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnDown.setImageResource(R.drawable.arrow_down_inactive);
//                    btnDown.setImageAlpha(255);
                }
                return false;
            }
        });

        //************************
        //     OnClick Events    *
        //************************


    }

    private void setLayouts()
    {
        if (LayoutDone) return;
        LayoutDone = true;

        btnSpeed = (ImageButton) findViewById(R.id.imgBtn_speed);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnSpeed.getLayoutParams(), "XYXY");

        btnSlow = (ImageButton) findViewById(R.id.imgBtn_slow);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnSlow.getLayoutParams(), "XYXY");

        btnStamina = (ImageButton) findViewById(R.id.imgBtn_stamina);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnStamina.getLayoutParams(), "XYXY");

        btnInvisble = (ImageButton) findViewById(R.id.imgBtn_invisible);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnInvisble.getLayoutParams(), "XYXY");

        btnUp = (ImageButton) findViewById(R.id.imgBtn_upInAc);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnUp.getLayoutParams(), "XYXY");

        btnLeft = (ImageButton) findViewById(R.id.imgBtn_leftInAc);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnLeft.getLayoutParams(), "XYXY");

        btnRight = (ImageButton) findViewById(R.id.imgBtn_rightInAc);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnRight.getLayoutParams(), "XYXY");

        btnDown = (ImageButton) findViewById(R.id.imgBtn_downInAc);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnDown.getLayoutParams(), "XYXY");
    }
}
