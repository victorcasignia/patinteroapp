package com.example.userr.patinteroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class A_Stages extends AppCompatActivity {

    private boolean LayoutDone = false;
    private boolean EventDone = false;

    private ImageView imgLife, imgSpeed, imgSlow, imgStamina, imgInvisble, imgStageFrame;
    private ImageButton btnLvl1, btnLvl2, btnLvl3, btnLvl4, btnLvl5, btnLvl6, btnLvl7, btnLvl8, btnLvl9, btnPrev, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fullscreen
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_a__stages);

        setLayouts();
        setEvents();
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
        btnLvl1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnLvl1.setImageAlpha(128);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnLvl1.setImageAlpha(255);
                }
                return false;
            }
        });

        btnNext.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnNext.setImageAlpha(128);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnNext.setImageAlpha(255);
                }
                return false;
            }
        });



        //************************
        //     OnClick Events    *
        //************************
        btnLvl1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent OpenPlay = new Intent(G.actPatintero, A_PlayArea.class);
                startActivity(OpenPlay);
            }
        });
    }

    private void setLayouts() {
        if (LayoutDone) return;
        LayoutDone = true;

        imgLife = (ImageView) findViewById(R.id.imgView_life);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) imgLife.getLayoutParams(), "XYXY");

        imgSpeed = (ImageView) findViewById(R.id.imgView_speed);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) imgSpeed.getLayoutParams(), "XYXY");

        imgSlow = (ImageView) findViewById(R.id.imgView_slow);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) imgSlow.getLayoutParams(), "XYXY");

        imgStamina = (ImageView) findViewById(R.id.imgView_stamina);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) imgStamina.getLayoutParams(), "XYXY");

        imgInvisble = (ImageView) findViewById(R.id.imgView_invisible);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) imgInvisble.getLayoutParams(), "XYXY");

        imgStageFrame = (ImageView) findViewById(R.id.imgView_stageFrame);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) imgStageFrame.getLayoutParams(), "XYXY");

        btnLvl1 = (ImageButton) findViewById(R.id.imgBtn_stageUnlockInAct);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnLvl1.getLayoutParams(), "XYXY");

        btnLvl2 = (ImageButton) findViewById(R.id.imgBtn_stageLockInAct2);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnLvl2.getLayoutParams(), "XYXY");

        btnLvl3 = (ImageButton) findViewById(R.id.imgBtn_stageLockInAct3);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnLvl3.getLayoutParams(), "XYXY");

        btnLvl4 = (ImageButton) findViewById(R.id.imgBtn_stageLockInAct4);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnLvl4.getLayoutParams(), "XYXY");

        btnLvl5 = (ImageButton) findViewById(R.id.imgBtn_stageLockInAct5);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnLvl5.getLayoutParams(), "XYXY");

        btnLvl6 = (ImageButton) findViewById(R.id.imgBtn_stageLockInAct6);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnLvl6.getLayoutParams(), "XYXY");

        btnLvl7 = (ImageButton) findViewById(R.id.imgBtn_stageLockInAct7);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnLvl7.getLayoutParams(), "XYXY");

        btnLvl8 = (ImageButton) findViewById(R.id.imgBtn_stageLockInAct8);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnLvl8.getLayoutParams(), "XYXY");

        btnLvl9 = (ImageButton) findViewById(R.id.imgBtn_stageLockInAct9);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnLvl9.getLayoutParams(), "XYXY");

        btnPrev = (ImageButton) findViewById(R.id.imgBtn_prevInAc);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnPrev.getLayoutParams(), "XYXY");

        btnNext = (ImageButton) findViewById(R.id.imgBtn_nextAc);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnNext.getLayoutParams(), "XYXY");


    }
}
