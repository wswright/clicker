package com.wswright.clicker.screen;

import java.awt.*;
import java.util.Queue;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ScreenRect {
    private static final int RANDOM_SPIN_CUTOFF = 50;
    private int x;
    private int y;
    private int w;//Goes right
    private int h;//Goes up
    private Point p;
    private boolean isFinished = false;
    private Random random = new Random();
    private static boolean ENABLE_EVEN_DISTRIBUTION = true;
    private static boolean ENABLE_PIXEL_PADDING = true;
    private static int PIXEL_PADDING_AMOUNT = 20;
    private static int MAX_QUEUE_SIZE = 10000;
    private static Queue<Point> recentPoints = new ConcurrentLinkedQueue<>();


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
        MAX_QUEUE_SIZE = area() / (PIXEL_PADDING_AMOUNT*2);

        return this;
    }

    private boolean wasRecent(Point p) {
        for(Point recent : recentPoints) {
            if(ENABLE_PIXEL_PADDING) {
                 if(withinPixelsOf(PIXEL_PADDING_AMOUNT, p.x, p.y, recent.x, recent.y))
                     return true;
            } else {
                if(recent.x == p.x && recent.y == p.y)
                    return true;
            }
        }
        return false;
    }

    private boolean withinPixelsOf(int numPix, int x1, int y1, int x2, int y2) {
        return (Math.abs(x1-x2) <= numPix) ? ((Math.abs(y1-y2) <= numPix) ? true : false) : false;
    }



    public int area() {
        if(!isFinished)
            return 0;
        return w * h;
    }

    public Point getRandomPointInArea() {
        if(!isFinished)
            return new Point(x, y); //Use first point (or 0,0) if none exist

        if(ENABLE_EVEN_DISTRIBUTION) {
            if(MAX_QUEUE_SIZE > area())
                MAX_QUEUE_SIZE = area() / 2;
            Point newPoint;
            int loops = 0;
            do {
                int rand_x = getRandomWithBound(w, x);
                int rand_y = getRandomWithBound(h, y);
                newPoint = new Point(rand_x, rand_y);
                if(recentPoints.size() > 1000 && loops % 5 == 0)
                    recentPoints.remove();
                if(loops > 1000)
                    recentPoints.clear();
                loops++;
            } while (wasRecent(newPoint));
            System.out.println(String.format("LoopCounter: %d, QueueSize: %d", loops, recentPoints.size()));
            recentPoints.add(newPoint);
            if(loops > area() / PIXEL_PADDING_AMOUNT)
                for(int i=0; i<random.nextInt(recentPoints.size()/10); i++)
                    recentPoints.remove();//Remove one
            return newPoint;
        } else {
            int rand_x = getRandomWithBound(w, x);
            int rand_y = getRandomWithBound(h, y);
            return new Point(rand_x, rand_y);
        }
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
