package com.wswright.clicker.keyboard;

import com.wswright.clicker.config.Config;
import com.wswright.clicker.mouse.MouseGetter;
import com.wswright.clicker.screen.ScreenRect;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.*;

public class BoxBuilderKeyHook implements IKeyHook {
    private static final int KEY_CODE = Config.KEY_MOUSE_SIDE_BUTTON;
    private ScreenRect rect = new ScreenRect();
    private MouseGetter mouseGetter = new MouseGetter();

    public boolean isReady() {
        if(rect == null)
            return false;
        return rect.isFinished();
    }

    @Override
    public int getKeyCode() {
        return KEY_CODE;
    }

    public ScreenRect getRect() {
        return rect;
    }

    @Override
    public void pressed(NativeKeyEvent e) {
        System.out.println("BUILDING START - " + rect);
        Point p = mouseGetter.getMousePoint();
        rect = new ScreenRect();
        rect.firstPoint(p.x, p.y);
    }

    @Override
    public void holding(NativeKeyEvent e) {
        Point p = mouseGetter.getMouse().getLocation();
        System.out.println(String.format("BUILDING RECTANGLE - AREA: %d px^2", rect.secondPoint(p.x, p.y).area()));
    }

    @Override
    public void released(NativeKeyEvent e) {
        Point p = mouseGetter.getMousePoint();
        rect.secondPoint(p.x, p.y);
        System.out.println("BUILDING FINISH - " + rect);
    }
}
