package com.ardovic.slotstestapp;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

public class Overlay extends GameObject {

    ArrayList<Item> items;
    boolean stopping;
    int frameCount = -1, itemsNumber;

    public Overlay(int x, int y, int itemsNumber) {

        this.itemsNumber = itemsNumber;

        this.bitmap = StaticLoader.OVER;

        this.x = x;
        this.y = y;

        items = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < itemsNumber; i++) {
            if (i == 0) {
                items.add(new Item(rand.nextInt(4), this.x, this.y - StaticLoader.OVER_WIDTH));
            } else if (i == (itemsNumber - 1)) {
                while (true) {
                    int j = rand.nextInt(4);
                    if (j != items.get(0).type) {
                        items.add(new Item(j, this.x, this.y + ((i - 1) * StaticLoader.OVER_WIDTH)));
                        break;
                    }
                }
            } else {
                while (true) {
                    int j = rand.nextInt(4);
                    if (j != items.get(i - 1).type) {
                        items.add(new Item(j, this.x, this.y + ((i - 1) * StaticLoader.OVER_WIDTH)));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        super.update();


        if (frameCount != -1) {
            frameCount++;
            if (frameCount < (StaticLoader.FPS)) {
                addAcceleration(1);
            } else if (frameCount > (StaticLoader.FPS * 3) && frameCount < (StaticLoader.FPS * 4)) {
                addAcceleration(-1);
            } else if (frameCount >= (StaticLoader.FPS * 4)) {
                stop();
            }
        }


        items.get(0).update();
        for (int i = 1; i < items.size(); i++) {
            items.get(i).dy = items.get(0).dy;
            items.get(i).update();
        }

        if (stopping) {
            int distance = ((items.get(0).y - StaticLoader.OVER_OFFSET) % StaticLoader.OVER_WIDTH);
            if (Math.abs(distance) < 3) {
                items.get(0).dy = 0;
                // DONE
                stopping = false;
                frameCount = -1;
            }
        }

        checkPosition();
    }

    public void spin() {
        if (frameCount == -1) {
            frameCount = 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {


        for (Item item : items) {
            if (item.y > this.y - StaticLoader.OVER_WIDTH && item.y < this.y + StaticLoader.OVER_HEIGHT) {
                item.draw(canvas);
            }
        }
        super.draw(canvas);
        canvas.drawBitmap(StaticLoader.COVER, this.x, this.y - StaticLoader.OVER_WIDTH, null);
        canvas.drawBitmap(StaticLoader.COVER, this.x, this.y + StaticLoader.OVER_HEIGHT, null);
    }

    public void addAcceleration(int dy) {
        items.get(0).dy += dy;
    }

    public void stop() {

        int distance = (((items.get(0).y - StaticLoader.OVER_OFFSET) % StaticLoader.OVER_WIDTH));

        if (distance != 0) {
            items.get(0).dy = distance < (StaticLoader.OVER_WIDTH / 2) ? -1 : 1;
        }
        stopping = true;
    }

    private void checkPosition() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).y >= (this.y + StaticLoader.OVER_HEIGHT + (itemsNumber - 4) * StaticLoader.OVER_WIDTH)) {
                if (i == itemsNumber - 1) {
                    items.get(itemsNumber - 1).y = items.get(0).y - StaticLoader.OVER_WIDTH;
                } else {
                    items.get(i).y = items.get(i + 1).y - StaticLoader.OVER_WIDTH;
                }
            }
        }
    }
}
