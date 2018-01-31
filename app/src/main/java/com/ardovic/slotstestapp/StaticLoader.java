package com.ardovic.slotstestapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class StaticLoader {

    // Game settings
    public static int FPS = 60;

    public static int SCREEN_HEIGHT, SCREEN_WIDTH, OVER_HEIGHT, OVER_WIDTH, OVER_OFFSET;
    public static float UNIT;
    public static Bitmap OVER, S_1, S_2, S_3, S_4, COVER;

    public StaticLoader(Resources res, int side1, int side2) {

        if (side2 > side1) {
            SCREEN_WIDTH = side1;
            SCREEN_HEIGHT = side2;
        } else {
            SCREEN_WIDTH = side2;
            SCREEN_HEIGHT = side1;
        }

        UNIT = SCREEN_WIDTH / 1000.0f;

        /*
        Bitmap sizes - OVER: 190*570, ITEM: 100*100
        Assume SCREEN_WIDTH = 1000 units
        4 OVERS - 190 * 4 (760) units - 240 units left (120 from each side)
        */

        OVER_HEIGHT = (int) (UNIT * 570);
        OVER_WIDTH = (int) (UNIT * 190);
        OVER_OFFSET = (int) (UNIT * 120);

        OVER = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.over), OVER_WIDTH, OVER_HEIGHT, true);
        S_1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.s_1), OVER_WIDTH, OVER_WIDTH, true);
        S_2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.s_2), OVER_WIDTH, OVER_WIDTH, true);
        S_3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.s_3), OVER_WIDTH, OVER_WIDTH, true);
        S_4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.s_4), OVER_WIDTH, OVER_WIDTH, true);

        Bitmap bitmap = Bitmap.createBitmap(OVER_WIDTH, OVER_WIDTH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.LTGRAY);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(res.getColor(R.color.colorAccent));
        paint.setAntiAlias(true);
        Rect rectangle = new Rect(0, 0, OVER_WIDTH, OVER_WIDTH);
        canvas.drawRect(rectangle,paint);
        COVER = Bitmap.createScaledBitmap(bitmap, OVER_WIDTH, OVER_WIDTH, true);
    }


}



