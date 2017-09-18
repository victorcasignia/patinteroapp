package com.example.userr.patinteroapp.gameView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by P004 on 9/15/2017.
 */

/*
* asset class is used for GL rendering.
* assets are classes that contains the bitmap and incorporates it to GL
* draw functions for every bitmap are written here
*
* */

public class asset {
    //vertex shader. defines how vertices are drawn in rendering
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 a_texCoord;" +
                    "varying vec2 v_texCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  v_texCoord = a_texCoord;" +
                    "}";;
    //fragment shader. defines how points inside(fragments) are drawn in rendering
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec2 v_texCoord;" +
                    "uniform sampler2D s_texture;" +
                    "void main() {" +
                    "  gl_FragColor = texture2D( s_texture, v_texCoord );" +
                    "}";


    /*---------------Quadrangle variables-------------*/
    private FloatBuffer vertexBuffer;                               //vertex data described here
    private ShortBuffer drawListBuffer;                             //draw list order in buffer form
    private int mProgram;                                           //GL program ID
    private FloatBuffer uvBuffer;                                   //UV position buffer of texture quadrangle edges
    private int mPositionHandle;                                    //position handler of GL renderer
    private final short drawOrder[] = { 0, 1, 2, 0, 2, 3 };         //draw list order
    private static float uvs[] = new float[] {                      //UV position array of texture quadrangle edges
        0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
    };;
    private int textureID;                                          //textureID in memory

    static final int COORDS_PER_VERTEX = 3;                         //vertex dimension
    private Context context;

    /*---------------------Data preparation in constructor---------------------*/

    public asset(Context context, int textureID){                       //Initialize with context and textureID from res (e.g. R.drawable.npc_front)
        ByteBuffer abb = ByteBuffer.allocateDirect(uvs.length * 4);     //convert array of float to buffer
        abb.order(ByteOrder.nativeOrder());                             //UV positions to buffer
        uvBuffer = abb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(                     //draw list array to buffer
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);


        int vertexShader = loadShader(                 // prepare shaders and OpenGL program
                GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();                        //create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);              //add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);            //add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                             //create OpenGL program executables

        this.context = context;
        this.textureID = loadTexture(this.context, textureID);      //load texture/bitmap
    }

    /*-----------------Draw function-------------*/
    //mvp = Model-View-Projection matrix
    //coordinates in openGL. coords should already be converted to floatbuffer
    public void draw(float[] mvpMatrix, FloatBuffer coords) {
        vertexBuffer = coords;                                                          //position of quadrangle relative to GL coordinates
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, this.textureID);                     //Bind a texture ID so GL knows what texture to use
        GLES20.glUseProgram(mProgram);                                                  //use the program generated in the constructor
        int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");        //get position handler from the program
        GLES20.glEnableVertexAttribArray(mPositionHandle);                              //set GL to use vertex attrib array
        GLES20.glVertexAttribPointer(                                                   //prepare quadrangle coordinates to GL
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                0, vertexBuffer);

        int mTexCoordLoc = GLES20.glGetAttribLocation(mProgram, "a_texCoord" );         //prepare texture coordinates here
        GLES20.glEnableVertexAttribArray ( mTexCoordLoc );
        GLES20.glVertexAttribPointer ( mTexCoordLoc, 2, GLES20.GL_FLOAT,
                false,
                0, uvBuffer);
        int mtrxhandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");           //get mvp handler from the program
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, mvpMatrix, 0);                  //use the matrix given as reference before rendering
        int mSamplerLoc = GLES20.glGetUniformLocation (mProgram, "s_texture" );         //get sampler location. probably can be removed
        GLES20.glUniform1i ( mSamplerLoc, 0);                                           //no idea what this is. probably can be removed
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,                    //render elements after preparing data.
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);                             //disable used vertex arrays to avoid unwanted reusage
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);
    }

    /*--------------Load bitmap to GL--------------*/
    public int loadTexture(Context context, int resourceId)
    {
        int[] textureHandle = new int[1];                                               //initialize a texture handler
        GLES20.glGenTextures(1, textureHandle, 0);                                      //use the texture handler to point to last place in GL memory

        if (textureHandle[0] != 0)                                                      //if given memory index has value, dont continue
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();                                  //generate a defalt bitmap factory options
            options.inScaled = false;                                                                           //dont scale bitmap
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);    //decode bitmap from resources

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }
        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }

    public static int loadShader(int type, String shaderCode){              //load shader program for GL used. a shader is a seperate program used by the GPU for rendering

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}