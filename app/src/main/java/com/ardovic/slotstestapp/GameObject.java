package com.ardovic.slotstestapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameObject {

    Bitmap bitmap;

    int x, y, dx, dy;

    public void update() {
        x += dx;
        y += dy;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

}
