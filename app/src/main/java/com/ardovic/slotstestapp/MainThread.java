package com.ardovic.slotstestapp;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    public int mSeconds;
    private final static int FPS = StaticLoader.FPS;
    private GamePanel gamePanel;
    private final SurfaceHolder surfaceHolder;
    private boolean running;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        Canvas canvas;
        int frameCount = 0;
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        long targetTime = 1000 / FPS; // 33ms

        while (running) {
            startTime = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    if(canvas != null) {
                        this.gamePanel.draw(canvas);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            if(waitTime > 0) {
                try {
                    sleep(waitTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS) {
                frameCount = 0;
                totalTime = 0;
                mSeconds++;
            }
        }
    }

    public void setRunning(boolean b) {
        running = b;
    }

}
