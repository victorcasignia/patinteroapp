package com.example.userr.patinteroapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class A_Splash extends AppCompatActivity {

    private boolean LayoutDone = false;
    private boolean EventDone = false;

//    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private RelativeLayout RLayout;

    private ImageView imgRunPlayer;

    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_a__splash);

        RLayout = (RelativeLayout) findViewById(R.id.Splash_Page);
        ViewTreeObserver viewTreeObserver = RLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                G.sm = new ScreenMan(G.actPatintero, RLayout, 720, 1184);

                setLayouts();
                setEvents();
            }
        });


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

    private void loading()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 1){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(100);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingText.setText("Loading .. " + String.valueOf(mProgressStatus)+"%");
//                            mProgressBar.setProgress(mProgressStatus);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingText.setText("Completed!");
                        Intent openMenuPage = new Intent(G.actPatintero, A_Menu.class);
                        startActivity(openMenuPage);
                    }
                });
            }
        }).start();

    }

    //Animate
    public void HandleAnimation()
    {
        //imgRunPlayer, "x",RLayout.getWidth()+130
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imgRunPlayer, "x", 620f);
        animatorX.setDuration(1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();

    }

    private void setEvents()
    {
        if (EventDone) return;
        EventDone = true;
    }

    private void setLayouts()
    {
        if (LayoutDone) return;
        LayoutDone = true;

//        mProgressBar = (ProgressBar) findViewById(R.id.progBar_splash);
//        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) mProgressBar.getLayoutParams(), "XYXY");

        mLoadingText = (TextView) findViewById(R.id.LoadingCompleteTextView);
        mLoadingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLoadingText.getTextSize() * G.sm.scZX);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) mLoadingText.getLayoutParams(), "XYX1");

//        btnMove = (Button) findViewById(R.id.btn_move);
//        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) btnMove.getLayoutParams(), "XYXY");

        imgRunPlayer = (ImageView) findViewById(R.id.gif_runPlayer);
        G.sm.AdjustLocSiz((RelativeLayout.LayoutParams) imgRunPlayer.getLayoutParams(), "XYXY");

        loading();
        HandleAnimation();
    }
}
