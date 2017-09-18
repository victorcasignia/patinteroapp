package com.example.userr.patinteroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.userr.patinteroapp.gameView.gameLogic;
import com.example.userr.patinteroapp.gameView.gameView;

public class A_PlayArea extends AppCompatActivity {

    private boolean LayoutDone = false;
    private boolean EventDone = false;

    private ImageButton btnSpeed, btnSlow, btnStamina, btnInvisble, btnUp, btnDown, btnRight, btnLeft;

    private gameLogic x;
    private gameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__play_area);

        x = new gameLogic();
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
                    x.moveUp();
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
                    x.moveLeft();
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
                    x.moveRight();
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
                    x.moveDown();
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
