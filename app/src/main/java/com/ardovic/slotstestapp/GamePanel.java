package com.ardovic.slotstestapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    MainThread thread;
    ArrayList<Overlay> overlays;
    Handler handler;

    boolean rolling;

    int frameCount = -1;

    public GamePanel(Context context, Handler handler) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        this.handler = handler;

        overlays = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            overlays.add(new Overlay(StaticLoader.OVER_OFFSET + (i * StaticLoader.OVER_WIDTH), StaticLoader.OVER_OFFSET, 10 + i));
        }

    }

    public void roll() {
        if(overlays.get(0).frameCount == -1 &&
                overlays.get(1).frameCount == -1 &&
                overlays.get(2).frameCount == -1 &&
                overlays.get(3).frameCount == -1) {
            frameCount = 0;
            rolling = true;
        }
    }

    public void update() {
        for (Overlay overlay : overlays) {
            overlay.update();
        }

        if(frameCount != -1) {
            frameCount++;
            if(frameCount > 0) {
                overlays.get(0).spin();
            }
            if(frameCount > 10) {
                overlays.get(1).spin();
            }
            if(frameCount > 20) {
                overlays.get(2).spin();
            }
            if(frameCount > 30) {
                overlays.get(3).spin();
                frameCount = -1;
            }
        }

        if(rolling && overlays.get(0).frameCount == -1 &&
                overlays.get(1).frameCount == -1 &&
                overlays.get(2).frameCount == -1 &&
                overlays.get(3).frameCount == -1) {
            rolling = false;
            Message message = handler.obtainMessage();
            message.sendToTarget();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(0, PorterDuff.Mode.CLEAR);


        for (Overlay overlay : overlays) {
            overlay.draw(canvas);
        }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 100) {
            try {
                counter++;
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}