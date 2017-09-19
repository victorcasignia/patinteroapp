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

public class A_Menu extends AppCompatActivity {

    private boolean LayoutDone = false;
    private boolean EventDone = false;

    private ImageButton btnGame, btnOptions, btnAbout;
    private ImageView imgPlayer, imgNpc1, imgNpc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_a__menu);

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
        btnGame.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnGame.setImageResource(R.drawable.button_active_game);
//                    btnGame.setImageAlpha(128);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnGame.setImageResource(R.drawable.button_inactive_game);
//                    btnGame.setImageAlpha(255);
                }
                return false;
            }
        });

        btnOptions.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnOptions.setImageResource(R.drawable.button_active_options);
//                    btnAbout.setImageAlpha(128);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnOptions.setImageResource(R.drawable.button_inactive_options);
//                    btnAbout.setImageAlpha(255);
                }
                return false;
            }
        });


        btnAbout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnAbout.setImageResource(R.drawable.button_active_about);
//                    btnAbout.setImageAlpha(128);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnAbout.setImageResource(R.drawable.button_inactive_about);
//                    btnAbout.setImageAlpha(255);
                }
                return false;
            }
        });

        //************************
        //     OnClick Events    *
        //************************
        btnGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openStagePage = new Intent(G.actPatintero, A_Stages.class);
                startActivity(openStagePage);
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openAboutPage = new Intent(G.actPatintero, A_Options.class);
                startActivity(openAboutPage);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openAboutPage = new Intent(G.actPatintero, A_About.class);
                startActivity(openAboutPage);
            }
        });
    }

    private void setLayouts()
    {
        if (LayoutDone) return;
        LayoutDone = true;

        btnGame = (ImageButton) findViewById(R.id.imgBtn_inActGame);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnGame.getLayoutParams(), "XYXY");

        btnOptions = (ImageButton) findViewById(R.id.imgBtn_inActOpt);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnOptions.getLayoutParams(), "XYXY");

        btnAbout = (ImageButton) findViewById(R.id.imgBtn_inActAbt);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnAbout.getLayoutParams(), "XYXY");

        imgPlayer = (ImageView) findViewById(R.id.gif_player);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) imgPlayer.getLayoutParams(), "XYXY");

        imgNpc1 = (ImageView) findViewById(R.id.gif_npc1);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) imgNpc1.getLayoutParams(), "XYXY");

        imgNpc2 = (ImageView) findViewById(R.id.gif_npc2);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) imgNpc2.getLayoutParams(), "XYXY");

    }

}
