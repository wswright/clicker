package com.wswright.clicker.screen;

import java.awt.*;
import java.util.Random;

public class ScreenRect {
    private static final int RANDOM_SPIN_CUTOFF = 50;
    private int x;
    private int y;
    private int w;//Goes right
    private int h;//Goes up
    private Point p;
    private boolean isFinished = false;
    private Random random = new Random();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public ScreenRect() {

    }

    public ScreenRect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        if(w < 0 || h < 0) throw new IllegalArgumentException("Invalid Width or Height");
        isFinished = true;
    }

    public ScreenRect(Point p1, Point p2) {
        firstPoint(p1.x, p1.y);
        secondPoint(p2.x, p2.y);
        isFinished = true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public ScreenRect firstPoint(int x, int y) {
        random.setSeed(Integer.valueOf(x*y).hashCode());
        this.p = new Point(x,y);
        isFinished = false;
        return this;
    }

    public ScreenRect secondPoint(int x, int y) {
        Point p1 = p;
        Point p2 = new Point(x,y);

        this.x = Math.min(p1.x, p2.x);
        this.y = Math.min(p1.y, p2.y);
        this.w = Math.abs(p1.x - p2.x);
        this.h = Math.abs(p1.y - p2.y);
        isFinished = true;

        return this;
    }



    public int area() {
        if(!isFinished)
            return 0;
        return w * h;
    }

    public Point getRandomPointInArea() {
        if(!isFinished)
            return new Point(x, y); //Use first point (or 0,0) if none exist
        int rand_x = getRandomWithBound(w, x);
        int rand_y = getRandomWithBound(h, y);
        return new Point(rand_x, rand_y);
    }

    private int getRandomWithBound(int bound, int coord) {
        int rand = Integer.MAX_VALUE;
        int times = 0;
        while(rand > (coord+bound)) {
            rand = random.nextInt(bound) + coord;
            times++;
            if(times > RANDOM_SPIN_CUTOFF)
                return coord;
        }
        return rand;
    }

    @Override
    public String toString() {
        return "ScreenRect{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                ", isFinished=" + isFinished +
                ", area=" + area() +
                '}';
    }
}
