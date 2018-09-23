package com.wswright.clicker.loop;

import com.wswright.clicker.mouse.MouseGetter;
import com.wswright.clicker.screen.ScreenRect;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartStopLoop implements ILoop{
    private List<Integer> hooks = new ArrayList<>();
    private Map<Integer, Boolean> mapPressing = new HashMap<>();
    private ScreenRect rect = new ScreenRect();
    private MouseGetter mouseGetter = new MouseGetter();


    public StartStopLoop() {
        hooks.add(3667);    //Mouse Button
    }

    @Override
    public List<Integer> getHooks() {
        return hooks;
    }

    @Override
    public void keyTyped(Integer i) {
        System.out.println(String.format("SSLOOP: You Typed %d", i));
    }

    @Override
    public void keyPressed(Integer i) {
        if(mapPressing.containsKey(i))
            if(mapPressing.get(i))
                System.out.println(String.format("Still holding %d", i));
            else {
                System.out.println(String.format("SSLOOP: You Pressed %d", i));
                mapPressing.replace(i, true);
                start();
            }
        else {
            System.out.println(String.format("SSLOOP: You Pressed %d", i));
            start();
            mapPressing.put(i, true);
        }
    }

    @Override
    public void keyReleased(Integer i) {
        if(mapPressing.containsKey(i)) {
            mapPressing.replace(i, false);
        } else
            mapPressing.put(i, false);
        stop();
        System.out.println(String.format("SSLOOP: You Released %d", i));
    }

    private void start() {
        rect = new ScreenRect();
        Point p = mouseGetter.getMousePoint();
        rect.firstPoint(p.x, p.y);
    }

    private void stop() {
        Point p = mouseGetter.getMousePoint();
        rect.secondPoint(p.x, p.y);
        System.out.println(String.format("RECTANGLE: %s", rect));
    }
}
