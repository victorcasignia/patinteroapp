package com.example.userr.patinteroapp.gameView;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by P004 on 9/7/2017.
 */

public class gameView extends GLSurfaceView {
    public final gameRenderer renderer;

    public gameView(Context context, gameLogic gamelogic){
        super(context);

        //uses openGL ES 2.0
        setEGLContextClientVersion(2);

        //initialize renderer and connect to glsurfaceview
        renderer = new gameRenderer(context, gamelogic);
        setRenderer(renderer);

        // Renders continuously
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        renderer.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        renderer.onResume();
    }
}
