package com.example.userr.patinteroapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.RelativeLayout;

/**
 * Created by Userr on 05/23/2017.
 */

public class ScreenMan {

    //Portrait
    private float	scWBaseP = 720;		//1080;	//720;		//Base View width (Portrait)
    private float	scHBaseP = 1118;	//1701;	//1134f;	//Base View height (Portrait) Compatｔの時は縦幅が短い

    //Landscape
    private float	scWBaseL = 1118;	//1701;	//1134f;	//Base View width (Portrait)
    private float	scHBaseL = 720;		//1080;	//720f;		//Base View height (Portrait)

    public float actualScreenW = scWBaseP;
    public float actualScreenH = scHBaseP;
    public float	scZX = 1.0f;					//W Zoom rate
    public float	scZY = 1.0f;					//H Zoom rate
    public float	scZBigger = 1.0f;				//Bigger Zoom rate among width and height
    public float	scZSmaller = 1.0f;				//Smaller Zoom rate among width and height


    public ScreenMan(Activity AC, RelativeLayout RLayout, int BaseWidth, int BaseHeight)
    {
        Resources resources = AC.getResources();
        Configuration config = resources.getConfiguration();

        //Portrait
        scWBaseP = BaseWidth;
        scHBaseP = BaseHeight;

        //Landscape
        scWBaseL = BaseHeight;
        scHBaseL = BaseWidth;

        actualScreenW = RLayout.getWidth();
        actualScreenH = RLayout.getHeight();

        //Compute scale rate of Width and Height
        if(config.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            scZX = actualScreenW / scWBaseP;
            scZY = actualScreenH / scHBaseP;
        }
        else
        {
            scZX = actualScreenW / scWBaseL;
            scZY = actualScreenH / scHBaseL;
        }

        if (scZX >= scZY)
        {
            scZBigger = scZX;
            scZSmaller = scZY;
        }
        else
        {
            scZBigger = scZY;
            scZSmaller = scZX;
        }
    }

    public void AdjustLocSiz(RelativeLayout.LayoutParams lp, String strAdjType)
    {
        if (strAdjType.length() != 4) strAdjType = "1111";

        float z;
        z = getZoomRate(strAdjType.substring(0, 1));
        if (z != 1) lp.leftMargin *= z;

        z = getZoomRate(strAdjType.substring(1, 2));
        if (z != 1) lp.topMargin *= z;

        z = getZoomRate(strAdjType.substring(2, 3));
        if (z != 1) lp.width *= z;

        z = getZoomRate(strAdjType.substring(3, 4));
        if (z != 1) lp.height *= z;
    }

    private  float getZoomRate(String strT)
    {
        strT = strT.toUpperCase();
        if (strT.equals("X")) return scZX;
        if (strT.equals("Y")) return scZY;
        if (strT.equals("S")) return scZSmaller;
        if (strT.equals("B")) return scZBigger;
        return 1;
    }

}
