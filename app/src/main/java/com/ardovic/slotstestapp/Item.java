package com.ardovic.slotstestapp;

public class Item extends GameObject {

    int type;

    public Item(int type, int x, int y) {

        this.type = type;

        this.x = x;
        this.y = y;

        switch (type) {
            case 0:
                bitmap = StaticLoader.S_1;
                break;
            case 1:
                bitmap = StaticLoader.S_2;
                break;
            case 2:
                bitmap = StaticLoader.S_3;
                break;
            case 3:
                bitmap = StaticLoader.S_4;
                break;
        }
    }

}
